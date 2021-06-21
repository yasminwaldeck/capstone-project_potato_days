package de.neuefische.backend.controller;

import de.neuefische.backend.model.OMDb.OmdbDetails;
import de.neuefische.backend.model.OMDb.OmdbOverview;
import de.neuefische.backend.service.OmdbApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/omdb")
public class OmdbController {

    private OmdbApiService omdbApiService;

    @Autowired
    public OmdbController(OmdbApiService omdbApiService){
        this.omdbApiService = omdbApiService;
    }

    @GetMapping
    public List<OmdbOverview> searchByString(@RequestParam String searchString, String type){
        return omdbApiService.searchByString(searchString, type);
    }


    @GetMapping("/details/{id}")
    public OmdbDetails getDetails(@PathVariable String id){
        return omdbApiService.getDetails(id);
    }


}
