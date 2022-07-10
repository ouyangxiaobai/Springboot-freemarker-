package com.bysj.base.entity.admin;


public enum RoleType {
    ELECTRICIAN(0,"维修工"), //
    STUDENT(1,"学生"),
    SUPER_ADMINISTRATOR(2,"超级管理员"),
    GENERAL_MANAGER(3,"普通管理员"),
    TEST_ROLE(4,"测试角色");

    private int code;

    private String name;


    RoleType(int code, String name) {
        this.name = name;
    }

    public static String getByCode(int code) {
        for (RoleType value : RoleType.values()) {
            if(code==value.getCode()){
                return value.getName();
            }
        }

        return  null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
