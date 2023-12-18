package com.hzj.wiki.exception;

public enum BusinessExceptionCode {
    USER_LOGIN_NAME_EXITS("用户名已存在"),;
    private String desc;
    BusinessExceptionCode(String desc){this.desc = desc;}
    public String getDesc(){return desc;}
    public void setDesc(String desc){this.desc = desc;}
}
