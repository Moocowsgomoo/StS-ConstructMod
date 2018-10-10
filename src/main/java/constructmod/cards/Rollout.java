package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class Rollout extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Rollout");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final int COST = 1;
	public static final int ATTACK_DMG = 0;
	public static final int CYCLE_PLUS_ATTACK_DMG = 2;
	public static final int OVERHEAT = 12;
	public static final int UPGRADE_PLUS_OVERHEAT = 3;
	public static final int M_UPGRADE_PLUS_OVERHEAT = 5;
	private static final int POOL = 1;

	public Rollout() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.UNCOMMON, CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = CYCLE_PLUS_ATTACK_DMG;
		this.overheat = OVERHEAT;
	}

	@Override
	public void reduceOverheat(){
		this.baseDamage += this.magicNumber;
		super.reduceOverheat();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Rollout();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeOverheat(UPGRADE_PLUS_OVERHEAT);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeOverheat(M_UPGRADE_PLUS_OVERHEAT);
		}
	}
}
