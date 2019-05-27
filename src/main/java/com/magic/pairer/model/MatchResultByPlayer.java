package com.magic.pairer.model;

public class MatchResultByPlayer {
    private Long winnerId;
    private Long firstPlayerWins;
    private Long secondPlayerWins;

    /*
     TODO: refactor to be able to take any kind of match, not just BO3
     TODO: validate that match results make sense
      - winnerId is valid
      - loserWins <= 1
      - winnerWins <= 2
    */

    public MatchResultByPlayer() { }

    MatchResultByPlayer(Long winnerId, Long firstPlayerWins, Long secondPlayerWins) {

        // Make winner id -1 if draw
        this.winnerId = winnerId;
        this.firstPlayerWins = firstPlayerWins;
        this.secondPlayerWins= secondPlayerWins;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public Long getFirstPlayerWins() {
        return firstPlayerWins;
    }

    public Long getSecondPlayerWins() {
        return secondPlayerWins;
    }
}
