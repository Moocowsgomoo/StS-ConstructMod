package constructmod.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ScreenShake;

@SpirePatch(cls="com.megacrit.cardcrawl.screens.charSelect.CharacterOption", method="updateHitbox")
public class CharScreenSelectFXPatch {

	@SpireInsertPatch(rloc=49)
	public static void Insert(Object __obj_instance) {
		AbstractPlayer.PlayerClass chosenClass = CardCrawlGame.chosenCharacter;
		if (chosenClass.toString() == "THE_CONSTRUCT_MOD") {
			CardCrawlGame.sound.playV("AUTOMATON_ORB_SPAWN", 1.75f);
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
		}
	}
	
}
