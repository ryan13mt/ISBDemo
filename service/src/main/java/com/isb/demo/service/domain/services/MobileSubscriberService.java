package com.isb.demo.service.domain.services;

import com.isb.demo.service.domain.models.MobileSubscription;
import com.isb.demo.service.domain.models.MobileSubscriptionFilter;
import com.isb.demo.service.domain.models.MobileSubscriptionUpdateRequest;
import com.isb.demo.service.port.MobileSubscriptionDao;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MobileSubscriberService {

    private MobileSubscriptionDao mobileSubscriptionDao;

    private MobileSubscription get(final int id) {
        return mobileSubscriptionDao.get(id);
    }

    public List<MobileSubscription> find(final MobileSubscriptionFilter filterDto) {
        return mobileSubscriptionDao.find(filterDto);
    }

    public MobileSubscription add(@NotNull @Valid final MobileSubscription mobileSubscription) {
        final List<MobileSubscription> existingSubscription = find(new MobileSubscriptionFilter().setMsisdn(mobileSubscription.getMsisdn()));
        if (!existingSubscription.isEmpty()) {
            throw new IllegalStateException(String.format("Mobile subscription with number %s already exists", mobileSubscription.getMsisdn()));
        }
        return mobileSubscriptionDao.add(mobileSubscription);
    }

    public MobileSubscription update(int id, @NotNull @Valid final MobileSubscriptionUpdateRequest updateRequest) {
        return mobileSubscriptionDao.update(mobileSubscriptionDao.get(id).update(updateRequest));
    }

    public void delete(final int id) {
        mobileSubscriptionDao.delete(get(id));
    }
}
