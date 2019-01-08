package constructmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import constructmod.ConstructMod;
import constructmod.actions.DiscountRandomCardAction;
import constructmod.patches.AbstractCardEnum;

public class BatteryCore extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("BatteryCore");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 0;
	private static final int DISCOUNT = 1;
	//private static final int UPGRADE_PLUS_DISCOUNT = 1;
	private static final int POOL = 1;
	
	//private final ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();

	public BatteryCore() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.NONE, POOL);
		this.baseMagicNumber = this.magicNumber = DISCOUNT;
	}
	
	@Override
	public void triggerWhenDrawn(){	
		if (!this.canCycle()) return; // have to check this before super call, otherwise our test for canCycle is false since it JUST cycled.
		super.triggerWhenDrawn();
		
		flash();
		
		AbstractDungeon.actionManager.addToTop(new DiscountRandomCardAction(this.magicNumber));
		CloneCore();
		
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DiscountRandomCardAction(this.magicNumber));
		CloneCore();
	}

	@Override
	public AbstractCard makeCopy() {
		return new BatteryCore();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = DESCRIPTION + UPGRADE_DESCRIPTION;
			this.initializeDescription();
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = DESCRIPTION + M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
