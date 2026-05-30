package com.ankoki.skjadeplus.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

@Name("Async PreLogin")
@Description("Called when a player is connecting/logging on. You cannot get the player here.")
@Examples("on async player pre-login:")
@Since("1.1.0")
public class EvtAsyncPreLogin extends SimpleEvent {

    static {
        Skript.registerEvent("Async PreLogin", EvtAsyncPreLogin.class, AsyncPlayerPreLoginEvent.class,
                "[async] [player] pre( |-)login");
        EventValues.registerEventValue(AsyncPlayerPreLoginEvent.class, UUID.class,
                AsyncPlayerPreLoginEvent::getUniqueId, EventValues.TIME_NOW);
        EventValues.registerEventValue(AsyncPlayerPreLoginEvent.class, String.class,
                AsyncPlayerPreLoginEvent::getName, EventValues.TIME_NOW);
    }

    @Override
    public boolean check(Event event) {
        return event instanceof AsyncPlayerPreLoginEvent;
    }
}
