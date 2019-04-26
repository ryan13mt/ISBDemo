package com.isb.demo.service.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.isb.demo.service.domain.model.wrappers.MobileSubscriptionTestWrapper;
import com.isb.demo.service.domain.models.MobileSubscription;
import com.isb.demo.service.domain.models.MobileSubscriptionUpdateRequest;
import com.isb.demo.service.domain.models.ServiceType;
import com.isb.demo.service.port.MobileSubscriptionDao;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MobileSubscriptionServiceTest {

    @Mock
    private MobileSubscriptionDao mobileSubscriptionDao;

    @InjectMocks
    private MobileSubscriberService sut;

    @Test
    public void find() {
        final MobileSubscription mobileSubscription = MobileSubscriptionTestWrapper.buildValid("11111111", 1, 2).unwrap();
        given(mobileSubscriptionDao.find(any())).willReturn(Collections.singletonList(mobileSubscription));

        final List<MobileSubscription> retrievedMobileSubscription = sut.find(null);

        assertThat(retrievedMobileSubscription).isEqualTo(Collections.singletonList(mobileSubscription));

        verify(mobileSubscriptionDao, times(1)).find(null);
    }

    @Test
    public void add() {
        final MobileSubscription mobileSubscription = MobileSubscriptionTestWrapper.buildValid("11111111", 1, 2).unwrap();
        given(mobileSubscriptionDao.add(any())).willReturn(mobileSubscription);

        final MobileSubscription savedMobileSubscription = sut.add(mobileSubscription);

        assertThat(savedMobileSubscription).isEqualTo(mobileSubscription);

        final ArgumentCaptor<MobileSubscription> mobileSubscriptionArgumentCaptor = ArgumentCaptor.forClass(MobileSubscription.class);
        verify(mobileSubscriptionDao, times(1)).add(mobileSubscriptionArgumentCaptor.capture());
        verify(mobileSubscriptionDao, times(1)).find(any());

        final MobileSubscription unsavedMobileSubscription = mobileSubscriptionArgumentCaptor.getValue();
        assertThat(unsavedMobileSubscription.getId()).isEqualTo(savedMobileSubscription.getId());
        assertThat(unsavedMobileSubscription.getMsisdn()).isEqualTo(savedMobileSubscription.getMsisdn());
        assertThat(unsavedMobileSubscription.getCustomerIdOwner()).isEqualTo(savedMobileSubscription.getCustomerIdOwner());
        assertThat(unsavedMobileSubscription.getCustomerIdUser()).isEqualTo(savedMobileSubscription.getCustomerIdUser());
        assertThat(unsavedMobileSubscription.getServiceType()).isEqualTo(savedMobileSubscription.getServiceType());
        assertThat(unsavedMobileSubscription.getServiceStartDate()).isEqualTo(savedMobileSubscription.getServiceStartDate());
    }

    @Test(expected = IllegalStateException.class)
    public void addExisting() {
        final MobileSubscription mobileSubscription = MobileSubscriptionTestWrapper.buildValid("11111111", 1, 2).unwrap();
        given(mobileSubscriptionDao.find(any())).willReturn(Collections.singletonList(mobileSubscription));
        sut.add(mobileSubscription);
    }

    @Test
    public void update() {
        final MobileSubscription mobileSubscription = MobileSubscriptionTestWrapper.buildValid("11111111", 1, 2).unwrap();
        final MobileSubscription mobileSubscriptionUpdated = MobileSubscriptionTestWrapper.buildValidWithType("11111111", ServiceType.MOBILE_PREPAID).unwrap();
        given(mobileSubscriptionDao.get(mobileSubscription.getId())).willReturn(mobileSubscription);
        given(mobileSubscriptionDao.update(any())).willReturn(mobileSubscriptionUpdated);

        final MobileSubscription savedMobileSubscription = sut.update(mobileSubscriptionUpdated.getId(), new MobileSubscriptionUpdateRequest(3, 3, ServiceType.MOBILE_PREPAID));

        assertThat(savedMobileSubscription).isEqualTo(mobileSubscriptionUpdated);

        final ArgumentCaptor<MobileSubscription> mobileSubscriptionArgumentCaptor = ArgumentCaptor.forClass(MobileSubscription.class);
        verify(mobileSubscriptionDao, times(1)).update(mobileSubscriptionArgumentCaptor.capture());
        verify(mobileSubscriptionDao, times(1)).get(mobileSubscription.getId());

        final MobileSubscription unsavedMobileSubscription = mobileSubscriptionArgumentCaptor.getValue();
        assertThat(unsavedMobileSubscription.getId()).isEqualTo(savedMobileSubscription.getId());
        assertThat(unsavedMobileSubscription.getMsisdn()).isEqualTo(savedMobileSubscription.getMsisdn());
        assertThat(unsavedMobileSubscription.getCustomerIdOwner()).isEqualTo(3);
        assertThat(unsavedMobileSubscription.getCustomerIdUser()).isEqualTo(3);
        assertThat(unsavedMobileSubscription.getServiceType()).isEqualTo(savedMobileSubscription.getServiceType());
        assertThat(mobileSubscriptionUpdated.getServiceStartDate()).isEqualTo(savedMobileSubscription.getServiceStartDate());
    }

    @Test
    public void updateWithNullValues() {
        final MobileSubscription mobileSubscription = MobileSubscriptionTestWrapper.buildValid("11111111", 1, 2).unwrap();
        final MobileSubscription mobileSubscriptionUpdated = MobileSubscriptionTestWrapper.buildValid("11111111", 1, 2).unwrap();
        given(mobileSubscriptionDao.get(mobileSubscription.getId())).willReturn(mobileSubscription);
        given(mobileSubscriptionDao.update(any())).willReturn(mobileSubscriptionUpdated);

        final MobileSubscription savedMobileSubscription = sut.update(mobileSubscriptionUpdated.getId(), new MobileSubscriptionUpdateRequest(null, null, null));

        assertThat(savedMobileSubscription).isEqualTo(mobileSubscriptionUpdated);

        final ArgumentCaptor<MobileSubscription> mobileSubscriptionArgumentCaptor = ArgumentCaptor.forClass(MobileSubscription.class);
        verify(mobileSubscriptionDao, times(1)).update(mobileSubscriptionArgumentCaptor.capture());
        verify(mobileSubscriptionDao, times(1)).get(mobileSubscription.getId());

        final MobileSubscription unsavedMobileSubscription = mobileSubscriptionArgumentCaptor.getValue();
        assertThat(unsavedMobileSubscription.getId()).isEqualTo(savedMobileSubscription.getId());
        assertThat(unsavedMobileSubscription.getMsisdn()).isEqualTo(savedMobileSubscription.getMsisdn());
        assertThat(unsavedMobileSubscription.getCustomerIdOwner()).isEqualTo(savedMobileSubscription.getCustomerIdOwner());
        assertThat(unsavedMobileSubscription.getCustomerIdUser()).isEqualTo(savedMobileSubscription.getCustomerIdUser());
        assertThat(unsavedMobileSubscription.getServiceType()).isEqualTo(savedMobileSubscription.getServiceType());
        assertThat(mobileSubscriptionUpdated.getServiceStartDate()).isEqualTo(savedMobileSubscription.getServiceStartDate());
    }

    @Test
    public void delete() {
        final MobileSubscription mobileSubscription = MobileSubscriptionTestWrapper.buildValid("11111111", 1, 2).unwrap();
        given(mobileSubscriptionDao.get(mobileSubscription.getId())).willReturn(mobileSubscription);

        sut.delete(mobileSubscription.getId());

        verify(mobileSubscriptionDao, times(1)).get(mobileSubscription.getId());
        verify(mobileSubscriptionDao, times(1)).delete(mobileSubscription);

    }


}
