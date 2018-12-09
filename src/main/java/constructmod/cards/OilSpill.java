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
import constructmod.powers.OilSpillPower;

public class OilSpill extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("OilSpill");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 1;
	private static final int OIL_DAMAGE = 9;
	public static final int OVERHEAT = 5;
	private static final int UPGRADE_PLUS_OIL_DAMAGE = 2;
	private static final int M_UPGRADE_PLUS_OIL_DAMAGE = 3;
	private static final int POOL = 1;

	public OilSpill() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.UNCOMMON, CardTarget.ENEMY, POOL);
		this.baseMagicNumber = this.magicNumber = OIL_DAMAGE;
		this.overheat = OVERHEAT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (this.megaUpgraded){
			for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters){
				if (!mo.isDeadOrEscaped()) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo,p,new OilSpillPower(mo,this.magicNumber),this.magicNumber));
			}
		}
		else {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new OilSpillPower(m, this.magicNumber), this.magicNumber));
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new OilSpill();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_OIL_DAMAGE);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_OIL_DAMAGE);
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.target = CardTarget.ALL_ENEMY;
			this.initializeDescription();
		}
	}
}
