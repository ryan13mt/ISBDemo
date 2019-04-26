package com.isb.demo.service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileSubscriptionFilter {

    private String msisdn;
    private Integer customerIdOwner;
    private Integer customerIdUser;
    private ServiceType serviceType;
    private Long serviceStartDateFrom;
    private Long serviceStartDateTo;

    public MobileSubscriptionFilter setMsisdn(final String msisdn) {
        this.msisdn = msisdn;
        return this;
    }

}
