package bootstrap

import exception.WalletBalanceException
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.MessageDigest
import java.security.SecureRandom
import kotlin.random.Random

object Utils {

    val LINE_SEPARATOR: String = System.lineSeparator()

    fun hash(algorithm: String, stringToHash: String = generateRandomString()): String {
        return MessageDigest
            .getInstance(algorithm)
            .digest(stringToHash.toByteArray())
            .fold("") { acc, byte -> acc + "%02x".format(byte) }
    }

    fun generateKeyPair(): KeyPair {
        val pairGenerator = KeyPairGenerator.getInstance("RSA")
        pairGenerator.initialize(2048, SecureRandom())
        return pairGenerator.generateKeyPair()
    }

    fun generateRandomSalt() = Random.nextInt(0, Int.MAX_VALUE)

    fun fail(message: String): Nothing = throw WalletBalanceException(message)

    private fun generateRandomString(length: Int = 32): String {
        val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }
}