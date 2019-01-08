package constructmod.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.Level;

import com.megacrit.cardcrawl.cards.AbstractCard;
import basemod.BaseMod;
import constructmod.cards.AbstractConstructCard;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MakeStatEquivalentCopyPatch {
	
	@SpirePatch(clz=AbstractCard.class, method = "makeStatEquivalentCopy")
	public static class CopyForcedUpgradePatch{
		@SpireInsertPatch(rloc=1,localvars="card")
		public static void Insert(AbstractCard obj, AbstractCard card) {
			if (card instanceof AbstractConstructCard) {
				((AbstractConstructCard)card).forcedUpgrade = true;
			}
		}
	}
	
	@SpirePatch(clz=AbstractCard.class, method = "makeStatEquivalentCopy")
	public static class CopyIsMarriedPatch{
		public static AbstractCard Postfix(AbstractCard retVal, AbstractCard obj) {
			// only copy if specified beforehand with the copyIsMarried variable
			if (WeddingRingPatch.copyIsMarried.get(obj)) {
				//BaseMod.logger.log(Level.INFO,"Copying isMarried for: " + obj.name);
				WeddingRingPatch.isMarried.set(retVal, WeddingRingPatch.isMarried.get(obj));
			}
			WeddingRingPatch.copyIsMarried.set(obj, false); // normally we don't want to copy this info, so reset it.*/
			return retVal;
		}
	}

	@SpirePatch(clz= GridCardSelectScreen.class, method = "update")
	public static class ShowRingOnUpgradePreview{
		@SpireInsertPatch(locator = Locator.class)
		public static void Insert(GridCardSelectScreen obj) {
			WeddingRingPatch.copyIsMarried.set(ReflectionHacks.getPrivate(obj,GridCardSelectScreen.class,"hoveredCard"),true);
		}

		private static class Locator extends SpireInsertLocator {
			public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {

				Matcher finalMatcher = new Matcher.MethodCallMatcher(
						AbstractCard.class, "makeStatEquivalentCopy");

				return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
			}
		}
	}
}