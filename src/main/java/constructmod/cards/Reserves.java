package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class Reserves extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("Reserves");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	private static final int HP_THRESHOLD = 20;
	private static final int UPGRADE_PLUS_HP_THRESHOLD = 10;
	private static final int M_UPGRADE_PLUS_HP_THRESHOLD = 20;
	private static final int POOL = 1;

	public Reserves() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = HP_THRESHOLD;
		this.exhaust = true;
	}
	
	@Override
	public boolean canCycle() {
		return super.canCycle() && 
				AbstractDungeon.player.currentHealth > this.magicNumber;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(3));
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,3));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Reserves();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_HP_THRESHOLD);
		} else if (this.canUpgrade()){
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_HP_THRESHOLD);
		}
	}
}
