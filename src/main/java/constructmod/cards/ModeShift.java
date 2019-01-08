package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class ModeShift extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("ModeShift");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int UPGRADE_COST = 0;
	private static final int DRAW_AMT = 1;
	private static final int M_UPGRADE_PLUS_DRAW_AMT = 1;
	private static final int POOL = 1;

	public ModeShift() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);
		this.baseMagicNumber = this.magicNumber = DRAW_AMT;
	}
	
	@Override
	public void applyPowers(){
		super.applyPowers();
		if (this.megaUpgraded) this.retain = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		//AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05f));
		final int str = p.hasPower(StrengthPower.POWER_ID)?p.getPower(StrengthPower.POWER_ID).amount:0;
		final int dex = p.hasPower(DexterityPower.POWER_ID)?p.getPower(DexterityPower.POWER_ID).amount:0;
		if (dex-str != 0) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, dex-str), dex-str));
		if (str-dex != 0) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, str-dex), str-dex));
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new ModeShift();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADE_COST);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_DRAW_AMT);
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.retain = true;
		}
	}
}
