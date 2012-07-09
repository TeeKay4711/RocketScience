package ic2.rocketScience.entities;

import net.minecraft.src.*;

import ic2.rocketScience.mod_RocketScience;

import java.util.List;

public class EntityMissilePassengerWarhead extends EntityLiving
{

    double speed;
    int type;
    EntityPlayer rider;

    public EntityMissilePassengerWarhead(World world)
    {
        super(world);
        rider = null;
        speed = -.44F;
        texture = "/ic2/rocketScience/gfx/MissilePassenger.png";
    }

    public EntityMissilePassengerWarhead(World world, double i, double j, double k)//, int missileType)
    {
        super(world);
        setPosition(i + .5f, j, k + .5f);
        //type=missileType;
        rider = null;
        setSize(1.0F, 1.0F);
        speed = -.44F;
        texture = "/ic2/rocketScience/gfx/MissilePassenger.png";
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setDouble("Speed", speed);
        nbttagcompound.setInteger("Type", type);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        speed = nbttagcompound.getDouble("Speed");
        type = nbttagcompound.getInteger("Type");
    }

    protected void entityInit()
    {
    }

    public void onUpdate()
    {
        moveEntity(0, speed, 0);
        int x = (int)Math.floor(posX);
        int y = (int)Math.floor(posY);
        int z = (int)Math.floor(posZ);
        
        if ((worldObj.getBlockId(x, y-1, z ) != 0) || (worldObj.getBlockId(x, y, z) != 0))
        {
            explode();
        }

        if (posY <= 0)
        {
            explode();
        }
    }

    public boolean canBeCollidedWith()
    {
        return true;
    }

    public void explode()
    {
        if (rider != null)
        {
            rider.mountEntity(null);
            //EntityMissileDummy.updateFall(rider, 0.1f);
        }

        rider = null;

        if (type == 12)
        {
            entityDropItem(new ItemStack(mod_RocketScience.itemPassengerModule, 1), 0);
        }
        else if (type == 0)
        {
            entityDropItem(new ItemStack(mod_RocketScience.itemBoosterModule, 1, 15), 0);
        }
        else if (type == 15)
        {
            entityDropItem(new ItemStack(mod_RocketScience.itemPassengerDepleted, 1, 10001), 0);
        }

        setDead();
    }

    @Override
    public void updateRiderPosition()
    {
        if (riddenByEntity == null)
        {
            return;
        }
        else
        {
            riddenByEntity.setPosition(posX, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ);
            return;
        }
    }

    public String getEntityTexture()
    {
        return "/ic2/rocketScience/gfx/MissilePassenger.png";
    }

    @Override
    public int getMaxHealth()
    {
        // TODO Auto-generated method stub
        return 0;
    }
}