import Utils.hash
import java.time.Instant

data class Block(
    val previousHash: String,
    var hash: String = "",
    val datetime: Long = Instant.now().toEpochMilli(),
    val data: List<String>
) {
    init {
        hash = hash("SHA-256","$previousHash$data$datetime")
    }

    fun getSenderAddress() = data[0].split("||")[0]
    fun getReceiverAddress() = data[0].split("||")[1]
    fun getAmountTransferred() = data[0].split("||")[2].toBigDecimal()
}
