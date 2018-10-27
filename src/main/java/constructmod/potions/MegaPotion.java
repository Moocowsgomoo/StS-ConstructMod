package constructmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import constructmod.actions.MegaUpgradeRandomCardInHandAction;

public class MegaPotion extends AbstractPotion {
    public static final String POTION_ID = "construct:MegaPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String DESCRIPTIONS[] = potionStrings.DESCRIPTIONS;

    public MegaPotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.BOLT, PotionColor.WHITE);

        this.potency = this.getPotency();
        this.description = (this.potency==1?MegaPotion.DESCRIPTIONS[0]:MegaPotion.DESCRIPTIONS[1] + this.potency + MegaPotion.DESCRIPTIONS[2]);
        this.isThrown = false;
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize("mega-upgrade"), GameDictionary.keywords.get("mega-upgrade")));
    }

    @Override
    public void use(AbstractCreature target) {
        for (int i=0;i<this.potency;i++){
            AbstractDungeon.actionManager.addToBottom(new MegaUpgradeRandomCardInHandAction());
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new MegaPotion();
    }

    @Override
    public int getPotency(final int ascensionLevel) {
        return 1;
    }
}
