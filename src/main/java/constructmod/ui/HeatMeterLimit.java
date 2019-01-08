package constructmod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HeatMeterLimit {

    public static final float width = 64.0f * Settings.scale;
    public static final float height = 64.0f * Settings.scale;
    public static final float initialRotation = 30f;
    public float rotation=0f;
    public static final float distFromOrigin = 275.0f * Settings.scale;
    public static final float baseScale = 1f;
    public static final float fadeOutScale = 3f;
    public float scale = baseScale;
    public float alpha = 0.4f;

    public boolean cardExists = true;
    public boolean visible = true;

    public HeatMeterLimit(int amount){
        rotation = initialRotation - 12f*amount;
    }

    // called directly before onRefresh if the card still exists.
    public void setCardExists(){
        cardExists = !HeatMeter.flashFreezeActive && HeatMeter.shouldRender;
    }

    public void onRefresh(){
        visible = cardExists;
        cardExists = false;
    }

    public void drawHeatMeterLimit(float time, SpriteBatch sb){

        if (!visible && alpha > 0f) alpha -= 0.1f;
        else if (visible && alpha < 0.4f) alpha += 0.1f;

        if (!visible) scale += (fadeOutScale - scale) * 0.2f;
        else scale += (baseScale - scale) * 0.2f;

        if (alpha <= 0) return;

        Color prevColor = sb.getColor();
        sb.setColor(1f,0.6f,0.2f,alpha+(float)Math.sin(time*Math.PI*2f)*alpha*0.1f);
        sb.draw(HeatMeter.HEAT_LIMIT_IMG,
                AbstractDungeon.player.drawX + 22f*Settings.scale /*- 40f*Settings.scale*/ - width/2 - (float)Math.cos(rotation*Math.PI/180)*distFromOrigin,
                AbstractDungeon.player.drawY + 170f*Settings.scale - height/2 - (float)Math.sin(rotation*Math.PI/180)*distFromOrigin,
                width/2,height/2,width,height,
                scale,scale,0f,
                0,0,64,64,false,false);
        sb.setColor(prevColor);
    }
}
