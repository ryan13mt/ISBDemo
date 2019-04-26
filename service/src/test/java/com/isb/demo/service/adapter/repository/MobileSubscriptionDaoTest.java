package com.isb.demo.service.adapter.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.isb.demo.service.domain.model.wrappers.MobileSubscriptionTestWrapper;
import com.isb.demo.service.domain.models.MobileSubscription;
import com.isb.demo.service.domain.models.MobileSubscriptionFilter;
import com.isb.demo.service.domain.models.ServiceType;
import com.isb.demo.service.port.MobileSubscriptionDao;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@DataJpaTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MobileSubscriptionDaoTest {

    @Autowired
    private MobileSubscriptionDao sut;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void getById_shouldFindExactEntity() {
        final MobileSubscription mobileSubscription = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValid("1111111111", 2, 3).unwrap());

        final MobileSubscription retrievedMobileSubscription = sut.get(mobileSubscription.getId());
        assertThat(retrievedMobileSubscription).isEqualToComparingFieldByFieldRecursively(mobileSubscription);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getById_shouldFindNone_throwsEntityNotFoundException() {
        sut.get(3);
    }

    @Test
    public void findWithNoFilter_shouldReturnAll() {
        final MobileSubscription mobileSubscription = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValid("1111111111", 2, 3).unwrap());
        final MobileSubscription mobileSubscription2 = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValid("1111111112", 2, 3).unwrap());

        final List<MobileSubscription> retrievedMobileSubscription = sut.find(null);
        assertThat(retrievedMobileSubscription).containsExactly(mobileSubscription, mobileSubscription2);
    }

    @Test
    public void findWithMsisdnFilter_shouldReturnOne() {
        final MobileSubscription mobileSubscription = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValid("1111111111", 2, 3).unwrap());
        final MobileSubscription mobileSubscription2 = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValid("1111111112", 2, 3).unwrap());

        final List<MobileSubscription> retrievedMobileSubscription = sut.find(new MobileSubscriptionFilter().builder().msisdn("2").build());
        assertThat(retrievedMobileSubscription).containsExactly(mobileSubscription2);
    }

    @Test
    public void findWithOwnerFilter_shouldReturnOne() {
        final MobileSubscription mobileSubscription = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValid("1111111111", 2, 3).unwrap());
        final MobileSubscription mobileSubscription2 = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValid("1111111112", 4, 5).unwrap());

        final List<MobileSubscription> retrievedMobileSubscription = sut.find(new MobileSubscriptionFilter().builder().customerIdOwner(2).build());
        assertThat(retrievedMobileSubscription).containsExactly(mobileSubscription);
    }

    @Test
    public void findWithUserFilter_shouldReturnOne() {
        final MobileSubscription mobileSubscription = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValid("1111111111", 2, 3).unwrap());
        final MobileSubscription mobileSubscription2 = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValid("1111111112", 4, 5).unwrap());

        final List<MobileSubscription> retrievedMobileSubscription = sut.find(new MobileSubscriptionFilter().builder().customerIdUser(3).build());
        assertThat(retrievedMobileSubscription).containsExactly(mobileSubscription);
    }

    @Test
    public void findWithServiceTypeFilter_shouldReturnOne() {
        final MobileSubscription mobileSubscription = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValidWithType("1111111111", ServiceType.MOBILE_POSTPAID).unwrap());
        final MobileSubscription mobileSubscription2 = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValidWithType("1111111112", ServiceType.MOBILE_PREPAID).unwrap());

        final List<MobileSubscription> retrievedMobileSubscription = sut.find(new MobileSubscriptionFilter().builder().serviceType(ServiceType.MOBILE_POSTPAID).build());
        assertThat(retrievedMobileSubscription).containsExactly(mobileSubscription);
    }

    @Test
    public void findWithTimeFilter_shouldReturnOne() {
        final long currentTimestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        final long pastTimestamp = LocalDateTime.of(2000, 1, 1, 1, 1).toInstant(ZoneOffset.UTC).toEpochMilli();
        final MobileSubscription mobileSubscription = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValidWithTimestamp("1111111111", currentTimestamp).unwrap());
        final MobileSubscription mobileSubscription2 = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValidWithTimestamp("1111111112", pastTimestamp).unwrap());

        final List<MobileSubscription> retrievedMobileSubscription = sut.find(new MobileSubscriptionFilter().builder().serviceStartDateFrom(currentTimestamp).serviceStartDateTo(currentTimestamp).build());
        assertThat(retrievedMobileSubscription).containsExactly(mobileSubscription);
    }

    @Test
    public void add_valid_shouldAdd() {
        final MobileSubscription mobileSubscription = MobileSubscriptionTestWrapper.buildValid("1111111111", 2, 3).unwrap();
        final MobileSubscription newMobileSubscription = sut.add(mobileSubscription);
        assertThat(newMobileSubscription).isEqualToComparingFieldByField(mobileSubscription);
    }

    @Test
    public void update_valid_shouldUpdate() {
        final MobileSubscription mobileSubscription = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValidWithType("1111111111", ServiceType.MOBILE_POSTPAID).unwrap());

        mobileSubscription.setServiceType(ServiceType.MOBILE_PREPAID);
        final MobileSubscription newMobileSubscription = sut.update(mobileSubscription);

        assertThat(newMobileSubscription).isEqualToComparingFieldByField(mobileSubscription);
    }

    @Test(expected = EntityNotFoundException.class)
    public void delete_valid_shouldDelete() {
        final MobileSubscription mobileSubscription = entityManager.persistAndFlush(MobileSubscriptionTestWrapper.buildValidWithType("1111111111", ServiceType.MOBILE_POSTPAID).unwrap());
        sut.delete(mobileSubscription);
        sut.get(mobileSubscription.getId());
    }

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public MobileSubscriptionDao mobileSubscriptionDao(final MobileRepository mobileRepository, final EntityManager entityManager) {
            return new MobileSubscriptionSubscriptionDaoImpl(mobileRepository, entityManager);
        }
    }

}
