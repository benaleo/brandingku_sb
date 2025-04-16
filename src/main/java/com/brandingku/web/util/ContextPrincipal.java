package com.brandingku.web.util;

import com.brandingku.web.entity.Users;
import com.brandingku.web.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class ContextPrincipal {

    private static UserRepository usersRepository;

    public ContextPrincipal(UserRepository usersRepository) {
        ContextPrincipal.usersRepository = usersRepository;
    }

    public static Long getId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        
        if (principal instanceof Users) {
            return ((Users) principal).getId();
        } else if (principal instanceof User) {
            User user = (User) principal;
            Users usersEntity = usersRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
            return usersEntity.getId();
        }
        throw new IllegalArgumentException("Unknown principal type: " + principal.getClass());
    }

    public static String getSecureUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        
        if (principal instanceof Users) {
            return ((Users) principal).getSecureId();
        } else if (principal instanceof User) {
            User user = (User) principal;
            Users usersEntity = usersRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
            return usersEntity.getSecureId();
        }
        throw new IllegalArgumentException("Unknown principal type: " + principal.getClass());
    }
}
