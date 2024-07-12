package com.example.GestionCours.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = getRole(authentication.getAuthorities());
        String targetUrl = determineTargetUrl(role);

        // Vous pouvez personnaliser le message de succès ici ou rediriger vers une autre page
        String successMessage = "Connexion réussie pour " + userDetails.getUsername();
        request.getSession().setAttribute("successMessage", successMessage);

        // Rediriger vers une page de succès après la connexion réussie
        response.sendRedirect(targetUrl);
    }

    private String getRole(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ENSEIGNANT")) {
                return "ENSEIGNANT";
            } else if (authority.getAuthority().equals("ROLE_APPRENANT")) {
                return "APPRENANT";
            }
        }
        return null;
    }

    private String determineTargetUrl(String role) {
        if ("ENSEIGNANT".equals(role)) {
            return "/admin/dashboard";
        } else if ("APPRENANT".equals(role)) {
            return "/admin/view_cours";
        } else {
            throw new IllegalStateException("Unknown role: " + role);
        }
    }
}
