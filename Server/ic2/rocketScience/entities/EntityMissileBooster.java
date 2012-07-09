package ic2.rocketScience.entities;

import ic2.rocketScience.mod_RocketScience;
import ic2.rocketScience.tileEntities.TileEntityRadar;
import net.minecraft.src.*;

public class EntityMissileBooster extends EntityLiving
{

    public double speed;
    public int targetX;
    public int targetZ;
    public int missileType;
    EntityPlayer rider = null;

    public EntityMissileBooster(World world)
    {
        super(world);
        texture = "/ic2/rocketScience/gfx/MissileModel.png";
    }

    /*public MissileBoosterEntity(World world, int i, int j, int k, int x, int z, int type)
    {
    	super(world);
    	speed=0;
    	targetX=x;
    	targetZ=z;
    	setPosition(i+.5f,j,k+.5f);
        missileType=type;
        texture="/ic2/rocketScience/gfx/MissileModel.png";
    }*/

    public EntityMissileBooster(World world, double x, double y, double z)
    {
        super(world);
        speed = 0.0D;
        //targetX=x;
        //targetZ=z;
        setPosition(x + .5f, y, z + .5f);
        //missileType=type;
        texture = "/ic2/rocketScience/gfx/MissileModel.png";
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("Type", missileType);
        nbttagcompound.setInteger("TargetX", targetX);
        nbttagcompound.setInteger("TargetZ", targetZ);
        nbttagcompound.setDouble("Speed", speed);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        missileType = nbttagcompound.getInteger("Type");
        targetX = nbttagcompound.getInteger("TargetX");
        targetZ = nbttagcompound.getInteger("TargetZ");
        speed = nbttagcompound.getDouble("Speed");
    }

    protected void entityInit()
    {
    }

    public void onUpdate()
    {

	int x = (int)Math.floor(posX);
	int y = (int)Math.floor(posY);
	int z = (int)Math.floor(posZ);
	
        if (worldObj.getBlockId(x, y + 1, z) != 0 || worldObj.getBlockId(x, y + 2, z) != 0)
        {
            EntityMissileWarhead getHead = dropWarhead();
            System.out.println("Boom!");//getHead.explode();
        }

        moveEntity(0, speed, 0);

        if (speed < 1)
        {
            speed += 0.02D;
        }

        if (posY > 80) //worldObj.getHeight())
        {
            if (!shootdown())
            {
                dropWarhead();
            }
            else
            {
                worldObj.createExplosion(null, posX + targetX, posY, posZ + targetZ, 6.0F);
                setDead();
            }
        }

        worldObj.spawnParticle("flame", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
    }

    public boolean canBeCollidedWith()
    {
        return true;
    }

    public boolean shootdown()
    {
        int x = 0;
        int z = 0;

        for (int ecks = -50; ecks <= 50; ecks++)
        {
            for (int zee = -50; zee <= 50; zee++)
            {
                if (zee * zee + ecks * ecks > 2500)
                {
                    continue;
                }

                x = (int)posX + targetX + ecks;
                z = (int)posZ + targetZ + zee;
                int k;

                for (k = worldObj.getHeight() - 1; k > 0 && worldObj.getBlockId(x, k, z) == 0; k--) {}

                if (worldObj.getBlockId(x, k, z) == mod_RocketScience.blockMachineId && worldObj.getBlockMetadata(x, k, z) == 5)
                {
                    if (((TileEntityRadar)worldObj.getBlockTileEntity(x, k, z)).shootdown(this))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private EntityMissileWarhead dropWarhead()
    {
        EntityMissileWarhead warhead = new EntityMissileWarhead(worldObj, (int)posX + targetX, (int)posY, (int)posZ + targetZ); //,missileType);
        //ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("Client: missile targeted on "+(int)(targetX)+", "+(int)(targetZ));
        warhead.type = missileType;
        //warhead.setAngles(90, 90);

        //if(!ModLoader.getMinecraftInstance().isMultiplayerWorld())
        //	worldObj.spawnEntityInWorld(warhead);
        if (!worldObj.isRemote)
        {
            worldObj.spawnEntityInWorld(warhead);
        }

        setDead();

        if (missileType == 12)
        {
            warhead.rider = rider;
            rider.mountEntity(null);
            rider.posX = warhead.posX;
            rider.posZ = warhead.posZ;
            rider.mountEntity(warhead);
            rider = null;
        }

        return warhead;
    }
    public void playerGetInRocket(EntityPlayer player)
    {
        player.mountEntity(this);
        rider = player;
    }

    public void updateRiderPosition()
    {
        if (riddenByEntity == null)
        {
            return;
        }
        else
        {
            double d = Math.cos(((double)rotationYaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D;
            double d1 = Math.sin(((double)rotationYaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D;
            riddenByEntity.setPosition(posX + d, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ + d1);
            return;
        }
    }

    public String getEntityTexture()
    {
        return "/ic2/rocketScience/gfx/MissileModel.png";
    }

    @Override
    public int getMaxHealth()
    {
        // TODO Auto-generated method stub
        return 0;
    }
}
