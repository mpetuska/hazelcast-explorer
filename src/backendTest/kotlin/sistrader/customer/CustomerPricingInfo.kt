package sistrader.customer

import sistrader.util.portable.CommonExternalizableDomainBase

data class CustomerPricingInfo(
  var eventId: Long? = null,
  var marketId: String? = null,
  var marketName: String? = null,
  var marketType: String? = null,
  var customerCode: String? = null,
  var isDelinked: Boolean? = null,
  var selectionPricingInfos: MutableList<CustomerSelectionPricingInfo>? = null
) : CommonExternalizableDomainBase()