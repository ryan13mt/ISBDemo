package com.isb.demo.service.adapter.repository;

import com.isb.demo.service.domain.models.MobileSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobileRepository extends JpaRepository<MobileSubscription, String> {

    MobileSubscription getById(int valueOf);

}
