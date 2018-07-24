package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
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


import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class HyperBeam extends AbstractConstructCard {
	public static final String ID = "HyperBeam";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 2;
	private static final int ATTACK_DMG = 50;
	private static final int DAZED_AMT = 2;
	private static final int UPGRADE_PLUS_DAZED_AMT = 0;
	private static final int M_UPGRADE_PLUS_DAZED_AMT = 2;
	private static final int M_UPGRADE_PLUS_ATTACK_DMG = 50;
	private static final int POOL = 1;

	public HyperBeam() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY, POOL);
		this.magicNumber = this.baseMagicNumber = DAZED_AMT;
		this.damage = this.baseDamage = ATTACK_DMG;
		this.isEthereal = !this.upgraded;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		//AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05f));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new com.megacrit.cardcrawl.vfx.combat.MindblastEffect(p.drawX+20, p.drawY+90)));
		
		AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m, 
			new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Dazed(), this.magicNumber, false, false));
	}
	
	@Override
    public void triggerOnEndOfPlayerTurn() {
        if (this.isEthereal) AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }

	@Override
	public AbstractCard makeCopy() {
		return new HyperBeam();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			//this.upgradeMagicNumber(UPGRADE_PLUS_DAZED_AMT);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.isEthereal = false;
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeDamage(M_UPGRADE_PLUS_ATTACK_DMG);
			this.upgradeMagicNumber(M_UPGRADE_PLUS_DAZED_AMT);
		}
	}
}
