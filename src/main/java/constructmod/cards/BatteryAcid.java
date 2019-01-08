package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class BatteryAcid extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("BatteryAcid");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 0;
	private static final int ENERGY_GAIN = 2;
	private static final int SLIMED_AMT = 1;
	private static final int UPGRADE_PLUS_ENERGY_GAIN = 1;
	private static final int POOL = 1;

	public BatteryAcid() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.NONE, POOL);
		this.magicNumber = this.baseMagicNumber = ENERGY_GAIN;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		//AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05f));
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
		if (this.megaUpgraded) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new EnergizedPower(p, 1),1));
		//AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,1));
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Slimed(), SLIMED_AMT, true, true));
	}

	@Override
	public AbstractCard makeCopy() {
		return new BatteryAcid();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_ENERGY_GAIN);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
