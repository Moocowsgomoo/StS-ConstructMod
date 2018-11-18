package constructmod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ImplosionExhaustPileAction extends AbstractGameAction
{
    private AbstractPlayer p;
    private boolean mega;

    public ImplosionExhaustPileAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.source = AbstractDungeon.player;
        this.p = AbstractDungeon.player;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            // Search through draw & discard piles, find all playable cards that aren't burns
            boolean isValid = true;
            final CardGroup validDrawpileCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (final AbstractCard c : p.drawPile.group) {
                for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (c.canUse(p, m) && c.cardID != Burn.ID) {
                        for (final CardQueueItem q : AbstractDungeon.actionManager.cardQueue) {
                            if (q.card.equals(c)) isValid = false;
                        }
                        if (isValid) validDrawpileCards.addToTop(c);
                        break; // break once we know it works with at least one monster
                    }
                }
            }

            int handCount = AbstractDungeon.player.hand.size();
            int burnCount = 0;

            // Draw burns from piles, with the condition that we don't draw them if there isn't a matching playable card.
            for (AbstractCard c : p.exhaustPile.group){
                if (c.cardID == Burn.ID && handCount < BaseMod.MAX_HAND_SIZE && burnCount < validDrawpileCards.size()){
                    handCount++;
                    burnCount++;
                    AbstractDungeon.actionManager.addToBottom(new FetchCardToHandAction(c,p.discardPile));
                }
            }

            // At this point, we've drawn all Burns into hand that will play a card when moved. HOWEVER, the card text
            // says to put ALL burns into hand, and the play effect is secondary. So if handsize < BaseMod.MAX_HAND_SIZE, we do a second
            // pass and just fill the player's hand with all the burns we can find. Mwahahaha.

            // Note: we haven't ACTUALLY drawn any cards yet, just set up the actions to do so. That's why we're using
            // this manual variable handCount instead of checking the actual hand size.

            for (AbstractCard c : p.exhaustPile.group){
                if (c.cardID == Burn.ID && handCount < BaseMod.MAX_HAND_SIZE){
                    handCount++;
                    AbstractDungeon.actionManager.addToBottom(new FetchCardToHandAction(c,p.exhaustPile));
                }
            }

            // burnCount is now an accurate reflection of the number of cards we will PLAY from
            // each pile, not necessarily the number of burns drawn.

            playCards(burnCount, validDrawpileCards);

            this.isDone = true;
        }
    }

    public void playCards(int count, CardGroup validCards){

        // This really shouldn't happen...
        if (count > validCards.size()) count = validCards.size();

        for (int i = 0; i < count; ++i) {
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
            AbstractMonster m = AbstractDungeon.getRandomMonster();
            if (card.canUse(AbstractDungeon.player, m)){
                card.applyPowers();
                AbstractDungeon.actionManager.addToBottom(new QueueCardFrontAction(card, m));
            }
        }
    }
}
