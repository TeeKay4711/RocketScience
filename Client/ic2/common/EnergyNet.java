package ic2.common;

import ic2.api.*;
import ic2.api.Direction;
import ic2.platform.Platform;
import java.io.PrintStream;
import java.util.*;
import net.minecraft.src.*;

public final class EnergyNet
{
    private static class EnergyPath
    {
        TileEntity target;
        Direction targetDirection;
        Set conductors;
        int minX;
        int minY;
        int minZ;
        int maxX;
        int maxY;
        int maxZ;
        double loss;
        int minInsulationEnergyAbsorption;
        int minInsulationBreakdownEnergy;
        int minConductorBreakdownEnergy;
        long totalEnergyConducted;

        EnergyPath()
        {
            target = null;
            conductors = new HashSet();
            minX = 0x7fffffff;
            minY = 0x7fffffff;
            minZ = 0x7fffffff;
            maxX = 0x80000000;
            maxY = 0x80000000;
            maxZ = 0x80000000;
            loss = 0.0D;
            minInsulationEnergyAbsorption = 0x7fffffff;
            minInsulationBreakdownEnergy = 0x7fffffff;
            minConductorBreakdownEnergy = 0x7fffffff;
            totalEnergyConducted = 0L;
        }
    }

    private static class EnergyBlockLink
    {
        Direction direction;
        double loss;

        EnergyBlockLink(Direction direction1, double d)
        {
            direction = direction1;
            loss = d;
        }
    }

    private static class EnergyTarget
    {
        TileEntity tileEntity;
        Direction direction;

        EnergyTarget(TileEntity tileentity, Direction direction1)
        {
            tileEntity = tileentity;
            direction = direction1;
        }
    }

    public static final double minConductionLoss = 0.0001D;
    private static Map worldToEnergyNetMap = new HashMap();
    private static Map entityLivingToShockEnergyMap = new HashMap();
    private World world;
    private Map energySourceToEnergyPathMap;

    public static EnergyNet getForWorld(World world1)
    {
        if (world1 == null)
        {
            return null;
        }

        if (!worldToEnergyNetMap.containsKey(world1))
        {
            worldToEnergyNetMap.put(world1, new EnergyNet(world1));
        }

        return (EnergyNet)worldToEnergyNetMap.get(world1);
    }

    public static void onTick()
    {
        Platform.profilerStartSection("Shocking");
        Iterator iterator = entityLivingToShockEnergyMap.entrySet().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            EntityLiving entityliving = (EntityLiving)entry.getKey();
            int i = (((Integer)entry.getValue()).intValue() + 63) / 64;

            if (entityliving.isEntityAlive())
            {
                entityliving.attackEntityFrom(IC2DamageSource.electricity, i);
            }
        }
        while (true);

