package com.example.gamebackend.controller;


import com.example.gamebackend.dto.GameDTO;
import com.example.gamebackend.service.GameService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @GetMapping("/user/{seq}")
    public String home(@PathVariable String seq) {

        logger.debug("Received seq: {}", seq);

        int seqInt = Integer.parseInt(seq);

        GameDTO dto = gameService.getUserDTOById(seqInt);
        System.out.println("안녕");
        return dto.getEmail();
    }

}



