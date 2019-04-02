package com.magic.pairer.controller;


import com.magic.pairer.model.Player;
import com.magic.pairer.repository.PlayerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by nweber on 4/1/19.
 */

@RestController
@RequestMapping("rs/")
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(value = "players", method = RequestMethod.GET)
    public List<Player> list() {
        return playerRepository.findAll();
    }
    @RequestMapping(value="players", method = RequestMethod.POST)
    public Player create(@RequestBody Player player) {
        return playerRepository.saveAndFlush(player);
    }
    @RequestMapping(value="players/{id}", method = RequestMethod.GET)
    public Player get(@PathVariable Long id) {
        return playerRepository.findOne(id);
    }
    @RequestMapping(value = "players/{id}", method = RequestMethod.PUT)
    public Player update(@PathVariable Long id, @RequestBody Player player) {
        Player existingPlayer = playerRepository.findOne(id);
        BeanUtils.copyProperties(player, existingPlayer);
        return PlayerStub.update(id, player);
    }
    @RequestMapping(value = "players/{id}", method = RequestMethod.DELETE)
    public Player delete(@PathVariable Long id) {
        Player existingPlayer = playerRepository.findOne(id);
        playerRepository.delete(existingPlayer);
        return existingPlayer;
    }
}
