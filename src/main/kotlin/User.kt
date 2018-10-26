package com.machinemode.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser


data class User(private val userRequest: OidcUserRequest,
                private val authorities: MutableCollection<out GrantedAuthority>): OidcUser {

    override fun getIdToken(): OidcIdToken = userRequest.idToken

    override fun getClaims(): MutableMap<String, Any> = userRequest.idToken.claims

    override fun getUserInfo() = OidcUserInfo(mutableMapOf())

    override fun getName(): String = userRequest.idToken.email

    override fun getAttributes(): MutableMap<String, Any> {
        return mutableMapOf()
    }

    override fun getAuthorities() = authorities
}
