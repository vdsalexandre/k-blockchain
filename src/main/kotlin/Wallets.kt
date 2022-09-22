import bootstrap.Utils.hash
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

data class Wallets(private val wallets: MutableMap<String, Wallet> = mutableMapOf()) {

    fun add(wallet: Wallet) = wallet.also { wallets[wallet.address] = it }

    fun updateWallets(blockchain: BlockChain) {
        blockchain.allBlocks().forEach {block ->
            block.data.forEach {transaction ->
                updateAmount(transaction.senderAddress, transaction.amount.negate())
                updateAmount(transaction.receiverAddress, transaction.amount)
            }
        }
    }

    fun print() = wallets.forEach(::println)

    private fun updateAmount(address: String, amount: BigDecimal) = wallets[address]?.let { it.balance += amount }
}

data class Wallet(
    val address: String = "WICOIN" + hash("SHA-256"),
    var balance: BigDecimal = ZERO
)
