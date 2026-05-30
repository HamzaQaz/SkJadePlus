# SkJadePlus

A maintained, modernized fork of [SkJade](https://github.com/Ankoki/SkJade) (originally by Ankoki) — a
Skript addon that adds custom conditions, effects, events, expressions and types.

**SkJadePlus** brings the addon to modern Minecraft and Skript:

- Minecraft **Java 26.1.2** (Paper)
- Skript **2.15.2+**
- Java **25**
- Gradle + paperweight build, with CI that boots a real Paper server across Java and Minecraft versions

> SkJadePlus is a drop-in successor to SkJade. It uses a distinct plugin name and package, so it does not
> conflict with the original. All original SkJade syntax is preserved unless noted below.

## Documentation

Full syntax reference (every event, condition, effect, expression and type, with patterns and examples):
**[docs/SYNTAX.md](docs/SYNTAX.md)**.

## Features

SkJadePlus packs 100+ Skript syntaxes for Minecraft 26.1.2 / Skript 2.15+:

- **World borders** — get/set size, center, damage amount/buffer, warning distance and time, animate size
  over time, reset, and within-border checks.
- **Particle shapes** — circle, star, cone and torus location generators for custom effects.
- **Players & entities** — fake damage, force sleep/wake, drop tool, exact target block, can-see and is-wet
  checks, nearest entity, spawn point, per-player client-side rain, and per-player time.
- **Chunks & worlds** — slime-chunk check, chunk-at-coords, async chunk refresh, core/main world, hardcore
  toggle, and nearest-structure locating.
- **Events** — async pre-login (inet address, kick message, login result) and a real-time event that fires
  at real-world GMT times.
- **Lasers / guardian beams** (8 syntaxes) — create, show, start, stop, color-cycle, attach entities, and
  read endpoints. Backed by [GuardianBeam](https://github.com/SkytAsul/GuardianBeam).
- **Utility expressions** — Roman numerals, formatted numbers, rainbow text, english plurals,
  timespan-to-ticks, unary/flipped booleans, characters, text-between, glowing items, and border slots.
- **Holograms** (18 syntaxes — requires **DecentHolograms**) — create, delete, teleport, add/remove text and
  item lines, per-player visibility, an `on hologram click` event, and line introspection.
- **Pastebin** (11 syntaxes) — create, configure (title, format, visibility, expiry, text), build, and read
  pastes.

### Soft-dependencies

| Feature | Plugin |
|---|---|
| Holograms | [DecentHolograms](https://github.com/DecentSoftware-eu/DecentHolograms) 2.9.10+ |
| Rotate Player | [ProtocolLib](https://github.com/dmulloy2/ProtocolLib) (26.1.x dev-build) |

The Elementals hook is not available on 26.1.2 (the Elementals plugin is unmaintained).

## Usage

```skript
on hologram click:
    send "you clicked %event-hologram%" to event-player

command /beam:
    trigger:
        show a laser from player to player's target block for 10 seconds
```

## Building

Requires JDK 25.

```bash
./gradlew build
```

The shaded plugin jar is produced under `build/libs/`. You can also boot a test server with the bundled
script:

```bash
./.github/scripts/load-test.sh 26.1.2 2.15.2
```

## Credits

- **Ankoki** — original author of SkJade.
- **HamzaQaz** — SkJadePlus fork & modernization.
- Lasers use **SkytAsul/GuardianBeam**; holograms integrate **DecentHolograms**.
