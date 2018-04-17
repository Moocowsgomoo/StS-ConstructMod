package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.AbstractPower;


import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class ElectricArmor extends AbstractCycleCard {
	public static final String ID = "ElectricArmor";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int UPGRADE_NEW_COST = 0;
	private static final int PLUS_DEX = 0;
	private static final int UPGRADE_PLUS_DEX = 1;
	private static final int POOL = 1;

	public ElectricArmor() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.POWER,
				AbstractCardEnum.CONSTRUCT_MOD_COLOR, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);
		this.baseMagicNumber = this.magicNumber = PLUS_DEX;
	}
	
	@Override
	public void atTurnStart(){
		hasCycled = false;
	}
	
	@Override
	public void triggerWhenDrawn(){
		AbstractPlayer p = AbstractDungeon.player;
		if (p.hasPower("Dexterity") && p.getPower("Dexterity").amount > 0) return;
		
		cycle();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (!p.hasPower("Dexterity")) return;
		
		int dex = p.getPower("Dexterity").amount + this.magicNumber;
		if (dex > 0){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, dex), dex));
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new ElectricArmor();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.upgradeMagicNumber(UPGRADE_PLUS_DEX);
			//this.upgradeBaseCost(UPGRADE_NEW_COST);
		}
	}
}
