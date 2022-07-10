package com.bysj.base.entity.admin;


public enum EmpType {
    electrician(0,"电工"), //   0
    plumber(1,"管道工"), //      1
    heating(2,"暖气工"); //      2

    private int code;

    private String name;


    EmpType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getByCode(int code) {

        for (EmpType value : EmpType.values()) {
            if(code==value.getCode()){
                return value.getName();
            }
        }

        return  null;
    }

    public static EmpType getEmpByCode(int code) {

        for (EmpType value : EmpType.values()) {
            if(code==value.getCode()){
                return value;
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
