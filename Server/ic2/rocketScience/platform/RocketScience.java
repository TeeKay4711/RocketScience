package ic2.rocketScience.platform;

import ic2.common.IC2DamageSource;
import ic2.platform.NetworkManager;
import ic2.platform.Platform;
import ic2.rocketScience.mod_RocketScience;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.WorldServer;
import net.minecraft.src.mod_IC2;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.NetworkMod;

public abstract class RocketScience extends NetworkMod
{
    @Override
    public String getVersion()
    {
        return "v0.89 Rewrite20120704";
    }

    public void load()
    {
        //IC2DamageSource.addLocalization((Configuration)null, this);
    }

    @Override
    public boolean onTickInGame(MinecraftServer server)
    {
        WorldServer world = server.getWorldManager(0);
        mod_RocketScience.OnTickInGame(world.playerEntities, world);
        //NetworkManager.onTick();
        return true;
    }

    /*public void addLocalization(Configuration var1, String var2, String var3)
    {
        Platform.AddLocalization(var2, var3);
    }*/
}
