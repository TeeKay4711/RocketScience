package ic2.rocketScience.items;

import ic2.rocketScience.tileEntities.TileEntityAutoMiner;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemAutominerFinder extends ItemRocketScience
{
    public ItemAutominerFinder(int i, int j)
    {
        super(i, j);
        maxStackSize = 1;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        TileEntityAutoMiner.locating = true;
        TileEntityAutoMiner.time = world.getWorldTime();
        TileEntityAutoMiner.thatGuy = entityplayer;
        return itemstack;
    }

}
