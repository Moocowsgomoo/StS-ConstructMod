package constructmod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;

import constructmod.relics.WeddingRing;
import javassist.CannotCompileException;
import javassist.CtBehavior;

// From the Mystic Mod (https://github.com/JohnnyDevo/The-Mystic-Project/blob/master/src/main/java/mysticmod/Patches/SpireHeartButtonEffectMysticTextPatch.java)
@SpirePatch(clz=SpireHeart.class, method = "buttonEffect")
public class SpireHeartPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(SpireHeart __instance, int buttonPressed) {
        if (AbstractDungeon.player.chosenClass == TheConstructEnum.THE_CONSTRUCT_MOD) {
            __instance.roomEventText.updateBodyText("NL You enter self-destruct mode...");
        }
    }
    public static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(RoomEventDialog.class, "updateDialogOption");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}