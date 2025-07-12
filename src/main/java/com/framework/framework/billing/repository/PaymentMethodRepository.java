package com.framework.framework.billing.repository;

import com.framework.framework.billing.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {

    Optional<PaymentMethod> findByName(String name);

    List<PaymentMethod> findAllByEnabledTrue();

    @Modifying
    @Transactional
    @Query("UPDATE PaymentMethod m SET m.enabled = false WHERE m.name NOT IN :activeNames")
    void deactivateMethodsNotIn(List<String> activeNames);
}
