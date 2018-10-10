package constructmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class FlammableFog extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("FlammableFog");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	public static final int COST = 1;
	public static final int OVERHEAT = 9;
	public static final int UPGRADE_PLUS_OVERHEAT = 2;
	public static final int M_UPGRADE_PLUS_OVERHEAT = 3;
	private static final int POOL = 1;

	public String desc;

	public FlammableFog() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.UNCOMMON, CardTarget.SELF, POOL);
		this.overheat = OVERHEAT;
		this.desc = DESCRIPTION;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		// causes crash!
		//AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(m.hb.cX, m.hb.cY), 0.01f));
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new NextTurnBlockPower(p,this.block),this.block));
	}

	@Override
	public void applyPowers(){
		this.baseBlock = this.overheat;
		super.applyPowers();
		this.rawDescription = EXTENDED_DESCRIPTION[0] + desc;
		initializeDescription();
	}

	@Override
	public void onMoveToDiscard(){
		this.rawDescription = desc;
		initializeDescription();
	}

	@Override
	public AbstractCard makeCopy() {
		return new FlammableFog();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeOverheat(UPGRADE_PLUS_OVERHEAT);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeOverheat(M_UPGRADE_PLUS_OVERHEAT);
		}
	}
}
