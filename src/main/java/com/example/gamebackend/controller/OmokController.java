package com.example.gamebackend.controller;

import com.example.gamebackend.dto.OmokDTO;
import com.example.gamebackend.service.OmokService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OmokController {

    private final OmokService omokService;

    @Autowired
    public OmokController(OmokService omokService) {

        this.omokService = omokService;

    }

    @PostMapping("/omok/place")
    public String place(@RequestBody OmokDTO omokDTO){
        return omokService.judge(omokDTO);

    }
}
