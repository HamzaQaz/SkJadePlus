package com.ankoki.skjadeplus.hooks.holograms.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.ankoki.skjadeplus.hooks.holograms.HologramManager;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.HologramLine;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Contents of Hologram Line")
@Description("Returns the text of a hologram line.")
@Examples("broadcast content of event-line")
@RequiredPlugins("DecentHolograms")
@Since("1.3.1")
public class ExprHologramLineContents extends SimpleExpression<Object> {

    static {
        Skript.registerExpression(ExprHologramLineContents.class, Object.class, ExpressionType.SIMPLE,
                "[skjade] [the] (content[s]|text|item) (of|in|at) [[the] holo[gram]] %hologramline%");
    }

    private Expression<HologramLine> lineExpr;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        lineExpr = (Expression<HologramLine>) exprs[0];
        return true;
    }

    @Nullable
    @Override
    protected Object[] get(Event e) {
        HologramLine line = lineExpr.getSingle(e);
        if (line == null) return new Object[0];
        return new Object[]{line.getContent()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "contents of " + lineExpr.toString(e, debug);
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET) {
            return CollectionUtils.array(String.class);
        }
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
        assert mode == ChangeMode.SET;
        if (delta == null || delta.length < 1 || delta[0] == null || lineExpr == null) return;
        HologramLine line = lineExpr.getSingle(e);
        if (line == null) return;
        Object obj = delta[0];
        if (!(obj instanceof String)) return;
        DHAPI.setHologramLine(line, (String) obj);
    }
}
