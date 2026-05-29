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
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Show Mining Stage")
@Description({"Shows the block break animation/stage (0-9) to players at a location.",
              "Optionally keyed to an entity id so multiple overlays can coexist on one block."})
@Examples("show mining stage 5 at player's target block")
@Since("1.2.0")
public class EffShowMiningStage extends Effect {

    private Expression<Number> stageExpr, entityId;
    private Expression<Location> location;
    private Expression<Player> playerExpr;
    private boolean remove = false;

    static {
        Skript.registerEffect(EffShowMiningStage.class,
                "(show|play) (mining|block break) (stage|animation) %number% at %locations% [to %-players%] [(1¦with [the] [entity] id %-number%|)]",
                "remove [the] (mining|block break) (stage|animation) at %locations% [for %-players%] [(1¦with [the] [entity] id %-number%|)]");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        if (matchedPattern == 0) {
            stageExpr = (Expression<Number>) exprs[0];
            location = (Expression<Location>) exprs[1];
            if (exprs.length == 3 && parseResult.mark == 1) {
                entityId = (Expression<Number>) exprs[2];
            } else if (exprs.length == 3) {
                playerExpr = (Expression<Player>) exprs[2];
            } else if (exprs.length == 4) {
                playerExpr = (Expression<Player>) exprs[2];
                entityId = (Expression<Number>) exprs[3];
            }
        } else {
            remove = true;
            location = (Expression<Location>) exprs[0];
            if (exprs.length > 1) {
                playerExpr = (Expression<Player>) exprs[1];
            } else {
                if (parseResult.mark == 1) entityId = (Expression<Number>) exprs[1];
            }
            if (exprs.length == 3) entityId = (Expression<Number>) exprs[2];
        }
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (location == null) return;
        int i = 100;
        if (stageExpr != null) {
            Number num = stageExpr.getSingle(e);
            if (num == null) return;
            i = num.intValue();
        }
        Integer ent = null;
        if (entityId != null) {
            Number num = entityId.getSingle(e);
            if (num == null) return;
            ent = num.intValue();
        }
        Location[] locs = location.getArray(e);
        Player[] players = playerExpr != null ? playerExpr.getArray(e) : Bukkit.getOnlinePlayers().toArray(new Player[0]);
        if (locs.length < 1) return;
        // Bukkit/Paper sendBlockDamage takes progress in [0,1]; 0 removes the overlay.
        // Map the legacy 0-9 stage onto that range (stage 9 = fully cracked).
        int stage = Math.max(0, Math.min(9, i));
        float progress = remove ? 0f : stage / 9.0f;
        for (Location loc : locs) {
            for (Player p : players) {
                if (ent != null) {
                    p.sendBlockDamage(loc, progress, ent);
                } else {
                    p.sendBlockDamage(loc, progress);
                }
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return remove ? "remove the mining stage at " + location.toString(e, debug) + (playerExpr != null ? " for " + playerExpr.toString(e, debug) : "") :
                "show mining stage " + stageExpr.toString(e, debug) + " at " + location.toString(e, debug) + (playerExpr != null ? " to " + playerExpr.toString(e, debug) : "");
    }
}
