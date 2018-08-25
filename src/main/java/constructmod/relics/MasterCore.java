package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import constructmod.ConstructMod;
import constructmod.cards.BatteryCore;
import constructmod.cards.FlameCore;
import constructmod.cards.ForceCore;
import constructmod.cards.GuardCore;
import constructmod.cards.ScopeCore;
import constructmod.cards.LaserCore;

public class MasterCore extends CustomRelic {
    public static final String ID = ConstructMod.makeID("MasterCore");
    private static final String IMG = "img/constructRelics/MasterCore.png";
    
    private final ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
	
    public MasterCore() {
        super(ID, new Texture(IMG), RelicTier.COMMON, LandingSound.SOLID);
        cards.add(new FlameCore());
        cards.add(new LaserCore());
        cards.add(new ScopeCore());
        cards.add(new ForceCore());
        cards.add(new GuardCore());
        cards.add(new BatteryCore());
    }
 
    @Override
    public void atBattleStart() {
    	/*boolean didSomething = false;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
        	if (c instanceof FlameCore || c instanceof LaserCore || c instanceof GuardCore || c instanceof ScopeCore || c instanceof BatteryCore) {
        		didSomething = true;
        		AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(
        				AbstractDungeon.player,AbstractDungeon.player,c.makeStatEquivalentCopy(),1,true,false));
        	}
        }*/
    	
    	for (int i=0;i<3;i++) {
	    	AbstractCard c = cards.get(AbstractDungeon.cardRandomRng.random(0,cards.size()-1));
	    	AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(c.makeCopy(),1,true,false));
    	}
    	
    	AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    	flash();
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new MasterCore();
    }
}