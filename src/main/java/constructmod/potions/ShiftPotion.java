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
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ShiftPotion extends AbstractPotion {
    public static final String POTION_ID = "construct:ShiftPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String DESCRIPTIONS[] = potionStrings.DESCRIPTIONS;

    public ShiftPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.S, PotionColor.GREEN);

        this.potency = this.getPotency();
        this.description = ShiftPotion.DESCRIPTIONS[0] + this.potency + (this.potency==1?ShiftPotion.DESCRIPTIONS[1]:ShiftPotion.DESCRIPTIONS[2]);
        this.isThrown = false;
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.STRENGTH.NAMES[0]), GameDictionary.keywords.get(GameDictionary.STRENGTH.NAMES[0])));
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.DEXTERITY.NAMES[0]), GameDictionary.keywords.get(GameDictionary.DEXTERITY.NAMES[0])));
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        final int str = target.hasPower(StrengthPower.POWER_ID)?target.getPower(StrengthPower.POWER_ID).amount:0;
        final int dex = target.hasPower(DexterityPower.POWER_ID)?target.getPower(DexterityPower.POWER_ID).amount:0;
        if (dex-str != 0) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new StrengthPower(target, dex-str), dex-str));
        if (str-dex != 0) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new DexterityPower(target, str-dex), str-dex));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(target,this.potency));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ShiftPotion();
    }

    @Override
    public int getPotency(final int ascensionLevel) {
        return 1;
    }
}
