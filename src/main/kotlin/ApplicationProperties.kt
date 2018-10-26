package com.machinemode.auth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app")
class ApplicationProperties {
    var auth = Auth()
    var keystore = Keystore()

    inner class Auth {
        lateinit var clientId: String
        lateinit var scopes: Array<out String>
        lateinit var grantTypes: Array<out String>
        lateinit var redirectUris: Array<out String>
        var accessTokenValiditySeconds = 0
        var loginPage: String = "/index.html"
    }

    inner class Keystore {
        lateinit var file: String
        lateinit var name: String
        lateinit var password: String
    }
}
