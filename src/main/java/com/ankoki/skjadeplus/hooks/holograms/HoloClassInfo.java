package com.ankoki.skjadeplus.hooks.holograms;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.util.coll.CollectionUtils;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramLine;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;
import org.skriptlang.skript.lang.converter.Converters;

public class HoloClassInfo {

    static {
        try {
            Classes.registerClass(new ClassInfo<>(Hologram.class, "hologram")
                    .user("holo(gram)?s?")
                    .name("Hologram")
                    .description("A hologram created with DecentHolograms.")
                    .since("1.0.0")
                    .changer(new Changer<Hologram>() {
                        @Nullable
                        @Override
                        public Class<?>[] acceptChange(ChangeMode mode) {
                            if (mode == ChangeMode.DELETE || mode == ChangeMode.RESET || mode == ChangeMode.ADD) {
                                return CollectionUtils.array(String.class, ItemStack.class);
                            }
                            return null;
                        }

                        @Override
                        public void change(Hologram[] holograms, @Nullable Object[] delta, ChangeMode mode) {
                            if (mode == ChangeMode.DELETE) {
                                HologramManager.deleteHologram(holograms);
                            } else if (mode == ChangeMode.ADD) {
                                if (delta == null || delta[0] == null) return;
                                for (Hologram hologram : holograms) {
                                    if (delta[0] instanceof String) {
                                        HologramManager.addTextLine(hologram, (String) delta[0]);
                                    } else if (delta[0] instanceof ItemStack) {
                                        HologramManager.addItemLine(hologram, (ItemStack) delta[0]);
                                    }
                                }
                            } else { // RESET
                                for (Hologram hologram : holograms) {
                                    HologramManager.clearLines(hologram);
                                }
                            }
                        }
                    })
                    .parser(new Parser<Hologram>() {
                        @Override
                        public boolean canParse(ParseContext context) {
                            return false;
                        }

                        @Override
                        public String toString(Hologram hologram, int i) {
                            return "hologram " + hologram.getName();
                        }

                        @Override
                        public String toVariableNameString(Hologram hologram) {
                            return "hologram:" + hologram.getName();
                        }
                    }));

            Converters.registerConverter(Hologram.class, Location.class, Hologram::getLocation);

            Classes.registerClass(new ClassInfo<>(HologramLine.class, "hologramline")
                    .user("holo(gram)?( |-)?lines?")
                    .name("Hologram Line")
                    .description("A line of a hologram.")
                    .since("1.0.0")
                    .changer(new Changer<HologramLine>() {
                        @Nullable
                        @Override
                        public Class<?>[] acceptChange(ChangeMode mode) {
                            if (mode == ChangeMode.DELETE || mode == ChangeMode.RESET) {
                                return CollectionUtils.array();
                            }
                            return null;
                        }

                        @Override
                        public void change(HologramLine[] hologramLines, @Nullable Object[] objects, ChangeMode mode) {
                            for (HologramLine line : hologramLines) {
                                HologramManager.removeLine(line);
                            }
                        }
                    })
                    .parser(new Parser<HologramLine>() {
                        @Override
                        public boolean canParse(ParseContext context) {
                            return false;
                        }

                        @Override
                        public String toString(HologramLine hologramLine, int i) {
                            return "hologram line";
                        }

                        @Override
                        public String toVariableNameString(HologramLine hologramLine) {
                            return "hologram line";
                        }
                    }));

            Converters.registerConverter(HologramLine.class, String.class, HologramLine::getContent);
            Converters.registerConverter(HologramLine.class, Number.class, HologramManager::getLineIndex);
        } catch (IllegalArgumentException ignored) {
        }
    }
}
