   package constructmod.actions;

   import com.megacrit.cardcrawl.actions.AbstractGameAction;
   import com.megacrit.cardcrawl.core.Settings;
   import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
   import com.megacrit.cardcrawl.relics.AbstractRelic;

   public class ReplaceRelicAction extends AbstractGameAction
   {

   		public AbstractRelic newRelic;
   		public AbstractRelic oldRelic;

		public ReplaceRelicAction(AbstractRelic newRelic,AbstractRelic oldRelic) {
			this.actionType = ActionType.SPECIAL;
			this.target = AbstractDungeon.player;
			this.newRelic = newRelic;
			this.oldRelic = oldRelic;
		}

		public void update()
		{
			AbstractDungeon.player.loseRelic(oldRelic.relicId);
			AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, newRelic);
			//newRelic.instantObtain(AbstractDungeon.player,AbstractDungeon.player.relics.indexOf(oldRelic.relicId),true);
			this.isDone = true;
		}
   }