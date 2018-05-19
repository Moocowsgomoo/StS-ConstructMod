# The Construct
Adds a new character for Slay The Spire, complete with 70+ new cards and 8 new relics.
* On reaching the first unlock level, you'll unlock an alternate starting relic in the "Mods" tab of the game's main menu.
* Right now all cards and relics are unlocked at the start, so subsequent unlocks don't do anything.

The Construct is a mix of the many robotic enemies you'll find in the Spire, and uses familiar abilities like Mode Shift, Stasis, and of course the Hyper Beam. Its main strength is adaptability; the new Cycle keyword gives you some control over the composition of your hand at any given time, and your starter Mode Shift cards allow you to switch between attack and defense on the fly.

This character is meant to feel as close to an official character as possible, with multiple potential deck archetypes and rewarding interactions between cards. Balance is an ongoing process and cards/relics may change over time.

[Card List](https://docs.google.com/spreadsheets/d/15IW24uQ3GVjkAM2wEQC83939FRxfUPWoshojcNTimdM/edit?usp=sharing)

![Character Select Image](github_resources/charselect.png)

## New Keyword: Cycle ##
**Definition:** When drawn, discard this and draw another card if the cycle condition is met. Only works once per card per turn.
**Examples:**
* Flak Barrage: Cycle if your Strength < 1. Deal 0 damage to a random enemy 4 times.
* Isolate: Cycle if there's more than one enemy. Your attacks deal double damage this turn.
* Guard Orb: Cycle. When cycled, gain 2 Block.

![Gameplay Image](github_resources/gameplay.png)

## Requirements ##
Copied from https://github.com/gskleres/FruityMod-StS

#### General Use ####
* Java 8 (JRE). Currently only Java 8 should be used, issues with Java 9 are being looked into.
* BaseMod v.2.10.0+ (https://github.com/daviscook477/BaseMod/releases)
* ModTheSpire v2.6.0+ (https://github.com/kiooeht/ModTheSpire/releases)

## Installation ##
1. If you have `ModTheSpire` already installed you can skip to step 5. Otherwise continue with step 2:
2. Download `ModTheSpire.jar` from the latest release (https://github.com/kiooeht/ModTheSpire/releases)
3. Move `ModTheSpire.jar` into your **Slay The Spire** directory. This directory is likely to be found under `C:\Program Files (x86)\Steam\steamapps\common\SlayTheSpire`. Place `ModTheSpire.jar` in that directory so it looks like `C:\Program Files (x86)\Steam\steamapps\common\SlayTheSpire\ModTheSpire.jar`
4. Create a `mods` folder in your **Slay The Spire** directory so it looks like `C:\Program Files (x86)\Steam\steamapps\common\SlayTheSpire\mods`
5. Download `BaseMod.jar` from the latest release (https://github.com/daviscook477/BaseMod/releases)
6. Move `BaseMod.jar` into the `mods` folder you created in step 4
7. Download `ConstructMod.jar` from the latest release (https://github.com/Moocowsgomoo/StS-ConstructMod/releases)
8. Move `ConstructMod.jar` into the `mods` folder you created in step 4
9. Your modded version of **Slay The Spire** can now be launched by double-clicking on `ModTheSpire.jar`
10. This will open a mod select menu where you need to make sure that both `BaseMod` and `ConstructMod` are checked before clicking **play**

Here is a great video showing how to install mods, by Xterminator: https://www.youtube.com/watch?v=r2m2aL1eEjw

## Special Thanks ##
1. Thanks to the [devs](https://www.megacrit.com/) of **SlayTheSpire** for making such and awesome game, allowing us to mod it, and allowing us to use recolored versions of their art assets in our mod
2. Thanks to t-larson and contributors (https://github.com/t-larson) for BaseMod!!
3. Thanks to kiooeht and contributors (https://github.com/kiooeht) for ModTheSpire!!
4. Thanks to everyone in the Discord community who's played the mod and given feedback!

## Other Mods ##
There are lots of fantastic mods that already exist for Slay the Spire. An updated list can be found at:
* https://github.com/kiooeht/ModTheSpire/wiki/List-of-Known-Mods
