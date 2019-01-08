package constructmod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import constructmod.cards.AbstractConstructCard;

public class GatlingGunVariable extends DynamicVariable
{
    @Override
    public String key()
    {
        return "construct:G";
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        return false;
    }

    @Override
    public int value(AbstractCard card)
    {
        return card.damage * card.magicNumber * (EnergyPanel.getCurrentEnergy()+(AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ChemicalX.ID)?2:0));
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        return card.damage * card.magicNumber * (EnergyPanel.getCurrentEnergy()+(AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ChemicalX.ID)?2:0));
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        return false;
    }
}