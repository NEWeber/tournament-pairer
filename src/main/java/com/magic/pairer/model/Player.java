package com.magic.pairer.model;


import com.magic.pairer.repository.MatchRepository;
import com.magic.pairer.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.LinkedList;

@Entity
//public class Player implements Comparable<Player> {
public class Player {
//    @Autowired
//    private PlayerRepository playerRepository;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private LinkedList<Long> matches = new LinkedList<>();
    private LinkedList<Long> opponentIds = new LinkedList<>();
    private Long totalMatchPoints = 0L;
    private Long recordThroughRound = 0L;
    private Long matchesPlayed = 0L;
    private Long matchesWon = 0L;
    private Long gamesPlayed = 0L;
    private Long gamesWon = 0L;
    private double playerMatchWinPercent = 0d;
    private double playerGameWinPercent = 0d;



//    public int compareTo(Player otherPlayer) {
//        if (totalMatchPoints > otherPlayer.getTotalMatchPoints()) {
//            return -1;
//        } else if (totalMatchPoints < otherPlayer.getTotalMatchPoints()) {
//            return 1;
//        }
//        // go to opponent Match win percent tie breaker
//        if (getOpponentMatchWinPercent() > otherPlayer.getOpponentMatchWinPercent()) {
//            return -1;
//        } else if (getOpponentMatchWinPercent() < otherPlayer.getOpponentMatchWinPercent()) {
//            return 1;
//        }
//        // go to player game win percent tie breaker
//        if (playerGameWinPercent > otherPlayer.getPlayerGameWinPercent()) {
//            return -1;
//        } else if (playerGameWinPercent > otherPlayer.getPlayerGameWinPercent()) {
//            return 1;
//        }
//        // go to opponent game win percent tie breaker
//        if (getOpponentGameWinPercent() > otherPlayer.getOpponentGameWinPercent()) {
//            return -1;
//        } else if (getOpponentGameWinPercent() < otherPlayer.getOpponentGameWinPercent()) {
//            return 1;
//        }
//        // last tiebreaker is a coin flip
//        return Math.random() < .5 ? 1 : -1;
//    }

    public Player() {
    }

    public Player(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getRecordThroughRound() {
        return recordThroughRound;
    }

    public Long incrementRound() {
        // TODO: this can be done in one line
        if (recordThroughRound == null) {
            recordThroughRound = 1L;
        } else {
            recordThroughRound += 1;
        }
        return recordThroughRound;
    }

    public void pairInMatchId(Long matchId) {
        matches.addLast(matchId);
    }

    public Long getTotalMatchPoints() {
        return totalMatchPoints;
    }

    // Add matchId and verify that it's the correct round/match?
     public void roundComplete(String winLoseDraw, Long opponentId, Long gamesPlayed, Long gamesWon) {
        matchesPlayed += 1;
        this.gamesPlayed += gamesPlayed;
        this.gamesWon += gamesWon;
        // TODO: make number of points for a win and a draw a constant
        if (winLoseDraw == "win") {
            totalMatchPoints += 3; // TODO: eliminate magic number
            matchesWon += 1;
        } else if (winLoseDraw == "draw") {
            totalMatchPoints += 1; // TODO: eliminate magic number
        }
        opponentIds.addLast(opponentId);
        incrementRound();
        updatePlayerGameWinPercent();
        updatePlayerMatchWinPercent();
    }

    private void updatePlayerMatchWinPercent() {
        this.playerMatchWinPercent = this.matchesWon / this.matchesPlayed;
    }

    private void updatePlayerGameWinPercent() {
        this.playerGameWinPercent = this.gamesWon / this.gamesPlayed;
    }

    public double getPlayerGameWinPercent() {
        // TODO: make minimum threshold a constant
        return playerGameWinPercent < .33d ? .33d : playerGameWinPercent; // TODO: eliminate magic number
    }

    public double getPlayerMatchWinPercent() {
        return playerMatchWinPercent < .33d ? .33d : playerMatchWinPercent; // TODO: eliminate magic number
    }

    public LinkedList<Long> getOpponentIds() {
        return opponentIds;
    }

//    // TODO: Optimize this!
//    public double getOpponentMatchWinPercent() {
//        double opponentsTotalWinPercent = 0d;
//        int lengthCount = 0;
//        for (Long opponentId : opponentIds) {
//            Player opponent = playerRepository.findOne(opponentId);
//            opponentsTotalWinPercent += opponent.getPlayerMatchWinPercent();
//            lengthCount += 1;
//        }
//        double opponentMatchWinPercent = opponentsTotalWinPercent / lengthCount;
//        return opponentMatchWinPercent < .33d ? .33d : opponentMatchWinPercent; // TODO: eliminate magic number
//    }
//
//    // TODO: Optimize this!
//    public double getOpponentGameWinPercent() {
//        double opponentsTotalWinPercent = 0d;
//        int lengthCount = 0;
//        for (Long opponentId : opponentIds) {
//            Player opponent = playerRepository.findOne(opponentId);
//            opponentsTotalWinPercent += opponent.getPlayerGameWinPercent();
//            lengthCount += 1;
//        }
//        double opponentGameWinPercent = opponentsTotalWinPercent / lengthCount;
//        return opponentGameWinPercent < .33d ? .33d : opponentGameWinPercent; // TODO: eliminate magic number
//    }
}

