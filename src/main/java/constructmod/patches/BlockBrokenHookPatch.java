package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import constructmod.cards.AbstractConstructCard;

@SpirePatch(clz = AbstractCreature.class, method = "brokeBlock")
public class BlockBrokenHookPatch {
	public static void Postfix(AbstractCreature obj) {
		for (final AbstractCard c : AbstractDungeon.player.drawPile.group){
			if (c instanceof AbstractConstructCard) ((AbstractConstructCard) c).onBlockBroken();
		}
		for (final AbstractCard c : AbstractDungeon.player.hand.group){
			if (c instanceof AbstractConstructCard) ((AbstractConstructCard) c).onBlockBroken();
		}
		for (final AbstractCard c : AbstractDungeon.player.discardPile.group){
			if (c instanceof AbstractConstructCard) ((AbstractConstructCard) c).onBlockBroken();
		}
		for (final AbstractCard c : AbstractDungeon.player.exhaustPile.group){
			if (c instanceof AbstractConstructCard) ((AbstractConstructCard) c).onBlockBroken();
		}
	}
}