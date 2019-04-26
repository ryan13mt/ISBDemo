package com.isb.demo.service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MobileSubscriptionUpdateRequest {

    private Integer customerIdOwner;
    private Integer customerIdUser;
    private ServiceType serviceType;
}