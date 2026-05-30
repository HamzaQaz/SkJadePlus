package com.ankoki.skjadeplus.hooks.holograms;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.DecentHologramsAPI;
import eu.decentsoftware.holograms.api.actions.Action;
import eu.decentsoftware.holograms.api.actions.ActionType;
import eu.decentsoftware.holograms.api.actions.ClickType;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramLine;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Thin wrapper around the DecentHolograms API (DHAPI).
 *
 * DecentHolograms keeps its own name-keyed registry, so holograms are looked up by their id
 * (name) rather than a local map. DecentHolograms is paged (hologram -> pages -> lines);
 * SkJadePlus uses single-page holograms, so line operations target page 0. Line numbers in this
 * API are 1-based to match the SkJadePlus syntax.
 */
public final class HologramManager {

    private HologramManager() {}

    public static Hologram createHologram(String key, Location location, boolean visible) {
        Hologram hologram = DHAPI.createHologram(key, location);
        hologram.setDefaultVisibleState(visible);
        // DecentHolograms only fires HologramClickEvent for clickable pages, and a page is
        // clickable only if it has at least one action. Register a no-op action for every click
        // type so SkJadePlus holograms can be reacted to via the "on hologram click" event.
        makeClickable(hologram);
        return hologram;
    }

    public static void makeClickable(Hologram hologram) {
        if (hologram == null) return;
        for (int i = 0; i < hologram.size(); i++) {
            HologramPage page = hologram.getPage(i);
            if (page == null) continue;
            for (ClickType type : ClickType.values()) {
                page.addAction(type, new Action(ActionType.NONE, null));
            }
        }
    }

    public static void deleteHologram(Hologram... holograms) {
        for (Hologram hologram : holograms) {
            if (hologram != null) DHAPI.removeHologram(hologram.getName());
        }
    }

    public static void clearLines(Hologram hologram) {
        if (hologram != null) DHAPI.setHologramLines(hologram, Collections.<String>emptyList());
    }

    public static void addTextLine(Hologram hologram, String line) {
        if (hologram != null) DHAPI.addHologramLine(hologram, line);
    }

    public static void addItemLine(Hologram hologram, ItemStack item) {
        if (hologram == null || item == null) return;
        if (item.getAmount() > 64 || item.getAmount() < 1) return;
        DHAPI.addHologramLine(hologram, item);
    }

    public static void setLine(Hologram hologram, int line, String text) {
        HologramLine l = getLine(hologram, line);
        if (l != null) DHAPI.setHologramLine(l, text);
    }

    public static void removeLine(Hologram hologram, int line) {
        if (hologram == null) return;
        HologramPage page = hologram.getPage(0);
        if (page == null) return;
        int index = Math.max(0, line - 1);
        if (index >= page.getLines().size()) return;
        DHAPI.removeHologramLine(page, index);
    }

    public static void removeLine(HologramLine line) {
        if (line == null) return;
        HologramPage page = line.getParent();
        if (page == null) return;
        int index = page.getLines().indexOf(line);
        if (index >= 0) DHAPI.removeHologramLine(page, index);
    }

    public static HologramLine getLine(Hologram hologram, int line) {
        if (hologram == null) return null;
        HologramPage page = hologram.getPage(0);
        if (page == null) return null;
        int index = Math.max(0, line - 1);
        List<HologramLine> lines = page.getLines();
        return index < lines.size() ? lines.get(index) : null;
    }

    public static HologramLine[] getLines(Hologram hologram) {
        if (hologram == null) return new HologramLine[0];
        HologramPage page = hologram.getPage(0);
        if (page == null) return new HologramLine[0];
        return page.getLines().toArray(new HologramLine[0]);
    }

    /** 1-based index of a line within its hologram's page, or -1 if not found. */
    public static int getLineIndex(HologramLine line) {
        if (line == null) return -1;
        HologramPage page = line.getParent();
        if (page == null) return -1;
        int idx = page.getLines().indexOf(line);
        return idx < 0 ? -1 : idx + 1;
    }

    public static Hologram getHologram(String key) {
        return DHAPI.getHologram(key);
    }

    public static Collection<Hologram> getAllHolograms() {
        return DecentHologramsAPI.get().getHologramManager().getHolograms();
    }

    public static Location getHoloLocation(Hologram holo) {
        return holo == null ? null : holo.getLocation();
    }

    public static String getIDFromHolo(Hologram hologram) {
        return hologram == null ? "" : hologram.getName();
    }
}
