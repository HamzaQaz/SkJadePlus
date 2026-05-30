package com.ankoki.skjadeplus.hooks.holograms.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.ankoki.skjadeplus.hooks.holograms.HologramManager;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramLine;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Hologram Line")
@Description("The hologram line of a specified hologram")
@Examples("set {_line::*} to all lines of the hologram with id \"hi\"")
@RequiredPlugins("DecentHolograms")
@Since("1.0.0")
public class ExprHologramLine extends SimpleExpression<HologramLine> {

    static {
        Skript.registerExpression(ExprHologramLine.class, HologramLine.class, ExpressionType.PROPERTY,
                "[all] [the] lines of %holograms%",
                "line %number% of [the] [holo[gram]] %hologram%");
    }

    private boolean single;
    private Expression<Number> exprNumber;
    private Expression<Hologram> exprHologram;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int pattern, Kleenean kleenean, ParseResult parseResult) {
        int i = 0;
        if (pattern == 1) {
            single = true;
            exprNumber = (Expression<Number>) exprs[i];
            i++;
        }
        exprHologram = (Expression<Hologram>) exprs[i];
        return true;
    }

    @Nullable
    @Override
    protected HologramLine[] get(Event event) {
        Hologram holo = exprHologram.getSingle(event);
        if (holo == null) return new HologramLine[0];
        if (exprNumber != null) {
            Number number = exprNumber.getSingle(event);
            if (number == null) return new HologramLine[0];
            HologramLine line = HologramManager.getLine(holo, number.intValue());
            return line == null ? new HologramLine[0] : new HologramLine[]{line};
        }
        return HologramManager.getLines(holo);
    }

    @Override
    public boolean isSingle() {
        return single;
    }

    @Override
    public Class<? extends HologramLine> getReturnType() {
        return HologramLine.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return (exprNumber == null ? "all the lines of " : "line " + exprNumber.toString(event, b) + " ") + " of " +
                exprHologram.toString(event, b);
    }
}
