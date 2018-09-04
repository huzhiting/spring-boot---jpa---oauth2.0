package com.uqiauto.api.config;


import com.uqiauto.api.secret.dao.AppSystemRepository;
import com.uqiauto.api.secret.model.AppSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

@Component
public class MyClientDetailsService  implements ClientDetailsService {

    private static final String GOODS_RESOURCE_ID = "goods";

    @Autowired
    private AppSystemRepository appSystemRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        AppSystem appSystem = appSystemRepository.getAppSystemByAppKey(clientId).get();
        BaseClientDetails details = new BaseClientDetails(appSystem.getAppKey(),GOODS_RESOURCE_ID,"select","client_credentials,refresh_token","client");
        details.setClientSecret(appSystem.getAppSecret());
        return details;
    }
}
