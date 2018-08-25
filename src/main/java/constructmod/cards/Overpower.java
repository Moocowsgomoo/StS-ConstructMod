package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.OverpowerPower;

public class Overpower extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Overpower");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int STATS = 3;
	private static final int UPGRADE_NEW_COST = 0;
	private static final int POOL = 1;

	public Overpower() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.POWER,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = STATS;
	}
	
	@Override
	public void applyPowers(){
		if (this.megaUpgraded) this.retain = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new OverpowerPower(p, 1, this.magicNumber), 1));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Overpower();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADE_NEW_COST);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.retain = true;
		}
	}
}
