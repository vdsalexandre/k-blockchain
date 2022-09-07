data class Wallets(private val wallets: MutableMap<String, Wallet> = mutableMapOf()) {

    fun add(wallet: Wallet) {
        wallets[wallet.address] = wallet
    }

    fun updateWallets(block: Block) {
        wallets[block.getSenderAddress()]?.let { it.balance -= block.getAmountTransferred() }
        wallets[block.getReceiverAddress()]?.let { it.balance += block.getAmountTransferred() }
    }
}
