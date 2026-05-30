package com.ankoki.skjadeplus.hooks.holograms.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.ankoki.skjadeplus.hooks.holograms.HologramManager;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Delete Hologram")
@Description("Deletes a hologram")
@Examples("delete the hologram with the id \"myHologram\"")
@RequiredPlugins("DecentHolograms")
@Since("1.0.0")
public class EffDeleteHolo extends Effect {

    static {
        Skript.registerEffect(EffDeleteHolo.class,
                "delete [(hd|holographic[ ]displays)] %hologram%");
    }

    private Expression<Hologram> hologram;

    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, ParseResult parseResult) {
        hologram = (Expression<Hologram>) exprs[0];
        return true;
    }

    @Override
    protected void execute(Event event) {
        Hologram holo = hologram.getSingle(event);
        if (holo == null) return;
        HologramManager.deleteHologram(holo);
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "delete " + hologram.toString(event, b);
    }
}
