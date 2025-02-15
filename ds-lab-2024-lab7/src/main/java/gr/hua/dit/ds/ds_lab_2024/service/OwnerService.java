package gr.hua.dit.ds.ds_lab_2024.service;

import gr.hua.dit.ds.ds_lab_2024.entities.Owner;
import gr.hua.dit.ds.ds_lab_2024.repositories.propertyRepository;
import gr.hua.dit.ds.ds_lab_2024.repositories.ownerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private ownerRepository ownerRepository;

    private propertyRepository propertyRepository;

    public OwnerService(ownerRepository ownerRepository, propertyRepository propertyRepository) {
        this.ownerRepository = ownerRepository;
        this.propertyRepository = propertyRepository;
    }
    public Owner getOwnerByUsername(String username) {
        return ownerRepository.findByUsername(username).orElse(null);
    }
    @Transactional
    public void saveowner(Owner owner) {
        ownerRepository.save(owner);
    }
    @Transactional
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }
}
