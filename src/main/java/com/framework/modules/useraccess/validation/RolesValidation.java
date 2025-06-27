package com.framework.modules.useraccess.validation;

import com.framework.common.enums.RoleType;
import com.framework.common.exceptions.PermissionDeniedException;
import com.framework.modules.useraccess.entity.UserRole;

import java.util.Set;
import java.util.stream.Collectors;

public class RolesValidation {

    private RolesValidation() {}

    public static void validateUserRole(RoleType role, Set<UserRole> userRoles) {
        Set<RoleType> allowedRoles = userRoles.stream()
                .map(UserRole::getRoleType)
                .collect(Collectors.toSet());

        if (!allowedRoles.contains(role)) {
            throw new PermissionDeniedException(
                    String.format("Access denied: the role does not have '%s' permission.", role)
            );
        }
    }
    public static <E extends Enum<E>> void validateUserRole(E role, Set<E> allowedRoles, String message) {
        if (!allowedRoles.contains(role)) {
            throw new PermissionDeniedException(message);
        }
    }
    public static <E extends Enum<E>> void validateUserRole(E role, Set<E> allowedRoles, String message, Object... args) {
        if (!allowedRoles.contains(role)) {
            throw new PermissionDeniedException(String.format(message, args));
        }
    }

    public static boolean hasPermission(RoleType roleType, Set<RoleType> allowedRoles) {
        return allowedRoles.contains(roleType);
    }
}
