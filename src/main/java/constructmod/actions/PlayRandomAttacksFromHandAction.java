package constructmod.actions;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;

import basemod.BaseMod;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class PlayRandomAttacksFromHandAction extends AbstractGameAction
{
    private AbstractPlayer p;
    
    public PlayRandomAttacksFromHandAction(final AbstractMonster target, final int times) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.amount = times;
        this.p = AbstractDungeon.player;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
        	
        	boolean isValid = true;
        	
        	final CardGroup validCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        	for (final AbstractCard c : p.drawPile.group) {
        		if (c.type == AbstractCard.CardType.ATTACK && c.cardPlayable((AbstractMonster)this.target)) {
        			for (final CardQueueItem q : AbstractDungeon.actionManager.cardQueue) {
        				if (q.card.equals(c)) isValid = false;
        			}
        			if (isValid) validCards.addToTop(c);
        		}
        	}
        	
        	BaseMod.logger.log(Level.DEBUG, "Valid Cards: " + validCards.size());
        	
        	if (this.amount > validCards.size()) this.amount = validCards.size();
            
            for (int i = 0; i < this.amount; ++i) {
                final AbstractCard card = validCards.getRandomCard(true);
                validCards.removeCard(card);
                AbstractDungeon.player.drawPile.removeCard(card);
                card.freeToPlayOnce = true;
                card.current_y = -200.0f * Settings.scale;
                card.target_x = Settings.WIDTH / 2.0f + i * 200;
                card.target_y = Settings.HEIGHT / 2.0f;
                card.targetAngle = 0.0f;
                card.lighten(false);
                card.drawScale = 0.12f;
                card.targetDrawScale = 0.75f;
                if (card.canUse(AbstractDungeon.player, (AbstractMonster)this.target)) {
                    card.applyPowers();
                    AbstractDungeon.actionManager.addToTop(new QueueCardAction(card, this.target));
                }
            }
            this.isDone = true;
        }
    }
}
