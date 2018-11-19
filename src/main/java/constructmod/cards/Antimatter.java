package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class Antimatter extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Antimatter");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final String ERROR_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[1];
	private static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[2];
	private static final int COST = 0;
	private static final int CARD_DMG = 4;
	private static final int UPGRADE_PLUS_CARD_DMG = 1;
	private static final int M_UPGRADE_PLUS_CARD_DMG = 1;
	private static final int POOL = 1;
	
	private String desc;

	public Antimatter() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY, POOL);
		this.baseDamage = this.damage = 0;
		this.magicNumber = this.baseMagicNumber = CARD_DMG;
		this.desc = DESCRIPTION;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (this.megaUpgraded) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new EnergizedPower(p, EnergyPanel.totalCount),EnergyPanel.totalCount));
		AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(EnergyPanel.totalCount));
		//if (this.megaUpgraded) AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(
				m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
		
		this.rawDescription = desc;
		initializeDescription();
	}
	
	/*@Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
		if (EnergyPanel.totalCount <= 0 || this.megaUpgraded) return true;
		
        this.cantUseMessage = ERROR_DESCRIPTION;
        return false;
    }*/
	
	@Override
	public void applyPowers(){
		
		this.damage = this.baseDamage = (AbstractDungeon.player.hand.size() - 1) * this.magicNumber;
		
		super.applyPowers();
		
		this.rawDescription = EXTENDED_DESCRIPTION + desc;
		initializeDescription();
	}

	@Override
	public void calculateCardDamage(AbstractMonster m){

		this.damage = this.baseDamage = (AbstractDungeon.player.hand.size() - 1) * this.magicNumber;

		super.calculateCardDamage(m);

		this.rawDescription = EXTENDED_DESCRIPTION + desc;
		initializeDescription();
	}
	
	@Override
	public void onMoveToDiscard(){
		this.rawDescription = desc;
		initializeDescription();
	}

	@Override
	public AbstractCard makeCopy() {
		return new Antimatter();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_CARD_DMG);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.desc = M_UPGRADE_DESCRIPTION;
			this.rawDescription = this.desc;
			initializeDescription();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_CARD_DMG);
		}
	}
}
