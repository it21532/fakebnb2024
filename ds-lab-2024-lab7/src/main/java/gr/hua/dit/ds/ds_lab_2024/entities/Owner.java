package gr.hua.dit.ds.ds_lab_2024.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "owner")
@DiscriminatorValue("OWNER")
public class Owner extends User {
    public Owner() {
        super();
    }

    public Owner(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}