package com.example.postApi.enity;

import java.util.List;

public class RstChildPostApi {

    private String ids;
    private String projectName;
    private String fileName;
    private String fileCreateDate;
    private List<RstPostApiVo> rstPostApi;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileCreateDate() {
        return fileCreateDate;
    }

    public void setFileCreateDate(String fileCreateDate) {
        this.fileCreateDate = fileCreateDate;
    }

    public List<RstPostApiVo> getRstPostApi() {
        return rstPostApi;
    }

    public void setRstPostApi(List<RstPostApiVo> rstPostApi) {
        this.rstPostApi = rstPostApi;
    }
}
