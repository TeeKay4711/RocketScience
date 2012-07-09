package ic2.rocketScience.containers;

import ic2.common.ContainerIC2;
import ic2.rocketScience.tileEntities.TileEntitySeparator;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotFurnace;

public class ContainerSeparator extends ContainerIC2
{

    public TileEntitySeparator tileentity;
    public short progress = 0;
    public int energy = 0;

    public ContainerSeparator(InventoryPlayer inventory, TileEntitySeparator tileentity)
    {
        this.tileentity = tileentity;
        this.addSlot(new Slot(tileentity, 0, 56, 17));
        this.addSlot(new SlotFurnace(inventory.player, tileentity, 2, 108, 17));
        this.addSlot(new SlotFurnace(inventory.player, tileentity, 3, 108, 53));
        this.addSlot(new Slot(tileentity, 1, 56, 53));

        for (int y = 0; y < 3; ++y)
        {
            for (int x = 0; x < 9; ++x)
            {
                this.addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        for (int z = 0; z < 9; ++z)
        {
            this.addSlot(new Slot(inventory, z, 8 + z * 18, 142));
        }
    }

    @Override
    public void updateCraftingResults()
    {
        super.updateCraftingResults();

        for (int x = 0; x < this.crafters.size(); ++x)
        {
            ICrafting crafting = (ICrafting)this.crafters.get(x);

            if (this.progress != this.tileentity.progress)
            {
                crafting.updateCraftingInventoryInfo(this, 0, this.tileentity.progress);
            }

            if (this.energy != this.tileentity.energy)
            {
                crafting.updateCraftingInventoryInfo(this, 1, this.tileentity.energy & 65535);
                crafting.updateCraftingInventoryInfo(this, 2, this.tileentity.energy >>> 16);
            }
        }

        this.progress = this.tileentity.progress;
        this.energy = this.tileentity.energy;
    }

    @Override
    public void updateProgressBar(int var1, int var2)
    {
        switch (var1)
        {
            case 0:
                this.tileentity.progress = (short)var2;
                break;

            case 1:
                this.tileentity.energy = this.tileentity.energy & -65536 | var2;
                break;

            case 2:
                this.tileentity.energy = this.tileentity.energy & 65535 | var2 << 16;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {
        return true;
    }

    @Override
    public int guiInventorySize()
    {
        return 4;
    }

    @Override
    public int getInput()
    {
        return this.firstEmptyFrom(0, 2, tileentity);
    }
}
