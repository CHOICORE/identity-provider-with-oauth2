package me.choicore.likeapuppy.identityprovider

import me.choicore.likeapuppy.core.common.Encryptor
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

class PasswordEncoderTests {

    @Test
    fun encodeTest() {
        val encoder: PasswordEncoder = BCryptPasswordEncoder()
        val decorateEncoder: Encryptor = object : Encryptor {
            override fun encode(rawPassword: String): String {
                return encoder.encode(rawPassword)
            }
        }
        val encodedPassword: String = encoder.encode("2")
        val decorateEncodedPassword: String = decorateEncoder.encode("2")
        Assertions.assertThat(encoder.matches("2", encodedPassword)).isTrue()
        Assertions.assertThat(encoder.matches("2", decorateEncodedPassword)).isTrue()
    }
}
