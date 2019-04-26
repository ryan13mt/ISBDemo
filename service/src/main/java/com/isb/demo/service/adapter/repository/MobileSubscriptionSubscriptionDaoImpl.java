package com.isb.demo.service.adapter.repository;

import static java.util.Objects.nonNull;

import com.isb.demo.service.domain.models.MobileSubscription;
import com.isb.demo.service.domain.models.MobileSubscriptionFilter;
import com.isb.demo.service.domain.models.QMobileSubscription;
import com.isb.demo.service.port.MobileSubscriptionDao;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class MobileSubscriptionSubscriptionDaoImpl implements MobileSubscriptionDao {

    private final MobileRepository mobileRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MobileSubscription get(int id) {
        log.trace("Getting Mobile Subscription with id %s", id);
        final MobileSubscription mobileSubscription = mobileRepository.getById(id);
        if (mobileSubscription == null) {
            log.trace("Mobile Subscription with id %s was not found", id);
            throw new EntityNotFoundException(String.format("Mobile subscription with id %s not found.", id));
        }
        log.trace("Mobile Subscription [%s] found and will be returned", mobileSubscription);
        return mobileSubscription;
    }

    @Override
    public List<MobileSubscription> find(final MobileSubscriptionFilter filter) {
        log.trace("Finding Mobile Subscriptions with filter");
        final QMobileSubscription mobileSubscription = QMobileSubscription.mobileSubscription;

        final JPAQueryFactory factory = new JPAQueryFactory(entityManager);
        JPAQuery<MobileSubscription> query = factory.selectFrom(mobileSubscription);

        if (filter != null) {
            setQuerySelection(mobileSubscription, query, filter);
        }
        return query.fetch();
    }

    @Override
    public MobileSubscription add(final MobileSubscription mobileSubscription) {
        log.trace("Creating new Mobile Subscription [%s]", mobileSubscription);
        return mobileRepository.save(mobileSubscription);
    }

    @Override
    public MobileSubscription update(final MobileSubscription mobileSubscription) {
        log.trace("Updating Mobile Subscription [%s]", mobileSubscription);
        return mobileRepository.save(mobileSubscription);
    }

    @Override
    public void delete(final MobileSubscription mobileSubscription) {
        log.trace("Deleting Mobile Subscription [%s]", mobileSubscription);
        mobileRepository.delete(mobileSubscription);
    }

    private void setQuerySelection(final QMobileSubscription mobileSubscription, final JPAQuery<MobileSubscription> query, final MobileSubscriptionFilter filter) {
        BooleanExpression msisdnExpression = null;
        BooleanExpression customerIdOwnerExpression = null;
        BooleanExpression customerIdUserExpression = null;
        BooleanExpression serviceTypeExpression = null;
        BooleanExpression createdRangeExpression = null;

        if (filter.getMsisdn() != null) {
            msisdnExpression = mobileSubscription.msisdn.contains(filter.getMsisdn());
        }

        if (filter.getCustomerIdOwner() != null) {
            customerIdOwnerExpression = mobileSubscription.customerIdOwner.eq(filter.getCustomerIdOwner());
        }

        if (filter.getCustomerIdUser() != null) {
            customerIdUserExpression = mobileSubscription.customerIdUser.eq(filter.getCustomerIdUser());
        }

        if (filter.getServiceType() != null) {
            serviceTypeExpression = mobileSubscription.serviceType.eq(filter.getServiceType());
        }

        if (nonNull(filter.getServiceStartDateFrom())) {
            createdRangeExpression = mobileSubscription.serviceStartDate.between(filter.getServiceStartDateFrom(), filter.getServiceStartDateTo());
        }

        query.where(msisdnExpression, customerIdOwnerExpression, customerIdUserExpression, serviceTypeExpression, createdRangeExpression);
    }
}
