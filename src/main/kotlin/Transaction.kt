import bootstrap.Utils
import bootstrap.Utils.LINE_SEPARATOR
import bootstrap.Utils.fail
import bootstrap.Utils.generateRandomSalt
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.security.Key
import java.security.interfaces.RSAPublicKey
import java.util.Base64

data class Transaction(
    val senderKey: RSAPublicKey,
    val receiverKey: RSAPublicKey,
    val amount: BigDecimal,
    var transactionHash: String = ""
) {
    init {
        if (amount <= ZERO) fail("Error - The amount of a transaction must be superior than 0, it was $amount")

        transactionHash = Utils.hash("SHA-256", "$senderKey$receiverKey$amount${generateRandomSalt()}")
    }

    fun hasSender(publicKey: RSAPublicKey) = senderKey == publicKey

    fun hasReceiver(publicKey: RSAPublicKey) = receiverKey == publicKey

    override fun toString(): String {
        return buildString {
            append(LINE_SEPARATOR)
            append("${Transaction::class.java.canonicalName}(")
            append("senderKey=${keyContent(senderKey)}, ")
            append("receiverKey=${keyContent(receiverKey)}, ")
            append("amount=$amount, ")
            append("transactionHash=$transactionHash)")
        }
    }

    private fun keyContent(key: Key) = Base64.getEncoder().encodeToString(key.encoded)
}
