package com.example.util;

public enum ApiResultEnum {

    SUCCESS(200, "操作成功"),
    LOGIN(200, "登入成功"),
    FAIL(205, "没有数据变动"),
    NOT_FOUND(404, "not found"),
    UPDATA_TOKRN(501, "token更新成功"),
    ERROR_TOKRN(508, "非法的token"),
    OVER_TOKEN(514, "过期的token"),
    ERR(500, "未知错误，请稍后再试"),
    TOKEN_INVALID(230001, "用户信息验证失败"),
    THROWABLE_PARAM(205, "参数验证失败"),
    UNEXPECTED_RESULT(205, "获取数据异常"),
    DUPLICATION_OF_DATA(205, "记录已存在"),
    HTTP_READ_ERROR(205, "HTTP JSON数据读取失败"),
    NOT_FLAG(205, "无效树状图标识"),
    INVALID_FLAG(205, "无效类型标识"),
    NOT_EXIST_EXTERNALID(205, "系统唯一性标识不存在"),
    RELATION_EXIST(205, "关联关系还未清空，不可删除"),
    NOT_NULL_ROLE(205, "菜单所属角色不为空"),
    NOT_NULL_MENU(205, "存在下级菜单，无法删除"),
    OK(0, "执行成功"),
    TIME_OUT(-1, "连接超时"),
    SIGN_ERROR(-2, "签名错误"),
    DATA_ERROR(-3, "应用参数错误"),
    SYS_ERROR(-4, "执行异常"),
    NOT_FIND_THREE(-5, "找不到第三方信息"),
    DECRYPE_ERROR(-6, "数据解密失败"),
    NOT_FIND_DATA(-7, "找不到对应记录");

    public Integer code;
    public String message;

    private ApiResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
