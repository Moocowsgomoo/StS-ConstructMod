package constructmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.unique.TransmutationAction;
import com.megacrit.cardcrawl.actions.unique.TransmuteAction;
import com.megacrit.cardcrawl.actions.unique.Transmutev2Action;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;
import constructmod.actions.OverheatAction;
import constructmod.powers.AbstractCyclePower;
import constructmod.powers.FlashFreezePower;
import org.apache.logging.log4j.Level;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import constructmod.relics.ClockworkPhoenix;

public abstract class AbstractConstructCard extends CustomCard {
	
	public boolean megaUpgraded = false;
	public boolean forcedUpgrade = false;
	
	public boolean rebound = false;
	public boolean startInPlay = false;

	public boolean upgradedOverheat = false;
	public int overheat = -1;
	
	public AbstractConstructCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, int cardPool) {
		super(id, name, img.replace("cards/construct:", "constructCards/"), cost, rawDescription, type, color, rarity, target);
	}

	public boolean checkOverheat(){
		if (overheat <= 0 || AbstractDungeon.player.hasPower(FlashFreezePower.POWER_ID)) return false;
		//if (AbstractDungeon.player.hand.contains(this)) this.flash(Color.RED.cpy());
		if (ConstructMod.cyclesThisTurn >= overheat){
			AbstractDungeon.actionManager.addToTop(new OverheatAction(this));
			return true;
		}
		return false;
	}

	public void upgradeOverheat(int amount){
		this.overheat += amount;
		this.upgradedOverheat = true;
	}

	public void startInPlayEffect(){
		// the thing that happens when this card reads "start combat with this in play". Hopefully just applying a power.
	}
	
	public void CloneCore() {
		
		if (this.upgraded) {
			AbstractCard c = this.makeCopy();
			
			if (this.megaUpgraded || AbstractDungeon.player.hasRelic("Quantum Egg")) c.upgrade();
			
			if (AbstractDungeon.player.hasRelic("Quantum Egg")) {
				AbstractDungeon.player.getRelic("Quantum Egg").flash();
			}
			
			AbstractDungeon.player.discardPile.addToTop(c);
			AbstractDungeon.overlayMenu.discardPilePanel.pop(); // vfx when a card is added
		}
	}
	
	@Override
    public boolean canUpgrade() {
		
		if (this.megaUpgraded) return false;
		
		return 	super.canUpgrade() || 
				this.forcedUpgrade || 
				CardCrawlGame.mainMenuScreen.screen == CurScreen.RUN_HISTORY ||
				(AbstractDungeon.player.hasRelic(ClockworkPhoenix.ID) && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase != RoomPhase.COMBAT);
    }
	
	public boolean canUpgrade(boolean forced) {
		this.forcedUpgrade = forced;
		boolean result = canUpgrade();
		this.forcedUpgrade = false;
		return result;
	}
	
	public void upgrade(boolean forcedUpgrade) {
		this.upgrade(forcedUpgrade,false);
	}
	
	public void upgrade(boolean forcedUpgrade, boolean inCombat) {
		this.forcedUpgrade = forcedUpgrade;
		this.upgrade();
		this.forcedUpgrade = false;
	}
	
	public AbstractCard makeStatEquivalentCopy() {
        BaseMod.logger.log(Level.DEBUG,"Copying card " + this.name + " with " + this.timesUpgraded + " upgrades.");
        
        // Most of this logic is handled in MakeStatEquivalentCopyPatch.
        
        final AbstractConstructCard card = (AbstractConstructCard)super.makeStatEquivalentCopy();
        card.forcedUpgrade = false;
        card.overheat = this.overheat;
        return card;
    }
	
	protected void megaUpgradeName() {
        this.upgradeName();
        this.megaUpgraded = true;
        
        // reset these since they carry over from the original upgrade.
        // they just turn the number green on the card description when set to true
        // and will be set again in the mega-upgrade code which comes after this, if they are actually changed.
        this.upgradedDamage = false;
        this.upgradedBlock = false;
        this.upgradedCost = false;
        this.upgradedMagicNumber = false;
    }
	
	// The purple mega-upgrade title is rendered via RenderMegaUpgradePatch.
}
