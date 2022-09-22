package bootstrap

import exception.WalletBalanceException
import java.security.MessageDigest
import kotlin.random.Random

object Utils {

    private fun generateRandomString(length: Int = 32): String {
        val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    fun hash(algorithm: String, stringToHash: String = generateRandomString()): String {
        return MessageDigest
            .getInstance(algorithm)
            .digest(stringToHash.toByteArray())
            .fold("") { acc, byte -> acc + "%02x".format(byte) }
    }

    fun fail(message: String): Nothing = throw WalletBalanceException(message)
}