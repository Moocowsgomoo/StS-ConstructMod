package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.DrawPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class ClockworkEgg extends CustomCard {
	public static final String ID = "ClockworkEgg";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 0;
	private static final int POOL = 1;
	
	public ClockworkEgg() {
		this(0);
	}
	
	public ClockworkEgg(final int upgrades) {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
		for (int i=0;i<upgrades;i++) {
			upgrade();
		}
	}
	
	@Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
		if (this.timesUpgraded > 0) return true;
		
        this.cantUseMessage = "I can't play this card.";
        return false;
    }

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (this.timesUpgraded == 1) {
			final AttackMode a = new AttackMode();
			a.upgrade();
			final DefenseMode d = new DefenseMode();
			d.upgrade();
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(a));
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(d));
		}
		else if (this.timesUpgraded == 2) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new StrengthPower(p,2),2));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new DexterityPower(p,2),2));
		}
		else if (this.timesUpgraded == 3) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new EnergizedPower(p,3),3));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new DrawCardNextTurnPower(p,3),3));
		}
		else if (this.timesUpgraded == 4) {
			AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.COMMON);
			AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.UNCOMMON);
			AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.RARE);
			p.masterDeck.removeCard(ID);
			// should auto-exhaust
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new ClockworkEgg();
	}
	
	@Override
    public boolean canUpgrade() {
        return (this.timesUpgraded < 4);
    }

	@Override
	public void upgrade() {
		if (this.timesUpgraded < 4) {
			this.timesUpgraded++;
			this.upgraded = true;
			this.cost++;
			this.costForTurn++;
			this.name = timesUpgraded<4?ClockworkEgg.NAME + "+" + this.timesUpgraded:"Clockwork Phoenix";
			this.initializeTitle();
			this.rawDescription = EXTENDED_DESCRIPTION[timesUpgraded-1];
			this.initializeDescription();
			if (timesUpgraded == 4) {
				this.exhaust = true;
				this.loadCardImage("img/cards/"+"ClockworkPhoenix"+".png");
			}
		}
	}
}
