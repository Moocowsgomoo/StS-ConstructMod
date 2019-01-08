package constructmod.cards;

import org.apache.logging.log4j.Level;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;

import basemod.BaseMod;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;
import constructmod.relics.ClockworkPhoenix;

import java.util.Iterator;

public class ClockworkEgg extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("ClockworkEgg");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final String M_SCRAMBLED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 0;
	private static final int POOL = 1;
	
	private boolean isScrambled = false;
	
	public ClockworkEgg() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
		this.timesUpgraded = 0;
	}
	
	/*@Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
		if (this.timesUpgraded > 0) return true;
		
        this.cantUseMessage = "I can't play this card.";
        return false;
    }*/

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (this.timesUpgraded == 0) {
			this.exhaust = true;
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
		}
		else if (this.timesUpgraded == 1) {
			final AttackMode a = new AttackMode();
			a.upgrade();
			final DefenseMode d = new DefenseMode();
			d.upgrade();
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(a));
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(d));
		}
		else if (this.timesUpgraded == 2) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new StrengthPower(p,3),3));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new DexterityPower(p,3),3));
		}
		/*else if (this.timesUpgraded == 3) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new EnergizedPower(p,4),4));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new DrawCardNextTurnPower(p,4),4));
		}*/
		else if (this.timesUpgraded == 3) {
			
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new IntenseZoomEffect(p.drawX+20,p.drawY+90,false)));
			
			AbstractRelic relic;
			
			if (!p.hasRelic(MoltenEgg2.ID)) {
				relic = RelicLibrary.getRelic(MoltenEgg2.ID).makeCopy();
				AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, relic);
				AbstractDungeon.uncommonRelicPool.remove(relic.relicId);
			}
			if (!p.hasRelic(ToxicEgg2.ID)) {
				relic = RelicLibrary.getRelic(ToxicEgg2.ID).makeCopy();
				AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, relic);
				AbstractDungeon.uncommonRelicPool.remove(relic.relicId);
			}
			if (!p.hasRelic(FrozenEgg2.ID)) {
				relic = RelicLibrary.getRelic(FrozenEgg2.ID).makeCopy();
				AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, relic);
				AbstractDungeon.uncommonRelicPool.remove(relic.relicId);
			}
			if (!p.hasRelic(ClockworkPhoenix.ID)) {
				relic = RelicLibrary.getRelic(ClockworkPhoenix.ID).makeCopy();
				AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, relic);
			}

			// remove from master deck
			Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();
			while(var2.hasNext()) {
				AbstractCard c = (AbstractCard)var2.next();
				if (c.uuid.equals(this.uuid)) {
					AbstractDungeon.player.masterDeck.removeCard(c);
					break;
				}
			}

			this.exhaust = true;
		}
		else if (this.megaUpgraded) {
			
			if (this.isScrambled) {
				if (!AbstractDungeon.player.hasPower(ConfusionPower.POWER_ID)) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new ConfusionPower(p)));
				AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,5));
			} else {
				if (AbstractDungeon.getCurrRoom().rewards.size() < 6) { // hard-cap rewards in case someone finds an infinite combo
					
					AbstractDungeon.actionManager.addToBottom(new VFXAction(new IntenseZoomEffect(p.drawX+20,p.drawY+90,false)));
					
					final AbstractRelic.RelicTier tier = this.returnRandomRelicTier();
					AbstractDungeon.getCurrRoom().addRelicToRewards(tier);
				} else {
					AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0f, EXTENDED_DESCRIPTION[6], true));
				}
			}
		}
	}
	
	private AbstractRelic.RelicTier returnRandomRelicTier() {
        int roll = AbstractDungeon.relicRng.random(0, 99);
        if (roll < 50) {
            return AbstractRelic.RelicTier.COMMON;
        }
        if (roll > 82) {
            return AbstractRelic.RelicTier.RARE;
        }
        return AbstractRelic.RelicTier.UNCOMMON;
    }

	@Override
	public AbstractCard makeCopy() {
		return new ClockworkEgg();
	}
	
	@Override
	public AbstractCard makeStatEquivalentCopy() {
		ClockworkEgg copy = (ClockworkEgg)super.makeStatEquivalentCopy();
		if (copy.megaUpgraded && this.isScrambled) {
			copy.timesUpgraded--;
			copy.megaUpgraded = false;
			copy.upgrade(true,true);
		}
		return copy;
        /*final AbstractConstructCard card = (AbstractConstructCard) this.makeCopy();
        for (int i = 0; i < this.timesUpgraded; ++i) {
            card.upgrade(true, this.isScrambled);
        }
        card.cost = this.cost;
        card.costForTurn = this.costForTurn;
        card.isCostModified = this.isCostModified;
        card.isCostModifiedForTurn = this.isCostModifiedForTurn;
        card.inBottleLightning = this.inBottleLightning;
        card.inBottleFlame = this.inBottleFlame;
        card.inBottleTornado = this.inBottleTornado;
        card.isSeen = this.isSeen;
        card.isLocked = this.isLocked;
        card.misc = this.misc;
        return card;*/
    }
	
	@Override
    public boolean canUpgrade() {
        return super.canUpgrade() || (this.timesUpgraded < 3);
    }
	
	@Override
	public void upgrade(boolean forcedUpgrade, boolean inCombat) {
		if (inCombat && !this.megaUpgraded) this.isScrambled = true;
		super.upgrade(forcedUpgrade, inCombat);
	}

	@Override
	public void upgrade() {
		BaseMod.logger.log(Level.DEBUG, "Egg is being upgraded: " + this.timesUpgraded + this.upgraded);
		/*if (!this.upgraded && this.timesUpgraded > 0) { // Weird upgrades, like RtS's Demonic Infusion card
			this.upgradeName();
			this.initializeTitle();
			this.cost = 0;
			this.costForTurn = 0;
			this.rawDescription = M_UPGRADE_DESCRIPTION + " NL (Enjoy your shenanigans!)";
			this.initializeDescription();
			this.exhaust = true;
		}*/
		if (this.timesUpgraded < 3) {
			this.upgradeName();
			this.cost++;
			this.costForTurn++;
			this.name = ClockworkEgg.NAME + "+" + this.timesUpgraded;
			this.initializeTitle();
			this.rawDescription = EXTENDED_DESCRIPTION[timesUpgraded];
			this.initializeDescription();
			if (timesUpgraded == 3) this.exhaust = true;
			BaseMod.logger.log(Level.DEBUG, "New times: " + timesUpgraded);
			
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			
			if (this.isScrambled) {
				this.cost = 0;
				this.costForTurn = 0;
				this.name = EXTENDED_DESCRIPTION[4];
				this.initializeTitle();
				this.rawDescription = M_SCRAMBLED_DESCRIPTION;
				this.initializeDescription();
				this.loadCardImage("img/constructCards/"+"ScrambledEgg"+".png");
				BaseMod.logger.log(Level.DEBUG, "SCRAMBLED New times: " + timesUpgraded);
			}
			else {
				this.cost = 0;
				this.costForTurn = 0;
				this.name = EXTENDED_DESCRIPTION[5];
				this.initializeTitle();
				this.rawDescription = M_UPGRADE_DESCRIPTION;
				this.initializeDescription();
				this.exhaust = true;
				this.loadCardImage("img/constructCards/"+"ClockworkPhoenix"+".png");
				BaseMod.logger.log(Level.DEBUG, "MEGA New times: " + timesUpgraded);
			}
		}
	}
}
