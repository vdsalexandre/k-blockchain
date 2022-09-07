import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.startsWith
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal.valueOf

class CreateSimpleWalletTest {

    @Test
    fun `should be able to create an address in a wallet`() {
        val wallet = Wallet()

        assertThat(wallet.address.length).isEqualTo(70)
        assertThat(wallet.address).startsWith("WICOIN")
    }

    @Test
    fun `should be able to transfer money between two wallets`() {
        val firstWallet = Wallet(balance = valueOf(100))
        val secondWallet = Wallet()

        firstWallet.sendTo(otherWallet = secondWallet, amount = valueOf(10))

        assertThat(firstWallet.balance).isEqualTo(valueOf(90))
        assertThat(secondWallet.balance).isEqualTo(valueOf(10))
    }

    @Test
    fun `should thrown exception if balance is not enough for sending money`() {
        val expectedErrorMessage = "Not enough money in your wallet"
        val firstWallet = Wallet()
        val secondWallet = Wallet()

        val exception = assertThrows<WalletBalanceException> {
            firstWallet.sendTo(secondWallet, valueOf(25.50))
        }

        assertThat(exception.message).isEqualTo(expectedErrorMessage)
    }

    @Test
    fun `should thrown exception if amount to send is negative value`() {
        val expectedErrorMessage = "The amount to send must be greater than 0"
        val firstWallet = Wallet()
        val secondWallet = Wallet()

        val exception = assertThrows<WalletBalanceException> {
            firstWallet.sendTo(secondWallet, valueOf(-5))
        }

        assertThat(exception.message).isEqualTo(expectedErrorMessage)
    }
}