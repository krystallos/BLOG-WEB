package com.example.menu.enity;

import com.example.util.enityUtil.userEnity;

/**
 * 角色实体
 */
public class RoleEnity extends userEnity {

    private String roleName;
    private String isFlag;
    private String isDefule;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag;
    }

    public String getIsDefule() {
        return isDefule;
    }

    public void setIsDefule(String isDefule) {
        this.isDefule = isDefule;
    }
}
