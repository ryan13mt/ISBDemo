package com.isb.demo.service.port;

import com.isb.demo.service.domain.models.MobileSubscription;
import com.isb.demo.service.domain.models.MobileSubscriptionFilter;
import java.util.List;

public interface MobileSubscriptionDao {

    MobileSubscription get(int id);

    List<MobileSubscription> find(MobileSubscriptionFilter filterDto);

    MobileSubscription add(final MobileSubscription mobileSubscription);

    MobileSubscription update(final MobileSubscription mobileSubscription);

    void delete(final MobileSubscription mobileSubscription);
}
