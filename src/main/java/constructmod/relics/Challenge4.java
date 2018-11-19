package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import constructmod.ConstructMod;

public class Challenge4 extends CustomRelic {
    public static final String ID = ConstructMod.makeID("Challenge4");
    private static final String IMG = "img/constructRelics/Challenge4.png";

    public Challenge4() {
        super(ID, new Texture(IMG), RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return ConstructMod.getAllChallengeDescriptionsUpTo(4);
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Challenge4();
    }
}