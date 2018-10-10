package constructmod.actions;

import basemod.helpers.BaseModTags;
import basemod.helpers.CardTags;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import constructmod.cards.AbstractConstructCard;

public class CoolantAction extends AbstractGameAction
{

    public CoolantAction(int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.amount = amount;
    }
    
    @Override
    public void update() {
    	for (final AbstractCard c:AbstractDungeon.player.drawPile.group) {
        	BoostCard(c);
        }
        for (final AbstractCard c:AbstractDungeon.player.hand.group) {
        	if (BoostCard(c)) c.flash();
        }
        for (final AbstractCard c:AbstractDungeon.player.discardPile.group) {
        	BoostCard(c);
        }
        this.isDone = true;
    }
    
    public boolean BoostCard(AbstractCard c) {
    	boolean wasBoosted = false;
    	if (c instanceof AbstractConstructCard && ((AbstractConstructCard) c).overheat > 0) {
    		wasBoosted = true;
    		((AbstractConstructCard) c).overheat += this.amount;
    	}
    	return wasBoosted;
    }
}
