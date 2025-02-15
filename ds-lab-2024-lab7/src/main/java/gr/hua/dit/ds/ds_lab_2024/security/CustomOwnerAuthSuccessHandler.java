package gr.hua.dit.ds.ds_lab_2024.security;

import gr.hua.dit.ds.ds_lab_2024.entities.Owner;
import gr.hua.dit.ds.ds_lab_2024.service.OwnerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomOwnerAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final OwnerService ownerService;

    public CustomOwnerAuthSuccessHandler(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        // Get the username from the authentication principal.
        String username = authentication.getName();
        // Retrieve the owner using the username.
        Owner currentOwner = ownerService.getOwnerByUsername(username);

        if (currentOwner != null) {
            // Redirect to the dashboard using the owner's username.
            response.sendRedirect(request.getContextPath() + "/owner/dashboard/" + currentOwner.getUsername());
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}