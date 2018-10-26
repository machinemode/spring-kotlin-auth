package com.machinemode.auth.security

import com.machinemode.auth.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.ClassPathResource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory


@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig: AuthorizationServerConfigurerAdapter() {

    @Autowired
    private lateinit var properties: ApplicationProperties

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    private val tokenConverter: TokenConverter = TokenConverter()

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient(properties.auth.clientId)
                .authorizedGrantTypes(*properties.auth.grantTypes)
                .scopes(*properties.auth.scopes)
                .redirectUris(*properties.auth.redirectUris)
                .autoApprove(true)
                .accessTokenValiditySeconds(properties.auth.accessTokenValiditySeconds)
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        configureAccessTokenConverter()

        endpoints.tokenStore(JwtTokenStore(tokenConverter))
                .tokenEnhancer(tokenConverter)
                .authenticationManager(authenticationManager)
    }

    private fun configureAccessTokenConverter() {
        val keyStoreKeyFactory = KeyStoreKeyFactory(
                ClassPathResource(properties.keystore.file),
                properties.keystore.password.toCharArray())
        tokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair(properties.keystore.name))
    }
}
