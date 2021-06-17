package de.neuefische.backend.controller;

import de.neuefische.backend.model.MovieOmdbOverview;
import de.neuefische.backend.model.OmdbDetails;
import de.neuefische.backend.service.MovieApiService;
import de.neuefische.backend.service.OmdbDetailsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/details/")
public class OmdbDetailsController {

        private OmdbDetailsApiService omdbDetailsApiService;

        @Autowired
        public OmdbDetailsController(OmdbDetailsApiService omdbDetailsApiService){
            this.omdbDetailsApiService = omdbDetailsApiService;
        }

        @GetMapping("{id}")
        public OmdbDetails getDetails(@PathVariable String id){
            return omdbDetailsApiService.getDetails(id);
        }


}
