package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import constructmod.ConstructMod;

public class Challenge2 extends CustomRelic {
    public static final String ID = ConstructMod.makeID("Challenge2");
    private static final String IMG = "img/constructRelics/Challenge2.png";

    public Challenge2() {
        super(ID, new Texture(IMG), RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return ConstructMod.getAllChallengeDescriptionsUpTo(2);
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Challenge2();
    }
}