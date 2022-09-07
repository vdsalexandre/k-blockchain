import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import java.math.BigDecimal.valueOf

class CreateBlocksTest {
    @Test
    fun `should create the first bloc with a transaction between two wallets`() {
        val firstWallet = Wallet(balance = valueOf(10))
        val secondWallet = Wallet(balance = valueOf(125))
        val amount = valueOf(32.50)
        val firstBlock = Block(previousHash = FIRST_HASH, data = listOf("${firstWallet.address}||${secondWallet.address}||$amount"))

        assertThat(firstBlock.previousHash).isEqualTo("0")
        assertThat(firstBlock.data[0]).contains(firstWallet.address)
        assertThat(firstBlock.data[0]).contains(secondWallet.address)
        assertThat(firstBlock.data[0]).contains("$amount")
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
            data = listOf("${firstWallet.address}||${secondWallet.address}||37.50")
        )

        wallets.updateWallets(firstBlock)

        val secondBlock = Block(
            previousHash = firstBlock.hash,
            data = listOf("${firstWallet.address}||${secondWallet.address}||12.25")
        )

        wallets.updateWallets(secondBlock)

        val thirdBlock = Block(
            previousHash = secondBlock.hash,
            data = listOf("${secondWallet.address}||${firstWallet.address}||8.22")
        )

        wallets.updateWallets(thirdBlock)

        assertThat(firstWallet.balance).isEqualTo(valueOf(58.47))
        assertThat(secondWallet.balance).isEqualTo(valueOf(141.53))
        assertThat(thirdBlock.previousHash).isEqualTo(secondBlock.hash)
        assertThat(secondBlock.previousHash).isEqualTo(firstBlock.hash)
        assertThat(firstBlock.previousHash).isEqualTo(FIRST_HASH)
    }

    @Test
    fun `should create a blockchain with three blocks and transactions`() {
        val blockChain = BlockChain()

        val firstBlock = Block(
            previousHash = FIRST_HASH,
            data = listOf("1020304050||1122334455||17.35")
        )
        blockChain.add(firstBlock)

        val secondBlock = Block(
            previousHash = firstBlock.hash,
            data = listOf("1020304050||1122334455||14.78")
        )
        blockChain.add(secondBlock)

        val thirdBlock = Block(
            previousHash = secondBlock.hash,
            data = listOf("1122334455||1020304050||89.89")
        )
        blockChain.add(thirdBlock)

        blockChain.print()

        assertThat(blockChain.size()).isEqualTo(3)
    }

    companion object {
        const val FIRST_HASH = "0"
    }
}
