package com.isb.demo.service.domain.model.wrappers;

import com.isb.demo.service.domain.models.MobileSubscription;
import com.isb.demo.service.domain.models.ServiceType;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class MobileSubscriptionTestWrapper {

    private int id;
    private String msisdn;
    private int customerIdOwner;
    private int customerIdUser;
    private ServiceType serviceType;
    private long serviceStartDate;

    public static MobileSubscriptionTestWrapper buildValid(final String msisdn, final int owner, final int user) {
        return MobileSubscriptionTestWrapper.builder()
            .msisdn(msisdn)
            .customerIdOwner(owner)
            .customerIdUser(user)
            .serviceType(ServiceType.MOBILE_POSTPAID)
            .serviceStartDate(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli())
            .build();
    }

    public static MobileSubscriptionTestWrapper buildValidWithType(final String msisdn, final ServiceType serviceType) {
        return MobileSubscriptionTestWrapper.builder()
            .msisdn(msisdn)
            .customerIdOwner(1)
            .customerIdUser(2)
            .serviceType(serviceType)
            .serviceStartDate(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli())
            .build();
    }

    public static MobileSubscriptionTestWrapper buildValidWithTimestamp(final String msisdn, final long timestamp) {
        return MobileSubscriptionTestWrapper.builder()
            .msisdn(msisdn)
            .customerIdOwner(1)
            .customerIdUser(2)
            .serviceType(ServiceType.MOBILE_POSTPAID)
            .serviceStartDate(timestamp)
            .build();
    }

    public MobileSubscription unwrap() {
        return new MobileSubscription(this.id,
                                      this.msisdn,
                                      this.customerIdOwner,
                                      this.customerIdUser,
                                      this.serviceType,
                                      this.serviceStartDate);
    }

}
