package me.choicore.likeapuppy.identityprovider.authentication

import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.stereotype.Service

@Service
class OidcUserInfoService {

    fun loadUser(username: String): OidcUserInfo {
        return OidcUserInfo(loadUserAttributes(username))
    }

    private fun loadUserAttributes(username: String): Map<String, Any> {
        TODO()
    }
}
