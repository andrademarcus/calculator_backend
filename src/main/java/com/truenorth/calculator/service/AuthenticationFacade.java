package com.truenorth.calculator.service;

import com.truenorth.calculator.response.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId() {
        if (getAuthentication() != null && getAuthentication().getPrincipal() != null && getAuthentication().getPrincipal() instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) getAuthentication().getPrincipal()).getId();
        }
        return null;
    }

}