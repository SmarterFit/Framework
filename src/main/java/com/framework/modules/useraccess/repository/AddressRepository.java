package com.framework.modules.useraccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.framework.modules.useraccess.entity.Address;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}