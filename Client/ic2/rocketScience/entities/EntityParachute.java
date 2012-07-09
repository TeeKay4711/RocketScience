package ic2.rocketScience.entities;

import net.minecraft.src.*;

//public class EntityParachute extends EntityLiving
public class EntityParachute extends Entity
{

    EntityPlayer player = null;

    public EntityParachute(World world)
    {
        super(world);
        //texture = "/ic2/rocketScience/gfx/Parachute.png";
    }

    public EntityParachute(World world, int i, int j, int k, EntityPlayer play)
    {
        super(world);
        setPosition(i + .5f, j, k + .5f);
        player = play;
        //texture = "/ic2/rocketScience/gfx/Parachute.png";
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        //player = ModLoader.getMinecraftInstance().thePlayer;
    }

    protected void entityInit()
    {
    }

    public void onUpdate()
    {
        /*if (player == null || EntityMissileDummy.isFallDistance(player, 0.0F))
        {
            setDead();
            return;
        }*/
	if(player == null || player.fallDistance == 0.0F)
	{
	    System.out.println("Client: Parachute setDead(), player="+player);
	    setDead();
	    return;
	}
        //System.out.println("posX: "+player.posX+"posY: "+player.posY+"posZ: "+player.posZ);
        //System.out.println("posX: "+posX+"posY: "+posY+"posZ: "+posZ);
        //System.out.println(" motionX: "+(int)(player.motionX*1000)+" motionY: "+(int)(player.motionY*1000)+" motionZ: "+(int)(player.motionZ*1000));
        //System.out.println(" motionX: "+(int)(motionX*1000)+" motionY: "+(int)(motionY*1000)+" motionZ: "+(int)(motionZ*1000));
        posX = player.posX;
        posY = player.posY - 2;
        posZ = player.posZ;
    }

    public boolean canBeCollidedWith()
    {
        return false;
    }

    public String getEntityTexture()
    {
        return "/ic2/rocketScience/gfx/Parachute.png";
    }

    /*@Override
    public int getMaxHealth()
    {
        return 0;
    }*/

}
