package com.framework.modules.useraccess.entity.id;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

import com.framework.common.enums.RoleType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "user", "roleType" })
@Embeddable
public class UserRoleId implements Serializable {
    private UUID user;
    private RoleType roleType;
}