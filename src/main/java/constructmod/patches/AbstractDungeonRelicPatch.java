package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

public class AbstractDungeonRelicPatch {
	
	@SpirePatch(cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "returnRandomScreenlessRelic")
	public static class ReturnRandomScreenlessRelicPatch {
		public static AbstractRelic Postfix(AbstractRelic retVal, RelicTier tier) {
			while (retVal.relicId.equals("WeddingRing")) {
				retVal = RelicLibrary.getRelic(AbstractDungeon.returnRandomRelicKey(tier)).makeCopy();
			}
			return retVal;
		}
	}
	
	// TODO: Add patches for returnEndRandomRelicKey and returnRandomRelicKey.
	// This stops the ring from showing up if there are less than 2 selectable cards in your deck. Low-priority.
}