package com.magic.pairer.model;

public class MatchResultByWinner {
    private Long winnerId;
    private Long winnerWins;
    private Long loserWins;

    /*
     TODO: refactor to be able to take any kind of match, not just BO3
     TODO: validate that match results make sense
      - winnerId is valid
      - loserWins <= 1
      - winnerWins <= 2
    */

    public MatchResultByWinner() { }

    MatchResultByWinner(Long winnerId, Long winnerWins, Long loserWins) {

        // Make winner id -1 if draw
        this.winnerId = winnerId;
        this.winnerWins = winnerWins;
        this.loserWins = loserWins;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public Long getLoserWins() {
        return loserWins;
    }

    public Long getWinnerWins() {
        return winnerWins;
    }
}
