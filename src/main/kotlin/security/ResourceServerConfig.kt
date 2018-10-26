package com.machinemode.auth.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Configuration
@EnableResourceServer
@RestController
class ResourceServerConfiguration: ResourceServerConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.cors()
                .and().antMatcher("/userinfo").authorizeRequests().anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    // For a client to configure security.oauth2.resource.user-info-uri
    @RequestMapping("/userinfo")
    fun user(principal: OAuth2Authentication) = principal.userAuthentication
}
