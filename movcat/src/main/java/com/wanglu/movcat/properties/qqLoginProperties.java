package com.wanglu.movcat.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by wangl on 2017/10/12 0012.
 */
@Component
@ConfigurationProperties(prefix = "qq.login")
public class qqLoginProperties {

    private String appID;

    private String appKEY;

    private String redirectURI;

    private String accessTokenURL;

    private String authorizeURL;

    private String responseType;

    private String state;

    private String grantType;

    private String getOpenIDURL;

    private String getUserInfoURL;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppKEY() {
        return appKEY;
    }

    public void setAppKEY(String appKEY) {
        this.appKEY = appKEY;
    }

    public String getRedirectURI() {
        return redirectURI;
    }

    public void setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
    }

    public String getAccessTokenURL() {
        return accessTokenURL;
    }

    public void setAccessTokenURL(String accessTokenURL) {
        this.accessTokenURL = accessTokenURL;
    }

    public String getAuthorizeURL() {
        return authorizeURL;
    }

    public void setAuthorizeURL(String authorizeURL) {
        this.authorizeURL = authorizeURL;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getGetOpenIDURL() {
        return getOpenIDURL;
    }

    public void setGetOpenIDURL(String getOpenIDURL) {
        this.getOpenIDURL = getOpenIDURL;
    }

    public String getGetUserInfoURL() {
        return getUserInfoURL;
    }

    public void setGetUserInfoURL(String getUserInfoURL) {
        this.getUserInfoURL = getUserInfoURL;
    }
}
