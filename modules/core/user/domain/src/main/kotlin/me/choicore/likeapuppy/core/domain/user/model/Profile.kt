package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.domain.Validator
import me.choicore.likeapuppy.core.domain.user.model.constant.Gender
import java.net.URL

data class Profile(
    val nickname: String,
    val username: Username,
    val dateOfBirth: DateOfBirth,
    val gender: Gender,
    val profileImage: ProfileImage? = null,
    val aboutMe: String? = null,
) : Validator {

    override fun validate() {
    }
}

data class ProfileImage(
    val original: Image,
    val thumbnail: Image? = null,
) : Validator {

    override fun validate() {
    }
}

data class Image(
    val url: URL,
    val size: Long,
) : Validator {

    override fun validate() {
    }
}
