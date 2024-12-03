package project.cs3360.socketserver.packet;

import project.cs3360.socketserver.handler.IgnoreSelf;

import java.util.UUID;

@IgnoreSelf
public class PlayerLoginPacket extends Packet{
    private final String address;
    private final UUID player;
    private final String reason;

    public PlayerLoginPacket(String address, UUID player, String reason) {
        this.address = address;
        this.player = player;
        this.reason = reason;
    }

    public String getAddress() {
        return address;
    }

    public UUID getPlayer() {
        return player;
    }

    public String getReason() {
        return reason;
    }
}
