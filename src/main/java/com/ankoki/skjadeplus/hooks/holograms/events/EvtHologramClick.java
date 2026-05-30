package com.ankoki.skjadeplus.hooks.holograms.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.event.HologramClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Hologram Click")
@Description({"Called when a player clicks a SkJadePlus (DecentHolograms) hologram.",
        "`event-player` is the clicker and `event-hologram` is the clicked hologram.",
        "Note: DecentHolograms reports clicks at the hologram level, not per individual line."})
@Examples({"on hologram click:", "\tsend \"you clicked %event-hologram%\" to event-player"})
@RequiredPlugins("DecentHolograms")
@Since("1.0.0")
public class EvtHologramClick extends SkriptEvent {

    static {
        Skript.registerEvent("Hologram Click", EvtHologramClick.class, HologramClickEvent.class,
                "[skjadeplus|decent[ ]holograms|dh] holo[gram] click");
        EventValues.registerEventValue(HologramClickEvent.class, Player.class,
                HologramClickEvent::getPlayer, EventValues.TIME_NOW);
        EventValues.registerEventValue(HologramClickEvent.class, Hologram.class,
                HologramClickEvent::getHologram, EventValues.TIME_NOW);
    }

    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return event instanceof HologramClickEvent;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "hologram click";
    }
}
