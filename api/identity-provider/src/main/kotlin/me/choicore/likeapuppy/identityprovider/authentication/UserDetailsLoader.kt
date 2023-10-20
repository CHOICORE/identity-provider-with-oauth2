package me.choicore.likeapuppy.identityprovider.authentication

import me.choicore.likeapuppy.core.domain.user.model.GrantedAuthority
import me.choicore.likeapuppy.core.domain.user.model.aggregate.Account
import me.choicore.likeapuppy.core.domain.user.service.internal.AccountQueryProcessor
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserDetailsLoader(
    private val accountQueryProcessor: AccountQueryProcessor,
) : UserDetailsService {
    /**
     * Loads a user from the database using the provided identifier.
     *
     * @param username The email or mobile number used to identify the user to be loaded.
     * @return A [UserDetails] object that corresponds to the given identifier.
     * @throws UsernameNotFoundException if the user could not be found.
     */
    @Transactional(readOnly = true)
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        try {
            val account: Account = accountQueryProcessor.findUserAccountByUsername(username)
            return convertToUserDetails(account)
        } catch (e: IllegalStateException) {
            throw UsernameNotFoundException(e.message, e)
        }
    }

    private fun convertToUserDetails(account: Account): User =
        User(
            account.username,
            account.password,
            account.isEnabled,
            account.isAccountNonExpired,
            account.isCredentialsNonExpired,
            account.isAccountNonLocked,
            convertToSimpleGrantedAuthorities(account.authorities),
        )

    private fun convertToSimpleGrantedAuthorities(grantedAuthorities: List<GrantedAuthority>): MutableSet<SimpleGrantedAuthority> {
        return grantedAuthorities.mapTo(mutableSetOf()) { grantedAuthority: GrantedAuthority ->
            SimpleGrantedAuthority(grantedAuthority.authority.name)
        }
    }
}
