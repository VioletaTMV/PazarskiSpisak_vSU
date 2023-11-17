package com.pazarskispisak.PazarskiSpisak.models.entities;

import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "display_nickname", unique = true, nullable = false)
    private String displayNickname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_user_roles",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<UserRoleEntity> userRoles;

    @Column(name = "registered_on", nullable = false)
    @CreationTimestamp
    private LocalDate registeredOn;

    @Column(name = "last_time_logged_in")
    private LocalDateTime lastTimeLoggedIn;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "legacy_id")
    private Short legacyId;

    @Column(name = "legacy_user_logname")
    private String legacyUserLogname;

    @OneToOne
    @JoinColumn(name = "shop_list_from_recipes_id", referencedColumnName = "id")
    private ShoppingListFromRecipes shoppingListFromRecipes;

    public User() {
        this.registeredOn = LocalDate.now();
        this.userRoles = new LinkedHashSet<>();
        this.isDeleted = false;
//        this.chosenRecipesForCookWithDesiredServingsAmountMap = new LinkedHashMap<>();
//        this.shoppingListMap = new LinkedHashMap<>(new HashMap<>());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    public LocalDateTime getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(LocalDateTime lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getDisplayNickname() {
        return displayNickname;
    }

    public void setDisplayNickname(String displayNickname) {
        this.displayNickname = displayNickname;
    }

    public Short getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(Short legacyId) {
        this.legacyId = legacyId;
    }

    public String getLegacyUserLogname() {
        return legacyUserLogname;
    }

    public void setLegacyUserLogname(String legacyUserLogname) {
        this.legacyUserLogname = legacyUserLogname;
    }

    public Set<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
    }

    public ShoppingListFromRecipes getShoppingListFromRecipes() {
        return shoppingListFromRecipes;
    }

    public User setShoppingListFromRecipes(ShoppingListFromRecipes shoppingListFromRecipes) {
        this.shoppingListFromRecipes = shoppingListFromRecipes;
        return this;
    }
}
