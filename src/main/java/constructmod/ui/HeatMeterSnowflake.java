package constructmod.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import constructmod.cards.FlashFreeze;

import java.util.Random;

public class HeatMeterSnowflake {

    public static final float width = 128.0f * Settings.scale;
    public static final float height = 128.0f * Settings.scale;
    public float meterPosAngle=0f;
    public float localRotation = 0f;
    public static final float localRtVelRange = 30f;
    public float localRtVel = 0f;
    public static final float distFromOriginBase =  240.0f * Settings.scale;
    public static final float distFromOriginRange = 50.0f * Settings.scale;
    public float distFromOrigin = distFromOriginBase;
    public static final float targetScaleBase = 0.5f;
    public static final float targetScaleRange = 0.2f;
    public float targetScale = targetScaleBase;
    public float scale = 0f;
    public static final float startAngle = 30.0f;
    public static final float startAlpha = 1f;
    public float alpha = startAlpha;
    public static final Random random = new Random();
    public static final float baseLifetime = 3.0f;
    public static final float lifetimeRange = 1.0f;
    public float startLifetime = baseLifetime;
    public float lifetime = baseLifetime;
    public float timeOffset;

    public HeatMeterSnowflake(float timeOffset){
        randomizeParams(5);
        lifetime = startLifetime*timeOffset;
    }

    public void randomizeParams(int maxAngle){
        startLifetime = lifetime = baseLifetime + lifetimeRange*2f*random.nextFloat() - lifetimeRange;
        distFromOrigin = random.nextFloat()*distFromOriginRange*2f -distFromOriginRange + distFromOriginBase;
        localRtVel = random.nextFloat()*localRtVelRange*2f - localRtVelRange;
        targetScale = random.nextFloat()*targetScaleRange*2f - targetScaleRange + targetScaleBase;
        meterPosAngle = startAngle - random.nextFloat()*(maxAngle*12f);
        alpha = startAlpha;
    }

    public void drawSnowflake(int maxAngle, SpriteBatch sb){

        lifetime -= Gdx.graphics.getDeltaTime();
        if (lifetime <= 0) randomizeParams(maxAngle);

        localRotation += localRtVel*Gdx.graphics.getDeltaTime();
        scale = (1-(lifetime/startLifetime)) * targetScale;
        alpha = (lifetime/startLifetime)*startAlpha;

        Color prevColor = sb.getColor();
        sb.setColor(1f,1f,1f,alpha);
        sb.draw(HeatMeter.SNOWFLAKE_IMG,
                AbstractDungeon.player.drawX + 22f*Settings.scale /*- 40f*Settings.scale*/ - width/2 - (float)Math.cos(meterPosAngle*Math.PI/180)*distFromOrigin,
                AbstractDungeon.player.drawY + 170f*Settings.scale - height/2 - (float)Math.sin(meterPosAngle*Math.PI/180)*distFromOrigin,
                width/2,height/2,width,height,
                scale,scale,localRotation,
                0,0,128,128,false,false);
        sb.setColor(prevColor);
    }
}
