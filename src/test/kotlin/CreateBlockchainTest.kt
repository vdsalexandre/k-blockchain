import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.startsWith
import org.junit.jupiter.api.Test
import java.math.BigDecimal.valueOf

class CreateBlockchainTest {

    @Test
    fun `user should be able to create an address in his wallet`() {
        val wallet = Wallet()

        assertThat(wallet.address.length).isEqualTo(70)
        assertThat(wallet.address).startsWith("WICOIN")
    }

    @Test
    internal fun `should be able to transfer wicoins between two wallets`() {
        val firstWallet = Wallet(balance = valueOf(100))
        val secondWallet = Wallet()

        firstWallet.sendTo(otherWallet = secondWallet, amount = valueOf(10))

        assertThat(firstWallet.balance).isEqualTo(valueOf(90))
        assertThat(secondWallet.balance).isEqualTo(valueOf(10))
    }
}