package com.isb.demo.service.adapter.dto;

import com.isb.demo.service.domain.models.ServiceType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MobileSubscriptionCreateRequestDto {

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$")
    private String msisdn;

    @NotNull
    private Integer customerIdOwner;

    @NotNull
    private Integer customerIdUser;

    @NotNull
    private ServiceType serviceType;
}