import assertk.assertThat
import assertk.assertions.isEqualTo
import exception.WalletBalanceException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal.valueOf

class CreateBlocksTest {

    @Test
    internal fun `should throw exception if transaction contains a negative amount`() {
        val wallets = Wallets()
        val firstWallet = Wallet()
        wallets.add(firstWallet)
        val secondWallet = Wallet()
        wallets.add(secondWallet)
        val wrongAmount = valueOf(-10)

        val exception = assertThrows<WalletBalanceException> {

            listOf(
                Transaction(firstWallet.publicKey(), secondWallet.publicKey(), wrongAmount)
            )
        }
        assertThat(exception.message).isEqualTo("The amount of a transaction must be superior than 0, it was $wrongAmount")
    }

    @Test
    fun `should create the first bloc with a transaction between two wallets`() {
        val firstWallet = Wallet()
        val secondWallet = Wallet()
        val amount = valueOf(32.50)
        val firstBlock =
            Block(
                previousHash = FIRST_HASH,
                data = listOf(Transaction(firstWallet.publicKey(), secondWallet.publicKey(), amount))
            )

        assertThat(firstBlock.previousHash).isEqualTo("0")
        assertThat(firstBlock.data[0].senderKey).isEqualTo(firstWallet.publicKey())
        assertThat(firstBlock.data[0].receiverKey).isEqualTo(secondWallet.publicKey())
        assertThat(firstBlock.data[0].amount).isEqualTo(amount)
    }

    @Test
    fun `should create a blockchain with three blocks + initialization block`() {
        val blockChain = BlockChain(1)
        val firstWallet = Wallet()
        val secondWallet = Wallet()

        blockChain.add(listOf(Transaction(firstWallet.publicKey(), secondWallet.publicKey(), valueOf(17.35))))

        blockChain.add(listOf(Transaction(firstWallet.publicKey(), secondWallet.publicKey(), valueOf(14.78))))

        blockChain.add(listOf(Transaction(secondWallet.publicKey(), firstWallet.publicKey(), valueOf(89.89))))

        assertThat(blockChain.size()).isEqualTo(4)
    }

//    @Test
//    fun `should find previous block from another block`() {
//        val blockChain = BlockChain(1)
//        val wallets = Wallets()
//        val firstWallet = Wallet()
//        wallets.add(firstWallet)
//        val secondWallet = Wallet()
//        wallets.add(secondWallet)
//
//        for (i in 0..9) {
//            blockChain.add(
//                listOf(
//                    Transaction(
//                        senderKey = firstWallet.publicKey(),
//                        receiverKey = secondWallet.publicKey(),
//                        amount = valueOf(i + 10L)
//                    )
//                )
//            )
//        }
//
//        wallets.updateWallets(blockChain)
//
//        assertThat(firstWallet.getBalance()).isEqualTo(valueOf(855))
//        assertThat(secondWallet.getBalance()).isEqualTo(valueOf(245))
//        assertThat(blockChain.size()).isEqualTo(11)
//    }
//
//    @Test
//    fun `should create a block with a few transactions`() {
//        val wallets = Wallets()
//        val firstWallet = Wallet()
//        wallets.add(firstWallet)
//        val secondWallet = Wallet()
//        wallets.add(secondWallet)
//        val blockChain = BlockChain(1)
//
//        val transactions = listOf(
//            Transaction(firstWallet.publicKey(), secondWallet.publicKey(), valueOf(15.75)),
//            Transaction(secondWallet.publicKey(), firstWallet.publicKey(), valueOf(55.55)),
//            Transaction(firstWallet.publicKey(), secondWallet.publicKey(), valueOf(37.28)),
//            Transaction(secondWallet.publicKey(), firstWallet.publicKey(), valueOf(11.99)),
//            Transaction(firstWallet.publicKey(), secondWallet.publicKey(), valueOf(8.03))
//        )
//
//        blockChain.add(transactions)
//        wallets.updateWallets(blockChain)
//
//        assertThat(firstWallet.getBalance()).isEqualTo(valueOf(106.48))
//        assertThat(secondWallet.getBalance()).isEqualTo(valueOf(193.52))
//        assertThat(blockChain.size()).isEqualTo(2)
//    }

    companion object {
        const val FIRST_HASH = "0"
    }
}
