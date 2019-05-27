package com.magic.pairer.controller;

import com.magic.pairer.model.Match;
import com.magic.pairer.model.MatchPlayers;
import com.magic.pairer.model.MatchResultByWinner;
import com.magic.pairer.controller.PlayerController;

import com.magic.pairer.model.MatchResultByWinner;
import com.magic.pairer.repository.MatchRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rs/")
public class MatchController {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private PlayerController playerController;

    @RequestMapping(value = "matches", method = RequestMethod.GET)
    public List<Match> list() {
        return matchRepository.findAll();
    }
    @RequestMapping(value = "matches", method = RequestMethod.POST)
    public Match create(@RequestBody MatchPlayers matchPlayers) {
        Match newMatch = new Match(matchPlayers);
        Match currentMatch = matchRepository.saveAndFlush(newMatch);
        playerController.addPairOfPlayersMatch(currentMatch.getId(), matchPlayers);
        return currentMatch;
    }
    @RequestMapping(value = "matches/{id}", method = RequestMethod.GET)
    public Match get(@PathVariable Long id) {
        return matchRepository.findOne(id);
    }
    @RequestMapping(value = "matches/{id}", method = RequestMethod.PUT)
    public Match update(@PathVariable Long id, @RequestBody Match match) {
        Match existingMatch = matchRepository.findOne(id);
        BeanUtils.copyProperties(match, existingMatch);
        return matchRepository.saveAndFlush(existingMatch);
    }
    @RequestMapping(value = "matches/{id}", method = RequestMethod.DELETE)
    public Match delete(@PathVariable Long id) {
        Match existingMatch = matchRepository.findOne(id);
        matchRepository.delete(existingMatch);
        return existingMatch;
    }
    // Make winner id -1 if a draw
    @RequestMapping(value="matches/{id}/results", method = RequestMethod.PUT)
        public Match setMatchResult(@PathVariable Long id, @RequestBody MatchResultByWinner matchResult) {
        Match existingMatch = matchRepository.findOne(id);
        if(existingMatch.matchComplete == false) {
            existingMatch.setMatchResults(matchResult);
            playerController.roundCompleteForPairOfPlayers(existingMatch.getMatchPlayers(), existingMatch.getMatchResultsByPlayer());
            matchRepository.saveAndFlush(existingMatch);
        }
        return existingMatch;
    }
}
