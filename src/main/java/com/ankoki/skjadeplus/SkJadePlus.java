package com.ankoki.skjadeplus;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.registrations.Classes;
import org.skriptlang.skript.lang.converter.Converters;
import ch.njol.util.coll.CollectionUtils;
import com.ankoki.pastebinapi.api.PasteBuilder;
import com.ankoki.skjadeplus.commands.SkJadeCmd;
import com.ankoki.skjadeplus.elements.pastebinapi.PasteManager;
import com.ankoki.skjadeplus.hooks.holograms.HoloClassInfo;
import com.ankoki.skjadeplus.listeners.PlayerJoin;
import com.ankoki.skjadeplus.utils.*;
import com.ankoki.skjadeplus.utils.events.RealTimeEvent;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jdt.annotation.Nullable;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * IMPORTANT
 * <p>
 * SkJadePlus is named SkJadePlus because Jade West from Victorious is absolutely
 * gorgeous. We love Liz Gillies. Ok bye now <3
 */
public class SkJadePlus extends JavaPlugin {

    private boolean beta;
    private static SkJadePlus instance;
    private String version;
    private PluginManager pluginManager;
    private SkriptAddon addon;
    private boolean latest = true;
    private Config config = null;
    private final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        instance = this;
        pluginManager = this.getServer().getPluginManager();
        version = this.getDescription().getVersion();
        if (!this.isSkriptEnabled()) {
            Console.info("Skript wasn't found. Are you sure it's installed and up to date?");
            pluginManager.disablePlugin(this);
            return;
        }
        config = new Config(this);
        addon = Skript.registerAddon(this);
        this.loadClassInfo();
        if (Utils.getServerMajorVersion() > 12) {
            new NonLegacyClassInfo();
        }

        this.loadElements();
        // TODO(Phase 6): ProtocolLib hook disabled until a 26.1.2-compatible ProtocolLib is wired.
        // if (isPluginEnabled("ProtocolLib") && Config.PROTOCOL_LIB_ENABLED) {
        //     Console.info("ProtocolLib was found! Enabling support");
        //     this.loadProtocolElements();
        // }
        if (isPluginEnabled("DecentHolograms") && Config.HOLOGRAPHIC_DISPLAYS_ENABLED) {
            Console.info("DecentHolograms was found! Enabling hologram support.");
            this.loadHDElements();
        }
        // Elementals hook disabled: upstream Elementals is abandoned (no 26.1.2 build), so it can
        // never be present at runtime on this server version. Re-enable only if Elementals is updated.
        // if (isPluginEnabled("Elementals") && Config.ELEMENTALS_ENABLED) {
        //     Plugin elementals = pluginManager.getPlugin("Elementals");
        //     assert elementals != null;
        //     if (Utils.checkPluginVersion(elementals, 1, 4)) {
        //         Console.info("Elementals was found! Enabling support");
        //         this.loadElementalsElements();
        //     } else {
        //         Console.info("Elementals was found, however it is an outdated version! Please upgrade to atleast version 1.4.");
        //     }
        // }

        this.registerListeners(new PlayerJoin());
        if (version.endsWith("-beta")) {
            Console.warning("You are running on an unstable release, SkJadePlus could potentionally " +
                    "function incorrectly!");
            Console.warning("Switching to a non-beta version of SkJadePlus is HIGHLY recommended, especially if you're " +
                    "runninng on a production server, as data might be lost!");
            beta = true;
        }
        // TODO(SkJadePlus): register a dedicated bStats project id; 10131 was Ankoki's SkJade.
        // new Metrics(this, 10131);
        this.getServer().getPluginCommand("skjadeplus").setExecutor(new SkJadeCmd());
        this.startRealTime();

        long fin = System.currentTimeMillis() - start;
        Console.info("SkJadePlus v" + version + " has been successfully enabled in " + df.format(fin / 1000.0) + " seconds (" +
                fin + "ms)");

        if (!isBeta() && !Config.DISABLE_UPDATE_CHECKER) {
            new Thread(() -> {
                UpdateChecker checker = new UpdateChecker("HamzaQaz", "SkJadePlus");
                if (!checker.isLatest()) {
                    Console.info("You are not running the latest version of SkJadePlus! Please update here:");
                    Console.info("https://www.github.com/HamzaQaz/SkJadePlus/releases/latest");
                    latest = false;
                }
            }).start();
        }
    }

    private boolean isSkriptEnabled() {
        Plugin skript = pluginManager.getPlugin("Skript");
        if (skript == null) return false;
        if (!skript.isEnabled()) return false;
        return Skript.isAcceptRegistrations();
    }

    private boolean isPluginEnabled(String pluginName) {
        Plugin plugin = pluginManager.getPlugin(pluginName);
        if (plugin == null) return false;
        return plugin.isEnabled();
    }

    private void loadElements() {
        try {
            addon.loadClasses("com.ankoki.skjadeplus.elements",
                    "expressions",
                    "effects",
                    "events",
                    "conditions",
                    "pastebinapi");
        } catch (IOException ex) {
            Console.info("Something went horribly wrong!");
            ex.printStackTrace();
        }
    }

    private void loadHDElements() {
        try {
            new HoloClassInfo();
            addon.loadClasses("com.ankoki.skjadeplus.hooks.holograms");
        } catch (IOException ex) {
            Console.info("Something went horribly wrong enabling hologram support!");
            ex.printStackTrace();
        }
    }

    // Disabled: Elementals is abandoned upstream with no 26.1.2 build (see onEnable).
    private void loadElementalsElements() {
        // addon.loadClasses("com.ankoki.skjadeplus.hooks.elementals");
    }

    // TODO(Phase 6): re-enable once a 26.1.2-compatible ProtocolLib is wired.
    private void loadProtocolElements() {
        // addon.loadClasses("com.ankoki.skjadeplus.hooks.protocollib");
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> this.pluginManager.registerEvents(listener, this));
    }

    private void loadClassInfo() {
        //Pastebin ClassInfo
        Classes.registerClass(new ClassInfo<>(PasteBuilder.class, "paste")
                .user("paste?s?")
                .name("Paste")
                .description("A PasteBuilder created with SkJadePlus.")
                .since("1.0.0")
                .changer(new Changer<PasteBuilder>() {
                    @Nullable
                    @Override
                    public Class<?>[] acceptChange(ChangeMode mode) {
                        if (mode == ChangeMode.DELETE || mode == ChangeMode.RESET || mode == ChangeMode.REMOVE_ALL) {
                            return CollectionUtils.array();
                        }
                        return null;
                    }

                    @Override
                    public void change(PasteBuilder[] what, @Nullable Object[] delta, ChangeMode mode) {
                        switch (mode) {
                            case DELETE:
                                PasteManager.deletePaste(what);
                                break;
                            case RESET:
                            case REMOVE_ALL:
                                PasteManager.resetPaste(what);
                        }
                    }
                }));

        //Character ClassInfo
        Classes.registerClass(new ClassInfo<>(Character.class, "character")
                .user("char(acter)?s?")
                .name("Character")
                .description("A single character.")
                .since("1.1.0"));

        Converters.registerConverter(Character.class, String.class, String::valueOf);
        Converters.registerConverter(Character.class, Integer.class, Character::getNumericValue);
    }

    private void startRealTime() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getPluginManager().callEvent(new RealTimeEvent(new Date())), 0L, 20 * 60L);
    }

    public boolean isBeta() {
        return beta;
    }

    public String getVersion() {
        return version;
    }

    public static SkJadePlus getInstance() {
        return instance;
    }

    public boolean isLatest() {
        return latest;
    }

    public Config getOwnConfig() {
        return config;
    }
}