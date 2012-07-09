package ic2.rocketScience.items;

import ic2.rocketScience.entities.EntityRangefinder;
import net.minecraft.src.*;

public class ItemRangefinder extends ItemRocketScience
{

    public ItemRangefinder(int i, int j)
    {
        super(i, j);
        maxStackSize = 1;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        EntityRangefinder entityarrow = new EntityRangefinder(world, entityplayer, 1.0F);
        //if(!world.multiplayerWorld)
        //{
        world.spawnEntityInWorld(entityarrow);
        //}
        return itemstack;
    }

    public static int ecks = 0;
    public static int zee = 0;
}
