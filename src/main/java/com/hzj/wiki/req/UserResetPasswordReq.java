package com.hzj.wiki.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

public class UserResetPasswordReq implements Serializable {

    private Long id;

    @Override
    public String toString() {
        return "UserResetPasswordReq{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "【密码】不能为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$",message = "【密码】至少包含数字和英文，长度6-32")
    private String password;

    private static final long serialVersionUID = 1L;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserResetPasswordReq other = (UserResetPasswordReq) that;
        return  (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        return result;
    }

}