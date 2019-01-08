package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
import constructmod.relics.Challenge1;

public class ShiftGuard extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("ShiftGuard");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String CHALLENGE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 1;
	private static final int BLOCK_AMT = 5;
	private static final int GAIN_DEX = 1;
	private static final int UPGRADE_PLUS_BLOCK = 1;
	private static final int UPGRADE_PLUS_GAIN_DEX = 1;
	private static final int M_UPGRADE_PLUS_BLOCK = 2;
	private static final int M_UPGRADE_PLUS_GAIN_DEX = 1;
	private static final int POOL = 1;

	public static final int CYCLE_CHALLENGE_THRESHOLD = 6;

	public ShiftGuard() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, (ConstructMod.hasChallengeActive(CYCLE_CHALLENGE_THRESHOLD)? CHALLENGE_DESCRIPTION:"")+DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
		this.block = this.baseBlock = BLOCK_AMT;
		this.magicNumber = this.baseMagicNumber = GAIN_DEX;
	}

	@Override
	public boolean canCycle() {
		return ConstructMod.hasChallengeActive(CYCLE_CHALLENGE_THRESHOLD) && super.canCycle() &&
				AbstractDungeon.player.hasPower(StrengthPower.POWER_ID) &&
				AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount < 0;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, -this.magicNumber), -this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new ShiftGuard();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_PLUS_BLOCK);
			this.upgradeMagicNumber(UPGRADE_PLUS_GAIN_DEX);
		} else if (this.canUpgrade()){
			this.megaUpgradeName();
			this.upgradeBlock(M_UPGRADE_PLUS_BLOCK);
			this.upgradeMagicNumber(M_UPGRADE_PLUS_GAIN_DEX);
		}
	}
}
