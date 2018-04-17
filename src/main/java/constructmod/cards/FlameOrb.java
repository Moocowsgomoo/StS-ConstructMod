package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.AbstractPower;


import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class FlameOrb extends AbstractCycleCard {
	public static final String ID = "FlameOrb";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 0;
	private static final int HP_DMG = 3;
	//private static final int UPGRADE_PLUS_HP_DMG = 2;
	private static final int POOL = 1;

	public FlameOrb() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCT_MOD_COLOR, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);
		this.baseMagicNumber = this.magicNumber = HP_DMG;
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
		
		if (upgraded) AbstractDungeon.player.discardPile.addToTop(new FlameOrb());
		AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(
			new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
		
		cycle();
		//if (upgraded) AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(),1));
		//AbstractDungeon.player.onCycle(this);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (upgraded) AbstractDungeon.player.discardPile.addToTop(new FlameOrb());
		AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(
			new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
		//if (upgraded) AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(),1));
	}

	@Override
	public AbstractCard makeCopy() {
		return new FlameOrb();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = DESCRIPTION + UPGRADE_DESCRIPTION;
			this.initializeDescription();
			//this.upgradeMagicNumber(UPGRADE_PLUS_HP_DMG);
		}
	}
}
