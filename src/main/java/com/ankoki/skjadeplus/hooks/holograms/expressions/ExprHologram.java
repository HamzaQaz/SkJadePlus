package com.ankoki.skjadeplus.hooks.holograms.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.ankoki.skjadeplus.hooks.holograms.HologramManager;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.event.HologramClickEvent;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Hologram")
@Description("A DecentHolograms Hologram. The ID of every hologram should be unique.")
@Examples({"event-holo",
            "the hologram with id the \"id\""})
@RequiredPlugins("DecentHolograms")
@Since("1.0.0")
public class ExprHologram extends SimpleExpression<Hologram> {

    static {
        Skript.registerExpression(ExprHologram.class, Hologram.class, ExpressionType.PROPERTY,
                "[the] holo[gram] with [the] id %string%",
                "event(-| )holo[gram]");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, ParseResult parseResult) {
        if (i == 1 && !ParserInstance.get().isCurrentEvent(HologramClickEvent.class)) {
            Skript.error("You cannot use event-hologram outside a hologram click event!");
            return false;
        }
        if (i == 1) {
            inEvent = true;
            return true;
        }
        key = (Expression<String>) exprs[0];
        return true;
    }

    private Expression<String> key;
    private boolean inEvent;

    @Nullable
    @Override
    protected Hologram[] get(Event event) {
        if (inEvent) return new Hologram[]{getFromEvent(event)};
        else if (key == null) return new Hologram[0];
        return new Hologram[]{HologramManager.getHologram(key.getSingle(event))};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Hologram> getReturnType() {
        return Hologram.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return inEvent ? "event-hologram" : "the hologram with the id " + key.toString(event, b);
    }

    private Hologram getFromEvent(Event e) {
        if (e instanceof HologramClickEvent) {
            return ((HologramClickEvent) e).getHologram();
        }
        return null;
    }
}