package com.example.gamebackend.service;

import com.example.gamebackend.dto.GameDTO;
import com.example.gamebackend.mapper.GameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@org.springframework.stereotype.Service
public class GameService {
    private final GameMapper gameMapper;

    public GameService(GameMapper gameMapper) {

        this.gameMapper = gameMapper;
    }

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);
    public GameDTO getUserDTOById(Integer seq){
        System.out.println("service : "+seq);
        logger.debug("getUserDTOById 호출됨");

        return gameMapper.selectUserDTOById(seq);
   }
}
