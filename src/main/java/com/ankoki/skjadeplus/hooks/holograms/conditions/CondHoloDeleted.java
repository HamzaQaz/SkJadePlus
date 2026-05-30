package com.ankoki.skjadeplus.hooks.holograms.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.ankoki.skjadeplus.hooks.holograms.HologramManager;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Hologram Deleted")
@Description("Checks whether a hologram has been deleted. DecentHolograms has no explicit deleted flag, " +
        "so a hologram is considered deleted when it is no longer registered under its id.")
@Examples({"if {_holo} has been deleted:",
        "\tbroadcast \"the hologram is gone\""})
@Since("1.0")
@RequiredPlugins("DecentHolograms")
public class CondHoloDeleted extends Condition {

	static {
		Skript.registerCondition(CondHoloDeleted.class,
				"%hologram% (has been|is) deleted");
	}

	private Expression<Hologram> holoExpr;

	@Override
	public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, ParseResult parseResult) {
		holoExpr = (Expression<Hologram>) exprs[0];
		return true;
	}

	@Override
	public boolean check(Event event) {
		Hologram holo = holoExpr.getSingle(event);
		if (holo == null)
			return true;
		// DecentHolograms has no isDeleted(); a hologram is "deleted" when it is no longer
		// registered under its id (name).
		return HologramManager.getHologram(holo.getName()) == null;
	}

	@Override
	public String toString(@Nullable Event event, boolean b) {
		return holoExpr.toString(event, b) + " has been deleted";
	}

}
