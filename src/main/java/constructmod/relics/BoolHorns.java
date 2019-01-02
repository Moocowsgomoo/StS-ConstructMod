package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import constructmod.ConstructMod;

public class BoolHorns extends CustomRelic {
    public static final String ID = ConstructMod.makeID("BoolHorns");
    public static final String IMG = ConstructMod.makeRelicImg(ID);
    public static final String OUTLINE_IMG = ConstructMod.makeRelicOutlineImg(ID);

    public BoolHorns() {
        super(ID, new Texture(IMG),new Texture(OUTLINE_IMG), RelicTier.RARE, LandingSound.FLAT);
        this.setCounter(3);
        //this.updateDescription(AbstractDungeon.player.chosenClass);
    }
 
    @Override
    public void atBattleStart() {
        AbstractMonster mo = AbstractDungeon.getRandomMonster();
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new DamageAction(mo,new DamageInfo(AbstractDungeon.player,this.counter, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
        this.setCounter(3);
        this.stopPulse();
        this.updateDescription(AbstractDungeon.player.chosenClass);
    }

    @Override
    public void justEnteredRoom(AbstractRoom room){
        if (room.phase != AbstractRoom.RoomPhase.COMBAT){
            this.setCounter(counter*2);
            this.updateDescription(AbstractDungeon.player.chosenClass);
            this.flash();
            this.beginLongPulse();
        }
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + 3 + DESCRIPTIONS[1]; // don't update number since it says "start of EACH combat"
    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void onEquip(){
        this.updateDescription(AbstractDungeon.player.chosenClass);
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new BoolHorns();
    }
}