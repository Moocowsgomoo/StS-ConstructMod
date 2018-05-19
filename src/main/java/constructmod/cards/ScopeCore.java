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

public class ScopeCore extends AbstractCycleCard {
	public static final String ID = "ScopeCore";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 0;
	private static final int VULN = 1;
	private static final int UPGRADE_PLUS_VULN = 1;
	private static final int POOL = 1;

	public ScopeCore() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY, POOL);
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
		cycle();
		
		final AbstractMonster mo = AbstractDungeon.getMonsters().getRandomMonster(true);
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(
			mo,p,new VulnerablePower(mo, this.magicNumber, false),this.magicNumber,true,AbstractGameAction.AttackEffect.NONE));
		CloneCore();
		
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		final AbstractMonster mo = AbstractDungeon.getMonsters().getRandomMonster(true);
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
			mo,p,new VulnerablePower(mo, this.magicNumber, false),this.magicNumber,true,AbstractGameAction.AttackEffect.NONE));
		CloneCore();
	}

	@Override
	public AbstractCard makeCopy() {
		return new ScopeCore();
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
