package gr.hua.dit.ds.ds_lab_2024.service;

import gr.hua.dit.ds.ds_lab_2024.entities.Tenant;
import gr.hua.dit.ds.ds_lab_2024.repositories.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;

@Service
public class TenantService {

    private final TenantRepository TenantRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public TenantService(TenantRepository TenantRepository,
                         BCryptPasswordEncoder passwordEncoder) {
        this.TenantRepository = TenantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Tenant getTenantByUsername(String username) {
        return TenantRepository.findByUsername(username).orElse(null);
    }
    @Transactional
    public List<Tenant> getAllTenants() {
        return TenantRepository.findAll();
    }
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}

