package constructmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.UIStrings;
import constructmod.ConstructMod;
import constructmod.characters.TheConstruct;
import constructmod.ui.HeatMeter;

import java.util.ArrayList;

@SpirePatch(cls="com.megacrit.cardcrawl.core.AbstractCreature", method="renderPowerTips")
public class ShowCardsCycledPatch {

	public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("constructCharacterTipText");

	@SpireInsertPatch(rloc=1,localvars="tips")
	public static void Insert(AbstractCreature obj, SpriteBatch sb, ArrayList<PowerTip> tips) {
		if (obj instanceof TheConstruct && HeatMeter.numSegmentsToRender > 0){
			tips.add(new PowerTip(uiStrings.TEXT[0] + ConstructMod.cyclesThisTurn,uiStrings.TEXT[1]));
		}
	}
}
