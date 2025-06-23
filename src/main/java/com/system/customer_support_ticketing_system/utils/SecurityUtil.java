package com.system.customer_support_ticketing_system.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {
    public static Long getUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
