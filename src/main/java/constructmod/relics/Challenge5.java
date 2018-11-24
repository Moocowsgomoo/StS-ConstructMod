package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import constructmod.ConstructMod;

public class Challenge5 extends CustomRelic {
    public static final String ID = ConstructMod.makeID("Challenge5");
    private static final String IMG = "img/constructRelics/Challenge5.png";

    public Challenge5() {
        super(ID, new Texture(IMG), RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return ConstructMod.getAllChallengeDescriptionsUpTo(5);
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Challenge5();
    }
}