        entityLivingToShockEnergyMap.clear();
        Platform.profilerEndSection();
    }

    private EnergyNet(World world1)
    {
        energySourceToEnergyPathMap = new HashMap();
        world = world1;
    }

    public void addTileEntity(TileEntity tileentity)
    {
        if (!(tileentity instanceof IEnergyTile) || ((IEnergyTile)tileentity).isAddedToEnergyNet())
        {
            return;
        }

        if (tileentity instanceof IEnergyAcceptor)
        {
            List list = discover(tileentity, true, 0x7fffffff);
            Iterator iterator = list.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                EnergyNet.EnergyPath energypath = (EnergyNet.EnergyPath)iterator.next();
                IEnergySource ienergysource = (IEnergySource)energypath.target;

                if (energySourceToEnergyPathMap.containsKey(ienergysource) && (double)ienergysource.getMaxEnergyOutput() > energypath.loss)
                {
                    energySourceToEnergyPathMap.remove(ienergysource);
                }
            }
            while (true);
        }

        if (tileentity instanceof IEnergySource);
    }

    public void removeTileEntity(TileEntity tileentity)
    {
        if (!(tileentity instanceof IEnergyTile) || !((IEnergyTile)tileentity).isAddedToEnergyNet())
        {
            return;
        }

        if (tileentity instanceof IEnergyAcceptor)
        {
            List list = discover(tileentity, true, 0x7fffffff);
            Iterator iterator = list.iterator();
            label0:

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                EnergyNet.EnergyPath energypath = (EnergyNet.EnergyPath)iterator.next();
                IEnergySource ienergysource = (IEnergySource)energypath.target;

                if (!energySourceToEnergyPathMap.containsKey(ienergysource) || (double)ienergysource.getMaxEnergyOutput() <= energypath.loss)
                {
                    continue;
                }

                if (tileentity instanceof IEnergyConductor)
                {
                    energySourceToEnergyPathMap.remove(ienergysource);
                    continue;
                }

                Iterator iterator1 = ((List)energySourceToEnergyPathMap.get(ienergysource)).iterator();

                do
                {
                    if (!iterator1.hasNext())
                    {
                        continue label0;
                    }
                }
                while (((EnergyNet.EnergyPath)iterator1.next()).target != tileentity);

                iterator1.remove();
            }
            while (true);
        }

        if (tileentity instanceof IEnergySource)
        {
            energySourceToEnergyPathMap.remove((IEnergySource)tileentity);
        }
    }

    public int emitEnergyFrom(IEnergySource ienergysource, int i)
    {
        if (!energySourceToEnergyPathMap.containsKey(ienergysource))
        {
            energySourceToEnergyPathMap.put(ienergysource, discover((TileEntity)ienergysource, false, ienergysource.getMaxEnergyOutput()));
        }

        int j = 0;
        Vector vector = new Vector();
        double d = 0.0D;
        Iterator iterator = ((List)energySourceToEnergyPathMap.get(ienergysource)).iterator();
        label0:

        do
        {
            EnergyNet.EnergyPath energypath;
            IEnergySink ienergysink;

            do
            {
                if (!iterator.hasNext())
                {
                    break label0;
                }

                energypath = (EnergyNet.EnergyPath)iterator.next();
                ienergysink = (IEnergySink)energypath.target;
            }
            while (!ienergysink.demandsEnergy());

            d += 1.0D / energypath.loss;
            vector.add(energypath);
        }
        while (vector.size() < i);

        for (Iterator iterator1 = vector.iterator(); iterator1.hasNext();)
        {
            EnergyNet.EnergyPath energypath1 = (EnergyNet.EnergyPath)iterator1.next();
            IEnergySink ienergysink1 = (IEnergySink)energypath1.target;
            int k = (int)Math.floor((double)Math.round(((double)i / d / energypath1.loss) * 100000D) / 100000D);
            int l = (int)Math.floor(energypath1.loss);

            if (k > l)
            {
                int i1 = ienergysink1.injectEnergy(energypath1.targetDirection, k - l);
                j += k - i1;
                int j1 = k - l - i1;
                energypath1.totalEnergyConducted += j1;

                if (j1 > energypath1.minInsulationEnergyAbsorption)
                {
                    List list = world.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, AxisAlignedBB.getBoundingBox(energypath1.minX - 1, energypath1.minY - 1, energypath1.minZ - 1, energypath1.maxX + 2, energypath1.maxY + 2, energypath1.maxZ + 2));

                    for (Iterator iterator3 = list.iterator(); iterator3.hasNext();)
                    {
                        EntityLiving entityliving = (EntityLiving)iterator3.next();
                        int k1 = 0;
                        Iterator iterator5 = energypath1.conductors.iterator();
                        IEnergyConductor ienergyconductor2;
                        label1:

                        do
                        {
                            TileEntity tileentity;

                            do
                            {
                                if (!iterator5.hasNext())
                                {
                                    break label1;
                                }

                                ienergyconductor2 = (IEnergyConductor)iterator5.next();
                                tileentity = (TileEntity)ienergyconductor2;
                            }
                            while (!entityliving.boundingBox.intersectsWith(AxisAlignedBB.getBoundingBox(tileentity.xCoord - 1, tileentity.yCoord - 1, tileentity.zCoord - 1, tileentity.xCoord + 2, tileentity.yCoord + 2, tileentity.zCoord + 2)));

                            int l1 = j1 - ienergyconductor2.getInsulationEnergyAbsorption();

                            if (l1 > k1)
                            {
                                k1 = l1;
                            }
                        }
                        while (ienergyconductor2.getInsulationEnergyAbsorption() != energypath1.minInsulationEnergyAbsorption);

                        if (entityLivingToShockEnergyMap.containsKey(entityliving))
                        {
                            entityLivingToShockEnergyMap.put(entityliving, Integer.valueOf(((Integer)entityLivingToShockEnergyMap.get(entityliving)).intValue() + k1));
                        }
                        else
                        {
                            entityLivingToShockEnergyMap.put(entityliving, Integer.valueOf(k1));
                        }
                    }

                    if (j1 >= energypath1.minInsulationBreakdownEnergy)
                    {
                        Iterator iterator4 = energypath1.conductors.iterator();

                        do
                        {
                            if (!iterator4.hasNext())
                            {
                                break;
                            }

                            IEnergyConductor ienergyconductor1 = (IEnergyConductor)iterator4.next();

                            if (j1 >= ienergyconductor1.getInsulationBreakdownEnergy())
                            {
                                ienergyconductor1.removeInsulation();

                                if (ienergyconductor1.getInsulationEnergyAbsorption() < energypath1.minInsulationEnergyAbsorption)
                                {
                                    energypath1.minInsulationEnergyAbsorption = ienergyconductor1.getInsulationEnergyAbsorption();
                                }
                            }
                        }
                        while (true);
                    }
                }

                if (j1 >= energypath1.minConductorBreakdownEnergy)
                {
                    Iterator iterator2 = energypath1.conductors.iterator();

                    while (iterator2.hasNext())
                    {
                        IEnergyConductor ienergyconductor = (IEnergyConductor)iterator2.next();

                        if (j1 >= ienergyconductor.getConductorBreakdownEnergy())
                        {
                            ienergyconductor.removeConductor();
                        }
                    }
                }
            }
        }

        return i - j;
    }

    public long getTotalEnergyConducted(TileEntity tileentity)
    {
        long l = 0L;

        if ((tileentity instanceof IEnergyConductor) || (tileentity instanceof IEnergySink))
        {
            List list = discover(tileentity, true, 0x7fffffff);

            for (Iterator iterator1 = list.iterator(); iterator1.hasNext();)
            {
                EnergyNet.EnergyPath energypath1 = (EnergyNet.EnergyPath)iterator1.next();
                IEnergySource ienergysource = (IEnergySource)energypath1.target;

                if (energySourceToEnergyPathMap.containsKey(ienergysource) && (double)ienergysource.getMaxEnergyOutput() > energypath1.loss)
                {
                    Iterator iterator2 = ((List)energySourceToEnergyPathMap.get(ienergysource)).iterator();

                    while (iterator2.hasNext())
                    {
                        EnergyNet.EnergyPath energypath2 = (EnergyNet.EnergyPath)iterator2.next();

                        if ((tileentity instanceof IEnergySink) && energypath2.target == tileentity || (tileentity instanceof IEnergyConductor) && energypath2.conductors.contains((IEnergyConductor)tileentity))
                        {
                            l += energypath2.totalEnergyConducted;
                        }
                    }
                }
            }
        }

        if ((tileentity instanceof IEnergySource) && energySourceToEnergyPathMap.containsKey((IEnergySource)tileentity))
        {
            for (Iterator iterator = ((List)energySourceToEnergyPathMap.get((IEnergySource)tileentity)).iterator(); iterator.hasNext();)
            {
                EnergyNet.EnergyPath energypath = (EnergyNet.EnergyPath)iterator.next();
                l += energypath.totalEnergyConducted;
            }
        }

        return l;
    }

    private List discover(TileEntity tileentity, boolean flag, int i)
    {
        HashMap hashmap = new HashMap();
        LinkedList linkedlist = new LinkedList();
        linkedlist.add(tileentity);

        do
        {
            if (linkedlist.isEmpty())
            {
                break;
            }

            TileEntity tileentity1 = (TileEntity)linkedlist.remove();

            if (!tileentity1.isInvalid())
            {
                double d = 0.0D;

                if (tileentity1 != tileentity)
                {
                    d = ((EnergyNet.EnergyBlockLink)hashmap.get(tileentity1)).loss;
                }

                List list = getValidReceivers(tileentity1, flag);
                Iterator iterator1 = list.iterator();

                do
                {
                    if (!iterator1.hasNext())
                    {
                        break;
                    }

                    EnergyNet.EnergyTarget energytarget = (EnergyNet.EnergyTarget)iterator1.next();

                    if (energytarget.tileEntity == tileentity)
                    {
                        continue;
                    }

                    double d1 = 0.0D;

                    if (energytarget.tileEntity instanceof IEnergyConductor)
                    {
                        d1 = ((IEnergyConductor)energytarget.tileEntity).getConductionLoss();

                        if (d1 < 0.0001D)
                        {
                            d1 = 0.0001D;
                        }

                        if (d + d1 >= (double)i)
                        {
                            continue;
                        }
                    }

                    if (!hashmap.containsKey(energytarget.tileEntity) || ((EnergyNet.EnergyBlockLink)hashmap.get(energytarget.tileEntity)).loss > d + d1)
                    {
                        hashmap.put(energytarget.tileEntity, new EnergyNet.EnergyBlockLink(energytarget.direction, d + d1));

                        if (energytarget.tileEntity instanceof IEnergyConductor)
                        {
                            linkedlist.remove(energytarget.tileEntity);
                            linkedlist.add(energytarget.tileEntity);
                        }
                    }
                }
                while (true);
            }
        }
        while (true);

        LinkedList linkedlist1 = new LinkedList();
        Iterator iterator = hashmap.entrySet().iterator();
        label0:

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            TileEntity tileentity2 = (TileEntity)entry.getKey();

            if ((flag || !(tileentity2 instanceof IEnergySink)) && (!flag || !(tileentity2 instanceof IEnergySource)))
            {
                continue;
            }

            EnergyNet.EnergyBlockLink energyblocklink = (EnergyNet.EnergyBlockLink)entry.getValue();
            EnergyNet.EnergyPath energypath = new EnergyNet.EnergyPath();

            if (energyblocklink.loss > 0.10000000000000001D)
            {
                energypath.loss = energyblocklink.loss;
            }
            else
            {
                energypath.loss = 0.10000000000000001D;
            }

            energypath.target = tileentity2;
            energypath.targetDirection = energyblocklink.direction;
            IEnergyConductor ienergyconductor;

            if (!flag && (tileentity instanceof IEnergySource))
            {
                do
                {
                    tileentity2 = energyblocklink.direction.applyToTileEntity(tileentity2);

                    if (tileentity2 == tileentity)
                    {
                        break;
                    }

                    if (tileentity2 instanceof IEnergyConductor)
                    {
                        ienergyconductor = (IEnergyConductor)tileentity2;

                        if (tileentity2.xCoord < energypath.minX)
                        {
                            energypath.minX = tileentity2.xCoord;
                        }

                        if (tileentity2.yCoord < energypath.minY)
                        {
                            energypath.minY = tileentity2.yCoord;
                        }

                        if (tileentity2.zCoord < energypath.minZ)
                        {
                            energypath.minZ = tileentity2.zCoord;
                        }

                        if (tileentity2.xCoord > energypath.maxX)
                        {
                            energypath.maxX = tileentity2.xCoord;
                        }

                        if (tileentity2.yCoord > energypath.maxY)
                        {
                            energypath.maxY = tileentity2.yCoord;
                        }

                        if (tileentity2.zCoord > energypath.maxZ)
                        {
                            energypath.maxZ = tileentity2.zCoord;
                        }

                        energypath.conductors.add(ienergyconductor);

                        if (ienergyconductor.getInsulationEnergyAbsorption() < energypath.minInsulationEnergyAbsorption)
                        {
                            energypath.minInsulationEnergyAbsorption = ienergyconductor.getInsulationEnergyAbsorption();
                        }

                        if (ienergyconductor.getInsulationBreakdownEnergy() < energypath.minInsulationBreakdownEnergy)
                        {
                            energypath.minInsulationBreakdownEnergy = ienergyconductor.getInsulationBreakdownEnergy();
                        }

                        if (ienergyconductor.getConductorBreakdownEnergy() < energypath.minConductorBreakdownEnergy)
                        {
                            energypath.minConductorBreakdownEnergy = ienergyconductor.getConductorBreakdownEnergy();
                        }

                        energyblocklink = (EnergyNet.EnergyBlockLink)hashmap.get(tileentity2);

                        if (energyblocklink == null)
                        {
                            throw new RuntimeException((new StringBuilder()).append("EnergyNet: reachedTileEntities corrupted (").append(energypath.target).append(" [").append(energypath.target.xCoord).append(" ").append(energypath.target.yCoord).append(" ").append(energypath.target.zCoord).append("] -> ").append(tileentity2).append(" [").append(tileentity2.xCoord).append(" ").append(tileentity2.yCoord).append(" ").append(tileentity2.zCoord).append("] -> ").append(tileentity).append(" [").append(tileentity.xCoord).append(" ").append(tileentity.yCoord).append(" ").append(tileentity.zCoord).append("])").toString());
                        }
                    }
                    else
                    {
                        if (tileentity2 != null)
                        {
                            System.out.println((new StringBuilder()).append("EnergyNet: EnergyBlockLink corrupted (").append(energypath.target).append(" [").append(energypath.target.xCoord).append(" ").append(energypath.target.yCoord).append(" ").append(energypath.target.zCoord).append("] -> ").append(tileentity2).append(" [").append(tileentity2.xCoord).append(" ").append(tileentity2.yCoord).append(" ").append(tileentity2.zCoord).append("] -> ").append(tileentity).append(" [").append(tileentity.xCoord).append(" ").append(tileentity.yCoord).append(" ").append(tileentity.zCoord).append("])").toString());
                        }

                        continue label0;
                    }
                }
                while (true);
            }

            linkedlist1.add(energypath);
        }
        while (true);

        return linkedlist1;
    }

    private List getValidReceivers(TileEntity tileentity, boolean flag)
    {
        LinkedList linkedlist = new LinkedList();
        Direction adirection[] = Direction.values();
        int i = adirection.length;

        for (int j = 0; j < i; j++)
        {
            Direction direction = adirection[j];
            TileEntity tileentity1 = direction.applyToTileEntity(tileentity);

            if (!(tileentity1 instanceof IEnergyTile) || !((IEnergyTile)tileentity1).isAddedToEnergyNet())
            {
                continue;
            }

            Direction direction1 = direction.getInverse();

            if ((!flag && (tileentity instanceof IEnergyEmitter) && ((IEnergyEmitter)tileentity).emitsEnergyTo(tileentity1, direction) || flag && (tileentity instanceof IEnergyAcceptor) && ((IEnergyAcceptor)tileentity).acceptsEnergyFrom(tileentity1, direction)) && (!flag && (tileentity1 instanceof IEnergyAcceptor) && ((IEnergyAcceptor)tileentity1).acceptsEnergyFrom(tileentity, direction1) || flag && (tileentity1 instanceof IEnergyEmitter) && ((IEnergyEmitter)tileentity1).emitsEnergyTo(tileentity, direction1)))
            {
                linkedlist.add(new EnergyNet.EnergyTarget(tileentity1, direction1));
            }
        }

        return linkedlist;
    }

    public List discoverTargets(TileEntity tileentity, boolean flag, int i)
    {
        List list = discover(tileentity, flag, i);
        LinkedList linkedlist = new LinkedList();

        for (Iterator iterator = list.iterator(); iterator.hasNext(); linkedlist.add(((EnergyNet.EnergyPath)iterator.next()).target)) { }

        return linkedlist;
    }
}
