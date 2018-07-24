package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

@SpirePatch(cls="com.megacrit.cardcrawl.cards.CardGroup", method = "getGroupWithoutBottledCards")
public class GetGroupWithoutBottledCardsPatch {
	public static CardGroup Postfix(CardGroup retVal, CardGroup obj) {
		final CardGroup toRemove = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		for (final AbstractCard c : retVal.group) {
            if (WeddingRingPatch.isMarried.get(c)) {
                toRemove.addToTop(c);
            }
        }
		for (final AbstractCard c2 : toRemove.group) {
			retVal.removeCard(c2);
		}
		return retVal;
	}
}