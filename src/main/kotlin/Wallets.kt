data class Wallets(private val wallets: MutableMap<String, Wallet> = mutableMapOf()) {

    fun add(wallet: Wallet): Wallet {
        wallets[wallet.address] = wallet
        return wallet
    }

    fun updateWallets(block: Block) {
        block.data.forEach { transaction ->
            wallets[transaction.senderAddress]?.let { it.balance -= transaction.amount }
            wallets[transaction.receiverAddress]?.let { it.balance += transaction.amount }
        }
    }
}
