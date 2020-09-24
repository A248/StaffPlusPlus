package net.shortninja.staffplus.player;

import org.bukkit.entity.Player;

import java.util.UUID;

public class SppPlayer {

    private final UUID id;
    private final String username;
    private final boolean online;
    private Player player;

    public SppPlayer(UUID id, String username) {
        this.id = id;
        this.username = username;
        this.online = false;
    }

    public SppPlayer(UUID id, String username, Player player) {
        this.id = id;
        this.username = username;
        this.online = true;
        this.player = player;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isOnline() {
        return online;
    }

    public Player getPlayer() {
        if(!online) {
            throw new RuntimeException("Cannot retrieve bukkit player. Player is offline");
        }
        return player;
    }
}
