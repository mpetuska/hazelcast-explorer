package lt.petuska.hazelcast.explorer.configuration.application

import io.ktor.util.*
import org.kodein.di.*
import org.kodein.di.generic.*
import service.route.*

@KtorExperimentalAPI
fun Kodein.Builder.registerBindings() {
  bind<HzeConfigService>() with singleton { HzeConfigService(instance()) }

//  bind<UserService>() with singleton { UserService(instance("main"), instance(), instance("rootUserName")) }
//  bind<AccountService>() with singleton { AccountService(instance("main"), instance(), instance("rootAccountName")) }
//  bind<AccountCheckService>() with singleton { AccountCheckService(instance("main"), instance("rootAccountName")) }
//  bind<TransactionService>() with singleton { TransactionService(instance("main"), instance("revolut"), instance()) }
//  bind("rootUserName") from singleton { instance<Application>().getAppProperty<String>("root.user.name") }
//  bind("rootAccountName") from singleton { instance<Application>().getAppProperty<String>("root.user.account.name") }
//  bind("rootAccountCurrency") from singleton { instance<Application>().getAppProperty<String>("root.user.account.currency") }
//  bind("revolutApiKey") from singleton { instance<Application>().getAppProperty<String>("revolut.apiKey") }
//  bind("revolutCexEndpoint") from singleton { instance<Application>().getAppProperty<String>("revolut.cexEndpoint") }
//  bind<CurrencyExchangeService>("revolut") with singleton {
//    CurrencyExchangeService(
//      instance("revolutApiKey"),
//      instance("revolutCexEndpoint")
//    )
//  }
}