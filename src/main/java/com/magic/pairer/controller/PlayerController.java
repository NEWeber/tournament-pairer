package com.magic.pairer.controller;


import com.magic.pairer.model.MatchPlayers;
import com.magic.pairer.model.MatchResultByPlayer;
import com.magic.pairer.model.Player;
import com.magic.pairer.repository.PlayerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by nweber on 4/1/19.
 */

// FIXME: Handle NPE for non-existent match ids

@RestController
@RequestMapping("rs/")
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(value = "players", method = RequestMethod.GET)
    public List<Player> list() {
        return playerRepository.findAll();
    }
    @RequestMapping(value = "players", method = RequestMethod.POST)
    public Player create(@RequestBody Player player) {
        return playerRepository.saveAndFlush(player);
    }
    @RequestMapping(value = "players/{id}", method = RequestMethod.GET)
    public Player get(@PathVariable Long id) {
        return playerRepository.findOne(id);
    }
    @RequestMapping(value = "players/{id}", method = RequestMethod.PUT)
    public Player update(@PathVariable Long id, @RequestBody Player player) {
        Player existingPlayer = playerRepository.findOne(id);
        BeanUtils.copyProperties(player, existingPlayer);
        return playerRepository.saveAndFlush(existingPlayer);
    }
    @RequestMapping(value = "players/{id}", method = RequestMethod.DELETE)
    public Player delete(@PathVariable Long id) {
        Player existingPlayer = playerRepository.findOne(id);
        playerRepository.delete(existingPlayer);
        return existingPlayer;
    }
    @RequestMapping(value = "players/{id}/omw", method = RequestMethod.GET)
    public double getOmw(@PathVariable Long id) {
        Player existingPlayer = playerRepository.findOne(id);
        // TODO: only calculate if the player's OMW is not known (check/update round)
        // TODO: Optimize this!
        double opponentsTotalMatchWinPercent = 0d;
        int lengthCount = 0;
        for (Long opponentId : existingPlayer.getOpponentIds()) {
            Player opponent = playerRepository.findOne(opponentId);
            opponentsTotalMatchWinPercent += opponent.getPlayerMatchWinPercent();
            lengthCount += 1;
        }
        double opponentMatchWinPercent = opponentsTotalMatchWinPercent / lengthCount;
        double finalReturn = opponentMatchWinPercent < .33d ? .33d : opponentMatchWinPercent; // TODO: eliminate magic number
        existingPlayer.setOpponentMatchWinPercent(finalReturn); // TODO: also update which round this is from
        playerRepository.saveAndFlush(existingPlayer);
        return finalReturn;
    }
    @RequestMapping(value = "players/{id}/ogw", method = RequestMethod.GET)
    public double getOgw(@PathVariable Long id) {
        Player existingPlayer = playerRepository.findOne(id);
        // TODO: only calculate if the player's OGW is not known (check/update round)
        // TODO: Optimize this!
        double opponentsTotalGameWinPercent = 0d;
        int lengthCount = 0;
        for (Long opponentId : existingPlayer.getOpponentIds()) {
            Player opponent = playerRepository.findOne(opponentId);
            opponentsTotalGameWinPercent += opponent.getPlayerGameWinPercent();
            lengthCount += 1;
        }
        double opponentGameWinPercent = opponentsTotalGameWinPercent / lengthCount;
        double finalReturn = opponentGameWinPercent < .33d ? .33d : opponentGameWinPercent; // TODO: eliminate magic number
        existingPlayer.setOpponentGameWinPercent(finalReturn); // TODO: also update which round this is from
        playerRepository.saveAndFlush(existingPlayer);
        return finalReturn;
    }
    public void addPairOfPlayersMatch(Long matchId, MatchPlayers matchPlayers) {
        addMatchPlayedIn(matchId, matchPlayers.getFirstPlayerId());
        addMatchPlayedIn(matchId, matchPlayers.getSecondPlayerId());
    }
    private void addMatchPlayedIn(Long matchId, Long playerId) {
        Player existingPlayer = playerRepository.findOne(playerId);
        existingPlayer.pairInMatchId(matchId);
        playerRepository.saveAndFlush(existingPlayer);
    }
    public void roundCompleteForPairOfPlayers(MatchPlayers matchPlayers, MatchResultByPlayer matchResult) {
        Long winnerId = matchResult.getWinnerId();
        Long firstPlayerId = matchPlayers.getFirstPlayerId();
        Long secondPlayerId = matchPlayers.getSecondPlayerId();
        Long gamesPlayed = matchResult.getFirstPlayerWins() + matchResult.getSecondPlayerWins();
        Long firstPlayerWins = matchResult.getFirstPlayerWins();
        Long secondPlayerWins = matchResult.getSecondPlayerWins();
        // TODO: write this cleaner
        if (winnerId == -1) { // draw
           roundComplete(firstPlayerId, "draw", secondPlayerId, gamesPlayed, firstPlayerWins);
           roundComplete(secondPlayerId, "draw", firstPlayerId, gamesPlayed, secondPlayerWins);
        } else if (winnerId == firstPlayerId) {
            roundComplete(firstPlayerId, "win", secondPlayerId, gamesPlayed, firstPlayerWins);
            roundComplete(secondPlayerId, "lose", firstPlayerId, gamesPlayed, secondPlayerWins);
        } else if (winnerId == secondPlayerId) {
            roundComplete(firstPlayerId, "lose", secondPlayerId, gamesPlayed, firstPlayerWins);
            roundComplete(secondPlayerId, "win", firstPlayerId,  gamesPlayed, secondPlayerWins);
        } else {
            // TODO: throw error
        }
    }
    private void roundComplete(Long playerId, String winLoseDraw, Long opponentId, Long gamesPlayed, Long playerGameWins) {
        Player existingPlayer = playerRepository.findOne(playerId);
        existingPlayer.roundComplete(winLoseDraw, opponentId, gamesPlayed, playerGameWins);
        playerRepository.saveAndFlush(existingPlayer);
    }
}
