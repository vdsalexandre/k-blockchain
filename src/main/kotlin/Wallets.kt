import bootstrap.Utils.fail
import bootstrap.Utils.generateKeyPair
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

data class Wallets(private val wallets: MutableMap<RSAPublicKey, Wallet> = mutableMapOf()) {

    fun add(wallet: Wallet) = wallets.putIfAbsent(wallet.publicKey(), wallet)

    operator fun get(publicKey: RSAPublicKey) = wallets[publicKey]

    fun getWallets() = wallets.map { it.value }
}

data class Wallet(var publicKey: RSAPublicKey? = null, private var privateKey: RSAPrivateKey? = null) {

    init {
        val keyPair = generateKeyPair()
        publicKey = keyPair.public as RSAPublicKey
        privateKey = keyPair.private as RSAPrivateKey
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