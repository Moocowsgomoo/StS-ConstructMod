package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
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
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.actions.SwapDrawPileAndDiscardAction;
import constructmod.patches.AbstractCardEnum;

public class UnbalancingBlast extends AbstractConstructCard {
	public static final String ID = "UnbalancingBlast";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 10;
	private static final int UPGRADE_PLUS_ATTACK_DMG = 2;
	private static final int M_UPGRADE_NEW_COST = 0;
	private static final int POOL = 1;

	public UnbalancingBlast() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
	}
	
	@Override
	public void applyPowers(){
		super.applyPowers();
		if (this.upgraded) this.retain = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		
		// fx
		//AbstractDungeon.actionManager.addToBottom(new VFXAction(new IntenseZoomEffect(m.drawX, m.drawY, false), 0.05f));
		
		// damage
		AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m, 
			new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
		
		// swap
		AbstractDungeon.actionManager.addToBottom(new SwapDrawPileAndDiscardAction());
	}

	@Override
	public AbstractCard makeCopy() {
		return new UnbalancingBlast();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
			this.retain = true;
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeBaseCost(M_UPGRADE_NEW_COST);
		}
	}
}
