package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.actions.DealMultiRandomDamageAction;
import constructmod.patches.AbstractCardEnum;

public class FlakBarrage extends AbstractCycleCard {
	public static final String ID = "FlakBarrage";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 0;
	private static final int TIMES = 3;
	private static final int UPGRADE_PLUS_TIMES = 1;
	private static final int M_UPGRADE_PLUS_TIMES = 2;
	private static final int POOL = 1;

	public FlakBarrage() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = TIMES;
	}
	
	@Override
	public void atTurnStart(){
		hasCycled = false;
	}
	
	@Override
	public void triggerWhenDrawn(){
		AbstractPlayer p = AbstractDungeon.player;
		if (p.hasPower("Strength") && p.getPower("Strength").amount > 0) return;
		
		cycle();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DealMultiRandomDamageAction(
				new DamageInfo(p, this.baseDamage, this.damageTypeForTurn), this.magicNumber, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
	}

	@Override
	public AbstractCard makeCopy() {
		return new FlakBarrage();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_TIMES);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_TIMES);
		}
	}
}
