package constructmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.Iterator;

public class QueueCardFrontAction extends AbstractGameAction {
    private AbstractCard card;
    public int energyOnUse = 0;

    public QueueCardFrontAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public QueueCardFrontAction(AbstractCard card, AbstractCreature target, int energyOnUse){
        this.duration = Settings.ACTION_DUR_FAST;
        this.card = card;
        this.target = target;
        this.energyOnUse = energyOnUse;
    }

    public QueueCardFrontAction(AbstractCard card, AbstractCreature target) {
        this(card,target,0);
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.card == null) {
                AbstractDungeon.actionManager.cardQueue.add(0, new CardQueueItem());
            } else if (!this.queueContains(this.card)) {
                AbstractDungeon.actionManager.cardQueue.add(0, new CardQueueItem(this.card, (AbstractMonster)this.target,this.energyOnUse));
            }

            this.isDone = true;
        }

    }

    private boolean queueContains(AbstractCard card) {
        Iterator var2 = AbstractDungeon.actionManager.cardQueue.iterator();

        CardQueueItem i;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            i = (CardQueueItem)var2.next();
        } while(i.card != card);

        return true;
    }
}
