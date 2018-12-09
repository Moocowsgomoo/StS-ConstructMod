package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import constructmod.ConstructMod;
import constructmod.actions.CardToTopOfDrawPileAction;
import constructmod.actions.CopyCardToDrawPileAction;
import constructmod.cards.AbstractConstructCard;

import java.lang.reflect.Field;

@SpirePatch(clz= RestoreRetainedCardsAction.class, method = "update")
public class RetainToTopOfDrawPilePatch {
    public static void Prefix(RestoreRetainedCardsAction obj) {
        try {
            if (ConstructMod.hasChallengeActive(3)) {
                Field cardGroupField = RestoreRetainedCardsAction.class.getDeclaredField("group");
                cardGroupField.setAccessible(true);
                AbstractDungeon.actionManager.addToTop(new CardToTopOfDrawPileAction(((CardGroup) cardGroupField.get(obj)).getRandomCard(true)));
                //for (AbstractCard c : ((CardGroup) cardGroupField.get(obj)).group) {
                //    AbstractDungeon.actionManager.addToTop(new CardToTopOfDrawPileAction(c));
                //}
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}