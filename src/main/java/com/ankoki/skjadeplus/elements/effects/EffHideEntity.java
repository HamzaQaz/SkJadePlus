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
import com.ankoki.skjadeplus.SkJadePlus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Hide Entity")
@Description({"Hides an entity from a player or all players.",
              "Unlike the old destroy-packet approach this is persistent and reference-counted by the server, ",
              "so the entity stays hidden (use 'reveal'/showEntity-based syntax to undo, or it resets on the entity respawning)."})
@Examples("hide player's target entity for all players")
@Since("1.3.0")
public class EffHideEntity extends Effect {

    private Expression<Entity> entity;
    private Expression<Player> playerExpr;

    static {
        Skript.registerEffect(EffHideEntity.class,
                "[skjadeplus] (hide|destroy|send [a] destroy packet for) [[the] entity] %entities% (1¦(from|for) %-players%|)");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        entity = (Expression<Entity>) exprs[0];
        playerExpr = parseResult.mark == 1 ? (Expression<Player>) exprs[1] : null;
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (entity == null) return;
        Entity[] entities = entity.getArray(e);
        Player[] players = playerExpr == null
                ? Bukkit.getOnlinePlayers().toArray(new Player[0])
                : playerExpr.getArray(e);
        for (Player player : players) {
            for (Entity target : entities) {
                player.hideEntity(SkJadePlus.getInstance(), target);
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "hide " + entity.toString(e, debug) + (playerExpr == null ? "" : " from " + playerExpr.toString(e, debug));
    }
}
