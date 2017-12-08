package com.wanglu.movcat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    //主键
    @Id
    @GeneratedValue
    private Integer id;

    //名称
    private String name;

    //头像
    private String imgUrl;

    //手机号
    private String tel;

    //邮箱
    private String email;

    //密码
    private String password;

    //标签
    private String tag;

    //性别
    private String gender;

    //省份
    private String province;

    //城市
    private String city;

    //openId
    private String openId;

    public User() {
    }

    public User(String name, String imgUrl, String gender, String province, String city, String openId) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.gender = gender;
        this.province = province;
        this.city = city;
        this.openId = openId;
    }

    public User(String name, String tel, String email, String password) {
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", tag='" + tag + '\'' +
                ", gender='" + gender + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }
}
