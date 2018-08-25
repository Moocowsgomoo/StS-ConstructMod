package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModTags;
import basemod.helpers.CardTags;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class Strike_Gold extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("Strike_Gold");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 6;
	private static final int UPGRADE_PLUS_ATTACK_DMG = 3;
	private static final int M_UPGRADE_PLUS_ATTACK_DMG = 6;
	private static final int POOL = 1;

	public Strike_Gold() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
		CardTags.addTags(this, BaseModTags.BASIC_STRIKE, BaseModTags.STRIKE);
	}
	
	@Override
	public boolean canCycle() {
		return super.canCycle() && 
				AbstractDungeon.player.hasPower(StrengthPower.POWER_ID) && 
				AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount < 0;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
					new DamageInfo(p, this.damage, this.damageTypeForTurn),
					AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Strike_Gold();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
		} else if (canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeDamage(M_UPGRADE_PLUS_ATTACK_DMG);
		}
	}
}
