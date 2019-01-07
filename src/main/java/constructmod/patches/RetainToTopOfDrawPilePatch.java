package constructmod.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import constructmod.ConstructMod;
import constructmod.actions.CardToTopOfDrawPileAction;
import constructmod.actions.CopyCardToDrawPileAction;
import constructmod.cards.AbstractConstructCard;

import java.lang.reflect.Field;
import java.util.ArrayList;

@SpirePatch(clz= RestoreRetainedCardsAction.class, method = "update")
public class RetainToTopOfDrawPilePatch {
    public static void Prefix(RestoreRetainedCardsAction obj) {
        try {
            if (ConstructMod.hasChallengeActive(3)) {
                Field cardGroupField = RestoreRetainedCardsAction.class.getDeclaredField("group");
                cardGroupField.setAccessible(true);
                CardGroup group = ((CardGroup)cardGroupField.get(obj));
                CardGroup discarded = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard c:group.group){
                    discarded.addToRandomSpot(c);
                }
                int size = group.size();
                size = MathUtils.floor(size*0.5f);
                for (;size>0;size--) {
                    AbstractCard card = discarded.getTopCard();
                    ConstructMod.logger.debug(card.originalName);
                    discarded.removeCard(card);
                    AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(card,AbstractDungeon.player.hand));
                }
                discarded.clear();
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