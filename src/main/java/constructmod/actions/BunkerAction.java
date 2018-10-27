package constructmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import constructmod.relics.ClawGrip;

public class BunkerAction extends AbstractGameAction
{
    public BunkerAction(AbstractCreature owner, int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.target = owner;
        this.amount = amount;
    }
    
    @Override
    public void update() {
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.retain) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.target, AbstractDungeon.player, amount));
            }
        }
        this.isDone = true;
    }
}
