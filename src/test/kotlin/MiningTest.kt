import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal.valueOf

class MiningTest {
    private val wallets = Wallets()
    private lateinit var wicoinWallet: Wallet
    private lateinit var initialTransactions: List<Transaction>

    private lateinit var wallet1: Wallet
    private lateinit var wallet2: Wallet
    private lateinit var wallet3: Wallet
    private lateinit var wallet4: Wallet

    @BeforeEach
    internal fun setUp() {
        wicoinWallet = Wallet()
        wallet1 = Wallet()
        wallets.add(wallet1)
        wallet2 = Wallet()
        wallets.add(wallet2)
        wallet3 = Wallet()
        wallets.add(wallet3)
        wallet4 = Wallet()
        wallets.add(wallet4)
        initialTransactions = listOf(
            Transaction(wicoinWallet.publicKey(), wallet1.publicKey(), valueOf(100)),
            Transaction(wicoinWallet.publicKey(), wallet2.publicKey(), valueOf(200)),
            Transaction(wicoinWallet.publicKey(), wallet3.publicKey(), valueOf(300)),
            Transaction(wicoinWallet.publicKey(), wallet4.publicKey(), valueOf(400)),
        )
    }

    @Test
    fun `should use blockchain, transactions and update wallets balance`() {
        val wicoinBlockchain = BlockChain(difficulty = 3, initialData = initialTransactions)
        wicoinBlockchain.add(
            listOf(
                Transaction(senderKey = wallet1.publicKey(), receiverKey = wallet2.publicKey(), amount = valueOf(25)),
                Transaction(senderKey = wallet1.publicKey(), receiverKey = wallet3.publicKey(), amount = valueOf(50)),
                Transaction(senderKey = wallet4.publicKey(), receiverKey = wallet1.publicKey(), amount = valueOf(22)),
                Transaction(senderKey = wallet2.publicKey(), receiverKey = wallet4.publicKey(), amount = valueOf(35)),
                Transaction(senderKey = wallet3.publicKey(), receiverKey = wallet1.publicKey(), amount = valueOf(12.50))
            )
        )

        wallets.getWallets().forEachIndexed { index, wallet ->
            println("wallet$index -> balance [ ${wallet.getBalance(wicoinBlockchain)} ]")
        }
    }
}