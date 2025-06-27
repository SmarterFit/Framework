package com.framework.modules.useraccess.repository;

import com.framework.modules.useraccess.entity.UserRole;
import com.framework.modules.useraccess.entity.id.UserRoleId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

}
