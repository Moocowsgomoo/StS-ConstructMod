package constructmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import constructmod.cards.AbstractConstructCard;

public class MegaUpgradeRandomCardInHandAction extends AbstractGameAction
{

    public MegaUpgradeRandomCardInHandAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
    }
    
    @Override
    public void update() {

        final CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        tmp.clear();
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if ((c instanceof AbstractConstructCard && !((AbstractConstructCard) c).megaUpgraded)) {
                tmp.addToTop(c);
            }
        }
        if (tmp.size() <= 0){ // no construct cards we can hit, regular-upgrade a card instead.
            for (final AbstractCard c : AbstractDungeon.player.hand.group) {
                if ((c.canUpgrade())) {
                    tmp.addToTop(c);
                }
            }
        }
        if (tmp.size() > 0) { // found a card of some sort we can upgrade
            AbstractCard c = tmp.getRandomCard(true);
            for (int i=0;i<5;i++) {
                if (c instanceof AbstractConstructCard) ((AbstractConstructCard)c).upgrade(true,true);
                else c.upgrade();
                c.flash();
                c.applyPowers();
            }
        }
        this.isDone = true;
    }
}
