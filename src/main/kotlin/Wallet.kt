import java.math.BigDecimal
import java.security.MessageDigest
import kotlin.random.Random

data class Wallet(val address: String = createAddress(), var balance: BigDecimal = BigDecimal.ZERO) {
    fun sendTo(otherWallet: Wallet, amount: BigDecimal) {
        otherWallet.balance += amount
        balance -= amount
    }

    companion object {
        private val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        private fun createAddress(): String {
            return "WICOIN" + MessageDigest
                .getInstance("SHA-256")
                .digest(generateRandomByteArray())
                .fold("") { acc, byte -> acc + "%02x".format(byte) }
        }

        private fun generateRandomByteArray(): ByteArray {
            return (1..32)
                .map { Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
                .toByteArray()
        }
    }
}
