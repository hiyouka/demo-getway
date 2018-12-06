package com.jy.gateway.social.weixin.api;

/**
 * @author TiHom
 */
public interface Weixin {
    WeixinUserInfo getUserInfo(String openId);
}
