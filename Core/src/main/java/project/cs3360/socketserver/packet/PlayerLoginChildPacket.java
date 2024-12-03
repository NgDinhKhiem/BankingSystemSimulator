package project.cs3360.socketserver.packet;

import project.cs3360.socketserver.handler.IgnoreSelf;

import java.util.UUID;

@IgnoreSelf
public class PlayerLoginChildPacket extends PlayerLoginPacket{
    public PlayerLoginChildPacket(String address, UUID player, String reason) {
        super(address, player, reason);
    }
}
