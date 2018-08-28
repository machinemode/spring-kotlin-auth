package com.machinemode.uaa

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Configuration
@EnableResourceServer
@RestController
class ResourceServerConfiguration: ResourceServerConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.antMatcher("/userinfo").authorizeRequests().anyRequest().authenticated()
                .and().antMatcher("/").authorizeRequests().anyRequest().permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    // For a client to configure security.oauth2.resource.user-info-uri
    @RequestMapping("/userinfo")
    fun user(principal: Principal) = mapOf("name" to principal.name)
}
