//package com.pazarskispisak.PazarskiSpisak.userTempForSessionTracking;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.annotation.SessionScope;
//


//Целия измислен CurrentUser изчезва, тъй като Spring Security поема следенето на текущия потребител
//@Component
//@SessionScope
//public class CurrentUser {
//
//    private String displayNickname;;
//    private boolean isLoggedIn;
//
//    public String getDisplayNickname() {
//        return displayNickname;
//    }
//
//    public CurrentUser setDisplayNickname(String displayNickname) {
//        this.displayNickname = displayNickname;
//        return this;
//    }
//
//    public boolean isLoggedIn() {
//        return isLoggedIn;
//    }
//
//    public CurrentUser setLoggedIn(boolean loggedIn) {
//        this.isLoggedIn = loggedIn;
//        return this;
//    }
//
//    public void clearCurrentUser(){
//        isLoggedIn = false;
//        displayNickname = null;
//    }
//
//    public boolean isGuest(){
//        return !isLoggedIn;
//    }
//
//}
