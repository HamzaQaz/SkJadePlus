package com.ankoki.skjadeplus.hooks.protocollib.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.util.Arrays;

@Name("Rotate Player")
@Description("Rotates a player client-side without teleporting them, by sending a position packet with the new yaw/pitch.")
@Examples("rotate all players by 3 horizontally and 7 vertically")
@RequiredPlugins("ProtocolLib")
@Since("1.0.0")
public class EffRotatePlayer extends Effect {

    static {
        Skript.registerEffect(EffRotatePlayer.class,
                "rotate %players% by %number% [horizontally] [[and] %-number% [vertically]]");
    }

    private Expression<Player> players;
    private Expression<Number> horizontal;
    private Expression<Number> vertical;

    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, ParseResult parseResult) {
        players = (Expression<Player>) exprs[0];
        horizontal = (Expression<Number>) exprs[1];
        vertical = (Expression<Number>) exprs[2];
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (players == null || horizontal == null) return;
        Number num1 = horizontal.getSingle(e);
        if (num1 == null) return;
        float h = num1.floatValue();
        float v = 0;
        if (vertical != null) {
            Number num2 = vertical.getSingle(e);
            if (num2 == null) return;
            v = num2.floatValue();
        }
        float finalV = v;
        Arrays.stream(players.getArray(e)).forEach(player -> {
            if (player == null) return;
            Location loc = player.getLocation();
            // A fresh packet per send (the old code mutated a shared static container).
            PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.POSITION);
            packet.getDoubles().write(0, loc.getX());
            packet.getDoubles().write(1, loc.getY());
            packet.getDoubles().write(2, loc.getZ());
            packet.getFloat().write(0, loc.getYaw() + h);
            packet.getFloat().write(1, loc.getPitch() + finalV);
            // ProtocolLib 5.x's sendServerPacket no longer declares InvocationTargetException.
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        });
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "rotate " + players.toString(event, b) + " by " + horizontal.toString(event, b) +
                (vertical != null ? " and " + vertical.toString(event, b) + " vertically" : "");
    }
}
