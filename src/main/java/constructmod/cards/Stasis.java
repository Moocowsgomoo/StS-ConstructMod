package constructmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.actions.StasisAction;
import constructmod.patches.AbstractCardEnum;

public class Stasis extends AbstractConstructCard {
	public static final String ID = "Stasis";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 1;
	private static final int NUM_COPIES = 1;
	private static final int UPGRADE_PLUS_NUM_COPIES = 1;
	private static final int M_UPGRADE_PLUS_NUM_COPIES = 1;
	private static final int POOL = 1;

	public Stasis() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.NONE, POOL);
		this.magicNumber = this.baseMagicNumber = NUM_COPIES;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new StasisAction(p, this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Stasis();
	}
	
	@Override
	public void applyPowers() {
		super.applyPowers();
		if (this.megaUpgraded) this.retain = true;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.upgradeMagicNumber(UPGRADE_PLUS_NUM_COPIES);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_NUM_COPIES);
			this.isInnate = true;
			this.retain = true;
		}
	}
}
