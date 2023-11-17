package com.pazarskispisak.PazarskiSpisak.config;

import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
import com.pazarskispisak.PazarskiSpisak.repository.UserRepository;
import com.pazarskispisak.PazarskiSpisak.service.servicesImpl.PazarskiSpisakUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private String rememberMeKey;

    public SecurityConfiguration(@Value("${PazarskiSpisak.remember.me.key}") String rememberMeKey) {
        this.rememberMeKey = rememberMeKey;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//Configuration goes here

      return httpSecurity.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/user/register", "/user/login", "/user/login-error").permitAll()
                        .requestMatchers("/recipes-by-category").permitAll()
//                        само за регистрирани потребители ще могат да добавят в списък за сега
                        .requestMatchers(HttpMethod.GET, "/list/recipes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/recipe").permitAll()
                        .requestMatchers("/error").permitAll()
                        //да изтрия долния ред когато го изтествам в постман така че Products да си е достъпно само за логнати юзъри
//                        .requestMatchers("/products/**").permitAll()
                        .requestMatchers("/admin/**").hasRole(UserRoleEnum.ADMIN.name())
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin -> {
                    formLogin
                            .loginPage("/user/login")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/")
                            .failureForwardUrl("/user/login-error");
                }
        ).logout(
                logout -> {
                    logout
                            .logoutUrl("/user/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                }
        ).rememberMe(rememberMe -> {
                    rememberMe
                            .key(rememberMeKey)
                            .rememberMeParameter("rememberme")
                            .rememberMeCookieName("rememberme");
                }
        ).build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new PazarskiSpisakUserDetailsServiceImpl(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

}
