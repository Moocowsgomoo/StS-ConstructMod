package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import constructmod.ConstructMod;

public class Challenge1 extends CustomRelic {
    public static final String ID = ConstructMod.makeID("Challenge1");
    private static final String IMG = "img/constructRelics/Challenge1.png";

    public Challenge1() {
        super(ID, new Texture(IMG), RelicTier.STARTER, LandingSound.CLINK);
    }
    
    @Override
    public String getUpdatedDescription() {
        return ConstructMod.getAllChallengeDescriptionsUpTo(1);
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Challenge1();
    }
}