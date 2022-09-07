import Utils.fail
import Utils.hash
import java.math.BigDecimal

data class Wallets(private val wallets: MutableMap<String, Wallet> = mutableMapOf()) {

    fun add(wallet: Wallet): Wallet {
        wallets[wallet.address] = wallet
        return wallet
    }

    fun updateWallets(block: Block) {
        block.data.forEach { transaction ->
            updateAmount(transaction.senderAddress, transaction.amount.negate())
            updateAmount(transaction.receiverAddress, transaction.amount)
        }
    }

    private fun updateAmount(address: String, amount: BigDecimal) = wallets[address]?.let { it.balance += amount }
}

data class Wallet(var address: String = "", var balance: BigDecimal = BigDecimal.ZERO) {

    init {
        address = "WICOIN" + hash("SHA-256")
    }

    fun sendTo(otherWallet: Wallet, amount: BigDecimal) {
        when {
            amount < BigDecimal.ZERO -> fail("The amount to send must be greater than 0")
            balance < amount -> fail("Not enough money in your wallet")
            else -> {
                otherWallet.balance += amount
                balance -= amount
            }
        }
    }
}