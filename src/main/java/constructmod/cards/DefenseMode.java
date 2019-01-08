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


import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class DefenseMode extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("DefenseMode");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String CHALLENGE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 0;
	private static final int DEX = 2;
	public static final int CHALLENGE_DEX = 1;
	private static final int UPGRADE_PLUS_DEX = 1;
	private static final int M_UPGRADE_PLUS_DEX = 1;
	private static final int POOL = 1;

	public static final int STATS_CHALLENGE_THRESHOLD = 1;
	public static final int CYCLE_CHALLENGE_THRESHOLD = 6;

	public DefenseMode() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, (ConstructMod.hasChallengeActive(CYCLE_CHALLENGE_THRESHOLD)? CHALLENGE_DESCRIPTION:"")+DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, (ConstructMod.hasChallengeActive(STATS_CHALLENGE_THRESHOLD)?CardRarity.RARE:CardRarity.BASIC), AbstractCard.CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = (ConstructMod.hasChallengeActive(STATS_CHALLENGE_THRESHOLD)?CHALLENGE_DEX:DEX);
		this.retain = true;
	}

	@Override
	public boolean canCycle() {
		ConstructMod.logger.debug(ConstructMod.hasChallengeActive(1));
		return ConstructMod.hasChallengeActive(CYCLE_CHALLENGE_THRESHOLD) && super.canCycle() &&
				AbstractDungeon.player.hasPower(StrengthPower.POWER_ID) &&
				AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount < 0;
	}

	@Override
	public void applyPowers(){
		super.applyPowers();
		this.retain = true;
		if (ConstructMod.hasChallengeActive(STATS_CHALLENGE_THRESHOLD)){
			this.rarity = CardRarity.RARE;
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		//AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05f));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, -this.magicNumber), -this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new DefenseMode();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_DEX);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_DEX);
		}
	}
}
