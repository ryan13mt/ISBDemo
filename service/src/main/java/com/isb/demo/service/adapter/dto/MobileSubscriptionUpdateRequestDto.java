package com.isb.demo.service.adapter.dto;

import com.isb.demo.service.domain.models.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MobileSubscriptionUpdateRequestDto {

    @Nullable
    private Integer customerIdOwner;

    @Nullable
    private Integer customerIdUser;

    @Nullable
    private ServiceType serviceType;
}