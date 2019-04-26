package com.isb.demo.service.adapter.transformer;

import com.isb.demo.service.adapter.dto.MobileSubscriptionDto;
import com.isb.demo.service.domain.models.MobileSubscription;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MobileSubscriptionTransformer {

    public MobileSubscriptionDto transformToDto(final MobileSubscription mobileSubscription) {
        log.debug("Transforming MobileSubscription to MobileSubscriptionDto");
        return new MobileSubscriptionDto(mobileSubscription.getId(),
                                         mobileSubscription.getMsisdn(),
                                         mobileSubscription.getCustomerIdOwner(),
                                         mobileSubscription.getCustomerIdUser(),
                                         mobileSubscription.getServiceType(),
                                         mobileSubscription.getServiceStartDate());
    }

    public List<MobileSubscriptionDto> transformListToDtoList(List<MobileSubscription> subscriptionList) {
        log.debug("Transforming list of MobileSubscriptions to list of MobileSubscriptionDtos");
        return subscriptionList.stream().map(this::transformToDto).collect(Collectors.toList());
    }

}
