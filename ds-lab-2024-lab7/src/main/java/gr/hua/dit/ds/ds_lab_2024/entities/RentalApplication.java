package gr.hua.dit.ds.ds_lab_2024.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "rental_application")
public class RentalApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tenant_username", referencedColumnName = "username")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "property_title", referencedColumnName = "title")
    private property property;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public RentalApplication() {
        this.status = ApplicationStatus.SUBMITTED;
    }

    public RentalApplication(Tenant tenant, property property) {
        this.tenant = tenant;
        this.property = property;
        this.status = ApplicationStatus.SUBMITTED;
    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Tenant getTenant() {
        return tenant;
    }
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public property getProperty() {
        return property;
    }
    public void setProperty(property property) {
        this.property = property;
    }

    public ApplicationStatus getStatus() {
        return status;
    }
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}