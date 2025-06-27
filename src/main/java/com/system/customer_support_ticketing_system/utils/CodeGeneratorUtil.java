package com.system.customer_support_ticketing_system.utils;

import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
public class CodeGeneratorUtil {
    private static final SecureRandom random = new SecureRandom();

    public String generate6DigitCode(){
        return String.format("%06d", random.nextInt(1_000_000));
    }
}
