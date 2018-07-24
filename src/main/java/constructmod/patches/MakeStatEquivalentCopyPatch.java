package constructmod.patches;

import org.apache.logging.log4j.Level;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.cards.AbstractCard", method = "makeStatEquivalentCopy")
public class MakeStatEquivalentCopyPatch {
	public static AbstractCard Postfix(AbstractCard retVal, AbstractCard obj) {
		// only copy if specified beforehand with the copyIsMarried variable
		if (WeddingRingPatch.copyIsMarried.get(obj)) {
			BaseMod.logger.log(Level.INFO,"Copying isMarried for: " + obj.name);
			WeddingRingPatch.isMarried.set(retVal, WeddingRingPatch.isMarried.get(obj));
		}
		WeddingRingPatch.copyIsMarried.set(obj, false); // normally we don't want to copy this info, so reset it.*/
		return retVal;
	}
}