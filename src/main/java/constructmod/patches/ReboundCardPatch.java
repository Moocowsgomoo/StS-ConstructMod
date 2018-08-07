package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import basemod.ReflectionHacks;
import constructmod.cards.AbstractConstructCard;

@SpirePatch(cls="com.megacrit.cardcrawl.actions.utility.UseCardAction", method="ctor", paramtypes= {"com.megacrit.cardcrawl.cards.AbstractCard","com.megacrit.cardcrawl.core.AbstractCreature"})
public class ReboundCardPatch {
	@SpireInsertPatch(rloc=6)
	public static void Insert(Object __obj_instance, final AbstractCard card, final AbstractCreature target) {
		UseCardAction act = (UseCardAction) __obj_instance;
		//AbstractCard tc = (AbstractCard) ReflectionHacks.getPrivate(act, UseCardAction.class, "targetCard");
		if (card instanceof AbstractConstructCard && ((AbstractConstructCard)card).rebound) {
			act.reboundCard = true;
		}
	}
}
