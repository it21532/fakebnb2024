package gr.hua.dit.ds.ds_lab_2024.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "property")
public class property {

    // Use title as the primary key.
    @Id
    @Column(name = "title", nullable = false, unique = true)
    @NotEmpty(message = "Title is required")
    @Size(min = 3, max = 50)
    private String title;

    @Column(nullable = false)
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Square meters is required")
    @Positive(message = "Square meters must be positive")
    @Column(nullable = false)
    private double squareMeters;

    @NotEmpty(message = "Address is required")
    @Column(nullable = false)
    private String address;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "owner_username")
    private Owner owner;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "property_Tenant",
            joinColumns = @JoinColumn(name = "property_title"),
            inverseJoinColumns = @JoinColumn(name = "tenant_username"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"property_title", "tenant_username"})
    )
    private List<Tenant> tenants = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PropertyStatus status;

    public property() {
        this.status = PropertyStatus.PENDING;
    }

    public property(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
        this.status = PropertyStatus.PENDING;
    }

    // Getters and setters

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Owner getOwner() {
        return owner;
    }
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }
    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }
    public void addTenant(Tenant tenant) {
        this.tenants.add(tenant);
    }

    public PropertyStatus getStatus() {
        return status;
    }
    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    public double getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(double squareMeters) {
        this.squareMeters = squareMeters;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "property{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", owner=" + owner +
                ", status=" + status +
                '}';
    }
}