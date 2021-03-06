package com.api.response;

/**
 * @Author admin
 * @Date 2019/11/23 21:37
 * @Description 状态码枚举
 */
public enum StatusCode {

    Success(200, "成功"),
    Fail(-1, "失败"),
    InvalidParams(10010, "非法参数");

    private Integer code;
    private String msg;

    StatusCode() {
    }

    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
