package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.FastDrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.OilSpillPower;

public class CreateCores extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("CreateCores");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 1;
	private static final int NUM_CORES = 3;
	//private static final int UPGRADE_PLUS_NUM_CORES = 1;
	public static final int OVERHEAT = 10;
	public static final int UPGRADE_PLUS_OVERHEAT = 5;
	private static final int POOL = 1;

	public CreateCores() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.COMMON, CardTarget.NONE, POOL);
		this.baseMagicNumber = this.magicNumber = NUM_CORES;
		this.overheat = OVERHEAT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		for (int i=0;i<this.magicNumber;i++) {
			AbstractCard c = ConstructMod.getRandomCore();
			if (this.megaUpgraded) c.upgrade();
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c,1,true,true));
		}
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,1));
	}

	@Override
	public AbstractCard makeCopy() {
		return new CreateCores();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeOverheat(UPGRADE_PLUS_OVERHEAT);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
