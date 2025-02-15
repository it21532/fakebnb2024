package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.RentalApplication;
import gr.hua.dit.ds.ds_lab_2024.entities.Tenant;
import gr.hua.dit.ds.ds_lab_2024.service.TenantService;
import jakarta.transaction.Transactional;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import gr.hua.dit.ds.ds_lab_2024.service.RentalApplicationService;

import java.util.List;

@Controller
@Secured({"ROLE_TENANT", "ROLE_ADMIN"}) // Only tenants and admins may access this endpoint.
public class TenantDashboardController {

    private final TenantService tenantService;
    private final RentalApplicationService rentalApplicationService;

    public TenantDashboardController(TenantService tenantService, RentalApplicationService rentalApplicationService) {
        this.tenantService = tenantService;
        this.rentalApplicationService = rentalApplicationService;
    }

    @GetMapping("/tenant/dashboard/{tenantUsername}")
    @Transactional
    public String tenantDashboard(@PathVariable("tenantUsername") String tenantUsername, Model model) {
        Tenant currentTenant = tenantService.getTenantByUsername(tenantUsername);
        model.addAttribute("tenant", currentTenant);
        List<RentalApplication> rejectedApplications = rentalApplicationService.getRejectedApplicationsForTenant(tenantUsername);
        model.addAttribute("rejectedApplications", rejectedApplications);
        List<RentalApplication> approvedApps = rentalApplicationService.getApprovedApplicationsForTenant(tenantUsername);
        model.addAttribute("approvedApplications", approvedApps);
        return "tenant/dashboard";
    }
}