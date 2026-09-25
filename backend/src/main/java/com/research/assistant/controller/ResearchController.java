package com.research.assistant.controller;


import com.research.assistant.dto.ResearchRequest;
import com.research.assistant.service.ResearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/research")
@CrossOrigin(origins = "*")
public class ResearchController {

    private final ResearchService researchService;

    @PostMapping
    public ResponseEntity<String> getResearch(@RequestBody ResearchRequest researchRequest) {
        String result = researchService.getResearch(researchRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}