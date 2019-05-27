package com.magic.pairer.model;

public class MatchPlayers {
    private Long firstPlayerId;
    private Long secondPlayerId;

    public MatchPlayers() { }

    public MatchPlayers(Long firstPlayerId, Long secondPlayerId) {
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
    }

    public Long getFirstPlayerId() {
        return firstPlayerId;
    }

    public Long getSecondPlayerId() {
        return secondPlayerId;
    }
}
