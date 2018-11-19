package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import constructmod.ConstructMod;

public class Challenge3 extends CustomRelic {
    public static final String ID = ConstructMod.makeID("Challenge3");
    private static final String IMG = "img/constructRelics/Challenge3.png";

    public Challenge3() {
        super(ID, new Texture(IMG), RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return ConstructMod.getAllChallengeDescriptionsUpTo(3);
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Challenge3();
    }
}