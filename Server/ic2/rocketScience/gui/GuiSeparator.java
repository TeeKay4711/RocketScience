package ic2.rocketScience.gui;

import org.lwjgl.opengl.GL11;

import ic2.rocketScience.containers.ContainerSeparator;
import ic2.rocketScience.tileEntities.TileEntitySeparator;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.TileEntity;

public class GuiSeparator extends GuiContainer
{
    public TileEntitySeparator tileentity;

    public GuiSeparator(InventoryPlayer inventory, TileEntitySeparator tileentity)
    {
        super(new ContainerSeparator(inventory, tileentity));
        this.tileentity = tileentity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString("Isotopic Separator", 42, 6, 0x404040);

        if (tileentity.getMaterial(0) == 1)
        {
            fontRenderer.drawString("Li-6 extraction: " + tileentity.percentage + "%", 70, ySize - 96 + 2, 0x404040);
        }
        else if (tileentity.getMaterial(0) == 2)
        {
            fontRenderer.drawString("H-2 extraction: " + tileentity.percentage + "%", 70, ySize - 96 + 2, 0x404040);
        }

        fontRenderer.drawString("Inventory", 8, ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2,
            int var3)
    {
        int texture = mc.renderEngine.getTexture("/ic2/rocketScience/gfx/GuiSeparator.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(texture);
        int xx = (width - xSize) / 2;
        int yy = (height - ySize) / 2;
        drawTexturedModalRect(xx, yy, 0, 0, xSize, ySize);
        int charge;

        if (tileentity.energy > 0)
        {
            charge = (int)(14.0F * tileentity.getChargeLevel());
            drawTexturedModalRect(xx + 56, yy + 36 + 14 - charge, 176, 14 - charge, 14, charge);
        }

        int progress;
        progress = (int)(24.0F * tileentity.getProgress());
        drawTexturedModalRect(xx + 79, yy + 34, 176, 14, progress + 1, 16);
    }
}
