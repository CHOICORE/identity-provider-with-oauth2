package plugins

import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

abstract class CheckstyleUtil {
    companion object {
        @Throws(IOException::class)
        fun getCheckstyleConfig(resourcePath: String): String {
            CheckstyleUtil::class.java.getResourceAsStream(resourcePath).use { inputStream ->
                val sb = StringBuilder()
                inputStream?.let { it ->
                    InputStreamReader(it, StandardCharsets.UTF_8).use { reader ->
                        var c: Int
                        while (reader.read().also { c = it } != -1) {
                            sb.append(c.toChar())
                        }
                    }
                }
                return sb.toString()
            }
        }
    }
}
