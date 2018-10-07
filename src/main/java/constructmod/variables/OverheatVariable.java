package constructmod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import constructmod.cards.AbstractConstructCard;

public class OverheatVariable extends DynamicVariable
{
    @Override
    public String key()
    {
        return "construct:O";
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        return false;
    }

    @Override
    public int value(AbstractCard card)
    {
        if (card instanceof AbstractConstructCard) return ((AbstractConstructCard) card).overheat;
        else return 0;
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        if (card instanceof AbstractConstructCard) return ((AbstractConstructCard) card).overheat;
        else return 0;
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        if (card instanceof AbstractConstructCard) return ((AbstractConstructCard) card).upgradedOverheat;
        else return false;
    }
}