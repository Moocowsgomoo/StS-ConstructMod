package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class FierceBash extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("FierceBash");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 9;
	private static final int UPGRADE_PLUS_ATTACK_DMG = 3;
	private static final int M_UPGRADE_PLUS_ATTACK_DMG = 1;
	private static final int POOL = 1;

	public FierceBash() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
	}
	
	@Override
	public void applyPowers(){
		super.applyPowers();
		if (this.megaUpgraded) this.retain = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m, 
				new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		
		// return if enemy is attacking or blocking
		if (m.intent.equals(AbstractMonster.Intent.ATTACK) || m.intent.equals(AbstractMonster.Intent.ATTACK_BUFF) || 
				m.intent.equals(AbstractMonster.Intent.ATTACK_DEBUFF) || m.intent.equals(AbstractMonster.Intent.ATTACK_DEFEND) || 
				m.intent.equals(AbstractMonster.Intent.DEFEND) || m.intent.equals(AbstractMonster.Intent.DEFEND_BUFF) ||
				m.intent.equals(AbstractMonster.Intent.DEFEND_DEBUFF)) {
			return;
		}
		
		AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m, 
				new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		
	}

	@Override
	public AbstractCard makeCopy() {
		return new FierceBash();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeDamage(M_UPGRADE_PLUS_ATTACK_DMG);
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.retain = true;
		}
	}
}
