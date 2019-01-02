package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.AutoturretPower;
import constructmod.powers.SunScreenMegaPower;
import constructmod.powers.SunScreenPower;

public class SunScreen extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("SunScreen");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK = 3;
	private static final int UPGRADE_PLUS_BLOCK = 2;
	private static final int M_UPGRADE_PLUS_BLOCK = 1;
	private static final int POOL = 1;

	public SunScreen() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.POWER,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.RARE, CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = BLOCK;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (megaUpgraded) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SunScreenMegaPower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SunScreenPower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new SunScreen();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_BLOCK);
			//this.rawDescription = UPGRADE_DESCRIPTION;
			//this.initializeDescription();
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_BLOCK);
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
