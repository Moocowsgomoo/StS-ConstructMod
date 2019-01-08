package constructmod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.*;
import java.util.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.NoDrawPower;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.*;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.actions.common.*;
import org.apache.logging.log4j.*;

public class ConstructTumbleAction extends AbstractGameAction
{
    private boolean shuffleCheck;
    private static final Logger logger;
    public static ArrayList<AbstractCard> drawnCards;
    
    public ConstructTumbleAction(final AbstractCreature source, final int amount) {
        this.shuffleCheck = false;
        if (AbstractDungeon.player.hasPower(NoDrawPower.POWER_ID)) {
            AbstractDungeon.player.getPower(NoDrawPower.POWER_ID).flash();
            this.setValues(AbstractDungeon.player, source, amount);
            this.isDone = true;
            this.duration = 0.0f;
            this.actionType = ActionType.WAIT;
            return;
        }
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        }
        else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
    }
    
    @Override
    public void update() {
        if (this.amount <= 0) {
            this.isDone = true;
            return;
        }
        final int deckSize = AbstractDungeon.player.drawPile.size();
        final int discardSize = AbstractDungeon.player.discardPile.size();
        if (SoulGroup.isActive()) {
            return;
        }
        if (deckSize + discardSize == 0) {
            this.isDone = true;
            return;
        }
        if (AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.player.createHandIsFullDialog();
            this.isDone = true;
            return;
        }
        if (!this.shuffleCheck) {
            if (this.amount > deckSize) {
                final int tmp = this.amount - deckSize;
                AbstractDungeon.actionManager.addToTop(new ConstructTumbleAction(AbstractDungeon.player, tmp));
                AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
                if (deckSize != 0) {
                    AbstractDungeon.actionManager.addToTop(new ConstructTumbleAction(AbstractDungeon.player, deckSize));
                }
                this.amount = 0;
                this.isDone = true;
            }
            this.shuffleCheck = true;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.amount != 0 && this.duration < 0.0f) {
            if (Settings.FAST_MODE) {
                this.duration = Settings.ACTION_DUR_XFAST;
            }
            else {
                this.duration = Settings.ACTION_DUR_FASTER;
            }
            --this.amount;
            if (!AbstractDungeon.player.drawPile.isEmpty()) {
            	ConstructTumbleAction.drawnCards.add(AbstractDungeon.player.drawPile.getTopCard());
                AbstractDungeon.player.draw();
                AbstractDungeon.player.hand.refreshHandLayout();
            }
            else {
                ConstructTumbleAction.logger.warn("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: " + AbstractDungeon.player.masterDeck.getCardNames());
                this.isDone = true;
            }
            if (this.amount == 0) {
                this.isDone = true;
            }
        }
    }
    
    static {
        logger = LogManager.getLogger(DrawCardAction.class.getName());
        ConstructTumbleAction.drawnCards = new ArrayList<AbstractCard>();
    }
}
