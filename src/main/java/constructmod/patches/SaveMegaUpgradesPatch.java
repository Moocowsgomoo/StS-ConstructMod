package constructmod.patches;

import basemod.abstracts.CustomSavableRaw;
import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile.ModSaves;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.SensoryStone;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import constructmod.cards.AbstractConstructCard;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(clz= CardLibrary.class, method="getCopy",paramtypes = {"java.lang.String","int","int"})
public class SaveMegaUpgradesPatch {
    @SpireInsertPatch(locator = Locator.class,localvars = {"retVal"})
    public static void EnableMegaUpgrade(final String key, final int upgradeTime, final int misc, AbstractCard retVal) {
        //ModSaves.ArrayListOfJsonElement modCardSaves = ModSaves.modCardSaves.get(CardCrawlGame.saveFile);
        //if (retVal instanceof CustomSavableRaw) {
        //    ((CustomSavableRaw)retVal).onLoadRaw(modCardSaves == null || i >= modCardSaves.size() ? null : modCardSaves.get(i));
       // }
        if (retVal instanceof AbstractConstructCard) {
            ((AbstractConstructCard) retVal).forcedUpgrade = true;
        }
    }

    public static AbstractCard Postfix(AbstractCard retVal, final String key, int upgradeTime, final int misc) {
        if (retVal instanceof AbstractConstructCard) {
            ((AbstractConstructCard) retVal).forcedUpgrade = false;
        }
        return retVal;
    }

    private static class Locator extends SpireInsertLocator {

        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {

            Matcher finalMatcher = new Matcher.MethodCallMatcher(
                    AbstractCard.class, "upgrade");

            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}