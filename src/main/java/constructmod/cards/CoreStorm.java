package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.AutoturretPower;
import constructmod.powers.PanicFirePower;
import constructmod.powers.SiegeFormPower;

public class CoreStorm extends AbstractConstructCard {
	public static final String ID = "CoreStorm";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 2;
	private static final int POWER_DAMAGE = 5;
	private static final int UPGRADE_POWER_DAMAGE = 3;
	private static final int NUM_ORBS = 5;
	private static final int M_UPGRADE_PLUS_NUM_ORBS = 5;
	//private static final int UPGRADE_NEW_COST = 1;
	private static final int POOL = 1;
	
	private int numOrbs;

	public CoreStorm() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.POWER,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = POWER_DAMAGE;
		this.numOrbs = NUM_ORBS;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(p,p, new CoreShard(),this.numOrbs,true,false));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AutoturretPower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new CoreStorm();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_POWER_DAMAGE);
		} else if (canUpgrade()) {
			this.megaUpgradeName();
			this.numOrbs += M_UPGRADE_PLUS_NUM_ORBS;
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
