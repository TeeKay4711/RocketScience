package ic2.rocketScience.blocks;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import ic2.common.BlockMultiID;
import ic2.common.TileEntityBlock;
import ic2.common.TileEntityElecMachine;
import ic2.rocketScience.mod_RocketScience;
import ic2.rocketScience.tileEntities.TileEntityAutoMiner;
import ic2.rocketScience.tileEntities.TileEntityDefense;
import ic2.rocketScience.tileEntities.TileEntitySeparator;
import ic2.rocketScience.tileEntities.TileEntityLaser;
import ic2.rocketScience.tileEntities.TileEntityOffense;
import ic2.rocketScience.tileEntities.TileEntityRadar;

public class BlockRocketScienceMachine extends BlockMultiID
{
    public BlockRocketScienceMachine(int blockId)
    {
        super(blockId, Material.iron);
        this.setHardness(2.0F);
    }

    @Override
    protected int damageDropped(int var1)
    {
        return var1;
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player)
    {
        int metaData = world.getBlockMetadata(x, y, z);
        TileEntity tileentity = world.getBlockTileEntity(x, y, z);
        System.out.println("BlockRocketScienceMachine");

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
                        mod_RocketScience.displayGuiScreen(player, mod_RocketScience.guiIdIsotope, tileentity);
                    }

                    break;

                    /*case 1:
                    	if(!world.isRemote)
                    	{
                    		mod_RocketScience.displayGuiScreen(player, mod_RocketScience.guiIdFusion, tileentity);
                    	}
                    	break;*/
                case 1:
                    if (!world.isRemote)
                    {
                        mod_RocketScience.displayGuiScreen(player, mod_RocketScience.guiIdAutoMiner, tileentity);
                    }

                    break;

                case 2:
                    if (!world.isRemote)
                    {
                        mod_RocketScience.displayGuiScreen(player, mod_RocketScience.guiIdDefense, tileentity);
                    }

                    break;

                case 3:
                    if (!world.isRemote)
                    {
                        mod_RocketScience.displayGuiScreen(player, mod_RocketScience.guiIdOffense, tileentity);
                    }

                    break;

                case 4:
                    if (!world.isRemote)
                    {
                        //mod_RocketScience.displayGuiScreen(player, mod_RocketScience.guiIdIsotope, tileentity);
                    }

                    break;

                default:
                    return false;
            }
        }

        return true;
    }

    @Override
    public String getTextureFile()
    {
        return "/ic2/rocketScience/gfx/blocks.png";
    }

    @Override
    public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side)
    {
        int metaData = iBlockAccess.getBlockMetadata(x, y, z);

        if (metaData == 0)
        {
            if (side == 0)
            {
                return 11;
            }

            if (side == 1)
            {
                return 8;
            }

            if (side == ((TileEntityElecMachine)iBlockAccess.getBlockTileEntity(x, y, z)).getFacing())
            {
                return !((TileEntityElecMachine)iBlockAccess.getBlockTileEntity(x, y, z)).getActive() ? 9 : 10;
            }
            else
            {
                return 11;
            }
        }

        if (metaData == 1)
        {
            return side != 1 && side != 0 ? 16 : 8;
        }

        if (metaData == 2)
        {
            return side != 1 && side != 0 ? 22 : 21;
        }

        if (metaData == 3)
        {
            return side != 1 && side != 0 ? 20 : 19;
        }

        if (metaData == 4)
        {
            if (side == 0)
            {
                return 11;
            }

            return side != 1 ? 25 : 24;
        }

        if (metaData == 5)
        {
            if (side == 0)
            {
                return 4;
            }

            return side != 1 ? 23 : 5;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int side, int metaData)
    {
        if (metaData == 0)
        {
            if (side == 1 || side == 0)
            {
                return 8;
            }

            return side != 3 ? 11 : 9;
        }

        if (metaData == 1)
        {
            return side != 1 && side != 0 ? 16 : 8;
        }

        if (metaData == 2)
        {
            return side != 1 && side != 0 ? 22 : 21;
        }

        if (metaData == 3)
        {
            return side != 1 && side != 0 ? 20 : 19;
        }

        if (metaData == 4)
        {
            if (side == 0)
            {
                return 11;
            }

            return side != 1 ? 25 : 24;
        }

        if (metaData == 5)
        {
            if (side == 0)
            {
                return 4;
            }

            return side != 1 ? 23 : 5;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public TileEntityBlock getBlockEntity(int id)
    {
        switch (id)
        {
            case 0:
                return (TileEntityBlock)new TileEntitySeparator();

            case 1:
                return (TileEntityBlock)new TileEntityAutoMiner();

            case 2:
                return (TileEntityBlock)new TileEntityDefense();

            case 3:
                return (TileEntityBlock)new TileEntityOffense();

            case 4:
                return (TileEntityBlock)new TileEntityLaser();

            case 5:
                return (TileEntityBlock)new TileEntityRadar();

            default:
                return null;
        }
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return mod_RocketScience.machineModelID;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }
}
