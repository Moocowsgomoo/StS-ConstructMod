   package constructmod.actions;

   import com.megacrit.cardcrawl.actions.AbstractGameAction;
   import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
   import com.megacrit.cardcrawl.cards.DamageInfo;
   import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
   import com.megacrit.cardcrawl.core.AbstractCreature;
   import com.megacrit.cardcrawl.core.Settings;
   import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
   import com.megacrit.cardcrawl.powers.LoseStrengthPower;
   import com.megacrit.cardcrawl.powers.StrengthPower;

   public class SiegeFormAction extends AbstractGameAction
   {

		public SiegeFormAction(AbstractCreature owner, int amount) {
			this.actionType = ActionType.POWER;
			this.duration = Settings.ACTION_DUR_XFAST;
			this.target = owner;
			this.amount = amount;
		}

		public void update()
		{
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.target, new StrengthPower(this.target, this.amount), this.amount));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.target, new LoseStrengthPower(this.target, this.amount), this.amount));
			this.isDone = true;
		}
   }