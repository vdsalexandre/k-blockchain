package bootstrap

import exception.WalletBalanceException
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.MessageDigest
import java.security.SecureRandom
import kotlin.random.Random

object Utils {
    val LINE_SEPARATOR: String = System.lineSeparator()

    private const val KEY_SIZE = 2048
    private const val RANDOM_STRING_LENGTH = 32
    private const val KEYPAIR_ALGORITHM = "RSA"

    fun hash(algorithm: String, stringToHash: String = generateRandomString()): String {
        return MessageDigest
            .getInstance(algorithm)
            .digest(stringToHash.toByteArray())
            .fold("") { acc, byte -> acc + "%02x".format(byte) }
    }

    fun generateKeyPair(): KeyPair {
        val pairGenerator = KeyPairGenerator.getInstance(KEYPAIR_ALGORITHM)
        pairGenerator.initialize(KEY_SIZE, SecureRandom())
        return pairGenerator.generateKeyPair()
    }

    fun generateRandomSalt() = Random.nextInt(0, Int.MAX_VALUE)

    fun fail(message: String): Nothing = throw WalletBalanceException(message)

    private fun generateRandomString(length: Int = RANDOM_STRING_LENGTH): String {
        val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }
}