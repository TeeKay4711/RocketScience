package ic2.rocketScience.tileEntities;

import ic2.api.*;
import ic2.api.Direction;
import ic2.common.*;
import ic2.platform.NetworkManager;
import ic2.platform.Platform;
import java.util.*;
import net.minecraft.src.*;

//Referenced classes of package ic2.common:
//         TileEntityBlock, INetworkTileEntityEventListener, EnergyNet

public class TileEntitySuperconductor extends TileEntityCable
{

    public TileEntitySuperconductor()
    {
        addedToEnergyNet = false;
    }

    public boolean acceptsEnergyFrom(TileEntity tileentity, Direction direction)
    {
        return true; //!(tileentity instanceof TileEntityCable) || canInteractWithCable((TileEntityCable)tileentity);
    }

    public boolean emitsEnergyTo(TileEntity tileentity, Direction direction)
    {
        return true; //!(tileentity instanceof TileEntityCable) || canInteractWithCable((TileEntityCable)tileentity);
    }

    public double getConductionLoss()
    {
        return 0;
    }

    public int getInsulationEnergyAbsorption()
    {
        return 9001;
    }

    public int getInsulationBreakdownEnergy()
    {
        return 9001;
    }

    public int getConductorBreakdownEnergy()
    {
        return 9001;
    }

    public void removeInsulation()
    {
    }

    public void removeConductor()
    {
        worldObj.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
        NetworkManager.initiateTileEntityEvent(this, 0, true);
    }

    public List getNetworkedFields()
    {
        Vector vector = new Vector(1);
        vector.add("color");
        vector.addAll(super.getNetworkedFields());
        return vector;
    }

    public boolean addedToEnergyNet;
    private static final int EventRemoveConductor = 0;
}
