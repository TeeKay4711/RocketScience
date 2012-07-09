package ic2.rocketScience.entities;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

public class EntityMissileDummy extends EntityLiving
{
    public EntityMissileDummy(World var1)
    {
        super(var1);
    }

    public static void updateFall(Entity var0, float var1)
    {
        var0.fallDistance = var1;
    }

    public static boolean isFallDistance(Entity var0, float var1)
    {
        return var0.fallDistance == var1;
    }

    public int getMaxHealth()
    {
        return 0;
    }
}

/*package net.minecraft.src;

public class MissileDummyEntity extends EntityLiving
{
  public MissileDummyEntity(World world)
  {
    super(world);
  }

  public static void updateFall(Entity titty, float fall)
  {
    fallDistance = fall;
  }

  public static boolean isFallDistance(Entity titty, float fall)
  {
    return fallDistance == fall;
  }

  public int getMaxHealth()
  {
    return 0;
  }
}*/
