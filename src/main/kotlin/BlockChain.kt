data class BlockChain(private val blocks: MutableList<Block> = mutableListOf()) {

    fun add(block: Block) = blocks.add(block)

    fun print() {
        blocks.forEach(::println)
    }

    fun size() = blocks.size
}
