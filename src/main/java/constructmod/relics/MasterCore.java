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

public class MasterCore extends CustomRelic {
    public static final String ID = ConstructMod.makeID("MasterCore");
    private static final String IMG = "img/constructRelics/MasterCore.png";
	
    public MasterCore() {
        super(ID, new Texture(IMG), RelicTier.COMMON, LandingSound.SOLID);
    }
 
    @Override
    public void atBattleStart() {
    	for (int i=0;i<3;i++) {
	    	AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(ConstructMod.getRandomCore(),1,true,false));
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