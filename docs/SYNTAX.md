# SkJadePlus — Syntax Reference

SkJadePlus is a Skript addon for Minecraft 26.1.2 / Skript 2.15+. It bundles a broad mix of events, conditions, effects, and expressions covering world borders, particle shapes, players, chunks, lasers, holograms, and Pastebin. Some features rely on soft-dependencies: DecentHolograms for the hologram syntaxes, and ProtocolLib for the protocol-based effects. Anything outside those areas works with no extra plugins.

## Contents

- [Events](#events)
- [Conditions](#conditions)
- [Effects](#effects)
- [Expressions](#expressions)
- [Custom Types](#custom-types)
- [Lasers / Guardian Beams](#lasers--guardian-beams)
- [Holograms](#holograms)
- [ProtocolLib](#protocollib)
- [Pastebin](#pastebin)
- [Requirements](#requirements)

## Events

### Async PreLogin

**Pattern(s):** `[async] [player] pre( |-)login`

Called when a player is connecting/logging on. You cannot get the player here.

**Example:**
```skript
on async player pre-login:
```

**Since:** 1.1.0

### Real Time

**Pattern(s):** `(at|when it([']s| is)) %times% (((in|of) [the] real world|irl)|GMT)`

An event called at a real time, GMT time.

**Example:**
```skript
when it's 12:15 in the real world:
```

**Since:** 1.1.0

## Conditions

### Boolean

**Pattern(s):** `%booleans%`

Shortens statements such as true = true to just true.

**Example:**
```skript
if true:
```

**Since:** 1.0.0

### Break Block

**Pattern(s):** `%block% is (connected to|exposed to|touching) %itemtype%`

Makes player break a block.

**Example:**
```skript
make player break event-block
```

**Since:** 1.4.0

### Can Break Block

**Pattern(s):** `%itemtype% can(1¦(not|[']t)|) break %block%`

Checks if an item type can break a block.

**Example:**
```skript
if player's tool can break player's target block:
```

**Since:** 1.0.0

### Can See

**Pattern(s):**
- `%entity% can see %entity%`
- `%entity% can([']t|not) see %entity%`

Checks if a living entity can see another living entity.

**Example:**
```skript
if player can see loop-player:
```

**Since:** 1.0.0

### Is Wet

**Pattern(s):**
- `%entity% is(1¦(n[']t| not)|) [standing] in water`
- `%entity% is(1¦(n[']t| not)|) [standing] in [the] rain`
- `%entity% is(1¦(n[']t| not)|) [standing] in a bubble column`
- `%entity% is(1¦(n[']t| not)|) [standing] in water or [a] bubble column`
- `%entity% is(1¦(n[']t| not)|) [standing] in [the] rain or [a] bubble column`
- `%entity% is(1¦(n[']t| not)|) (wet|soaked|moist|damp|drenched|sopping|soggy|dripping)`
- `%entity% is(1¦(n[']t| not)|) in lava`
- `%entity% is(1¦(n[']t| not)|) [standing] in lava or water`
- `%entity% is(1¦(n[']t| not)|) (in|touching) any [type of] liquid`
- `%player% is(1¦(n[']t| not)|) [standing] in water`

Checks if a player (1.1.0) or an entity (1.3.2) is in any type of liquid.

**Example:**
```skript
if player is wet:
```

**Since:** 1.1.0
**Requires:** Paper for anything but is in water.

### Location is Within

**Pattern(s):** `within [[the] location] %location% and [[the] location] %location%`

Checks if a location is between two locations.

**Example:**
```skript
if player's location is within arg-1's location and arg-2's location:
```

**Since:** 1.0.0

### Multiple Conditions

**Pattern(s):**
- `<.+> && <.+>`
- `<.+> \|\| <.+>`

Allows you to use multiple conditions.

**Example:**
```skript
if attacker is online && victim is online:
```

**Since:** 1.0.0

### Slime Chunk

**Pattern(s):** `slime(y|chunk)`

Checks if a chunk is a slime chunk.

**Example:**
```skript
if {_chunk} is a slime chunk:
```

**Since:** 1.0.0

### Within World Border

**Pattern(s):** `(within|inside) [the] world[(-| )]border`

Checks if the given location is within the world border.

**Example:**
```skript
if {_futureLocation} is not inside the world border:
	cancel event
	send "You cannot teleport outside the world border!"
```

**Since:** 1.0.0

## Effects

### Break Block

**Pattern(s):** `make %player% (mine|break) [the] [block [at]] %block/location%`

Makes player break a block.

**Example:**
```skript
make player break event-block
```

**Since:** 1.4.0
**Requires:** 1.17+

### Drop Item

**Pattern(s):** `make %players% drop (1¦[their] [current] item|2¦all [the] items in [their] hand|their [(whole|entire)] inv[entory])`

Makes the player drop one of their current item, or their inventory.

**Example:**
```skript
make event-player drop their current item
```

**Since:** 1.0.0

### Evaluate Effect

**Pattern(s):** `(eval[uate]|execute) %strings%`

Execute a Skript effect from a string.

**Example:**
```skript
evaluate "set player's flight mode to true"
```

**Since:** 1.0.0

### Fake Damage

**Pattern(s):** `make %players% take fake damage [for %-players%]`

Makes a player look like they took damage (the visual hurt animation only, no actual damage).

**Example:**
```skript
make event-player take fake damage
```

**Since:** 1.0.0

### Force Rain

**Pattern(s):** `((1¦force [it] to rain|force it to stop raining)|make it (1¦|stop) rain[ing]) for %players%`

Make it start/stop raining for specific players (client-side per-player weather).

**Example:**
```skript
make it stop raining for {queue::*}
```

**Since:** 1.1.0

### Force Sleep

**Pattern(s):** `(force|make) %players% [to] sleep at %location%`

Forces a player to sleep at a location as long as there is a bed at that location.

**Example:**
```skript
force event-player to sleep at {%player's uuid%::permabed}
```

**Since:** 1.0.0

### Force Wake

**Pattern(s):** `(force|make) %players% [to] wake[ ]up [(1¦and set spawn[[ ]point])]`

Forces a player to wake up if they are sleeping.

**Example:**
```skript
force {_p} to wake up and set spawn point
```

**Since:** 1.0.0

### Hide Entity

**Pattern(s):** `[skjadeplus] (hide|destroy|send [a] destroy packet for) [[the] entity] %entities% (1¦(from|for) %-players%|)`

Hides an entity from a player or all players. Unlike the old destroy-packet approach this is persistent and reference-counted by the server, so the entity stays hidden (use 'reveal'/showEntity-based syntax to undo, or it resets on the entity respawning).

**Example:**
```skript
hide player's target entity for all players
```

**Since:** 1.3.0

### Lasagna

**Pattern(s):** `(lasagna|an Italian dish made of stacked layers of thin flat pasta alternating with fillings and other vegetables, cheese and seasonings and spices)`

Lasagna.

**Example:**
```skript
lasagna
```

**Since:** 1.1.0

### Refresh Chunks

**Pattern(s):** `(reload|refresh) [the] [chunk[s]] %chunks%`

Refeshes the chunks given asynchronously.

**Example:**
```skript
refresh the chunk at arg-1
```

**Since:** 1.0.0

### Reset World Border

**Pattern(s):** `[skjade] reset ([the] [world[ ]]border [of] %worlds%|%worlds%'s world border)`

Resets the world border of a world.

**Example:**
```skript
Reset the world border of player's world
```

**Since:** 1.3.0

### Show Mining Stage

**Pattern(s):**
- `(show|play) (mining|block break) (stage|animation) %number% at %locations% [to %-players%] [(1¦with [the] [entity] id %-number%|)]`
- `remove [the] (mining|block break) (stage|animation) at %locations% [for %-players%] [(1¦with [the] [entity] id %-number%|)]`

Shows the block break animation/stage (0-9) to players at a location. Optionally keyed to an entity id so multiple overlays can coexist on one block.

**Example:**
```skript
show mining stage 5 at player's target block
```

**Since:** 1.2.0

### World Border Size Over Time

**Pattern(s):**
- `set [skjade] [world[ ]]border size of %world% to %number% over %timespan%`
- `set [skjade] %world%'s [world[ ]]border size to %number% over %timespan%`

Sets the worldborder of a world over time.

**Example:**
```skript
set player's world's world border to 10 over 10 seconds
```

**Since:** 1.3.0

## Expressions

### Border Slots

**Pattern(s):** `border slots of [the] [inventory] %inventory%`

Returns the border slots of an inventory.

**Example:**
```skript
set border slots of the event-inventory to glowing diamond
```

**Since:** 1.0.0

### Character

**Pattern(s):** `'<(.)>'`

A single character.

**Example:**
```skript
broadcast 'h'
```

**Since:** 1.1.0

### Chunk at Location

**Pattern(s):** `chunk %number%(, | and) %number% of %world%`

Returns the chunk in a world from the x and y coordinates.

**Example:**
```skript
set {_ch} to chunk 1, 3 of player's world
```

**Since:** 1.0.0

### Circle

**Pattern(s):** `[a[n]] (1¦upright|) circle (at|from) %location% with [a] radius [of] %number% [and %-number% total (points|blocks|locations)]`

Returns the points of the outline of a circle which

**Example:**
```skript
set all blocks at (circle at player's location with a radius of 5) to red wool
```

**Since:** 1.0.0

### Cone

**Pattern(s):** `[a] cone (with [a] cent(re|er) [of]|around) %location%(,| and) [a] radius [of] %number%[(,| and)] [a] height [of] %number%[(,| and)] [a] density [of] %number%`

Returns a list of locations to make up a cone.

**Example:**
```skript
show blue dust at cone around player, radius 5, height 8 and density 10
```

**Since:** 1.2.0

### Core/Primary World

**Pattern(s):** `[the] (core|main|original|primary) world [of [the] server]`

Returns the main world, aka the fallback world.

**Example:**
```skript
set {_world} to the main world
```

**Since:** 1.3.0

### English Plural

**Pattern(s):** `[the] [english] plural of %string%`

Returns the english plural of a word. This may be inaccurate.

**Example:**
```skript
set {_plural} to english plural of "helicopter"
```

**Since:** 1.0.0

### Exact Target Block

**Pattern(s):**
- `%entity%'s exact target[ed] block`
- `%entity%'s exact target[ed] block including (1¦[only ]source|[any] [type of]) fluid[s]`

Gets the exact target block of an entity.

**Example:**
```skript
set {-target::%player's uuid%} to player's exact target block
```

**Since:** 1.1.0

### Flipped Boolean

**Pattern(s):** `(flipped |toggled |inverted |!)%boolean%`

Returns the flipped value of a boolean

**Example:**
```skript
broadcast "%flipped {bed::%player's uuid%}%"
set player's flight mode to !(player's flight mode)
```

**Since:** 1.0.0

### Formatted Number

**Pattern(s):** `[the] formatted number %number%`

Formats a number over 999 to be in readable form, such as 2000 -> 2,000.

**Example:**
```skript
broadcast "%player%'s Balance: $%formatted number {eco::%%player's uuid%%}%
```

**Since:** 1.2.0

### Glowing ItemStack

**Pattern(s):** `glowing %itemstack%`

Makes a glowing(enchanted with no enchant flag) item.

**Example:**
```skript
give player glowing diamond
```

**Since:** 1.0.0

### Hardcore World

**Pattern(s):**
- `[the] hardcore (value|mode|state) of %world%`
- `[the] %world%'s hardcore (value|mode|state)`

Gets and sets wether or not a world is hardcore.

**Example:**
```skript
set the hardcore value of world "world" to true
```

**Since:** 1.3.0

### ID of Entity

**Pattern(s):**
- `%entity%'s id`
- `the id of %entity%`

Returns the id of an entity.

**Example:**
```skript
broadcast "%event-entity's id%"
```

**Since:** 1.2.0

### Inet Address

**Pattern(s):** `event(-| )[inet]address`

Used for event-address in the AsyncPlayerPreLoginEvent

**Example:**
```skript
send "event-address"
```

**Since:** 1.1.0

### Kick Message

**Pattern(s):** `[event(-| )]kick[( |-)]message`

Gets/sets the kick message in an AsyncPlayerPreLoginEvent

**Example:**
```skript
set kick-message to "lol u got kicked"
```

**Since:** 1.1.0

### Login Result

**Pattern(s):** `[event( |-)]login[( |-)]result`

Gets/sets the login result for an async prelogin event. Please note this can only be set to "kick other", "allowed", "kick banned", "kick full", or "kick whitelist"

**Example:**
```skript
broadcast event-login-result
```

**Since:** 1.1.0

### Midnight

**Pattern(s):** `midnight`

Returns the date of midnight.

**Example:**
```skript
set {_midnight} to midnight
```

**Since:** 1.0.0

### Nearest Entity

**Pattern(s):**
- `[the] (nearest|closest) (1¦[entity of type] %-entitytype%|entity) to %entity%`
- `[the] (nearest|closest) entity (1¦[entity of type] %-entitytype%|entity) to %location%`

Returns the nearest entity to a location.

**Example:**
```skript
kill the closest entity to player
```

**Since:** 1.0.0

### Nearest Structure

**Pattern(s):** `[the] (nearest|closest) (1¦(not |un)(explored|discovered)|) [structure [of]] %skjstructuretype% (in|within) [a] radius [of] %number% (around|at|from|of) %location%`

Find the closest nearby structure of a given structure type. Will not be set if not found. Finding unexplored structures can, and will, block if the world is looking in chunks that gave not generated yet. This can lead to the world temporarily freezing while locating an unexplored structure. The radius is not a rigid square radius. Each structure may alter how many chunks to check for each iteration. Do not assume that only a radius x radius chunk area will be checked. For example, a woodland mansion can potentially check up to 20,000 blocks away (or more) regardless of the radius used. This will not load or generate chunks. This can also lead to instances where the server can hang if you are only looking for unexplored structures. This is because it will keep looking further and further out in order to find the structure.

**Example:**
```skript
teleport player to the closest village within radius 10 around player
```

**Since:** 1.3.0

### Player's Spawn Point

**Pattern(s):**
- `[the] %player%[']s spawn[ point]`
- `[the] spawn [point] of %player%`

Gets and sets the spawn point of a player.

**Example:**
```skript
set player's spawn point to event-location
```

**Since:** 1.3.1

### Rainbow Text

**Pattern(s):** `(1¦pastel rainbow|2¦monochrome |[normal ]rainbow ) %string%`

Returns the specified text in rainbow. Just a note this looks crazy in console, but theres nothing we can do about it:p (like this: "")

**Example:**
```skript
broadcast pastel rainbow "hi! this is a pastel rainbow string"
```

**Since:** 1.0.0
**Requires:** Spigot 1.16+

### Roman Numerals

**Pattern(s):**
- `[[the] value of] %number% in roman numeral[s]`
- `[the] roman numeral[s] [value] of %number%`

Returns the given number in roman numerals.

**Example:**
```skript
send "Sharpness level: %roman numeral of (level of sharpness of player's tool)%"
```

**Since:** 1.0.0

### Star

**Pattern(s):** `[(all [[of] the]|the)] [(loc[ation]s|points) of] [a] star (at|from) %location% with %number% points(,| and) [with] [a] radius [of] %number%(,| and) [a] density [of] %number%`

Returns the points of the outline of a star from the center, radius, and density

**Example:**
```skript
set blocks at (star at player's location with 5 points, with radius 10 and density 5) to red wool
```

**Since:** 1.0.0

### Text Between

**Pattern(s):** `[the] text between %character% and %character% (from|in) %string%`

Gets the text between two characters.

**Example:**
```skript
set {_money} to the text between '[' and ']' from line 1 of event-item's lore
```

**Since:** 1.1.0

### Time at Player

**Pattern(s):** `[the] time at [(player|the player)] %player%`

Returns the player's time.

**Example:**
```skript
broadcast "%the time at player%"
```

**Since:** 1.0.0

### Timespan in Ticks

**Pattern(s):**
- `%timespan% in ticks`
- `ticks of %timespan%`
- `[the] amount of ticks in %timespan%`

Converts a timespan into ticks.

**Example:**
```skript
broadcast "%20 seconds in ticks%"
```

**Since:** 1.3.0

### Torus/Giant Donut

**Pattern(s):** `[a] (torus|[giant ]donut) (at|around) %location% with [a] major radius [of] %number% and [a] minor radius [of] %number% [with [a] density [of] %-number%]`

Returns the locations to make up a giant donut/torus.

**Example:**
```skript
play green spark at a giant donut around player's location with major radius 5 and minor radius 2
```

**Since:** 1.2.0

### Unary Value

**Pattern(s):** `(-|unary [value of ])%~number%`

Returns the unary value of a number.

**Example:**
```skript
broadcast "%-{hello::%player's uuid%}"
```

**Since:** 1.3.0

### World Border Center

**Pattern(s):**
- `[skjade] [world[ ]]border cent(re|er) of %world%`
- `[skjade] [the] %world%'s [world[ ]]border cent(re|er)`
- `[skjade] [the] cent(re|er) of %world%'s [world[ ]]border`

Allows you to get and set the center of a world border.

**Example:**
```skript
set the centre of player's world's world border to player's location
```

**Since:** 1.3.0

### World Border Damage Amount

**Pattern(s):**
- `[skjade] [world[ ]]border damage amount of %world%`
- `[skjade] %world%'s [world[ ]]border damage amount`
- `[skjade] [the] damage amount of %world%'s [world[ ]]border`

Allows you to get and set the damage amount of a world border.

**Example:**
```skript
set the damage amount of player's world's world border to 3
```

**Since:** 1.3.0

### World Border Damage Buffer

**Pattern(s):**
- `[skjade] [world[ ]]border damage buffer of %world%`
- `[skjade] %world%'s [world[ ]]border damage buffer`
- `[skjade] [the] damage buffer of %world%'s [world[ ]]border`

Allows you to get and set the damage buffer of a world border.

**Example:**
```skript
set the damage buffer of player's world's world border to 3
```

**Since:** 1.3.0

### World Border Size

**Pattern(s):**
- `[skjade] [world[ ]]border size of %world%`
- `[skjade] %world%'s [world[ ]]border size`
- `[skjade] [the] size of %world%'s [world[ ]]border`

Allows you to get and set the size of a world border.

**Example:**
```skript
set the size of player's world's world border to 500
```

**Since:** 1.3.0

### World Border Warning Distance

**Pattern(s):**
- `[skjade] [world[ ]]border warning distance of %world%`
- `[skjade] %world%'s [world[ ]]border warning distance`
- `[skjade] [the] warning distance of %world%'s [world[ ]]border`

Allows you to get and set the warning distance of a world border.

**Example:**
```skript
set the warning distance of player's world's world border to 10
```

**Since:** 1.3.0

### World Border Warning Time

**Pattern(s):**
- `[skjade] [world[ ]]border warning time of %world%`
- `[skjade] %world%'s [world[ ]]border warning time`
- `[skjade] [the] warning time of %world%'s [world[ ]]border`

Allows you to get and set the warning time of a world border.

**Example:**
```skript
set the warning time of player's world's world border to 20 seconds
```

**Since:** 1.3.0

## Custom Types

### Character

- Codename: `character` (user pattern: `char(acter)?s?`)
- A single character.

### Hologram

- Codename: `hologram` (user pattern: `holo(gram)?s?`)
- A hologram created with DecentHolograms.
- **Requires:** DecentHolograms

### Hologram Line

- Codename: `hologramline` (user pattern: `holo(gram)?( |-)?lines?`)
- A line of a hologram.
- **Requires:** DecentHolograms

### Laser

- Codename: `laser` (user pattern: `laser?s?`)
- A guardian beam.

### Paste

- Codename: `paste` (user pattern: `paste?s?`)
- A PasteBuilder created with SkJadePlus (Pastebin).

### Structure Type

- Codename: `skjstructuretype` (user pattern: `skjstructuretype?s?`)
- A specified structure type.

## Lasers / Guardian Beams

### Attach Entity to Laser

**Pattern(s):** `attach %livingentity% to [the] end of %lasers%`

Attaches a living entity to the end of a laser.

**Example:**
```skript
attach event-mob to end of {_laser}
```

**Since:** 1.4

### Create Laser/Guardian Beam

**Pattern(s):** `create [a] [new] (la(s|z)er [beam]|guardian beam) from %location% to %location% for( %-timespan%|perm:[ ]ever) with [the] id %string%`

Creates a laser/guardian beam. Does NOT show it.

**Example:**
```skript
create a new laser from player to player's target block for 10 seconds with the id "kachow"
```

**Since:** 1.3.1

### Laser/Guardian Beam

**Pattern(s):** `[the] (la(s|z)er [beam]|guardian beam) with [the] id %string%`

Gets the laser/beam with the given id.

**Example:**
```skript
start the laser beam with id "my laser" for all players
```

**Since:** 1.3.1

### Make Laser Change Colour

**Pattern(s):** `(force|make) %lasers% [to] change colo[u]r[[']s]`

Makes a laser change colour. You cannot specify this coloUr.

**Example:**
```skript
force the laser with id "coochie vegan she a veggie" to change colour
```

**Since:** 1.3.1

### Show Laser/Guardian Beam

**Pattern(s):** `show [a] [new] (la(s|z)er [beam]|guardian beam) from %location% to %location% for %timespan% [for %-players%]`

Shows a guardiam beam between two points.

**Example:**
```skript
show a laser from player to player's target block for 10 seconds for player
```

**Since:** 1.3.1

### Start Laser/Beam

**Pattern(s):** `start %laser% [for %-players%]`

Starts a stored laser.

**Example:**
```skript
start the laser with id "nicki minaj is the queen of rap"
```

**Since:** 1.3.1

### Start Location of a Laser

**Pattern(s):** `[the] (1¦start|end)[ing] [loc[ation]] of %laser%`

Returns the start or end location of a laser.

**Example:**
```skript
broadcast "%the start location of the lazer with id ""i got a big batty"%""
```

**Since:** 1.3.1

### Stop Laser

**Pattern(s):** `stop %lasers%`

Stops any lasers in progress.

**Example:**
```skript
stop the laser with id "my twins big like tia tamara ugh wha"
```

**Since:** 1.3.1

## Holograms

All hologram syntaxes require the DecentHolograms plugin.

### Add Item Line

**Pattern(s):** `add [[the] item] %itemstack% to %hologram%`

Adds an item to the hologram.

**Example:**
```skript
add glowing diamond to the hologram with id "testHolo"
```

**Since:** 1.0.0
**Requires:** DecentHolograms

### Add Text Line

**Pattern(s):** `add [the] [text] [line[s]] %strings% to %hologram%`

Adds a line of text to the hologram.

**Example:**
```skript
add line "This is a hologram!" to the hologram with id "testHolo"
```

**Since:** 1.0.0
**Requires:** DecentHolograms

### All Holograms

**Pattern(s):** `all [of] [the] [skjade[s]] holograms`

Returns all holograms created by SkJadePlus.

**Example:**
```skript
delete all skjade holograms
```

**Since:** 1.2.0
**Requires:** DecentHolograms

### Contents of Hologram Line

**Pattern(s):** `[skjade] [the] (content[s]|text|item) (of|in|at) [[the] holo[gram]] %hologramline%`

Returns the text of a hologram line.

**Example:**
```skript
broadcast content of event-line
```

**Since:** 1.3.1
**Requires:** DecentHolograms

### Create Hologram

**Pattern(s):** `create [a] [(hd|holographic[ ]displays)] holo[gram] at %location% [with [the]] id %string% (1¦to be (hidden|invisible) [by default]|)`

Creates a hologram at the specified location with the id specified. The ID must be unique. (Holograms are not persistent over restart, so recreate them on script load.)

**Example:**
```skript
create a hologram at location(10,100,10,world("world")) with the id "testHolo"
```

**Since:** 1.0.0
**Requires:** DecentHolograms

### Delete Hologram

**Pattern(s):** `delete [(hd|holographic[ ]displays)] %hologram%`

Deletes a hologram.

**Example:**
```skript
delete the hologram with the id "myHologram"
```

**Since:** 1.0.0
**Requires:** DecentHolograms

### Hologram

**Pattern(s):**
- `[the] holo[gram] with [the] id %string%`
- `event(-| )holo[gram]`

A DecentHolograms Hologram. The ID of every hologram should be unique.

**Example:**
```skript
event-holo
the hologram with id the "id"
```

**Since:** 1.0.0
**Requires:** DecentHolograms

### Hologram Click

**Pattern(s):** `[skjadeplus|decent[ ]holograms|dh] holo[gram] click`

Called when a player clicks a SkJadePlus (DecentHolograms) hologram; `event-player` is the clicker and `event-hologram` is the clicked hologram.

**Example:**
```skript
on hologram click:
	send "you clicked %event-hologram%" to event-player
```

**Since:** 1.0.0
**Requires:** DecentHolograms

### Hologram Deleted

**Pattern(s):** `%hologram% (has been|is) deleted`

Checks whether a hologram has been deleted (considered deleted when no longer registered under its id).

**Example:**
```skript
if {_holo} has been deleted:
	broadcast "the hologram is gone"
```

**Since:** 1.0
**Requires:** DecentHolograms

### Hologram Line

**Pattern(s):**
- `[all] [the] lines of %holograms%`
- `line %number% of [the] [holo[gram]] %hologram%`

The hologram line of a specified hologram.

**Example:**
```skript
set {_line::*} to all lines of the hologram with id "hi"
```

**Since:** 1.0.0
**Requires:** DecentHolograms

### ID of a Hologram

**Pattern(s):**
- `[the] (id|key) of [the [hologram]] %hologram%`
- `%hologram%[']s (id|key)`

Returns the ID of a hologram.

**Example:**
```skript
broadcast event-hologram's id
```

**Since:** 1.2.0
**Requires:** DecentHolograms

### Is A Hologram

**Pattern(s):** `%object% is a [(hd|holographic displays|decent[ ]holograms)] hologram`

Checks if a value is a hologram, either a hologram object or the id/name of an existing hologram.

**Example:**
```skript
if {_holo} is a hologram:
```

**Since:** 1.0.0
**Requires:** DecentHolograms

### Remove Line

**Pattern(s):**
- `remove %hologramlines%`
- `remove [the] line %number% from %hologram%`

Removes a line from a hologram.

**Example:**
```skript
remove line number 3 from the hologram with id "testHolo"
```

**Since:** 1.0.0
**Requires:** DecentHolograms

### Reset Visibility

**Pattern(s):** `reset [the] visibility of %holograms% [(for all players|1¦for [the] [player] %-player%)]`

Resets the visibility of a hologram to its default visibility.

**Example:**
```skript
reset visibility of the hologram with id "%player%-hologram" for all players
reset the visibility of the hologram with id "%player%-hologram" for player
```

**Since:** 1.0.0
**Requires:** DecentHolograms

### Set Visibility

**Pattern(s):** `(1¦show|2¦hide) %holograms% (to|from) %players%`

Sets the visibility of a hologram to show or hide certain players.

**Example:**
```skript
show hologram with id "%arg-1%-hologram" to arg-1
hide the hologram with id "%player%-hologram" from player
```

**Since:** 1.0.0
**Requires:** DecentHolograms

### Teleport Hologram

**Pattern(s):** `teleport holo[gram][s] %holograms% to %location%`

Teleports a hologram to the given location.

**Example:**
```skript
teleport holo {_holo} to {_location}
```

**Since:** 1.4.3
**Requires:** DecentHolograms

## ProtocolLib

### Rotate Player

**Pattern(s):** `rotate %players% by %number% [horizontally] [[and] %-number% [vertically]]`

Rotates a player client-side without teleporting them, by sending a position packet with the new yaw/pitch.

**Example:**
```skript
rotate all players by 3 horizontally and 7 vertically
```

**Since:** 1.0.0
**Requires:** ProtocolLib

## Pastebin

### Build Paste

**Pattern(s):** `build [the] [paste] %paste% with the dev[eloper] key %string%`

Create's a pastebin paste. You should only call this once per paste. The developer key is linked to your pastebin account and found at this website: https://pastebin.com/doc_api#1

**Example:**
```skript
build the paste with the id "myPaste" with the developer key "{@developerKey}"
send the link of last built pastebin
```

**Since:** 1.0.0

### Create Paste

**Pattern(s):** `create [a] [new] paste[bin[ paste]] with [the] id %string%`

Creates a new pastebin paste. The ID must be unique.

**Example:**
```skript
create a new paste with the id "myPaste"
```

**Since:** 1.0.0

### Last Built Paste

**Pattern(s):** `[the] [(link|url) of] [the] last built paste[bin [paste]]`

Returns the link of the last built pastebin. If you build a new paste, this will be overridden.

**Example:**
```skript
send the url of the last built paste
```

**Since:** 1.0.0

### Paste

**Pattern(s):** `[the] paste[builder] with [the] id %string%`

Returns the pastebin paste with that id if it exists, else, it will be null.

**Example:**
```skript
set {logs::%player's uuid%} to the pastebuilder with the id "%player%Paste"
```

**Since:** 1.0.0

### Paste Response

**Pattern(s):** `[the] [raw] [paste] (response|text) from [[the] paste] %string% with the dev[eloper] key %string%`

Returns the raw text from a pastebin. The developer key is linked to your pastebin account and found at this website: https://pastebin.com/doc_api#1

**Example:**
```skript
set {_pastebin} to the paste repsone from "bpgexBcS" with the developer key {@apiKey}
```

**Since:** 1.0.0

### Set Paste Format

**Pattern(s):** `set [the] [text] format[ting] of %pastes% to %string%`

Sets the formattinng of a paste. You can find more about the formatting options here: https://pastebin.com/doc_api#5

**Example:**
```skript
set the format of the paste with the id "myPaste" to "java"
```

**Since:** 1.0.0

### Set Paste Text

**Pattern(s):** `set [the] [raw] text of %pastes% to %strings%`

Sets the text of a paste.

**Example:**
```skript
set the raw text of the paste with id "myPaste" to "Hi! This is my paste!"
```

**Since:** 1.0.0

### Set Paste Title

**Pattern(s):** `set [the] title of %pastes% to %string%`

Sets the title of a paste.

**Example:**
```skript
set the title of the paste with id "myPaste" to "Hi! This is my paste!"
```

**Since:** 1.0.0

### Set Paste Visibility

**Pattern(s):** `make %pastes% be (public|1¦private|2¦unlisted)`

Sets the visibility of a paste.

**Example:**
```skript
make the paste with the id "myPaste" be public
```

**Since:** 1.0.0

### Set Paste to Expire

**Pattern(s):**
- `set %pastes% to never expire`
- `set %pastes% to expire in (a|1|one) year`
- `set %pastes% to expire in (6|six) months`
- `set %pastes% to expire in (a|1|one) month`
- `set %pastes% to expire in (2|two) weeks`
- `set %pastes% to expire in (a|1|one) week`
- `set %pastes% to expire in (a|1|one) day`
- `set %pastes% to expire in (an|1|one) hour`
- `set %pastes% to expire in (ten|10) min[ute]s`

Set when a paste will expire.

**Example:**
```skript
set the paste with the id "myPaste" to never expire
```

**Since:** 1.0.0

## Requirements

- **Core:** Minecraft 26.1.2, Skript 2.15+. All syntaxes outside the soft-dependency sections below work with no extra plugins.
- **DecentHolograms (soft-dependency):** required for every entry under the Holograms section and for the `hologram` / `hologramline` custom types. Without it those syntaxes are unavailable.
- **ProtocolLib (soft-dependency):** required for the protocol effects under the ProtocolLib section (Rotate Player).
- **Paper:** some Is Wet condition patterns require Paper; the plain "is in water" pattern works on Spigot.
- **Elementals hook:** not available on 26.1.2.
