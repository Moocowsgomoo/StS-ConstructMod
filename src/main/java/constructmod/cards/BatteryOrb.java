package constructmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.actions.DiscountRandomCardAction;
import constructmod.patches.AbstractCardEnum;

public class BatteryOrb extends AbstractCycleCard {
	public static final String ID = "BatteryOrb";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 0;
	private static final int DISCOUNT = 1;
	private static final int POOL = 1;
	
	private final ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();

	public BatteryOrb() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.NONE, POOL);
		this.baseMagicNumber = this.magicNumber = DISCOUNT;
	}
	
	@Override
	public void atTurnStart(){
		hasCycled = false;
	}
	
	@Override
	public void triggerWhenDrawn(){
		AbstractPlayer p = AbstractDungeon.player;
		
		if (hasCycled) return;
		
		flash();
		
		AbstractDungeon.actionManager.addToBottom(new DiscountRandomCardAction(this.magicNumber));
		
		if (upgraded) {
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(
					list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1)),1));
		}
		
		cycle();
		//if (upgraded) AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(),1));
		//AbstractDungeon.player.onCycle(this);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DiscountRandomCardAction(this.magicNumber));
		
		if (upgraded) {
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(
					list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1)),1));
		}
		//if (upgraded) AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(),1));
	}

	@Override
	public AbstractCard makeCopy() {
		return new BatteryOrb();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
			
			list.clear();
			list.add(new FlameOrb());
		    list.add(new FlameOrb());
		    list.add(new ShockOrb());
		    list.add(new ShockOrb());
		    list.add(new GuardOrb());
		    list.add(new GuardOrb());
		    list.add(new BatteryOrb());
			//this.upgradeMagicNumber(UPGRADE_PLUS_VULN);
		}
	}
}
