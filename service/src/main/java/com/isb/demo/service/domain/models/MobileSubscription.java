package com.isb.demo.service.domain.models;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MobileSubscription {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private int id;

    @NotNull
    @Setter(AccessLevel.NONE)
    private String msisdn;

    @Setter(AccessLevel.NONE)
    private int customerIdOwner;

    @Setter(AccessLevel.NONE)
    private int customerIdUser;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @NotNull
    @Setter(AccessLevel.NONE)
    private long serviceStartDate;

    public MobileSubscription(@NotNull @NotEmpty final String msisdn,
                              final int customerIdOwner,
                              final int customerIdUser,
                              final ServiceType serviceType) {
        this.msisdn = msisdn;
        this.customerIdOwner = customerIdOwner;
        this.customerIdUser = customerIdUser;
        this.serviceType = serviceType;
        this.serviceStartDate = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public MobileSubscription update(final MobileSubscriptionUpdateRequest updateRequest) {
        this.customerIdOwner = updateRequest.getCustomerIdOwner() == null ? this.customerIdOwner : updateRequest.getCustomerIdOwner();
        this.customerIdUser = updateRequest.getCustomerIdUser() == null ? this.customerIdUser : updateRequest.getCustomerIdUser();
        this.serviceType = updateRequest.getServiceType() == null ? this.serviceType : updateRequest.getServiceType();

        return this;
    }

}
