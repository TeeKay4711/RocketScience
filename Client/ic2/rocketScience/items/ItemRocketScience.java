package ic2.rocketScience.items;

import net.minecraft.src.forge.ITextureProvider;
import ic2.platform.ItemCommon;

public class ItemRocketScience extends ItemCommon implements ITextureProvider
{

    public ItemRocketScience(int itemId, int index)
    {
        super(itemId);
        this.setIconIndex(index);
    }

    @Override
    public String getTextureFile()
    {
        return "/ic2/rocketScience/gfx/items.png";
    }
}
