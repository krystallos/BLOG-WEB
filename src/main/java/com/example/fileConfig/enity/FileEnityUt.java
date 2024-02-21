package com.example.fileConfig.enity;

import java.util.List;

public class FileEnityUt {

    private String pathName;
    private String fileName;
    private String fileType;
    private String isHas;
    private String filePath;
    private List<FileUtil> listPath;

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getIsHas() {
        return isHas;
    }

    public void setIsHas(String isHas) {
        this.isHas = isHas;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<FileUtil> getListPath() {
        return listPath;
    }

    public void setListPath(List<FileUtil> listPath) {
        this.listPath = listPath;
    }
}
