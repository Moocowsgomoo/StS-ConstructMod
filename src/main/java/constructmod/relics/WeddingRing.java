package constructmod.relics;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.rooms.*;

import basemod.abstracts.CustomRelic;
import constructmod.ConstructMod;
import constructmod.patches.WeddingRingPatch;

import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.*;

public class WeddingRing extends CustomRelic
{
    public static final String ID = ConstructMod.makeID("WeddingRing");
    private static final String IMG = "img/constructRelics/WeddingRing.png";
    private static final String OUTLINE = "img/constructRelics/outline/WeddingRing.png";
    private boolean cardSelected;
    public AbstractCard card1;
    public AbstractCard card2;
    public boolean canPlayCard = true;
    
    public AbstractCard cardToPlay = null;
    
    public WeddingRing() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.CLINK);
        this.cardSelected = true;
        
        this.card1 = this.card2 = null;
    }
    
    @Override
    public String getUpdatedDescription() {
    	if (AbstractDungeon.player != null &&
    			AbstractDungeon.player.hasRelic(this.ID) && 
    			(this.card1 == null || this.card2 == null) && 
    			ConstructMod.marriedCard1 >= 0 && 
    			ConstructMod.marriedCard2 >= 0 &&
    			ConstructMod.marriedCard1 < AbstractDungeon.player.masterDeck.size() &&
    			ConstructMod.marriedCard2 < AbstractDungeon.player.masterDeck.size()) {
        	this.card1 = AbstractDungeon.player.masterDeck.group.get(ConstructMod.marriedCard1);
            this.card2 = AbstractDungeon.player.masterDeck.group.get(ConstructMod.marriedCard2);
            WeddingRingPatch.isMarried.set(this.card1, true);
            WeddingRingPatch.isMarried.set(this.card2, true);
            return "Once per turn, after you play " + FontHelper.colorString(this.card1.name, "y") + " or " + FontHelper.colorString(this.card2.name, "y") + ", play the other one too.";
        }
    	else return DESCRIPTIONS[0];
    }
    
    @Override
    public void onEquip() {
    	
    	for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
        	WeddingRingPatch.isMarried.set(card, false);
        }
    	
        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        
        CardGroup validCards = new CardGroup(CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
        	if (c.cost > -2) validCards.addToTop(c);
        }
        
        AbstractDungeon.gridSelectScreen.open(validCards, 2, "Choose 2 cards to link.", false, false, false, false);
    }
    
    @Override
    public void onUnequip() {
        if (this.card1 != null) {
            final AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(this.card1);
            if (cardInDeck != null) {
            	WeddingRingPatch.isMarried.set(this.card1, false);
            }
        }
        if (this.card2 != null) {
            final AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(this.card2);
            if (cardInDeck != null) {
            	WeddingRingPatch.isMarried.set(this.card2, false);
            }
        }
    }
    
    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() > 1) {
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                WeddingRingPatch.isMarried.set(card, false);
            }
            this.cardSelected = true;
            this.card1 = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            WeddingRingPatch.isMarried.set(this.card1, true);
            this.card2 = AbstractDungeon.gridSelectScreen.selectedCards.get(1);
            WeddingRingPatch.isMarried.set(this.card2, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.description = "Once per turn, after you play " + FontHelper.colorString(this.card1.name, "y") + " or " + FontHelper.colorString(this.card2.name, "y") + ", play the other one too.";
            this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
            this.initializeTips();
        }
    }
    
    public void setDescriptionAfterLoading() {
    	this.description = "Once per turn, after you play " + FontHelper.colorString(this.card1.name, "y") + " or " + FontHelper.colorString(this.card2.name, "y") + ", play the other one too.";
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    
    @Override
    public void atTurnStart() {
    	this.canPlayCard = true;
    }
    
    @Override
    public void onPlayCard(final AbstractCard c, final AbstractMonster m) {
    	super.onPlayCard(c, m);
    	
    	cardToPlay = null;
    	
    	if (this.canPlayCard && WeddingRingPatch.isMarried.get(c)) {
    		
    		this.canPlayCard = false;
    		AbstractMonster mo = m;
    		if (mo == null || mo.isDeadOrEscaped()) mo = AbstractDungeon.getRandomMonster();
    		
	        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
	        	if (c != card && WeddingRingPatch.isMarried.get(card)) {
	        		cardToPlay = card;
	                card.freeToPlayOnce = true;
	                card.current_y = -200.0f * Settings.scale;
	                card.target_x = Settings.WIDTH / 2.0f;
	                card.target_y = Settings.HEIGHT / 2.0f;
	                card.targetAngle = 0.0f;
	                card.lighten(false);
	                card.drawScale = 0.12f;
	                card.targetDrawScale = 0.75f;
	                if (card.canUse(AbstractDungeon.player, mo)) {
	                    card.applyPowers();
	                    AbstractDungeon.actionManager.addToTop(new QueueCardAction(cardToPlay, mo));
	                }
	        	}
	        }
	        if (cardToPlay != null) AbstractDungeon.player.drawPile.removeCard(cardToPlay);
	        
	        for (AbstractCard card : AbstractDungeon.player.hand.group) {
	        	if (c != card && WeddingRingPatch.isMarried.get(card)) {
	        		cardToPlay = card;
	                card.freeToPlayOnce = true;
	                card.target_x = Settings.WIDTH / 2.0f;
	                card.target_y = Settings.HEIGHT / 2.0f;
	                card.targetAngle = 0.0f;
	                card.targetDrawScale = 0.75f;
	                if (card.canUse(AbstractDungeon.player, mo)) {
	                    card.applyPowers();
	                    AbstractDungeon.actionManager.addToTop(new QueueCardAction(cardToPlay, mo));
	                }
	        	}
	        }
	        if (cardToPlay != null) AbstractDungeon.player.hand.removeCard(cardToPlay);
	        
	        for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
	        	if (c != card && WeddingRingPatch.isMarried.get(card)) {
	        		cardToPlay = card;
	                card.freeToPlayOnce = true;
	                card.current_y = -200.0f * Settings.scale;
	                card.target_x = Settings.WIDTH / 2.0f;
	                card.target_y = Settings.HEIGHT / 2.0f;
	                card.targetAngle = 0.0f;
	                card.lighten(false);
	                card.drawScale = 0.12f;
	                card.targetDrawScale = 0.75f;
	                if (card.canUse(AbstractDungeon.player, mo)) {
	                    card.applyPowers();
	                    AbstractDungeon.actionManager.addToTop(new QueueCardAction(cardToPlay, mo));
	                }
	        	}
	        }
	        if (cardToPlay != null) AbstractDungeon.player.discardPile.removeCard(cardToPlay);
	        
	        this.flash();
	        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    	}
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new WeddingRing();
    }
}
