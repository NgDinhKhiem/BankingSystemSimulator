package project.cs3360.socketserver.packet;

import java.util.UUID;

public class PlayerLogoutPacket extends Packet{
    private final String address;
    private final UUID player;
    private final String reason;

    public PlayerLogoutPacket(String address, UUID player, String reason) {
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
