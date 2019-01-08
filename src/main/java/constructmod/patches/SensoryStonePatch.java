package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.beyond.SensoryStone;
import com.megacrit.cardcrawl.localization.UIStrings;
import constructmod.cards.AbstractConstructCard;

import java.util.ArrayList;

@SpirePatch(clz= SensoryStone.class, method="getRandomMemory")
public class SensoryStonePatch {

	public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("constructMemoryText");

	@SpireInsertPatch(rloc=2, localvars = {"memories"})
	public static void Insert(SensoryStone obj, ArrayList<String> memories) {
		final String MEMORY_TEXT =
						uiStrings.TEXT[0] + uiStrings.TEXT[1] + uiStrings.TEXT[2];
		memories.add(MEMORY_TEXT);
	}
}
