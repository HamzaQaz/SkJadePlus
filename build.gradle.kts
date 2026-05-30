plugins {
    java
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.21"
    id("com.gradleup.shadow") version "9.4.2"
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

group = "com.ankoki"
version = "1.4.4-SkJadePlus"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")   // Paper / paperweight
    maven("https://repo.skriptlang.org/releases")               // Skript
    maven("https://jitpack.io/")                                // Pastebin-API, Elementals, GuardianBeam, DecentHolograms
    maven("https://repo.codemc.io/repository/maven-public/")    // bStats, NBT-API
    maven("https://repo.dmulloy2.net/repository/public/")       // ProtocolLib
}

dependencies {
    // Mojang-mapped Paper 26.1.2 (provides paper-api). No reobf on 26.1+.
    paperweight.paperDevBundle("26.1.2.build.66-stable")

    // Skript (compile against, provided at runtime)
    compileOnly("com.github.SkriptLang:Skript:2.15.2")

    // Soft-dependency hooks (provided by their own plugins at runtime)
    // ProtocolLib: vendored dev-build jar (5.5.0-SNAPSHOT) — the only artifact with MC 26.1.2
    // support. The Maven snapshot (5.4.0) lacks 26.1.2 and the JitPack build fails on Java 25.
    // Revisit when a stable 5.5.0 reaches Maven Central (net.dmulloy2:ProtocolLib).
    compileOnly(files("libs/ProtocolLib-5.5.0-SNAPSHOT-devbuild-20260512.jar"))
    // Elementals — abandoned upstream (2021, MC 1.16/Java 8); no 26.1.2 build exists, so its hook
    // cannot load on 26.1.2. Hook source excluded below; revisit only if Elementals is ever updated.
    // compileOnly("com.github.Ankoki:Elementals:1.4")
    // DecentHolograms — hologram hook (jitpack); 2.9.10 adds MC 26.1.2 support.
    compileOnly("com.github.decentsoftware-eu:decentholograms:2.9.10")

    // Shaded into the plugin jar
    implementation("com.github.Ankoki:Pastebin-API:1.0")
    implementation("org.bstats:bstats-bukkit:3.1.0")

    // Annotations (compile-time only)
    compileOnly("org.jetbrains:annotations:26.0.2")
    compileOnly("org.eclipse.jdt:org.eclipse.jdt.annotation:2.3.100")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

// Hooks excluded from compilation until their upstreams support MC 26.1.2. The hologram
// (DecentHolograms) and ProtocolLib hooks ARE compiled now — their dependencies are wired above.
sourceSets {
    main {
        java {
            exclude("com/ankoki/skjadeplus/hooks/elementals/**")  // blocked: Elementals abandoned, no 26.1.2 build
            exclude("com/ankoki/skjadeplus/elements/lasers/**")   // deferred: hand-rolled NMS dead -> GuardianBeam (follow-up)
        }
    }
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(25)
    }
    processResources {
        val props = mapOf("version" to project.version)
        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
    shadowJar {
        archiveClassifier.set("")
        relocate("org.bstats", "com.ankoki.skjadeplus.libs.metrics")
    }
    build {
        dependsOn(shadowJar)
    }
    runServer {
        minecraftVersion("26.1.2")
    }
}
