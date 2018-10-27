package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import constructmod.ConstructMod;
import constructmod.actions.BackfireDamageAction;
import constructmod.patches.AbstractCardEnum;

import static replayTheSpire.patches.CardFieldStuff.CHAOS_NEGATIVE_MAGIC;

public class Backfire extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Backfire");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 16;
	private static final int UPGRADE_PLUS_ATTACK_DMG = 3;
	private static final int M_UPGRADE_PLUS_ATTACK_DMG = 11;
	private static final int SELF_DMG = 5;
	private static final int UPGRADE_PLUS_SELF_DMG = -1;
	private static final int M_UPGRADE_PLUS_SELF_DMG = 2;
	private static final int POOL = 1;

	public Backfire() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF_AND_ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = SELF_DMG;
		if (ConstructMod.isReplayLoaded) {
			this.tags.add(CHAOS_NEGATIVE_MAGIC); // higher magic number is worse
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new BackfireDamageAction(this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
					new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Backfire();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
			this.upgradeMagicNumber(UPGRADE_PLUS_SELF_DMG);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeDamage(M_UPGRADE_PLUS_ATTACK_DMG);
			this.upgradeMagicNumber(M_UPGRADE_PLUS_SELF_DMG);
		}
	}
}
