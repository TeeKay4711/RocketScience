package ic2.rocketScience.tileEntities;

import java.util.HashMap;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import ic2.common.Ic2Items;
import ic2.common.TileEntityElectricMachine;
import ic2.rocketScience.mod_RocketScience;
import ic2.rocketScience.containers.ContainerSeparator;

public class TileEntitySeparator extends TileEntityElectricMachine
{

    public static HashMap recipes;
    public short percentage = 0;
    public short material = 0;

    public TileEntitySeparator()
    {
        super(4, 5, 100, 32);
    }

    public ItemStack getResultFor(ItemStack itemStack)
    {
        return (ItemStack)recipes.get(Integer.valueOf(itemStack.itemID));
    }

    @Override
    public String getGuiClassName(EntityPlayer var1)
    {
        return "GuiIsotope";
    }

    public Container getGuiContainer(InventoryPlayer inventory)
    {
        return new ContainerSeparator(inventory , this);
    }

    public static void initRecipes()
    {
        recipes = new HashMap();
        addRecipe(Ic2Items.waterCell.itemID, new ItemStack(mod_RocketScience.itemDeuteriumCell, 1));
        addRecipe(mod_RocketScience.itemLithiumCell.shiftedIndex, new ItemStack(mod_RocketScience.itemLithium6Cell, 1));
    }
    public static void addRecipe(int itemId, ItemStack itemStack)
    {
        recipes.put(Integer.valueOf(itemId), itemStack);
    }
    @Override
    public String getInvName()
    {
        return "Isotope Separator";
    }

    @Override
    public void operate()
    {
        if (!canOperate())
        {
            return;
        }

        if (inventory[0].itemID == Ic2Items.waterCell.itemID)
        {
            material = 2;
        }
        else if (inventory[0].itemID == mod_RocketScience.itemLithiumCell.shiftedIndex)
        {
            material = 1;
        }

        ItemStack itemStack = getResultFor(inventory[0]);
        inventory[0].stackSize--;

        if (inventory[0].stackSize <= 0)
        {
            inventory[0] = null;
        }

        if (material == 2)
        {
            percentage += 10;
        }
        else
        {
            percentage += 5;
        }

        if (percentage < 100)
        {
            if (inventory[3] == null)
            {
                inventory[3] = new ItemStack(Ic2Items.cell.getItem());
            }
            else
            {
                inventory[3].stackSize++;
            }

            return;
        }

        if (inventory[2] == null)
        {
            inventory[2] = itemStack.copy();
        }
        else
        {
            inventory[2].stackSize += itemStack.stackSize;
        }

        percentage = 0;
        material = 0;
    }

    @Override
    public boolean canOperate()
    {
        if (inventory[3] != null)
        {
            if (inventory[3].itemID != Ic2Items.cell.itemID)
            {
                return false;
            }

            if (inventory[3].stackSize >= 64)
            {
                return false;
            }
        }

        if (inventory[0] == null)
        {
            return false;
        }

        if (material == 2 && inventory[0].itemID != Ic2Items.waterCell.itemID)
        {
            return false;
        }

        if (material == 1 && inventory[0].itemID != mod_RocketScience.itemLithiumCell.shiftedIndex)
        {
            return false;
        }
        else
        {
            return super.canOperate();
        }
    }

    public int getMaterial(int i)
    {
        return this.material;
    }

    @Override
    public ItemStack getResultFor(ItemStack itemStack, boolean var2)
    {
        if (itemStack.itemID == Ic2Items.waterCell.itemID)
        {
            return new ItemStack(mod_RocketScience.itemDeuteriumCell);
        }

        if (itemStack.itemID == mod_RocketScience.itemLithiumCell.shiftedIndex)
        {
            return new ItemStack(mod_RocketScience.itemLithium6Cell);
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean canUpdate()
    {
        return true;
    }
}
