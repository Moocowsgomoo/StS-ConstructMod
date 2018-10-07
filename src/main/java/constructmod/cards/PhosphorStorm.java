package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class PhosphorStorm extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("PhosphorStorm");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final int COST = 1;
	public static final int ATTACK_DMG = 9;
	public static final int UPGRADE_PLUS_ATTACK_DMG = 2;
	public static final int M_UPGRADE_PLUS_ATTACK_DMG = -1;
	public static final int OVERHEAT = 1;
	private static final int POOL = 1;

	public PhosphorStorm() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.UNCOMMON, CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
		this.overheat = OVERHEAT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.03f));
		AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1f));
		AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));

		if (this.megaUpgraded) {
			AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(
					new DamageInfo(p, this.damage, this.damageTypeForTurn),AbstractGameAction.AttackEffect.FIRE));
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new PhosphorStorm();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeDamage(M_UPGRADE_PLUS_ATTACK_DMG);
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
