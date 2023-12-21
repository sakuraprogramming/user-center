package com.lql.usercenter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wx")
@Data
public class WxConfig {
    private String appId;
    private String appSecret;
    private String server;
    private String qrCodeUrl;
    private String tokenUrl;
    private String openIdUrl;
    private String userInfoUrl;
}