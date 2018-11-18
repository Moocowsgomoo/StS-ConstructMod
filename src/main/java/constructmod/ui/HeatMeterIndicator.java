package constructmod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import constructmod.ConstructMod;

public class HeatMeterIndicator {

    public static final float width = 256.0f * Settings.scale;
    public static final float height = 256.0f * Settings.scale;
    public static final float initialRotation = 30f;
    public float rotation=0f;
    public int prevNumCycles=0;
    public static final float targetDistFromOrigin = 250.0f * Settings.scale;
    public float distFromOrigin = targetDistFromOrigin;
    public static final float targetScale = 0.2f;
    public float scale = 0.2f;

    public void drawHeatMeterIndicator(int numCycles,int maxCyclesOnMeter, SpriteBatch sb){

        if (numCycles > maxCyclesOnMeter) numCycles = prevNumCycles; // as the meter fades (maxCycles = 0), stay where we are.
        rotation += ((initialRotation - numCycles*12f) - rotation) * 0.3f;
        prevNumCycles = numCycles;

        scale += (targetScale - scale) * 0.2f;
        distFromOrigin += (targetDistFromOrigin - distFromOrigin) * 0.2f;

        Color prevColor = sb.getColor();
        sb.setColor(Color.WHITE);
        sb.draw(HeatMeter.HEAT_MARKER_IMG,
                AbstractDungeon.player.drawX + 22f*Settings.scale /*- 40f*Settings.scale*/ - width/2 - (float)Math.cos(rotation*Math.PI/180)*distFromOrigin,
                AbstractDungeon.player.drawY + 170f*Settings.scale - height/2 - (float)Math.sin(rotation*Math.PI/180)*distFromOrigin,
                width/2,height/2,width,height,
                scale,scale,rotation,
                0,0,256,256,true,false);
        sb.setColor(prevColor);
    }
}
