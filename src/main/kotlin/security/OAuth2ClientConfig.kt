package com.machinemode.auth.security

import com.machinemode.auth.ApplicationProperties
import com.machinemode.auth.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

const val POST_LOGOUT_REDIRECT_PARAM = "post_logout_redirect_uri"

@Configuration
@EnableOAuth2Client
@EnableWebSecurity(debug = false)
class OAuth2ClientConfig: WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var properties: ApplicationProperties

    @Autowired
    private lateinit var userService: UserService

    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    private val logoutSuccessHandler = LogoutSuccessHandler { request, response, authentication ->
        response.sendRedirect(request.getParameter(POST_LOGOUT_REDIRECT_PARAM) ?: "/")
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

    override fun configure(http: HttpSecurity) {
        http.cors()
                .and().csrf().disable()
                .headers().frameOptions().disable()
                .and().logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .and().authorizeRequests()
                .antMatchers(properties.auth.loginPage, "/actuator/health").permitAll()
                .anyRequest().authenticated()
                .and().oauth2Login()
                .loginPage(properties.auth.loginPage)
                .userInfoEndpoint()
                .oidcUserService(userService)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }
}
