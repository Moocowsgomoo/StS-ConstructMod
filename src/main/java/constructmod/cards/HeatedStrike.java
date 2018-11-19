package constructmod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class HeatedStrike extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("HeatedStrike");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 6;
	private static final int OVERHEAT = 5;
	private static final int UPGRADE_PLUS_OVERHEAT = 5;
	private static final int M_UPGRADE_PLUS_OVERHEAT = 10;
	private static final int M_UPGRADE_PLUS_ATTACK_DMG = 6;
	private static final int POOL = 1;

	public HeatedStrike() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.BASIC, CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
		this.overheat = OVERHEAT;
		this.tags.add(BaseModCardTags.BASIC_STRIKE);
		this.tags.add(CardTags.STRIKE);
	}

	@Override
	public boolean canCycle() {
		return super.canCycle() &&
				AbstractDungeon.player.hasPower(StrengthPower.POWER_ID) &&
				AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount < 0;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
					new DamageInfo(p, this.damage, this.damageTypeForTurn),
					AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
	}

	@Override
	public AbstractCard makeCopy() {
		return new HeatedStrike();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeOverheat(UPGRADE_PLUS_OVERHEAT);
		} else if (canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeOverheat(M_UPGRADE_PLUS_OVERHEAT);
			this.upgradeDamage(M_UPGRADE_PLUS_ATTACK_DMG);
		}
	}
}
