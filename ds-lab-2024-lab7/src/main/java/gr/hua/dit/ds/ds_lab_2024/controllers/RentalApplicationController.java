package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.ApplicationStatus;
import gr.hua.dit.ds.ds_lab_2024.entities.RentalApplication;
import gr.hua.dit.ds.ds_lab_2024.entities.Tenant;
import gr.hua.dit.ds.ds_lab_2024.entities.property;
import gr.hua.dit.ds.ds_lab_2024.service.RentalApplicationService;
import gr.hua.dit.ds.ds_lab_2024.service.TenantService;
import gr.hua.dit.ds.ds_lab_2024.service.propertyService;
import jakarta.transaction.Transactional;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@Secured("ROLE_TENANT")
@RequestMapping("/rental")
public class RentalApplicationController {

    private final RentalApplicationService rentalApplicationService;
    private final TenantService tenantService;
    private final propertyService propertyService;

    public RentalApplicationController(RentalApplicationService rentalApplicationService,
                                       TenantService tenantService,
                                       propertyService propertyService) {
        this.rentalApplicationService = rentalApplicationService;
        this.tenantService = tenantService;
        this.propertyService = propertyService;
    }

    // Show form for tenants to apply for a property.
    // Now uses the property's title as identifier.
    @GetMapping("/apply/{propertyTitle}")
    @Transactional
    public String showApplicationForm(@PathVariable("propertyTitle") String propertyTitle, Model model, Principal principal) {
        property property = propertyService.getproperty(propertyTitle);
        model.addAttribute("property", property);
        // Bind an empty RentalApplication object to the form.
        RentalApplication application = new RentalApplication();
        model.addAttribute("application", application);
        return "rental/apply"; // Thymeleaf template for application submission.
    }

    // Process the rental application submission.
    @PostMapping("/apply/{propertyTitle}")
    public String submitApplication(@PathVariable("propertyTitle") String propertyTitle,
                                    @ModelAttribute("application") RentalApplication application,
                                    Principal principal,
                                    Model model) {
        // Retrieve the logged-in tenant using their username.
        String tenantUsername = principal.getName();
        Tenant tenant = tenantService.getTenantByUsername(tenantUsername);
        property property = propertyService.getproperty(propertyTitle);
        rentalApplicationService.submitApplication(tenant, property);
        model.addAttribute("successMessage", "Rental application submitted successfully!");
        return "redirect:/property"; // Redirect to the property list or tenant dashboard.
    }

    // Admin view: List pending rental applications.
    @Secured("ROLE_ADMIN")
    @GetMapping("/pending")
    public String viewPendingApplications(Model model) {
        List<RentalApplication> pendingApplications = rentalApplicationService.getApplicationsByStatus(ApplicationStatus.SUBMITTED);
        model.addAttribute("pendingApplications", pendingApplications);
        return "admin/pendingRentalApplications"; // Thymeleaf template.
    }

    // Admin action: Approve a rental application.
    @Secured("ROLE_ADMIN")
    @PostMapping("/{id}/approve")
    public String approveApplication(@PathVariable int id) {
        rentalApplicationService.updateApplicationStatus(id, ApplicationStatus.APPROVED);
        return "redirect:/rental/pending";
    }

    // Admin action: Reject a rental application.
    @Secured("ROLE_ADMIN")
    @PostMapping("/{id}/reject")
    public String rejectApplication(@PathVariable int id) {
        rentalApplicationService.updateApplicationStatus(id, ApplicationStatus.REJECTED);
        return "redirect:/rental/pending";
    }
}