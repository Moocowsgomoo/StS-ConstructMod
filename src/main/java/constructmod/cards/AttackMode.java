package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class AttackMode extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("AttackMode");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	private static final int STR = 2;
	private static final int UPGRADE_PLUS_STR = 1;
	private static final int M_UPGRADE_PLUS_STR = 2;
	private static final int POOL = 1;

	public AttackMode() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = STR;
		this.retain = true;
	}
	
	@Override
	public void applyPowers(){
		super.applyPowers();
		this.retain = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		//AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05f));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, -this.magicNumber), -this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new AttackMode();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_STR);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_STR);
		}
	}
}
