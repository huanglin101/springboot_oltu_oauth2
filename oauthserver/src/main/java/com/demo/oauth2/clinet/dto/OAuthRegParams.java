package com.demo.oauth2.clinet.dto;

import com.demo.oauth2.server.dto.OauthParam;

public class OAuthRegParams extends OauthParam {

    private String registrationType;
    private String name = "OAuth V2.0 Demo Application";
    private String url = "http://localhost:8080";
    private String description = "Demo Application of the OAuth V2.0 Protocol";
    private String icon = "http://localhost:8080/demo.png";
    private String registrationEndpoint;

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRegistrationEndpoint() {
        return registrationEndpoint;
    }

    public void setRegistrationEndpoint(String registrationEndpoint) {
        this.registrationEndpoint = registrationEndpoint;
    }
}
