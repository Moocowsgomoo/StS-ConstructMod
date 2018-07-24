package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import constructmod.relics.WeddingRing;

@SpirePatch(cls="com.megacrit.cardcrawl.cards.CardGroup", method = "initializeDeck")
public class CardGroupFromMasterDeckPatch {
	public static void Prefix(CardGroup obj, final CardGroup masterDeck) {
		// the one time we want to copy the isMarried property, when the master deck is being converted into your draw pile.
		for (final AbstractCard c : masterDeck.group) {
            WeddingRingPatch.copyIsMarried.set(c, true);
        }
	}
}