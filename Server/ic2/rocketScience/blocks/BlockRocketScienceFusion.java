package ic2.rocketScience.blocks;

import java.util.ArrayList;
import java.util.Random;

import ic2.common.*;
import ic2.platform.Platform;
import ic2.rocketScience.mod_RocketScience;
import ic2.rocketScience.tileEntities.TileEntityFusion;
import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class BlockRocketScienceFusion extends BlockMultiID
{

    public BlockRocketScienceFusion(int i)
    {
        super(i, Material.iron);
        setHardness(2);
    }

    protected int damageDropped(int i)
    {
        return 0;
    }

    /*public Integer getGui(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        TileEntity tileentity = world.getBlockTileEntity(i, j, k);
        switch(world.getBlockMetadata(i, j, k))
        {
        	default:
        		return Integer.valueOf(mod_RocketScience.guiIdFusion);
        }
    }*/

    public TileEntityBlock getBlockEntity(int i)
    {
        switch (i)
        {
            default:
                return new TileEntityFusion();
        }
    }

    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
    }

    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player)
    {
        /*int l = world.getBlockMetadata(x, y, z);
        Integer integer = getGui(world, x, y, z, entityplayer);
            if(integer == null)
            {
                return false;
            }
            if(!Platform.isSimulating())
            {
                return true;
            } else
            {
                //ModLoader.openGUI(entityplayer, RocketScience.getGuiForId(entityplayer, integer.intValue(), world.getBlockTileEntity(i, j, k)));
                return true;
            }*/
        int metaData = world.getBlockMetadata(x, y, z);
        TileEntity tileentity = world.getBlockTileEntity(x, y, z);
        System.out.println("BlockRocketScienceFusion");

        //System.out.println("Metadata: "+metaData);
        //System.out.println("TileEntity: "+tileentity);
        if (player.isSneaking())
        {
            return false;
        }
        else
        {
            switch (metaData)
            {
                case 0:
                    if (!world.isRemote)
                    {
                        mod_RocketScience.displayGuiScreen(player, mod_RocketScience.guiIdFusion, tileentity);
                    }

                    break;

                default:
                    return false;
            }
        }

        return true;
    }

    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        TileEntityFusion titty = (TileEntityFusion)world.getBlockTileEntity(i, j, k);
        //ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("Heat: " + titty.heat + ", fuel: " + titty.fuel + ", ignited: " + titty.ignited() + ", fuel type: " + titty.fueltype + ", production: " + titty.production + ", added to grid: " + titty.addedToEnergyNet + ", storage: " + titty.storage + ", breeding: " + titty.bred);
        //titty.bred=9499;
    }

    public String getTextureFile()
    {
        return "/ic2/rocketScience/gfx/blocks.png";
    }

    public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side)
    {
        if (side == 1 || side == 0)
        {
            if (((TileEntityFusion)iblockaccess.getBlockTileEntity(x, y, z)).isConverting())
            {
                return 6;
            }
            else
            {
                return 7;
            }
        }
        else
        {
            return 1;
        }
    }

    public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
        if (i == 1 || i == 0)
        {
            return 7;
        }

        return 1;
    }

    public void addCreativeItems(ArrayList arraylist)
    {
        arraylist.add(new ItemStack(this, 1, 0));
    }

}
