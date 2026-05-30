package com.ankoki.skjadeplus.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.function.EffFunctionCall;
import ch.njol.skript.lang.function.FunctionReference;
import ch.njol.util.Kleenean;
import com.ankoki.skjadeplus.SkJadePlus;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

/*Don't think this will go anywhere, however im keeping it on the table.
  I really think this would be useful, ESPECIALLY on minehut who do
  not have skript-reflect, however it comes with too many risks and
  errors that could be thrown that i don't think i could handle manually.*/
public class EffAsyncFunction extends Effect {

    /*static {
        Skript.registerEffect(EffAsyncFunction.class,
                "run [[the] function] <(.+)>\\([<.*?>]\\) async");
    }*/

    // TODO(SkJadePlus): experimental async function-call effect. It was never registered (the static
    // block above is commented out) and relied on the pre-2.x function API
    // (ch.njol.skript.lang.function.FunctionReference / EffFunctionCall), whose wiring moved to
    // org.skriptlang.skript.common.function in Skript 2.15. Left disabled pending a rewrite.

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return false;
    }

    @Override
    protected void execute(Event e) {
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "run function async";
    }
}
