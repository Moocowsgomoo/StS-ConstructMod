package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import constructmod.ConstructMod;

public class FoamFinger extends CustomRelic {
    public static final String ID = "FoamFinger";
    private static final String IMG = "img/relics/FoamFinger.png";
	
    public FoamFinger() {
        super(ID, new Texture(IMG), RelicTier.UNCOMMON, LandingSound.FLAT);
    }
 
    @Override
    public void atTurnStart() {
        flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
    
    @Override
    public void onEquip() {
        final AbstractPlayer player = AbstractDungeon.player;
        ++player.masterHandSize;
    }
    
    @Override
    public void onUnequip() {
        final AbstractPlayer player = AbstractDungeon.player;
        --player.masterHandSize;
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new FoamFinger();
    }
}