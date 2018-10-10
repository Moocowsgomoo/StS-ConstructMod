package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import constructmod.ConstructMod;
import constructmod.actions.DealMultiRandomDamageAction;
import constructmod.patches.AbstractCardEnum;

public class Flamethrower extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("Flamethrower");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 2;
	private static final int POOL = 1;

	public String desc = DESCRIPTION;

	public Flamethrower() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
	}
	
	@Override
	public boolean canCycle() {
		AbstractPlayer p = AbstractDungeon.player;
		return super.canCycle() && this.upgraded && this.getStatusCount() < 2;
				
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DealMultiRandomDamageAction(
				new DamageInfo(p, this.baseDamage, this.damageTypeForTurn), this.magicNumber, AbstractGameAction.AttackEffect.FIRE));
	}

	@Override
	public void applyPowers(){
		this.baseMagicNumber = this.getStatusCount();
		super.applyPowers();

		this.rawDescription = (this.magicNumber == 1?EXTENDED_DESCRIPTION[1]:EXTENDED_DESCRIPTION[2]) + desc;
		initializeDescription();
	}

	@Override
	public void onMoveToDiscard(){
		this.rawDescription = desc;
		initializeDescription();
	}

	public int getStatusCount(){
		int count = 0;
		for (AbstractCard c : AbstractDungeon.player.drawPile.group){
			if (c.cardID == Burn.ID || (this.megaUpgraded && c.type == CardType.STATUS)) count++;
		}
		for (AbstractCard c : AbstractDungeon.player.hand.group){
			if (c.cardID == Burn.ID || (this.megaUpgraded && c.type == CardType.STATUS)) count++;
		}
		for (AbstractCard c : AbstractDungeon.player.discardPile.group){
			if (c.cardID == Burn.ID || (this.megaUpgraded && c.type == CardType.STATUS)) count++;
		}
		return count;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Flamethrower();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			desc = UPGRADE_DESCRIPTION;
			this.rawDescription = desc;
			this.initializeDescription();
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			desc = EXTENDED_DESCRIPTION[0];
			this.rawDescription = desc;
			this.initializeDescription();
		}
	}
}
