package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class OmegaCannon extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("OmegaCannon");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 5;
	private static final int ATTACK_DMG = 15;
	private static final int UPGRADE_PLUS_ATTACK_DMG = 5;
	private static final int POOL = 1;
	
	private int prevDiscount = 0;

	public OmegaCannon() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
	}
	
	@Override
	public void applyPowers() {
		
		this.costForTurn += this.prevDiscount;
		
		super.applyPowers();
		if (!AbstractDungeon.player.hasPower("Strength")) return;
		if (!this.megaUpgraded && AbstractDungeon.player.getPower("Strength").amount < 0) return;

		int str = AbstractDungeon.player.getPower("Strength").amount;
		if (str < 0) str = -str;
		this.prevDiscount = str;
		
		if (this.costForTurn - this.prevDiscount < 0) this.prevDiscount = this.costForTurn;
		
		this.costForTurn = this.costForTurn - this.prevDiscount;
	}
	
	@Override
	public void onMoveToDiscard() {
		super.onMoveToDiscard();
		this.prevDiscount = 0;
	}
	
	@Override
	public void triggerOnEndOfPlayerTurn() {
		super.triggerOnEndOfPlayerTurn();
		this.prevDiscount = 0;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),AttackEffect.SMASH));
	}

	@Override
	public AbstractCard makeCopy() {
		return new OmegaCannon();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
