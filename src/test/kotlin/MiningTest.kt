import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal.TEN

class MiningTest {
    private val wallets = Wallets()
    private lateinit var wallet1: Wallet
    private lateinit var wallet2: Wallet
    private lateinit var wallet3: Wallet
    private lateinit var wallet4: Wallet

    @BeforeEach
    internal fun setUp() {
        wallet1 = wallets.add(Wallet())
        wallet2 = wallets.add(Wallet())
        wallet3 = wallets.add(Wallet())
        wallet4 = wallets.add(Wallet())
    }

    @Test
    fun `should mine one block and add it to the blockchain`() {
        val wicoinBlockchain = BlockChain()
        wicoinBlockchain.add(
            listOf(
                Transaction(senderAddress = wallet1.address, receiverAddress = wallet2.address, amount = TEN),
                Transaction(senderAddress = wallet1.address, receiverAddress = wallet3.address, amount = TEN),
                Transaction(senderAddress = wallet4.address, receiverAddress = wallet1.address, amount = TEN),
                Transaction(senderAddress = wallet2.address, receiverAddress = wallet4.address, amount = TEN),
                Transaction(senderAddress = wallet3.address, receiverAddress = wallet1.address, amount = TEN)
            )
        )

        wicoinBlockchain.print()
    }
}