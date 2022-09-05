import java.security.MessageDigest
import kotlin.random.Random

object Utils {
    private val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    private fun generateRandomString(length: Int = 32): String {
        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    fun hash(algorithm: String): String {
        return MessageDigest
            .getInstance(algorithm)
            .digest(generateRandomString().toByteArray())
            .fold("") { acc, byte -> acc + "%02x".format(byte) }
    }

    fun fail(message: String): Nothing {
        throw WalletBalanceException(message)
    }
}