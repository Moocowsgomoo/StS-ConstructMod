package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class SweepLaser extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("SweepLaser");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	private static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 4;
	private static final int UPGRADE_PLUS_ATTACK_DMG = 2;
	private static final int M_UPGRADE_PLUS_ATTACK_DMG = -1;
	private static final int POOL = 1;

	public SweepLaser() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
		this.baseMagicNumber = this.magicNumber = this.baseDamage;
	}
	
	@Override
	public void applyPowers() {
		this.isMultiDamage = true;
		super.applyPowers();
		this.magicNumber = this.damage;
		this.isMultiDamage = false;
		super.applyPowers();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		
		// attack
		AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM", 0.05f));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(p.drawX+20, p.drawY+90, m.drawX, m.drawY+50),0.2f));
		
		AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m, 
			new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
		
		// area attack
		AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY", 0.05f));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1f));
		
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(
				p, DamageInfo.createDamageMatrix(this.damage, true), this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
		
		if (this.megaUpgraded) {
			AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY", 0.05f));
			AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1f));
			
			AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(
					p, DamageInfo.createDamageMatrix(this.damage, true), this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
		}
		
	}

	@Override
	public AbstractCard makeCopy() {
		return new SweepLaser();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
			this.upgradeMagicNumber(UPGRADE_PLUS_ATTACK_DMG);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeDamage(M_UPGRADE_PLUS_ATTACK_DMG);
			this.upgradeMagicNumber(M_UPGRADE_PLUS_ATTACK_DMG);
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
