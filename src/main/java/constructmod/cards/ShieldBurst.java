package constructmod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class ShieldBurst extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("ShieldBurst");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 1;
	private static final int DMG_MULT = 1;
	private static final int UPGRADE_PLUS_DMG_MULT = 1;
	private static final int M_UPGRADE_PLUS_DMG_MULT = 1;
	//private static final int M_UPGRADE_NEW_COST = 0;
	private static final int POOL = 1;
	
	private String desc = DESCRIPTION;

	public ShieldBurst() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL, POOL);
		this.baseDamage = this.damage = 0;
		this.magicNumber = this.baseMagicNumber = DMG_MULT;
		this.isMultiDamage = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(p,p,p.currentBlock));
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(
				p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SHIELD));
		
		this.rawDescription = desc;
		initializeDescription();
	}
	
	@Override
	public void applyPowers(){
		setBaseDamage();
		super.applyPowers();
		
		this.rawDescription = desc + EXTENDED_DESCRIPTION;
		initializeDescription();
	}

	@Override
	public void calculateCardDamage(AbstractMonster m){
		setBaseDamage();
		super.calculateCardDamage(m);

		this.rawDescription = desc + EXTENDED_DESCRIPTION;
		initializeDescription();
	}

	public void setBaseDamage(){
		if (!upgraded) this.damage = this.baseDamage = MathUtils.floor(AbstractDungeon.player.currentBlock * 1.5f);
		else this.damage = this.baseDamage = AbstractDungeon.player.currentBlock*this.magicNumber;
	}
	
	@Override
	public void onMoveToDiscard(){
		this.rawDescription = desc;
		initializeDescription();
	}

	@Override
	public AbstractCard makeCopy() {
		return new ShieldBurst();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_DMG_MULT);
			desc = UPGRADE_DESCRIPTION;
			this.rawDescription = desc;
			initializeDescription();
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_DMG_MULT);
		}
	}
}
