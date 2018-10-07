package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class Implosion extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Implosion");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK_AMT = 7;
	private static final int STR_DOWN_AMT = 2;
	private static final int UPGRADE_BLOCK_AMT = 2;
	private static final int UPGRADE_PLUS_STR_DOWN_AMT = 1;
	private static final int M_UPGRADE_PLUS_BLOCK_AMT = 1;
	private static final int M_UPGRADE_PLUS_STR_DOWN_AMT = 2;
	private static final int POOL = 1;

	public Implosion() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.UNCOMMON, CardTarget.SELF_AND_ENEMY, POOL);
		this.baseBlock = this.block = BLOCK_AMT;
		this.baseMagicNumber = this.magicNumber = STR_DOWN_AMT;
	}
	
	@Override
	public void applyPowers() {
		super.applyPowers();
		if (this.megaUpgraded) this.retain = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -this.magicNumber), -this.magicNumber));
        if (m != null && !m.hasPower("Artifact")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new GainStrengthPower(m, this.magicNumber), this.magicNumber));
        }
	}

	@Override
	public AbstractCard makeCopy() {
		return new Implosion();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_BLOCK_AMT);
			this.upgradeMagicNumber(UPGRADE_PLUS_STR_DOWN_AMT);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeBlock(M_UPGRADE_PLUS_BLOCK_AMT);
			this.upgradeMagicNumber(M_UPGRADE_PLUS_STR_DOWN_AMT);
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
