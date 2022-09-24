import bootstrap.Utils.fail
import bootstrap.Utils.generateKeyPair
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.security.PrivateKey
import java.security.PublicKey

data class Wallets(private val wallets: MutableMap<PublicKey, Wallet> = mutableMapOf()) {

    fun add(wallet: Wallet) = wallets.putIfAbsent(wallet.publicKey(), wallet)

    operator fun get(publicKey: PublicKey) = wallets[publicKey]

    fun getWallets() = wallets.map { it.value }
}

data class Wallet(var publicKey: PublicKey? = null, private var privateKey: PrivateKey? = null) {

    init {
        val keyPair = generateKeyPair()
        publicKey = keyPair.public
        privateKey = keyPair.private
    }

    fun publicKey() = publicKey ?: fail("Error - pair of keys generation failed")

    fun getBalance(blockchain: BlockChain): BigDecimal {
        return blockchain.blocks().map { block ->
           block.data.fold(initial = ZERO) { acc, transaction ->
                with(transaction) {
                    when {
                        hasSender(publicKey()) -> acc + amount.negate()
                        hasReceiver(publicKey()) -> acc + amount
                        else -> acc
                    }
                }
            }
        }.sumOf { it }
    }
}