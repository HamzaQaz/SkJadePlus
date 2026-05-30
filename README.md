# SkJadePlus

A maintained, modernized fork of [SkJade](https://github.com/Ankoki/SkJade) (originally by Ankoki) — a
Skript addon providing custom syntax for conditions, effects, events and expressions.

**SkJadePlus** updates the addon for modern Minecraft and Skript:

- Minecraft **Java 26.1.2** (Paper)
- Skript **2.15.2+**
- Java **25**
- Gradle + paperweight build, with CI testing across Java and Minecraft versions

> SkJadePlus is a drop-in successor to SkJade. It uses a distinct plugin name and package, so it will not
> conflict with the original. All original SkJade syntax is preserved unless noted in the changelog.

## Status

Active modernization in progress on branch `modernize/mc-26.1.2-skript-2.15`. See the issues/PRs for the
feature-by-feature port status (NMS effects → modern Paper API, holograms → maintained holo library).

## Building

Requires JDK 25.

```bash
./gradlew build
```

The shaded plugin jar is produced under `build/libs/`.

## Credits

- **Ankoki** — original author of SkJade.
- **HamzaQaz** — SkJadePlus fork & modernization.
