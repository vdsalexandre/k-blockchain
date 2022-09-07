class BlockChain {
    private val blocks: MutableList<Block> = mutableListOf(Block(previousHash = "0", data = listOf()))
    private var lastBlock = blocks.first()

    fun add(transactions: List<Transaction>) {
        val newBlock = Block(
            previousHash = lastBlock.hash,
            data = transactions
        )
        blocks.add(newBlock)
        lastBlock = newBlock
    }

    fun print() {
        blocks.forEach(::println)
    }

    fun size() = blocks.size

    fun lastBlock() = lastBlock
}
