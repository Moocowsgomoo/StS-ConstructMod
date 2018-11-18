package constructmod.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import constructmod.ConstructMod;
import constructmod.cards.AbstractConstructCard;
import constructmod.cards.FlashFreeze;
import constructmod.powers.FlashFreezePower;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class HeatMeter {
    public static Texture HEAT_BAR_IMG;
    public static Texture HEAT_MARKER_IMG;
    public static Texture HEAT_LIMIT_IMG;
    public static Texture SNOWFLAKE_IMG;

    public static boolean flashFreezeActive = false;
    public static int numSegmentsToRender = 0;
    public static boolean shouldRender = true;
    public static float heatBarRenderTimer=0f;
    public static final ArrayList<HeatMeterSegment> segments = new ArrayList<>();
    public static final ArrayList<HeatMeterSnowflake> snowflakes = new ArrayList<HeatMeterSnowflake>();
    public static HeatMeterIndicator indicator;

    public static final HashMap<Integer,HeatMeterLimit> cardOverheatLimits = new HashMap<>();

    public HeatMeter(){
        HEAT_BAR_IMG = ImageMaster.loadImage("img/constructUI/HeatMeterSegment.png");
        HEAT_MARKER_IMG = ImageMaster.loadImage("img/constructUI/HeatMeterIndicator.png");
        HEAT_LIMIT_IMG = ImageMaster.loadImage("img/constructUI/HeatMeterLimit.png");
        SNOWFLAKE_IMG = ImageMaster.loadImage("img/constructUI/Snowflake.png");
        segments.add(new HeatMeterSegment(0));
        segments.add(new HeatMeterSegment(1));
        segments.add(new HeatMeterSegment(2));
        segments.add(new HeatMeterSegment(3));
        snowflakes.add(new HeatMeterSnowflake(0f));
        snowflakes.add(new HeatMeterSnowflake(0.1f));
        snowflakes.add(new HeatMeterSnowflake(0.25f));
        snowflakes.add(new HeatMeterSnowflake(0.5f));
        snowflakes.add(new HeatMeterSnowflake(0.75f));
        snowflakes.add(new HeatMeterSnowflake(0.9f));
        indicator = new HeatMeterIndicator();
    }

    public void render(SpriteBatch sb){
        heatBarRenderTimer -= Gdx.graphics.getDeltaTime();
        if (heatBarRenderTimer <= 0){
            shouldRender = (AbstractDungeon.player.currentHealth > 0);
            checkVisibility();
            heatBarRenderTimer = 1.0f;
            flashFreezeActive = AbstractDungeon.player != null && AbstractDungeon.player.hasPower(FlashFreezePower.POWER_ID);
        }

        for (HeatMeterSegment h : segments) h.drawHeatMeterSegment(sb);
        for (HeatMeterLimit li : cardOverheatLimits.values()) li.drawHeatMeterLimit(heatBarRenderTimer, sb);
        if (numSegmentsToRender >= 1) indicator.drawHeatMeterIndicator(ConstructMod.cyclesThisTurn,numSegmentsToRender*5,sb);
        if (flashFreezeActive && shouldRender) {
            for (HeatMeterSnowflake s : snowflakes) s.drawSnowflake(numSegmentsToRender * 5, sb);
        }
    }

    public void onIncrementCycles(){
        if (ConstructMod.cyclesThisTurn % 5 == 4) getCurSegment().targetRedColor = 0.5f;
        else if (ConstructMod.cyclesThisTurn % 5 == 0) {
            HeatMeterSegment overheated = getSegment(ConstructMod.cyclesThisTurn-1);
            overheated.targetRedColor = 1f;
            overheated.scale *= 5f;
            overheated.distFromOrigin *= 1.3f;
        }
        else getCurSegment().redColor = 0.7f;

        indicator.scale *= 4f;
        indicator.distFromOrigin *= 1.2f;
    }

    public void onResetCycles(){
        for (HeatMeterSegment h : segments){
            h.targetRedColor = 0f;
        }
        indicator.scale = HeatMeterIndicator.targetScale;
}

    public HeatMeterSegment getCurSegment(){
        return getSegment(ConstructMod.cyclesThisTurn);
    }

    public HeatMeterSegment getSegment(int timesCycled){
        if (timesCycled > segments.size()*5) return segments.get(segments.size()-1);

        return segments.get((int)Math.floor(timesCycled*0.2f));
    }

    public void checkVisibility(){
        int highestOverheat = 0;
        int currentOverheat = 0;
        if (AbstractDungeon.player.drawPile != null) {
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c instanceof AbstractConstructCard) {
                    currentOverheat = ((AbstractConstructCard) c).overheat;
                    if (currentOverheat > highestOverheat) highestOverheat = currentOverheat;
                    addOverheatLimit(((AbstractConstructCard) c).overheat);
                }
            }
        }
        if (AbstractDungeon.player.discardPile != null) {
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c instanceof AbstractConstructCard) {
                    currentOverheat = ((AbstractConstructCard) c).overheat;
                    if (currentOverheat > highestOverheat) highestOverheat = currentOverheat;
                    addOverheatLimit(((AbstractConstructCard) c).overheat);
                }
            }
        }
        if (AbstractDungeon.player.hand != null) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof AbstractConstructCard) {
                    currentOverheat = ((AbstractConstructCard) c).overheat;
                    if (currentOverheat > highestOverheat) highestOverheat = currentOverheat;
                    addOverheatLimit(((AbstractConstructCard) c).overheat);
                }
            }
        }
        AbstractCard c = AbstractDungeon.player.cardInUse;
        if (c instanceof AbstractConstructCard){
            currentOverheat = ((AbstractConstructCard) c).overheat;
            if (currentOverheat > highestOverheat) highestOverheat = currentOverheat;
            addOverheatLimit(((AbstractConstructCard) c).overheat);
        }
        numSegmentsToRender = (int)Math.ceil(highestOverheat*0.2f);
        if (!shouldRender) numSegmentsToRender = 0;
        for (int i=0;i<segments.size();i++){
            segments.get(i).visible = (numSegmentsToRender > segments.get(i).index);
        }
        for (int i : cardOverheatLimits.keySet()){
            cardOverheatLimits.get(i).onRefresh();
        }
    }

    public void addOverheatLimit(int amount){
        if (amount <= 0) return;
        if (!cardOverheatLimits.containsKey(amount)) cardOverheatLimits.put(amount, new HeatMeterLimit(amount));
        else cardOverheatLimits.get(amount).setCardExists();
    }

    public void createSnowflake(){

    }
}
