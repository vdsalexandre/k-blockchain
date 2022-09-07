import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import java.math.BigDecimal.valueOf

class CreateBlocksTest {
    @Test
    fun `should create the first bloc with a transaction between two wallets`() {
        val firstWallet = Wallet(balance = valueOf(10))
        val secondWallet = Wallet(balance = valueOf(125))
        val amount = valueOf(32.50)
        val firstBlock =
            Block(
                previousHash = FIRST_HASH,
                data = listOf(Transaction(firstWallet.address, secondWallet.address, amount))
            )

        assertThat(firstBlock.previousHash).isEqualTo("0")
        assertThat(firstBlock.data[0].senderAddress).isEqualTo(firstWallet.address)
        assertThat(firstBlock.data[0].receiverAddress).isEqualTo(secondWallet.address)
        assertThat(firstBlock.data[0].amount).isEqualTo(amount)
    }

    @Test
    fun `should create three blocs with transactions between two wallets`() {
        val wallets = Wallets()
        val firstWallet = Wallet(balance = valueOf(100))
        wallets.add(firstWallet)
        val secondWallet = Wallet(balance = valueOf(100))
        wallets.add(secondWallet)


        val firstBlock = Block(
            previousHash = FIRST_HASH,
            data = listOf(Transaction(firstWallet.address, secondWallet.address, valueOf(37.50)))
        )

        wallets.updateWallets(firstBlock)

        val secondBlock = Block(
            previousHash = firstBlock.hash,
            data = listOf(Transaction(firstWallet.address, secondWallet.address, valueOf(12.25)))
        )

        wallets.updateWallets(secondBlock)

        val thirdBlock = Block(
            previousHash = secondBlock.hash,
            data = listOf(Transaction(secondWallet.address, firstWallet.address, valueOf(8.22)))
        )

        wallets.updateWallets(thirdBlock)

        assertThat(firstWallet.balance).isEqualTo(valueOf(58.47))
        assertThat(secondWallet.balance).isEqualTo(valueOf(141.53))
        assertThat(thirdBlock.previousHash).isEqualTo(secondBlock.hash)
        assertThat(secondBlock.previousHash).isEqualTo(firstBlock.hash)
        assertThat(firstBlock.previousHash).isEqualTo(FIRST_HASH)
    }

    @Test
    fun `should create a blockchain with three blocks + initialization block`() {
        val blockChain = BlockChain()

        blockChain.add(listOf(Transaction("1020304050", "1122334455", valueOf(17.35))))

        blockChain.add(listOf(Transaction("1020304050", "1122334455", valueOf(14.78))))

        blockChain.add(listOf(Transaction("1122334455", "1020304050", valueOf(89.89))))

        assertThat(blockChain.size()).isEqualTo(4)
    }

    @Test
    fun `should find previous block from another block`() {
        val blockChain = BlockChain()
        val wallets = Wallets()
        val firstWallet = wallets.add(Wallet(balance = valueOf(1000)))
        val secondWallet = wallets.add(Wallet(balance = valueOf(100)))

        for (i in 0..9) {
            blockChain.add(
                listOf(
                    Transaction(
                        senderAddress = firstWallet.address,
                        receiverAddress = secondWallet.address,
                        amount = valueOf(i + 10L)
                    )
                )
            )
            wallets.updateWallets(blockChain.lastBlock())
        }

        blockChain.print()

        assertThat(firstWallet.balance).isEqualTo(valueOf(855))
        assertThat(secondWallet.balance).isEqualTo(valueOf(245))
        assertThat(blockChain.size()).isEqualTo(11)
    }

    @Test
    fun `should create a block with a few transactions`() {
        val wallets = Wallets()
        val firstWallet = Wallet(balance = valueOf(100))
        wallets.add(firstWallet)
        val secondWallet = Wallet(balance = valueOf(200))
        wallets.add(secondWallet)
        val blockChain = BlockChain()

        val transactions = listOf(
            Transaction(firstWallet.address, secondWallet.address, valueOf(15.75)),
            Transaction(secondWallet.address, firstWallet.address, valueOf(55.55)),
            Transaction(firstWallet.address, secondWallet.address, valueOf(37.28)),
            Transaction(secondWallet.address, firstWallet.address, valueOf(11.99)),
            Transaction(firstWallet.address, secondWallet.address, valueOf(8.03)),
        )

        blockChain.add(transactions)
        wallets.updateWallets(blockChain.lastBlock())

        assertThat(firstWallet.balance).isEqualTo(valueOf(106.48))
        assertThat(secondWallet.balance).isEqualTo(valueOf(193.52))
        assertThat(blockChain.size()).isEqualTo(2)
    }

    companion object {
        const val FIRST_HASH = "0"
    }
}
