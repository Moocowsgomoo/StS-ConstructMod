package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class NuclearCore extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("NuclearCore");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 0;
	private static final int POISON = 4;
	private static final int POOL = 1;
	public static final int OVERHEAT = 8;

	public NuclearCore() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.RARE, CardTarget.SELF, POOL);
		this.baseMagicNumber = this.magicNumber = POISON;
		this.overheat = OVERHEAT;
	}
	
	@Override
	public void triggerWhenDrawn(){
		if (!this.canCycle()) return; // have to check this before super call, otherwise our test for canCycle is false since it JUST cycled.
		super.triggerWhenDrawn();
		
		flash();

		for (AbstractMonster m:AbstractDungeon.getMonsters().monsters){
			if (!m.isDeadOrEscaped()){
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,AbstractDungeon.player,new PoisonPower(m,AbstractDungeon.player,this.magicNumber),this.magicNumber, AbstractGameAction.AttackEffect.POISON));
			}
		}

		CloneCore();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {

		for (AbstractMonster mo:AbstractDungeon.getMonsters().monsters){
			if (!mo.isDeadOrEscaped()){
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo,AbstractDungeon.player,new PoisonPower(mo,AbstractDungeon.player,this.magicNumber),this.magicNumber, AbstractGameAction.AttackEffect.POISON));
			}
		}

		CloneCore();
	}

	@Override
	public AbstractCard makeCopy() {
		return new NuclearCore();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = DESCRIPTION + UPGRADE_DESCRIPTION;
			this.initializeDescription();
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = DESCRIPTION + M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
