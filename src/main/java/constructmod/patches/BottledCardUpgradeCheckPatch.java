package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import constructmod.relics.WeddingRing;

@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method = "bottledCardUpgradeCheck")
public class BottledCardUpgradeCheckPatch {
	public static void Postfix(AbstractPlayer obj, final AbstractCard c) {
		if (WeddingRingPatch.isMarried.get(c) && AbstractDungeon.player.hasRelic(WeddingRing.ID)) {
			((WeddingRing)AbstractDungeon.player.getRelic(WeddingRing.ID)).setDescriptionAfterLoading();
		}
	}
}