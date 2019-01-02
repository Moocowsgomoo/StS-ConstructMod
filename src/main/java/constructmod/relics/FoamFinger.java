package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import constructmod.ConstructMod;

public class FoamFinger extends CustomRelic {
    public static final String ID = ConstructMod.makeID("FoamFinger");
    public static final String IMG = ConstructMod.makeRelicImg(ID);
    public static final String OUTLINE_IMG = ConstructMod.makeRelicOutlineImg(ID);

    public boolean isIncreasingHandSize = false;

    public FoamFinger() {
        super(ID, new Texture(IMG),new Texture(OUTLINE_IMG), RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void atTurnStart() {
        if (this.counter > 0){
            flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            --this.counter;
            if (this.counter == 0) {
                stopPulse();
                ConstructMod.logger.debug("STOP PULSE");
            }
        } else if (isIncreasingHandSize){
            ConstructMod.logger.debug("SHUT OFF");
            --AbstractDungeon.player.gameHandSize;
            this.isIncreasingHandSize = false;
        }
    }

    @Override
    public void onEquip(){
        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            ++AbstractDungeon.player.gameHandSize;
            this.counter = 3;
            this.beginLongPulse();
        }
    }

    @Override
    public void atPreBattle() {
        ++AbstractDungeon.player.gameHandSize;
        this.isIncreasingHandSize = true;
        this.counter = 3;
        this.beginLongPulse();
    }

    @Override
    public void onVictory() {
        if (this.isIncreasingHandSize) --AbstractDungeon.player.gameHandSize;
        this.counter = -1;
        this.stopPulse();
    }

    @Override
    public void onUnequip(){
        if (this.isIncreasingHandSize) --AbstractDungeon.player.gameHandSize;
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