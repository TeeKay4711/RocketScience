package ic2.rocketScience.items;

import ic2.platform.*;
import net.minecraft.src.*;

public class ItemRocketScienceMachine extends ItemBlockCommon
{

    public ItemRocketScienceMachine(int i)
    {
        super(i);
        setHasSubtypes(true);
    }

    public int getMetadata(int i)
    {
        return i;
    }

    public String getItemNameIS(ItemStack itemstack)
    {
        int i = itemstack.getItemDamage();

        switch (i)
        {
            case 0: // '\0'
                return "blockSeparator";

            case 1: // '\001'
                return "blockAutoMiner";

            case 2:
                return "blockDefense";

            case 3:
                return "blockOffense";

            case 4:
                return "blockLaser";

            case 5:
                return "blockRadar";
        }

        return null;
    }
}