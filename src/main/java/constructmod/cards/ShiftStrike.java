package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class ShiftStrike extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("ShiftStrike");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String CHALLENGE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 1;
	private static final int ATTACK_DMG = 6;
	private static final int GAIN_STR = 1;
	private static final int UPGRADE_PLUS_ATTACK_DMG = 1;
	private static final int UPGRADE_PLUS_GAIN_STR = 1;
	private static final int M_UPGRADE_PLUS_ATTACK_DMG = 2;
	private static final int M_UPGRADE_PLUS_GAIN_STR = 1;
	private static final int POOL = 1;

	public static final int CYCLE_CHALLENGE_THRESHOLD = 6;

	public ShiftStrike() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, (ConstructMod.hasChallengeActive(CYCLE_CHALLENGE_THRESHOLD)? CHALLENGE_DESCRIPTION:"")+DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = GAIN_STR;
		this.tags.add(CardTags.STRIKE);
	}

	@Override
	public boolean canCycle() {
		return ConstructMod.hasChallengeActive(CYCLE_CHALLENGE_THRESHOLD) && super.canCycle() &&
				AbstractDungeon.player.hasPower(DexterityPower.POWER_ID) &&
				AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount < 0;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
					new DamageInfo(p, this.damage, this.damageTypeForTurn),
					AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, -this.magicNumber), -this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new ShiftStrike();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
			this.upgradeMagicNumber(UPGRADE_PLUS_GAIN_STR);
		} else if (canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeDamage(M_UPGRADE_PLUS_ATTACK_DMG);
			this.upgradeMagicNumber(M_UPGRADE_PLUS_GAIN_STR);
		}
	}
}
