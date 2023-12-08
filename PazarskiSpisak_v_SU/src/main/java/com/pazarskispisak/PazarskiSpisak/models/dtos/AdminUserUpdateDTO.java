//package com.pazarskispisak.PazarskiSpisak.models.dtos;
//
//import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
//import com.pazarskispisak.PazarskiSpisak.validations.ValueOfEnum;
//
//import java.util.List;
//
//public class AdminUserUpdateDTO {
//
//    private Long userId;
//    @ValueOfEnum(enumClass = UserRoleEnum.class, message = "Невалидна роля")
//    private List<String> userRoleEnumList;
//
//    public AdminUserUpdateDTO(){}
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public List<String> getUserRoleEnumList() {
//        return userRoleEnumList;
//    }
//
//    public void setUserRoleEnumList(List<String> userRoleEnumList) {
//        this.userRoleEnumList = userRoleEnumList;
//    }
//}
