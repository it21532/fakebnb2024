package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.ApplicationStatus;
import gr.hua.dit.ds.ds_lab_2024.entities.RentalApplication;
import gr.hua.dit.ds.ds_lab_2024.entities.Owner;
import gr.hua.dit.ds.ds_lab_2024.entities.property;
import gr.hua.dit.ds.ds_lab_2024.service.OwnerService;
import gr.hua.dit.ds.ds_lab_2024.service.RentalApplicationService;
import gr.hua.dit.ds.ds_lab_2024.service.propertyService;
import jakarta.transaction.Transactional;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Secured({"ROLE_OWNER", "ROLE_ADMIN"}) // Only owners and admins can access these endpoints.
public class OwnerDashboardController {

    private final OwnerService ownerService;
    private final RentalApplicationService rentalApplicationService;
    private final propertyService propertyService;


    public OwnerDashboardController(OwnerService ownerService,
                                    RentalApplicationService rentalApplicationService,
                                    propertyService propertyService) {
        this.ownerService = ownerService;
        this.rentalApplicationService = rentalApplicationService;
        this.propertyService = propertyService;
    }
    @GetMapping("/owner/dashboard/{ownerUsername}")
    @Transactional
    public String ownerDashboard(@PathVariable("ownerUsername") String ownerUsername, Model model) {
        Owner currentOwner = ownerService.getOwnerByUsername(ownerUsername);
        List<RentalApplication> pendingApplications = rentalApplicationService.getApplicationsForOwner(ownerUsername);
        List<property> myProperties = propertyService.getPropertiesByOwner(ownerUsername);
        model.addAttribute("owner", currentOwner);
        model.addAttribute("pendingApplications", pendingApplications);
        model.addAttribute("myProperties", myProperties);
        return "owner/dashboard";  // Points to templates/owner/dashboard.html.
    }

    @PostMapping("/owner/rentalRequests/{ownerUsername}/{appId}/approve")
    public String approveRentalRequest(@PathVariable("ownerUsername") String ownerUsername,
                                       @PathVariable("appId") Integer appId) {
        rentalApplicationService.updateApplicationStatus(appId, ApplicationStatus.APPROVED);
        return "redirect:/owner/dashboard/" + ownerUsername;
    }

    @PostMapping("/owner/rentalRequests/{ownerUsername}/{appId}/reject")
    public String rejectRentalRequest(@PathVariable("ownerUsername") String ownerUsername,
                                      @PathVariable("appId") Integer appId) {
        rentalApplicationService.updateApplicationStatus(appId, ApplicationStatus.REJECTED);
        return "redirect:/owner/dashboard/" + ownerUsername;
    }
}