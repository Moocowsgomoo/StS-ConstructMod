package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import constructmod.ConstructMod;

public class ClockworkPhoenix extends CustomRelic {
    public static final String ID = ConstructMod.makeID("ClockworkPhoenix");
    private static final String IMG = "img/constructRelics/ClockworkPhoenix.png";
	
    public ClockworkPhoenix() {
        super(ID, new Texture(IMG), RelicTier.SPECIAL, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new ClockworkPhoenix();
    }
}