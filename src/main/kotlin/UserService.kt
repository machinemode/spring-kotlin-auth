package com.machinemode.auth

import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service

@Service
class UserService : OAuth2UserService<OidcUserRequest, OidcUser> {
    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        return User(userRequest, AuthorityUtils.createAuthorityList("ROLE_USER"))
    }
}
