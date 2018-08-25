package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class Disorient extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Disorient");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 0;
	private static final int STR_LOSS = 0;
	private static final int UPGRADE_STR_LOSS = 1;
	private static final int M_UPGRADE_STR_LOSS = 1;
	private static final int POOL = 1;

	public Disorient() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY, POOL);
		this.baseMagicNumber = this.magicNumber = STR_LOSS;
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		int dex = (p.hasPower("Dexterity") ? p.getPower("Dexterity").amount:0) + this.magicNumber;
		
		if (megaUpgraded) AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
		
		if (dex > 0) {
			for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
	            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new StrengthPower(mo, -dex), -dex, true, AbstractGameAction.AttackEffect.POISON));
	        }
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new Disorient();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_STR_LOSS);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_STR_LOSS);
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
