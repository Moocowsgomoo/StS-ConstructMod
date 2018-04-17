package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class Antimatter extends CustomCard {
	public static final String ID = "Antimatter";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final String ERROR_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[1];
	private static final int COST = 0;
	private static final int CARD_DMG = 3;
	private static final int UPGRADE_PLUS_CARD_DMG = 1;
	private static final int POOL = 1;

	public Antimatter() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCT_MOD_COLOR, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY, POOL);
		this.baseDamage = this.damage = 0;
		this.magicNumber = this.baseMagicNumber = CARD_DMG;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(
				m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
		
		this.rawDescription = DESCRIPTION;
		initializeDescription();
	}
	
	@Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
		if (EnergyPanel.totalCount <= 0) return true;
		
        this.cantUseMessage = ERROR_DESCRIPTION;
        return false;
    }
	
	@Override
	public void applyPowers(){
		
		this.damage = this.baseDamage = (AbstractDungeon.player.hand.size() - 1) * this.magicNumber;
		
		super.applyPowers();
		
		this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION;
		initializeDescription();
	}
	
	@Override
	public void onMoveToDiscard(){
		this.rawDescription = DESCRIPTION;
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
		}
	}
}
