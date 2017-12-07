package com.wanglu.movcat.model;

//验证码
public class IdentifyingCode {

    private String tel;

    private String code;

    private String createTime;

    public IdentifyingCode() {
    }

    public IdentifyingCode(String tel, String code, String createTime) {
        this.tel = tel;
        this.code = code;
        this.createTime = createTime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "IdentifyingCode{" +
                "tel='" + tel + '\'' +
                ", code='" + code + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
