package com.isb.demo.service.adapter;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import com.isb.demo.service.adapter.dto.MobileSubscriptionCreateRequestDto;
import com.isb.demo.service.adapter.dto.MobileSubscriptionDto;
import com.isb.demo.service.adapter.dto.MobileSubscriptionUpdateRequestDto;
import com.isb.demo.service.adapter.transformer.MobileSubscriptionTransformer;
import com.isb.demo.service.domain.models.MobileSubscription;
import com.isb.demo.service.domain.models.MobileSubscriptionFilter;
import com.isb.demo.service.domain.models.MobileSubscriptionUpdateRequest;
import com.isb.demo.service.domain.services.MobileSubscriberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping(path = "/mobileSubscription", produces = APPLICATION_JSON_UTF8_VALUE)
public class MobileController {

    private MobileSubscriberService mobileSubscriberService;
    private MobileSubscriptionTransformer mobileSubscriptionTransformer;

    @ApiOperation(
        value = "Get a list of mobile subscribers related with the filtering criteria provided. If no filter is present, return all mobile subscribers.")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "The request has been successfully processed and a list of applicable mobile subscriptions will be presented if any exists."),
        @ApiResponse(code = 400, message = "The request is rejected because it was not understood by the server due to malformed syntax. Do not repeat the request without modifications."),
        @ApiResponse(code = 422, message = "The request is rejected because the server was unable to process the given request due to semantic validations. Do not repeat the request without modifications.")
    })
    @GetMapping
    public List<MobileSubscriptionDto> getMobileSubscriptions(@Valid final MobileSubscriptionFilter filterDto) {
        log.trace("Received request to get mobile subscription list filtered by {}", filterDto);
        return mobileSubscriptionTransformer.transformListToDtoList(mobileSubscriberService.find(filterDto));
    }

    @ApiOperation(
        value = "Add a mobile subscription.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
        @ApiResponse(code = 201, message = "The request has been successfully processed and a mobile subscription has been created."),
        @ApiResponse(code = 400, message = "The request is rejected because it was not understood by the server due to malformed syntax. Do not repeat the request without modifications."),
        @ApiResponse(code = 422, message = "The request is rejected because the server was unable to process the given request due to semantic validations. Do not repeat the request without modifications.")
    })
    @PostMapping
    public MobileSubscriptionDto addMobileSubscription(@RequestBody @NotNull @Valid final MobileSubscriptionCreateRequestDto mobileSubscriptionDto) {
        log.trace("Received request to create a new mobile subscription with these details: {}", mobileSubscriptionDto);
        final MobileSubscription mobileSubscription = new MobileSubscription(mobileSubscriptionDto.getMsisdn(),
                                                                             mobileSubscriptionDto.getCustomerIdOwner(),
                                                                             mobileSubscriptionDto.getCustomerIdUser(),
                                                                             mobileSubscriptionDto.getServiceType());

        return mobileSubscriptionTransformer.transformToDto(mobileSubscriberService.add(mobileSubscription));
    }

    @ApiOperation(
        value = "Update a mobile subscription.")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "The request has been successfully processed and the mobile subscription has been updated."),
        @ApiResponse(code = 400, message = "The request is rejected because it was not understood by the server due to malformed syntax. Do not repeat the request without modifications."),
        @ApiResponse(code = 422, message = "The request is rejected because the server was unable to process the given request due to semantic validations. Do not repeat the request without modifications."),
        @ApiResponse(code = 500, message = "The request is rejected because the server was unable to find the entity with the id provided.")
    })
    @PatchMapping(path = "/{id}")
    public MobileSubscriptionDto updateMobileSubscription(@PathVariable final int id, @RequestBody final MobileSubscriptionUpdateRequestDto updates) {
        log.trace("Received request to update mobile subscription with ID {} with updated details: {}", id, updates);
        return mobileSubscriptionTransformer.transformToDto(mobileSubscriberService.update(id, new MobileSubscriptionUpdateRequest(updates.getCustomerIdOwner(),
                                                                                                                                   updates.getCustomerIdUser(),
                                                                                                                                   updates.getServiceType())));
    }

    @ApiOperation(
        value = "Delete a mobile subscription.")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "The request has been successfully processed and the mobile subscription has been deleted."),
        @ApiResponse(code = 400, message = "The request is rejected because it was not understood by the server due to malformed syntax. Do not repeat the request without modifications."),
        @ApiResponse(code = 422, message = "The request is rejected because the server was unable to process the given request due to semantic validations. Do not repeat the request without modifications."),
        @ApiResponse(code = 500, message = "The request is rejected because the server was unable to find the entity with the id provided.")
    })
    @DeleteMapping(path = "/{id}")
    public void deleteMobileSubscription(@PathVariable final int id) {
        log.trace("Received request to delete mobile subscription with ID {}", id);
        mobileSubscriberService.delete(id);
    }


}
