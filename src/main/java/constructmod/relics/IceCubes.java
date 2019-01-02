package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import constructmod.ConstructMod;
import constructmod.powers.FlashFreezePower;

public class IceCubes extends CustomRelic {
    public static final String ID = ConstructMod.makeID("IceCubes");
    public static final String IMG = ConstructMod.makeRelicImg(ID);
    public static final String OUTLINE_IMG = ConstructMod.makeRelicOutlineImg(ID);

    public IceCubes() {
        super(ID, new Texture(IMG),new Texture(OUTLINE_IMG), RelicTier.SHOP, LandingSound.CLINK);
    }
 
    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FlashFreezePower(AbstractDungeon.player, 3), 3));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new IceCubes();
    }
}