package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import basemod.abstracts.CustomCard;
import constructmod.patches.AbstractCardEnum;

public class ScopeOrb extends AbstractCycleCard {
	public static final String ID = "ScopeOrb";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 0;
	private static final int VULN = 1;
	private static final int UPGRADE_PLUS_VULN = 1;
	private static final int POOL = 1;

	public ScopeOrb() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCT_MOD_COLOR, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY, POOL);
		this.baseMagicNumber = this.magicNumber = VULN;
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
		
		final AbstractMonster mo = AbstractDungeon.getMonsters().getRandomMonster(true);
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
			mo,p,new VulnerablePower(mo, 1, false),1,true,AbstractGameAction.AttackEffect.NONE));
		if (upgraded) AbstractDungeon.player.discardPile.addToTop(new ScopeOrb());
		
		cycle();
		//if (upgraded) AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(),1));
		//AbstractDungeon.player.onCycle(this);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		final AbstractMonster mo = AbstractDungeon.getMonsters().getRandomMonster(true);
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
			mo,p,new VulnerablePower(mo, 1, false),1,true,AbstractGameAction.AttackEffect.NONE));
		if (upgraded) AbstractDungeon.player.discardPile.addToTop(new ScopeOrb());
		//if (upgraded) AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(),1));
	}

	@Override
	public AbstractCard makeCopy() {
		return new ScopeOrb();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = DESCRIPTION + UPGRADE_DESCRIPTION;
			this.initializeDescription();
			//this.upgradeMagicNumber(UPGRADE_PLUS_VULN);
		}
	}
}
