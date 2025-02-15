package gr.hua.dit.ds.ds_lab_2024.service;

import gr.hua.dit.ds.ds_lab_2024.entities.Owner;
import gr.hua.dit.ds.ds_lab_2024.entities.PropertyStatus;
import gr.hua.dit.ds.ds_lab_2024.entities.property;
import gr.hua.dit.ds.ds_lab_2024.entities.Tenant;
import gr.hua.dit.ds.ds_lab_2024.repositories.propertyRepository;
import gr.hua.dit.ds.ds_lab_2024.repositories.ownerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class propertyService {

    private final propertyRepository propertyRepository;
    private final ownerRepository ownerRepository;

    public propertyService(propertyRepository propertyRepository, ownerRepository ownerRepository) {
        this.propertyRepository = propertyRepository;
        this.ownerRepository = ownerRepository;
    }

    @Transactional
    public List<property> getproperties(){
        return propertyRepository.findAll();
    }

    @Transactional
    public void saveproperty(property property) {
        propertyRepository.save(property);
    }

    @Transactional
    public property getproperty(String propertyTitle) {
        return propertyRepository.findById(propertyTitle).orElse(null);
    }

    @Transactional
    public void assignownerToproperty(String propertyTitle, Owner owner) {
        property property = propertyRepository.findById(propertyTitle).get();
        System.out.println(property);
        System.out.println(property.getOwner());
        property.setOwner(owner);
        System.out.println(property.getOwner());
        propertyRepository.save(property);
    }

    @Transactional
    public void unassignownerFromproperty(String propertyTitle) {
        property property = propertyRepository.findById(propertyTitle).get();
        property.setOwner(null);
        propertyRepository.save(property);
    }

    @Transactional
    public void assignTenantToproperty(String propertyTitle, Tenant tenant) {
        property property = propertyRepository.findById(propertyTitle).get();
        property.addTenant(tenant);
        System.out.println("property Tenants: ");
        System.out.println(property.getTenants());
        propertyRepository.save(property);
    }

    @Transactional
    public List<property> getPropertiesByStatus(PropertyStatus status) {
        return propertyRepository.findByStatus(status);
    }

    @Transactional
    public List<property> getPropertiesByOwner(String ownerUsername) {
        // This method assumes your propertyRepository has a corresponding query method.
        return propertyRepository.findByOwnerUsername(ownerUsername);
    }


    @Transactional
    public boolean existsByTitle(String title) {
        return propertyRepository.existsById(title);
    }

    @Transactional
    public List<property> getApprovedProperties() {
        return propertyRepository.findByStatus(PropertyStatus.APPROVED);
    }
    @Transactional
    public List<property> getPropertiesBySquareMetersRange(double minSquareMeters, double maxSquareMeters) {
        return propertyRepository.findBySquareMetersBetweenAndStatus(minSquareMeters, maxSquareMeters, PropertyStatus.APPROVED);
    }
    @Transactional
    public List<property> getPropertiesSorted(Sort sort) {
        return propertyRepository.findByStatus(PropertyStatus.APPROVED, sort);
    }
    @Transactional
    public List<property> getPropertiesByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return propertyRepository.findByPriceBetweenAndStatus(minPrice, maxPrice, PropertyStatus.APPROVED);
    }
    @Transactional
    public void deleteProperty(String title) {
        propertyRepository.deleteById(title);
    }
}