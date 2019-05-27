package com.magic.pairer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// TODO: Handle draws explicitly
// TODO: Add a way to add/associate a location (table) to the match
// TODO: Add player reporting that both must agree to commit as the match result

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long firstPlayerId;
    private Long secondPlayerId;
    //TODO: find more elegant solution?
    private Long winnerId;
    private Long numberFirstPlayerGameWins;
    private Long numberSecondPlayerGameWins;
    public boolean matchComplete;


    public Match() { }
    public Match(MatchPlayers matchPlayers) {
        // TODO: Verify that player ids are valid
        this.firstPlayerId = matchPlayers.getFirstPlayerId();
        this.secondPlayerId = matchPlayers.getSecondPlayerId();
    }

    public Long getId() {
        return id;
    }

    public MatchPlayers getMatchPlayers() {
        return new MatchPlayers(firstPlayerId, secondPlayerId);
    }

    // TODO: clean this up. Right now, it's translating the class MatchResult to the values in Match and then having to
    //  translate them back. Maybe just store the MatchResult and have the DB store the class?
    public void setMatchResults(MatchResultByWinner matchResult) {
        // winnerId -1 means a draw
        this.winnerId = matchResult.getWinnerId();
        if (winnerId == firstPlayerId) {
            numberFirstPlayerGameWins = matchResult.getWinnerWins();
            numberSecondPlayerGameWins = matchResult.getLoserWins();
        } else if (winnerId == secondPlayerId || winnerId == -1) {
            numberSecondPlayerGameWins = matchResult.getWinnerWins();
            numberFirstPlayerGameWins = matchResult.getLoserWins();
        } else {
            // TODO: throw error here
        }
        matchComplete = true;
    }
    // TODO: clean up, see comment above.
    public MatchResultByWinner getMatchResultsByWinner () {
        Long winnerWins = numberFirstPlayerGameWins > numberSecondPlayerGameWins ? numberFirstPlayerGameWins : numberSecondPlayerGameWins;
        Long loserWins = numberFirstPlayerGameWins < numberSecondPlayerGameWins ? numberFirstPlayerGameWins : numberSecondPlayerGameWins;
        return new MatchResultByWinner(winnerId, winnerWins, loserWins);
    }

    public MatchResultByPlayer getMatchResultsByPlayer () {
        return new MatchResultByPlayer(winnerId, numberFirstPlayerGameWins, numberSecondPlayerGameWins);
    }

    public Long getWinnerId() {
        return winnerId;
    }
}

