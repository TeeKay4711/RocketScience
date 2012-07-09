package ic2.rocketScience.gui;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import ic2.rocketScience.containers.ContainerDummy;
import ic2.rocketScience.tileEntities.TileEntityMissile;
import ic2.rocketScience.tileEntities.TileEntityOffense;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.TileEntity;

public class GuiOffense extends GuiContainer
{

    TileEntityOffense offense;

    public GuiOffense(InventoryPlayer inventoryplayer, TileEntityOffense off)
    {
        super(new ContainerDummy(inventoryplayer, off));
        offense = off;
    }

    public void initGui()
    {
        super.initGui();
        controlList.clear();
        controlList.add(new GuiButtonInvisible(0, 141 + guiLeft, 47 + guiTop, 11, 11, ""));
        controlList.add(new GuiButtonInvisible(1, 37 + guiLeft, 68 + guiTop, 12, 12, ""));
        controlList.add(new GuiButtonInvisible(2, 50 + guiLeft, 68 + guiTop, 12, 12, ""));
        controlList.add(new GuiButtonInvisible(3, 63 + guiLeft, 68 + guiTop, 12, 12, ""));
        controlList.add(new GuiButtonInvisible(4, 105 + guiLeft, 68 + guiTop, 12, 12, ""));
        controlList.add(new GuiButtonInvisible(5, 118 + guiLeft, 68 + guiTop, 12, 12, ""));
        controlList.add(new GuiButtonInvisible(6, 131 + guiLeft, 68 + guiTop, 12, 12, ""));
        controlList.add(new GuiButtonInvisible(7, 37 + guiLeft, 90 + guiTop, 12, 12, ""));
        controlList.add(new GuiButtonInvisible(8, 50 + guiLeft, 90 + guiTop, 12, 12, ""));
        controlList.add(new GuiButtonInvisible(9, 63 + guiLeft, 90 + guiTop, 12, 12, ""));
        controlList.add(new GuiButtonInvisible(10, 105 + guiLeft, 90 + guiTop, 12, 12, ""));
        controlList.add(new GuiButtonInvisible(11, 118 + guiLeft, 90 + guiTop, 12, 12, ""));
        controlList.add(new GuiButtonInvisible(12, 131 + guiLeft, 90 + guiTop, 12, 12, ""));
    }

    protected void drawGuiContainerBackgroundLayer(float f, int secks, int vylence)
    {
        //xSize=143;
        //ySize=109;
        int i = mc.renderEngine.getTexture("/ic2/rocketScience/gfx/GUIOffense.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        //int j = (width - xSize) / 2;
        //int k = (height - ySize) / 2;
        int j = (width - 143) / 2;
        int k = (height - 109) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
    }

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString("Missile Targeting System", 30, 34, 0x404040);
        fontRenderer.drawString(offense.attachedMissiles.size() + "", 30, 49, 0x40FF40);
        fontRenderer.drawString("Missiles", 52, 48, 0x404040);
        fontRenderer.drawString("Launch", 104, 48, 0x404040);
        fontRenderer.drawString("X:", 22, 70, 0x404040);

        if (offense.ecks > 0)
        {
            fontRenderer.drawString(offense.ecks + "", 81, 71, 0x40FF40);
        }
        else
        {
            fontRenderer.drawString(offense.ecks + "", 81, 71, 0xFF4040);
        }

        fontRenderer.drawString("Y:", 22, 92, 0x404040);

        if (offense.zee > 0)
        {
            fontRenderer.drawString(offense.zee + "", 81, 93, 0x40FF40);
        }
        else
        {
            fontRenderer.drawString(offense.zee + "", 81, 93, 0xFF4040);
        }

        fontRenderer.drawString("GPS", 60, 115, 0x404040);
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton.id == 1)
        {
            offense.ecks -= 100;
        }
        else if (guibutton.id == 2)
        {
            offense.ecks -= 10;
        }
        else if (guibutton.id == 3)
        {
            offense.ecks--;
        }
        else if (guibutton.id == 4)
        {
            offense.ecks++;
        }
        else if (guibutton.id == 5)
        {
            offense.ecks += 10;
        }
        else if (guibutton.id == 6)
        {
            offense.ecks += 100;
        }
        else if (guibutton.id == 7)
        {
            offense.zee -= 100;
        }
        else if (guibutton.id == 8)
        {
            offense.zee -= 10;
        }
        else if (guibutton.id == 9)
        {
            offense.zee--;
        }
        else if (guibutton.id == 10)
        {
            offense.zee++;
        }
        else if (guibutton.id == 11)
        {
            offense.zee += 10;
        }
        else if (guibutton.id == 12)
        {
            offense.zee += 100;
        }

        if (offense.ecks > 999)
        {
            offense.ecks = 999;
        }
        else if (offense.ecks < -999)
        {
            offense.ecks = -999;
        }

        if (offense.zee > 999)
        {
            offense.zee = 999;
        }
        else if (offense.zee < -999)
        {
            offense.zee = -999;
        }

        Iterator it = offense.attachedMissiles.iterator();

        while (it.hasNext())
        {
            TileEntityMissile titty = (TileEntityMissile)it.next();
            titty.targetX = offense.ecks;
            titty.targetZ = offense.zee;
        }

        if (guibutton.id == 0)
        {
            offense.fireMissiles();
        }

        super.actionPerformed(guibutton);
    }
}
