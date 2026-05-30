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
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Force Rain")
@Description("Make it start/stop raining for specific players (client-side per-player weather).")
@Examples("make it stop raining for {queue::*}")
@Since("1.1.0")
public class EffForceRain extends Effect {

    private Expression<Player> playerExpr;
    private boolean rain;

    static {
        Skript.registerEffect(EffForceRain.class,
                "((1¦force [it] to rain|force it to stop raining)|make it (1¦|stop) rain[ing]) for %players%");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        playerExpr = (Expression<Player>) exprs[0];
        rain = parseResult.mark == 1;
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (playerExpr == null) return;
        WeatherType type = rain ? WeatherType.DOWNFALL : WeatherType.CLEAR;
        for (Player p : playerExpr.getArray(e)) {
            p.setPlayerWeather(type);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "make it " + (rain ? "rain" : "stop raining") + " for " + playerExpr.toString(e, debug);
    }
}
