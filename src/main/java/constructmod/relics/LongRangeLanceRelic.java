package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import constructmod.ConstructMod;
import constructmod.actions.LoseRelicAction;

public class LongRangeLanceRelic extends CustomRelic {
    public static final String ID = ConstructMod.makeID("LongRangeLance");
    public static final String IMG = ConstructMod.makeRelicImg(ID);
    public static final String OUTLINE_IMG = ConstructMod.makeRelicOutlineImg(ID);

    public LongRangeLanceRelic() {
        super(ID, new Texture(IMG),new Texture(OUTLINE_IMG), RelicTier.SPECIAL, LandingSound.HEAVY);
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
        AbstractMonster mo = AbstractDungeon.getRandomMonster();
        this.flash();
        if (mo != null && mo.hb != null) AbstractDungeon.actionManager.addToBottom(new VFXAction(new VerticalImpactEffect(mo.hb.cX + mo.hb.width / 4.0f, mo.hb.cY - mo.hb.height / 4.0f)));
        if (mo != null && !mo.isDeadOrEscaped()) AbstractDungeon.actionManager.addToBottom(new DamageAction(mo,new DamageInfo(AbstractDungeon.player,this.counter, DamageInfo.DamageType.THORNS)));
        AbstractDungeon.actionManager.addToBottom(new LoseRelicAction(this.ID)); // need an action so no concurrentModificationException
        //AbstractDungeon.player.loseRelic(this.ID);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LongRangeLanceRelic();
    }
}