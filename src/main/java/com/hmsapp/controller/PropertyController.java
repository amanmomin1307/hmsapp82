package com.hmsapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    @PostMapping("/addProperty")
    public String addProperty(){
        return "Added";
    }

    @DeleteMapping("/deleteProperty")
    public String deleteProperty(){
        return "Deleted";
    }
}
