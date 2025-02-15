package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.property;
import gr.hua.dit.ds.ds_lab_2024.entities.PropertyStatus;
import gr.hua.dit.ds.ds_lab_2024.service.propertyService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/property")
@Secured("ROLE_ADMIN")
public class AdminPropertyController {

    private final propertyService propertyService;

    public AdminPropertyController(propertyService propertyService) {
        this.propertyService = propertyService;
    }

    // Display all pending properties for review.

    @GetMapping("/pending")
    public String showPendingProperties(Model model) {
        List<property> pendingProperties = propertyService.getPropertiesByStatus(PropertyStatus.PENDING);
        model.addAttribute("pendingProperties", pendingProperties);
        return "admin/pendingProperties"; // Corresponding Thymeleaf template.
    }

    // Approve a property.
    @PostMapping("/{title}/approve")
    public String approveProperty(@PathVariable String title) {
        property prop = propertyService.getproperty(title);
        if (prop != null) {
            prop.setStatus(PropertyStatus.APPROVED);
            propertyService.saveproperty(prop);
        }
        return "redirect:/admin/property/pending";
    }

    // Reject a property.
    @PostMapping("/{title}/reject")
    public String rejectProperty(@PathVariable String title) {
        property prop = propertyService.getproperty(title);
        if (prop != null) {
            prop.setStatus(PropertyStatus.REJECTED);
            propertyService.saveproperty(prop);
        }
        return "redirect:/admin/property/pending";
    }
}