package sistrader.customer

import sistrader.util.portable.CommonExternalizableDomainBase

class CustomerSelectionPricingInfo(
  var runnerNum: Int? = null,
  var modifierType: String? = null,
  var ticks: Int? = null,
  var fractionalPrice: String? = null
) : CommonExternalizableDomainBase()