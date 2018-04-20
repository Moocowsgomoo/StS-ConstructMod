package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import constructmod.ConstructMod;
import constructmod.cards.BatteryOrb;
import constructmod.cards.FlameOrb;
import constructmod.cards.GuardOrb;
import constructmod.cards.ShockOrb;

public class GenesisOrb extends CustomRelic {
    public static final String ID = "GenesisOrb";
    private static final String IMG = "img/relics/GenesisOrb.png";
	
    public GenesisOrb() {
        super(ID, new Texture(IMG), RelicTier.UNCOMMON, LandingSound.SOLID);
    }
 
    @Override
    public void atBattleStart() {
    	boolean didSomething = false;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
        	if (c instanceof FlameOrb || c instanceof ShockOrb || c instanceof GuardOrb || c instanceof BatteryOrb) {
        		didSomething = true;
        		AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(
        				AbstractDungeon.player,AbstractDungeon.player,c.makeStatEquivalentCopy(),1,true,false));
        	}
        }
        if (didSomething) {
        	flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new GenesisOrb();
    }
}