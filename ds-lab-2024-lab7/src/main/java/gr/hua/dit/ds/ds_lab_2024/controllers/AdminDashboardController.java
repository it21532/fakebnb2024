package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Owner;
import gr.hua.dit.ds.ds_lab_2024.entities.PropertyStatus;
import gr.hua.dit.ds.ds_lab_2024.entities.Tenant;
import gr.hua.dit.ds.ds_lab_2024.entities.property;
import gr.hua.dit.ds.ds_lab_2024.service.propertyService;
import jakarta.transaction.Transactional;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import gr.hua.dit.ds.ds_lab_2024.service.TenantService;
import gr.hua.dit.ds.ds_lab_2024.service.OwnerService;

import java.util.List;

@Controller
@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
public class AdminDashboardController {

    private final propertyService propertyService;
    private final TenantService TenantService;
    private final OwnerService OwnerService;

    public AdminDashboardController(propertyService propertyService,OwnerService OwnerService, TenantService TenantService) {
        this.propertyService = propertyService;
        this.TenantService = TenantService;
        this.OwnerService = OwnerService;
    }

    // Display the admin dashboard with pending properties.
    @GetMapping("/dashboard")
    @Transactional
    public String adminDashboard(Model model) {
        List<property> pendingProperties = propertyService.getPropertiesByStatus(PropertyStatus.PENDING);
        model.addAttribute("pendingProperties", pendingProperties);
        return "admin/dashboard"; // Points to the Thymeleaf template: templates/admin/dashboard.html.
    }

    // Approve a property submission by property title.
    @PostMapping("/dashboard/{title}/approve")
    @Transactional
    public String approveProperty(@PathVariable String title) {
        property prop = propertyService.getproperty(title);
        if (prop != null) {
            prop.setStatus(PropertyStatus.APPROVED);
            propertyService.saveproperty(prop);
        }
        return "redirect:/admin/dashboard";
    }

    // Reject a property submission by property title.
    @PostMapping("/dashboard/{title}/reject")
    @Transactional
    public String rejectProperty(@PathVariable String title) {
        property prop = propertyService.getproperty(title);
        if (prop != null) {
            prop.setStatus(PropertyStatus.REJECTED);
            propertyService.saveproperty(prop);
        }
        return "redirect:/admin/dashboard";
    }
    @GetMapping("/tenants")
    @Transactional
    public String listTenants(Model model) {
        List<Tenant> tenants = TenantService.getAllTenants(); // Make sure this method is implemented in TenantService
        model.addAttribute("tenants", tenants);
        return "admin/tenants"; // Create a Thymeleaf template at templates/admin/tenants.html
    }

    // List all owners
    @GetMapping("/owners")
    @Transactional
    public String listOwners(Model model) {
        List<Owner> owners = OwnerService.getAllOwners(); // Make sure this method is implemented in OwnerService
        model.addAttribute("owners", owners);
        return "admin/owners"; // Create a Thymeleaf template at templates/admin/owners.html
    }
}