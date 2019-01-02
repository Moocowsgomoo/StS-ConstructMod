   package constructmod.actions;

   import com.megacrit.cardcrawl.actions.AbstractGameAction;
   import com.megacrit.cardcrawl.cards.DamageInfo;
   import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
   import com.megacrit.cardcrawl.core.Settings;
   import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

   public class LoseRelicAction extends AbstractGameAction
   {

   		public String relicID;

		public LoseRelicAction(String ID) {
			this.actionType = ActionType.SPECIAL;
			this.target = AbstractDungeon.player;
			this.relicID = ID;
		}

		public void update()
		{
			AbstractDungeon.player.loseRelic(relicID);
			this.isDone = true;
		}
   }