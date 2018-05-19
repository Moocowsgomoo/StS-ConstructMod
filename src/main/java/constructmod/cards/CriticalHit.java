package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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
import constructmod.patches.AbstractCardEnum;

public class CriticalHit extends AbstractCycleCard {
	public static final String ID = "CriticalHit";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 14;
	private static final int UPGRADE_PLUS_ATTACK_DMG = 4;
	private static final int M_UPGRADE_PLUS_ATTACK_DMG = 7;
	private static final int M_UPGRADE_NEW_COST = 0;
	private static final int POOL = 1;

	public CriticalHit() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
	}
	
	@Override
	public void atTurnStart(){
		hasCycled = false;
	}
	
	@Override
	public void triggerWhenDrawn(){
		boolean anyVuln = false;
		int count = AbstractDungeon.getCurrRoom().monsters.monsters.size();
		for (int i = 0; i < count; i++) {
			AbstractMonster targetMonster = (AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
			if ((!targetMonster.isDeadOrEscaped())) {
				if ((targetMonster.hasPower("Vulnerable") && targetMonster.getPower("Vulnerable").amount > 0) || 
						(targetMonster.hasPower("Weak") && targetMonster.getPower("Weak").amount > 0 )){
					anyVuln = true;
					break;
				}
			}
		}
		if (!anyVuln) cycle();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
					new DamageInfo(p, this.damage, this.damageTypeForTurn),
					AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
	}

	@Override
	public AbstractCard makeCopy() {
		return new CriticalHit();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
		} else if (canUpgrade()) {
			this.megaUpgradeName();
			this.name = "Mega-Crit";
			this.initializeTitle();
			this.upgradeDamage(M_UPGRADE_PLUS_ATTACK_DMG);
			this.upgradeBaseCost(M_UPGRADE_NEW_COST);
		}
	}
}
