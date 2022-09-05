import Utils.fail
import Utils.hash
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

data class Wallet(val address: String = createAddress(), var balance: BigDecimal = ZERO) {

    fun sendTo(otherWallet: Wallet, amount: BigDecimal) {
        when {
            amount < ZERO -> fail("The amount to send must be greater than 0")
            balance < amount -> fail("Not enough money in your wallet")
            else -> {
                otherWallet.balance += amount
                balance -= amount
            }
        }
    }

    companion object {
        private fun createAddress(): String {
            return "WICOIN" + hash("SHA-256")
        }
    }
}
