package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import constructmod.cards.AbstractConstructCard;

@SpirePatch(
		cls="com.megacrit.cardcrawl.helpers.CardLibrary",
		method="getCopy"
		//paramtypes={"String", "int"}
	)
public class CardLibraryPatch {
	public static AbstractCard Replace(final String key, final int upgradeTime) {
        final AbstractCard source = CardLibrary.getCard(key);
        AbstractCard retVal = null;
        if (source == null) {
            retVal = CardLibrary.getCard("Madness").makeCopy();
        }
        else {
            retVal = CardLibrary.getCard(key).makeCopy();
        }
        for (int i = 0; i < upgradeTime; ++i) {
            if (retVal instanceof AbstractConstructCard) ((AbstractConstructCard)retVal).upgrade(true);
            else retVal.upgrade();
        }
        return retVal;
    }

}
