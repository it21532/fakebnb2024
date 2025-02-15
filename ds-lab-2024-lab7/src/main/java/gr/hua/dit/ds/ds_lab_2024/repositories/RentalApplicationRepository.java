package gr.hua.dit.ds.ds_lab_2024.repositories;

import gr.hua.dit.ds.ds_lab_2024.entities.RentalApplication;
import gr.hua.dit.ds.ds_lab_2024.entities.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RentalApplicationRepository extends JpaRepository<RentalApplication, Integer> {
    List<RentalApplication> findByStatus(ApplicationStatus status);

    // Find applications for a tenant (by tenant username) with a given status.
    List<RentalApplication> findByTenantUsernameAndStatus(String tenantUsername, ApplicationStatus status);

    // Find applications for properties owned by a given owner (by owner username) and with a given status.
    List<RentalApplication> findByPropertyOwnerUsernameAndStatus(String ownerUsername, ApplicationStatus status);
}