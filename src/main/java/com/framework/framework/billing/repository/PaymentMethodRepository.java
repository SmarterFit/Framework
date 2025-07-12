package com.framework.framework.billing.repository;

import com.framework.framework.billing.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, String> {
    Optional<PaymentMethod> findByIdAndEnabledTrue(String id);
    
    Optional<PaymentMethod> findByName(String name);

    List<PaymentMethod> findAllByEnabledTrue();

    @Modifying
    @Transactional
    @Query("UPDATE PaymentMethod m SET m.enabled = false WHERE m.id NOT IN :activeIds")
    void deactivateMethodsNotIn(List<String> activeIds);
}
