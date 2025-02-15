package gr.hua.dit.ds.ds_lab_2024.service;

import gr.hua.dit.ds.ds_lab_2024.entities.ApplicationStatus;
import gr.hua.dit.ds.ds_lab_2024.entities.RentalApplication;
import gr.hua.dit.ds.ds_lab_2024.entities.Tenant;
import gr.hua.dit.ds.ds_lab_2024.entities.property;
import gr.hua.dit.ds.ds_lab_2024.repositories.RentalApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalApplicationService {

    private final RentalApplicationRepository rentalApplicationRepository;

    public RentalApplicationService(RentalApplicationRepository rentalApplicationRepository) {
        this.rentalApplicationRepository = rentalApplicationRepository;
    }

    @Transactional
    public RentalApplication submitApplication(Tenant tenant, property property) {
        RentalApplication application = new RentalApplication(tenant, property);
        return rentalApplicationRepository.save(application);
    }

    @Transactional
    public List<RentalApplication> getApplicationsByStatus(ApplicationStatus status) {
        return rentalApplicationRepository.findByStatus(status);
    }

    @Transactional
    public List<RentalApplication> getApplicationsForOwner(String ownerUsername) {
        return rentalApplicationRepository.findByPropertyOwnerUsernameAndStatus(ownerUsername, ApplicationStatus.SUBMITTED);
    }

    // Return rejected applications for a tenant
    @Transactional
    public List<RentalApplication> getRejectedApplicationsForTenant(String tenantUsername) {
        return rentalApplicationRepository.findByTenantUsernameAndStatus(tenantUsername, ApplicationStatus.REJECTED);
    }

//    @Transactional
//    public List<RentalApplication> getApplicationsForTenant(String tenantUsername) {
//        // Ensure your RentalApplicationRepository defines this method:
//        return rentalApplicationRepository.findByTenantUsername(tenantUsername);
//    }

    @Transactional
    public RentalApplication getApplicationById(Integer id) {
        return rentalApplicationRepository.findById(id).orElse(null);
    }

    @Transactional
    public RentalApplication updateApplicationStatus(Integer id, ApplicationStatus status) {
        RentalApplication application = getApplicationById(id);
        if (application != null) {
            application.setStatus(status);
            rentalApplicationRepository.save(application);
        }
        return application;
    }
    @Transactional
    public List<RentalApplication> getApprovedApplicationsForTenant(String tenantUsername) {
        return rentalApplicationRepository.findByTenantUsernameAndStatus(tenantUsername, ApplicationStatus.APPROVED);
    }
}