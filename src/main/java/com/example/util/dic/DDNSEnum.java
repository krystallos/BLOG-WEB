package com.example.util.dic;

public enum DDNSEnum {

    EDITDOMAIN("edit_domain"),
    DELDOMAIN("del_domain"),
    ADDDOMAIN("add_domain"),
    SELECTDOMAIN("select_domain");

    public String message;

    private DDNSEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
