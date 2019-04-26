package com.isb.demo.service.adapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isb.demo.service.adapter.dto.MobileSubscriptionUpdateRequestDto;
import com.isb.demo.service.adapter.transformer.MobileSubscriptionTransformer;
import com.isb.demo.service.domain.model.wrappers.MobileSubscriptionTestWrapper;
import com.isb.demo.service.domain.models.MobileSubscription;
import com.isb.demo.service.domain.models.MobileSubscriptionFilter;
import com.isb.demo.service.domain.models.MobileSubscriptionUpdateRequest;
import com.isb.demo.service.domain.models.ServiceType;
import com.isb.demo.service.domain.services.MobileSubscriberService;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MobileController.class, secure = false)
public class MobileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MobileSubscriptionTransformer mobileSubscriptionTransformer;

    @MockBean
    private MobileSubscriberService mobileSubscriberService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void find() throws Exception {
        final MobileSubscription mobileSubscription = MobileSubscriptionTestWrapper.buildValid("1111111111", 1, 2).unwrap();

        when(mobileSubscriberService.find(any(MobileSubscriptionFilter.class))).thenReturn(Collections.singletonList(mobileSubscription));

        this.mockMvc.perform(get("/mobileSubscription")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .param("msisdn", "2"))
            .andExpect(status().isOk());

        verify(mobileSubscriberService, times(1)).find(any(MobileSubscriptionFilter.class));
    }

    @Test
    public void add() throws Exception {
        final MobileSubscription mobileSubscription = MobileSubscriptionTestWrapper.buildValid("1111111111", 1, 2).unwrap();
        when(mobileSubscriberService.add(any(MobileSubscription.class))).thenReturn(mobileSubscription);

        this.mockMvc.perform(post("/mobileSubscription")
                                 .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                 .content(objectMapper.writeValueAsString(mobileSubscriptionTransformer.transformToDto(mobileSubscription))))
            .andExpect(status().isCreated());

        verify(mobileSubscriberService, times(1)).add(any(MobileSubscription.class));
    }

    @Test
    public void patch() throws Exception {
        final MobileSubscription mobileSubscription = MobileSubscriptionTestWrapper.buildValid("1111111111", 1, 2).unwrap();
        final MobileSubscriptionUpdateRequestDto mobileSubscriptionUpdateRequestDto = new MobileSubscriptionUpdateRequestDto(1, 1, ServiceType.MOBILE_POSTPAID);
        when(mobileSubscriberService.update(anyInt(), any(MobileSubscriptionUpdateRequest.class))).thenReturn(mobileSubscription);

        this.mockMvc.perform(MockMvcRequestBuilders.patch("/mobileSubscription/100")
                                 .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                 .content(objectMapper.writeValueAsString(mobileSubscriptionUpdateRequestDto)))
            .andExpect(status().isOk());

        verify(mobileSubscriberService, times(1)).update(anyInt(), any(MobileSubscriptionUpdateRequest.class));
    }

    @Test
    public void delete() throws Exception {
        final MobileSubscription mobileSubscription = MobileSubscriptionTestWrapper.buildValid("1111111111", 1, 2).unwrap();

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/mobileSubscription/100")
                                 .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                 .content(objectMapper.writeValueAsString(mobileSubscriptionTransformer.transformToDto(mobileSubscription))))
            .andExpect(status().isOk());

        verify(mobileSubscriberService, times(1)).delete(100);
    }

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public MobileSubscriptionTransformer mobileSubscriptionTransformer() {
            return new MobileSubscriptionTransformer();
        }
    }

}
