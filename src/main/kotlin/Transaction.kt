import bootstrap.Utils.fail
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

data class Transaction(
    val senderAddress: String,
    val receiverAddress: String,
    val amount: BigDecimal
) {
    init {
        if (amount <= ZERO) fail("The amount of a transaction must be superior than 0, it was $amount")
    }
}
