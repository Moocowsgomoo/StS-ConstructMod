package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.BossCrystalImpactEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;

public class SynchronizePower extends AbstractPower implements PostDrawSubscriber, PostBattleSubscriber,
PostDungeonInitializeSubscriber {
	public static final String POWER_ID = "Synchronize";
	public static final String NAME = "Synchronize";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever you draw 2 of the same card in a row, deal ",
			" damage to ALL enemies.",
			" NL (Last drawn: ",
			")."
	};
	
	private String drawnCardName = "";
	
	public SynchronizePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/powers/synchronize.png");
	}
	
	@Override
	public void onInitialApplication() {
		BaseMod.subscribeToPostDraw(this);
		BaseMod.subscribeToPostBattle(this);
		BaseMod.subscribeToPostDungeonInitialize(this);
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
		if (!drawnCardName.equals("")) this.description = this.description + DESCRIPTIONS[2] + this.drawnCardName + DESCRIPTIONS[3];
	}
	
	@Override
	public void receivePostDraw (AbstractCard c) {
		if (c.originalName.equals(drawnCardName)) {
			this.flash();
			
			//AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.owner,1)); // occurs after damageAll due to action order
			AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(
					this.owner, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
			
			for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
	            if (!mo.isDeadOrEscaped()) {
	                AbstractDungeon.actionManager.addToTop(new VFXAction(new BossCrystalImpactEffect(mo.drawX, mo.drawY), 0.05f));
	            }
	        }
		
		}
		drawnCardName = c.originalName;
	}
	
	@Override
	public void receivePostBattle(AbstractRoom battleRoom) {
		BaseMod.unsubscribeFromPostDraw(this);
		/*
		 *  calling unsubscribeFromPostBattle inside the callback
		 *  for receivePostBattle means that when we're calling it
		 *  there is currently an iterator going over the list
		 *  of subscribers and calling receivePostBattle on each of
		 *  them therefore if we immediately try to remove the this
		 *  callback from the post battle subscriber list it will
		 *  throw a concurrent modification exception in the iterator
		 *  
		 *  for now we just add a delay - yes this is an atrocious solution
		 *  PLEASE someone with a better idea replace it
		 */
		Thread delayed = new Thread(() -> {
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				System.out.println("could not delay unsubscribe to avoid ConcurrentModificationException");
				e.printStackTrace();
			}
			BaseMod.unsubscribeFromPostBattle(this);
		});
		delayed.start();
	}

	@Override
	public void receivePostDungeonInitialize() {
		BaseMod.unsubscribeFromPostDraw(this);
		/*
		 *  calling unsubscribeFromPostDungeonInitialize inside the callback
		 *  for receivePostDungeonInitialize means that when we're calling it
		 *  there is currently an iterator going over the list
		 *  of subscribers and calling receivePostDungeonInitialize on each of
		 *  them therefore if we immediately try to remove the this
		 *  callback from the post battle subscriber list it will
		 *  throw a concurrent modification exception in the iterator
		 *  
		 *  for now we just add a delay - yes this is an atrocious solution
		 *  PLEASE someone with a better idea replace it
		 */
		Thread delayed = new Thread(() -> {
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				System.out.println("could not delay unsubscribe to avoid ConcurrentModificationException");
				e.printStackTrace();
			}
			BaseMod.unsubscribeFromPostDungeonInitialize(this);
		});
		delayed.start();
	}
}