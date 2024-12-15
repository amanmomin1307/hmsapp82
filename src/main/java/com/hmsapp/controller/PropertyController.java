package com.hmsapp.controller;

import com.hmsapp.entity.Property;
import com.hmsapp.repository.PropertyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private final PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addProperty")
    public String addProperty(){
        return "Added";
    }

    @DeleteMapping("/deleteProperty")
    public String deleteProperty(){
        return "Deleted";
    }

    //http://localhost:8080/api/v1/property/{serchParam}
    @GetMapping("/{searchParam}")
    public List<Property> searchProperty(
        @PathVariable String searchParam
    ){
        return propertyRepository.searchProperty(searchParam);
    }
}
