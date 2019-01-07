package constructmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RetainAllCardsAction extends AbstractGameAction{


    public RetainAllCardsAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
    }
    
    @Override
    public void update() {
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            c.retain = true;
        }
        this.isDone = true;
    }
}
