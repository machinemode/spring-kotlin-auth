package com.machinemode.auth.security

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter


class TokenConverter : JwtAccessTokenConverter() {

    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        val token = accessToken as DefaultOAuth2AccessToken

        @Suppress("UsePropertyAccessSyntax")
        token.setAdditionalInformation(mutableMapOf<String, Any>("scope" to "openid"))
        return super.enhance(token, authentication)
    }
}
