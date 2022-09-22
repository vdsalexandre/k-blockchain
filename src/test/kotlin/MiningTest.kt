import org.junit.jupiter.api.Test
import java.math.BigDecimal.valueOf

class MiningTest {
    companion object {
        private val wallets = Wallets()
        private val wallet1 = wallets.add(Wallet(balance = valueOf(100)))
        private val wallet2 = wallets.add(Wallet(balance = valueOf(200)))
        private val wallet3 = wallets.add(Wallet(balance = valueOf(300)))
        private val wallet4 = wallets.add(Wallet(balance = valueOf(400)))
    }

    @Test
    fun `should mine one block and add it to the blockchain`() {
        val wicoinBlockchain = BlockChain(difficulty = 3)
        wicoinBlockchain.add(
            listOf(
                Transaction(senderAddress = wallet1.address, receiverAddress = wallet2.address, amount = valueOf(25)),
                Transaction(senderAddress = wallet1.address, receiverAddress = wallet3.address, amount = valueOf(50)),
                Transaction(senderAddress = wallet4.address, receiverAddress = wallet1.address, amount = valueOf(22)),
                Transaction(senderAddress = wallet2.address, receiverAddress = wallet4.address, amount = valueOf(35)),
                Transaction(senderAddress = wallet3.address, receiverAddress = wallet1.address, amount = valueOf(12.50))
            )
        )
        wallets.updateWallets(wicoinBlockchain)
        wallets.print()
    }
}