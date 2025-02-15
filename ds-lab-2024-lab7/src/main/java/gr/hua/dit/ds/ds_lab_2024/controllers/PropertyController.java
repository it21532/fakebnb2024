package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.PropertyStatus;
import gr.hua.dit.ds.ds_lab_2024.entities.property;
import gr.hua.dit.ds.ds_lab_2024.service.propertyService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/property")
public class PropertyController {

    private final propertyService propertyService;

    public PropertyController(propertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/search")
    @Transactional
    public String searchProperties(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "minSquareMeters", required = false) Double minSquareMeters,
            @RequestParam(value = "maxSquareMeters", required = false) Double maxSquareMeters,
            @RequestParam(value = "sortCriteria", required = false) String sortCriteria,
            Model model) {

        List<property> properties;

        if ("price".equalsIgnoreCase(filterType) && minPrice != null && maxPrice != null) {
            properties = propertyService.getPropertiesByPriceRange(minPrice, maxPrice);
        } else if ("squareMeters".equalsIgnoreCase(filterType) && minSquareMeters != null && maxSquareMeters != null) {
            properties = propertyService.getPropertiesBySquareMetersRange(minSquareMeters, maxSquareMeters);
        } else if (sortCriteria != null) {
            Sort sort;
            switch (sortCriteria) {
                case "priceAsc":
                    sort = Sort.by("price").ascending();
                    break;
                case "priceDesc":
                    sort = Sort.by("price").descending();
                    break;
                case "squareMetersAsc":
                    sort = Sort.by("squareMeters").ascending();
                    break;
                case "squareMetersDesc":
                    sort = Sort.by("squareMeters").descending();
                    break;
                default:
                    sort = Sort.unsorted();
                    break;
            }
            properties = propertyService.getPropertiesSorted(sort);
        } else {
            properties = propertyService.getApprovedProperties();
        }
        model.addAttribute("properties", properties);
        return "property/properties";
    }
}