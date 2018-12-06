package com.jy.gateway.social.weixin.config;

import com.jy.gateway.properties.SecurityProperties;
import com.jy.gateway.properties.WeixinProperties;
import com.jy.gateway.social.TiHomConnectView;
import com.jy.gateway.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * @author TiHom
 */
@Configuration
@ConditionalOnProperty(prefix = "simple.security.social.weixin",name = "app-id")
public class WeixinAutoConfiguration extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
        return new WeixinConnectionFactory(weixinConfig.getProviderId(),
                weixinConfig.getAppId(),weixinConfig.getAppSecret());
    }

    /**
     * 绑定的视图
     */
    @Bean({"connect/wexinConnected","connect/weixinConnect"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View weixinConnectedView(){
        return new TiHomConnectView();
    }


}
