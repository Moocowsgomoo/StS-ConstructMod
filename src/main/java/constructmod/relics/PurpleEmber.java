package constructmod.relics;

import basemod.abstracts.CustomRelic;
import constructmod.ConstructMod;
import constructmod.actions.MegaUpgradeHandAction;
import constructmod.cards.AbstractConstructCard;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class PurpleEmber extends CustomRelic {
    public static final String ID = ConstructMod.makeID("PurpleEmber");
    private static final String IMG = "img/constructRelics/PurpleEmber.png";
	
    public PurpleEmber() {
        super(ID, new Texture(IMG), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }
    
    @Override
    public void atBattleStart() {
        if (this.counter == -2) {
            this.pulse = false;
            this.counter = -1;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new MegaUpgradeHandAction());
        }
    }
    
    @Override
    public void setCounter(final int counter) {
        super.setCounter(counter);
        if (counter == -2) {
            this.pulse = true;
        }
    }
    
    @Override
    public void onEnterRestRoom() {
        this.flash();
        this.counter = -2;
        this.pulse = true;
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new PurpleEmber();
    }
}