import Utils.hash
import java.time.Instant

class BlockChain {
    private val blocks: MutableList<Block> = mutableListOf(Block(previousHash = "0", data = listOf()))
    private var lastBlock = blocks.first()

    fun add(transactions: List<Transaction>) {
        val newBlock = Block(previousHash = lastBlock.hash, data = transactions)
        blocks.add(newBlock)
        lastBlock = newBlock
    }

    fun size() = blocks.size
    fun lastBlock() = lastBlock
}

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