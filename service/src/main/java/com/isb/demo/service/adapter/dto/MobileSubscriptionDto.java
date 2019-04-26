package com.isb.demo.service.adapter.dto;

import com.isb.demo.service.domain.models.ServiceType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class MobileSubscriptionDto {

    private int id;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$")
    private String msisdn;

    @NotNull
    private int customerIdOwner;

    @NotNull
    private int customerIdUser;

    @NotNull
    private ServiceType serviceType;

    private long serviceStartDate;

}