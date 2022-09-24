import bootstrap.Utils.hash
import java.time.Instant

class BlockChain(difficulty: Int = 5, initialData: List<Transaction> = listOf()) {
    private val validPrefix = "0".repeat(difficulty)
    private val blocks: MutableList<Block> = mutableListOf()
    private var lastBlock: Block

    init {
        blocks.add(
            mine(
                Block(previousHash = "0", data = initialData)
            )
        )
        lastBlock = blocks[0]
    }

    fun add(transactions: List<Transaction>): Block {
        val newBlock = Block(previousHash = lastBlock.hash, data = transactions)
        val minedBlock = mine(newBlock)
        blocks.add(minedBlock)
        lastBlock = minedBlock
        return minedBlock
    }

    fun size() = blocks.size

    fun blocks() = blocks.toList()

    fun isValid(): Boolean {
        for (i in 1 until blocks.size) {
            val previousBlock = blocks[i - 1]
            val currentBlock = blocks[i]

            when {
                currentBlock.hash != currentBlock.generateBlockHash() -> return false
                currentBlock.previousHash != previousBlock.generateBlockHash() -> return false
                !(isMined(previousBlock) && isMined(currentBlock)) -> return false
            }
        }
        return true
    }

    private fun mine(block: Block): Block {
        var minedBlock = block.copy()
        while (!isMined(minedBlock)) {
            minedBlock = minedBlock.copy(nonce = minedBlock.nonce + 1)
        }
        return minedBlock
    }

    private fun isMined(block: Block) = block.hash.startsWith(validPrefix)
}

data class Block(
    val previousHash: String,
    var hash: String = "",
    val datetime: Long = Instant.now().toEpochMilli(),
    val data: List<Transaction>,
    val nonce: Long = 0
) {
    init {
        hash = generateBlockHash()
    }

    fun generateBlockHash() = hash("SHA-256", "$previousHash$data$datetime$nonce")
}