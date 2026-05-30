package com.ankoki.skjadeplus.hooks.holograms.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.ankoki.skjadeplus.hooks.holograms.HologramManager;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Is A Hologram")
@Description("Checks if a value is a hologram, either a hologram object or the id/name of an existing hologram.")
@Examples("if {_holo} is a hologram:")
@RequiredPlugins("DecentHolograms")
@Since("1.0.0")
public class CondIsHologram extends Condition {

    static {
        Skript.registerCondition(CondIsHologram.class,
                "%object% is a [(hd|holographic displays|decent[ ]holograms)] hologram");
    }

    private Expression<?> object;

    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, ParseResult parseResult) {
        object = exprs[0];
        return true;
    }

    @Override
    public boolean check(Event event) {
        Object value = object.getSingle(event);
        if (value == null)
            return false;
        if (value instanceof Hologram)
            return true;
        if (value instanceof String)
            return HologramManager.getHologram((String) value) != null;
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return object.toString(event, b) + " is a hologram";
    }
}
