package com.hmsapp.repository;

import com.hmsapp.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    //JPQL TO SEARCH PROPERTY BASED ON CITY AND COUNTRY
    @Query("select p from Property p join p.city c join p.country co where c.name=:searchParam or co.name=:searchParam")
    List<Property> searchProperty(
        @Param("searchParam") String searchParam
    );
}