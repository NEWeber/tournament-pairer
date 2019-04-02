//This stub is unused right now,

package com.magic.pairer.controller;

import com.magic.pairer.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerStub {
	private static Map<Long, Player> players = new HashMap<Long, Player>();
	private static Long idIndex = 3L;

	//populate initial players
	static {
		Player a = new Player(1L, "Nicholas", "Weber");
		players.put(1L, a);
		Player b = new Player(2L, "Johann Sebastian", "Bach");
		players.put(2L, b);
		Player c = new Player(3L, "Dieterich", "Buxtehude");
		players.put(3L, c);
	}

	public static List<Player> list() {
		return new ArrayList<Player>(players.values());
	}

	public static Player create(Player player) {
		idIndex += idIndex;
		player.setId(idIndex);
		players.put(idIndex, player);
		return player;
	}

	public static Player get(Long id) {
		return players.get(id);
	}

	public static Player update(Long id, Player player) {
		players.put(id, player);
		return player;
	}

	public static Player delete(Long id) {
		return players.remove(id);
	}
}
