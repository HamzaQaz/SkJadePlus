package com.ankoki.skjadeplus.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Name("Fake Damage")
@Description("Makes a player look like they took damage (the visual hurt animation only, no actual damage).")
@Examples("make event-player take fake damage")
@Since("1.0.0")
public class EffFakeDamage extends Effect {

    private Expression<Player> damagedExpr;
    private Expression<Player> viewerExpr;

    static {
        Skript.registerEffect(EffFakeDamage.class,
                "make %players% take fake damage [for %-players%]");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        damagedExpr = (Expression<Player>) exprs[0];
        viewerExpr = (Expression<Player>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        List<Player> viewers = viewerExpr != null
                ? Arrays.asList(viewerExpr.getArray(e))
                : new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player player : damagedExpr.getArray(e)) {
            // Paper API (1.19.4+): play the hurt animation to just the given viewers.
            player.broadcastHurtAnimation(viewers);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "make " + damagedExpr.toString(e, debug) + " take fake damage for " + (viewerExpr == null ? "all players" : viewerExpr.toString(e, debug));
    }
}
