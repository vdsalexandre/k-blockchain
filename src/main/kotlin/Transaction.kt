import java.math.BigDecimal

data class Transaction(
    val senderAddress: String,
    val receiverAddress: String,
    val amount: BigDecimal
)
