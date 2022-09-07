import Utils.hash
import java.time.Instant

data class Block(
    val previousHash: String,
    var hash: String = "",
    val datetime: Long = Instant.now().toEpochMilli(),
    val data: List<Transaction>
) {
    init {
        hash = hash("SHA-256","$previousHash$data$datetime")
    }
}
