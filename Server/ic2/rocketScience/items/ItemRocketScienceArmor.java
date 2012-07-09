package ic2.rocketScience.items;

import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.EnumArmorMaterial;
import net.minecraft.src.ItemArmor;

public class ItemRocketScienceArmor extends ItemArmor
    implements ITextureProvider
{

    public ItemRocketScienceArmor(int i, int j, EnumArmorMaterial k, int l, int i1, int j1)
    {
        super(i, k, l, i1);
        setIconIndex(j);
        setMaxDamage(maxDamageArray[i1] * j1);
    }

    public String getTextureFile()
    {
        return "/ic2/rocketScience/gfx/items.png";
    }

    private static final int damageReduceAmountArray[] =
    {
        3, 8, 6, 3
    };
    private static final int maxDamageArray[] =
    {
        11, 16, 15, 13
    };

}