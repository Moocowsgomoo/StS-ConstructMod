package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import constructmod.ConstructMod;
import constructmod.actions.ReplaceRelicAction;

public class ExtraLongRangeLanceRelic extends CustomRelic {
    public static final String ID = ConstructMod.makeID("ExtraLongRangeLance");
    public static final String IMG = ConstructMod.makeRelicImg(ID);
    public static final String OUTLINE_IMG = ConstructMod.makeRelicOutlineImg(ID);

    public ExtraLongRangeLanceRelic() {
        super(ID, new Texture(IMG), new Texture(OUTLINE_IMG), RelicTier.SPECIAL, LandingSound.HEAVY);
        this.counter = 12;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1];
    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void atBattleStart() {
        LongRangeLanceRelic relic = new LongRangeLanceRelic();
        relic.setCounter(this.counter);
        relic.updateDescription(AbstractDungeon.player.chosenClass);
        AbstractDungeon.actionManager.addToBottom(new ReplaceRelicAction(relic,this));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ExtraLongRangeLanceRelic();
    }
}