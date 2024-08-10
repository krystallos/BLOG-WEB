package com.example.postApi.enity;

import com.example.util.enityUtil.userEnity;

import java.util.List;

/**
 * 接口文档-项目层
 */
public class RstPostProject extends userEnity {

    private String projectName;
    private String projectPid;
    private String isFile;
    private String createName;
    private Boolean hasChildren;
    private Integer hasCountFile;
    private String rstKey;

    private List<RstPostProject> children;

    public String getRstKey() {
        return rstKey;
    }

    public void setRstKey(String rstKey) {
        this.rstKey = rstKey;
    }

    public Integer getHasCountFile() {
        return hasCountFile;
    }

    public void setHasCountFile(Integer hasCountFile) {
        this.hasCountFile = hasCountFile;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public List<RstPostProject> getChildren() {
        return children;
    }

    public void setChildren(List<RstPostProject> children) {
        this.children = children;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPid() {
        return projectPid;
    }

    public void setProjectPid(String projectPid) {
        this.projectPid = projectPid;
    }

    public String getIsFile() {
        return isFile;
    }

    public void setIsFile(String isFile) {
        this.isFile = isFile;
    }
}
