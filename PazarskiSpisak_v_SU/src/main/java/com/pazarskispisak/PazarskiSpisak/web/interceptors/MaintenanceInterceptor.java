package com.pazarskispisak.PazarskiSpisak.web.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalTime;

@Component
public class MaintenanceInterceptor implements HandlerInterceptor {

    private static final LocalTime MAINTENANCE_START_TIME = LocalTime.of(3, 59);
    private static final LocalTime MAINTENANCE_END_TIME = LocalTime.of(4, 02);


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        System.out.println("I am in the preHandle ");

        LocalTime now = LocalTime.now();

        if (now.isAfter(MAINTENANCE_START_TIME) && now.isBefore(MAINTENANCE_END_TIME)) {
            //maintenance time, send to maintenance page
            String requestURL = request.getRequestURL().toString();

            if (!requestURL.endsWith("/maintenance")) {
                response.sendRedirect("/maintenance");
                return false;
            }
            return true;

        } else {
            return true;
        }

    }

}
