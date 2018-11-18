package constructmod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import constructmod.ConstructMod;
import constructmod.powers.FlashFreezePower;

import java.util.Random;

public class HeatMeterSegment {

    public int index;
    public float rotation;
    public float alpha;
    public boolean visible = false;
    public float redColor = 0f; // red tint - 1 is red, 0 is white, 0.5 is orange
    public float targetRedColor = 0f;

    public static final float VISIBLE_ALPHA = 0.5f;

    public static final float width = 37.0f * Settings.scale;
    public static final float height = 168.0f * Settings.scale;
    public static final float targetDistFromOrigin = 215.0f * Settings.scale;
    public float distFromOrigin = targetDistFromOrigin;
    public static final float targetScale = 1.4f;
    public float scale = targetScale;
    public static final Random random = new Random();

    public HeatMeterSegment(int index){
        this.index = index;
        this.rotation = index * -60f;
        this.visible = false;
        this.alpha = 0.0f;
    }

    public void drawHeatMeterSegment(SpriteBatch sb){
        if (!visible && alpha > 0) alpha -= 0.01f;
        else if (visible && alpha < VISIBLE_ALPHA) alpha += 0.01f;

        redColor += (targetRedColor - redColor) * 0.1f;
        distFromOrigin += (targetDistFromOrigin - distFromOrigin) * 0.2f;
        scale += (targetScale - scale) * 0.2f;

        Color prevColor = sb.getColor();
        if (HeatMeter.flashFreezeActive){
            sb.setColor(0f,1f,1f,alpha>0?alpha:0);
            redColor = 0f;
            targetRedColor = 0f;
        }
        else{
            sb.setColor(1,(1-redColor*redColor>0?1-redColor*redColor:0),(1-redColor*2>0?1-redColor*2:0),alpha>0?alpha:0);
        }
        sb.draw(HeatMeter.HEAT_BAR_IMG,
                AbstractDungeon.player.drawX + 22f*Settings.scale /*- 40f*Settings.scale*/ -
                        width/2 - (float)Math.cos(rotation*Math.PI/180)*distFromOrigin + (redColor>0.1f && redColor < 0.9f?(random.nextFloat()*10f-5f)*Settings.scale*redColor:0f),
                AbstractDungeon.player.drawY + 170f*Settings.scale - height/2
                        - (float)Math.sin(rotation*Math.PI/180)*distFromOrigin + (redColor>0.1f && redColor < 0.9f?(random.nextFloat()*10f-5f)*Settings.scale*redColor:0f),
                width/2,height/2,
                width, height,
                targetScale,targetScale,rotation,
                0, 0, 37, 168,
                false, false);
        sb.setColor(prevColor);
    }
}
