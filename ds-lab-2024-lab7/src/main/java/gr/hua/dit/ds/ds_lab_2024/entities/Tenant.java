package gr.hua.dit.ds.ds_lab_2024.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tenant")
@DiscriminatorValue("TENANT")
public class Tenant extends User {

    public Tenant() {
        super();
    }

    public Tenant(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}