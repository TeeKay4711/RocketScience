package ic2.rocketScience.platform;

import ic2.common.EntityDynamite;
import ic2.common.EntityIC2Explosive;
import ic2.common.EntityMiningLaser;
import ic2.common.IC2Achievements;
import ic2.common.IC2DamageSource;
import ic2.platform.Keyboard;
import ic2.platform.Platform;
import ic2.platform.RenderBlockCable;
import ic2.platform.RenderBlockCrop;
import ic2.platform.RenderBlockFence;
import ic2.platform.RenderBlockLuminator;
import ic2.platform.RenderBlockMiningPipe;
import ic2.platform.RenderCrossed;
import ic2.platform.RenderExplosiveBlock;
import ic2.platform.RenderFlyingItem;
import ic2.rocketScience.mod_RocketScience;
import ic2.rocketScience.entities.EntityDefenseLaser;
import ic2.rocketScience.entities.EntityMissileBooster;
import ic2.rocketScience.entities.EntityMissileMinerBooster;
import ic2.rocketScience.entities.EntityMissilePassengerBooster;
import ic2.rocketScience.entities.EntityMissilePassengerWarhead;
import ic2.rocketScience.entities.EntityMissileWarhead;
import ic2.rocketScience.entities.EntityParachute;
import ic2.rocketScience.renderers.RenderDefenseLaser;
import ic2.rocketScience.renderers.RenderMissile;
import ic2.rocketScience.renderers.RenderMissileMinerBooster;
import ic2.rocketScience.renderers.RenderMissilePassenger;
import ic2.rocketScience.renderers.RenderMissilePassengerWarhead;
import ic2.rocketScience.renderers.RenderMissileWarhead;
import ic2.rocketScience.renderers.RenderParachute;

import java.io.File;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.mod_IC2;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForgeClient;
import net.minecraft.src.forge.NetworkMod;

public class RocketScience extends NetworkMod {

    @Override
    public String getVersion()
    {
        return "v0.89 Rewrite20120704";
    }

    @Override
    public void load() {
        MinecraftForgeClient.preloadTexture("/ic2/rocketScience/gfx/blocks.png");
        MinecraftForgeClient.preloadTexture("/ic2/rocketScience/gfx/items.png");
        MinecraftForgeClient.preloadTexture("/ic2/rocketScience/gfx/MissileModel.png");

        Configuration config;
        
        try
        {
            config = new Configuration(new File(Platform.getMinecraftDir(), "/config/RocketScience.lang"));
            config.load();
        }
        catch (Exception var3)
        {
            System.out.println("[RocketScience] Error while trying to access language file!");
            config = null;
        }
        this.addLocalization(config, "blockMachine.name", "Machine Block");
        this.addLocalization(config, "blockIronFurnace.name", "Iron Furnace");
        this.addLocalization(config, "blockElecFurnace.name", "Electric Furnace");
        this.addLocalization(config, "blockMacerator.name", "Macerator");
        this.addLocalization(config, "blockExtractor.name", "Extractor");
        this.addLocalization(config, "blockCompressor.name", "Compressor");
        this.addLocalization(config, "blockCanner.name", "Canning Machine");
        this.addLocalization(config, "blockMiner.name", "Miner");
        this.addLocalization(config, "blockPump.name", "Pump");
        this.addLocalization(config, "blockMagnetizer.name", "Magnetizer");
        this.addLocalization(config, "blockElectrolyzer.name", "Electrolyzer");
        this.addLocalization(config, "blockRecycler.name", "Recycler");
        this.addLocalization(config, "blockAdvMachine.name", "Advanced Machine Block");
        this.addLocalization(config, "blockInduction.name", "Induction Furnace");
        this.addLocalization(config, "blockMatter.name", "Mass Fabricator");
        this.addLocalization(config, "blockTerra.name", "Terraformer");
        this.addLocalization(config, "tile.blockOreCopper.name", "Copper Ore");
        this.addLocalization(config, "tile.blockOreTin.name", "Tin Ore");
        this.addLocalization(config, "tile.blockOreUran.name", "Uranium Ore");
        this.addLocalization(config, "blockGenerator.name", "Generator");
        this.addLocalization(config, "blockGeoGenerator.name", "Geothermal Generator");
        this.addLocalization(config, "blockWaterGenerator.name", "Water Mill");
        this.addLocalization(config, "blockSolarGenerator.name", "Solar Panel");
        this.addLocalization(config, "blockWindGenerator.name", "Wind Mill");
        this.addLocalization(config, "blockNuclearReactor.name", "Nuclear Reactor");
        this.addLocalization(config, "tile.blockMiningPipe.name", "Mining Pipe");
        this.addLocalization(config, "tile.blockMiningTip.name", "Mining Pipe");
        this.addLocalization(config, "tile.blockRubWood.name", "Rubber Wood");
        this.addLocalization(config, "tile.blockRubSapling.name", "Rubber Tree Sapling");
        this.addLocalization(config, "tile.blockITNT.name", "Industrial TNT");
        this.addLocalization(config, "tile.blockNuke.name", "Nuke");
        this.addLocalization(config, "tile.blockRubber.name", "Rubber Sheet");
        this.addLocalization(config, "tile.blockReactorChamber.name", "Reactor Chamber");
        this.addLocalization(config, "tile.blockFenceIron.name", "Iron Fence");
        this.addLocalization(config, "tile.blockAlloy.name", "Reinforced Stone");
        this.addLocalization(config, "tile.blockAlloyGlass.name", "Reinforced Glass");
        this.addLocalization(config, "blockBatBox.name", "BatBox");
        this.addLocalization(config, "blockMFE.name", "MFE");
        this.addLocalization(config, "blockMFSU.name", "MFSU");
        this.addLocalization(config, "blockTransformerLV.name", "LV-Transformer");
        this.addLocalization(config, "blockTransformerMV.name", "MV-Transformer");
        this.addLocalization(config, "blockTransformerHV.name", "HV-Transformer");
        this.addLocalization(config, "tile.blockLuminator.name", "Luminator");
        this.addLocalization(config, "blockPersonalChest.name", "Personal Safe");
        this.addLocalization(config, "blockPersonalTrader.name", "Trade-O-Mat");
        this.addLocalization(config, "blockMetalCopper.name", "Copper Block");
        this.addLocalization(config, "blockMetalTin.name", "Tin Block");
        this.addLocalization(config, "blockMetalBronze.name", "Bronze Block");
        this.addLocalization(config, "blockMetalUranium.name", "Uranium Block");
        this.addLocalization(config, "blockTeleporter.name", "Teleporter");
        this.addLocalization(config, "blockTesla.name", "Tesla Coil");
        this.addLocalization(config, "tile.blockFoam.name", "Construction Foam");
        this.addLocalization(config, "tile.blockScaffold.name", "Scaffold");
        this.addLocalization(config, "tile.blockLuminatorD.name", "Luminator");
        this.addLocalization(config, "tile.blockCrop.name", "Crop");
        this.addLocalization(config, "blockCropmatron.name", "Crop-Matron");
        this.addLocalization(config, "tile.blockIronScaffold.name", "Iron Scaffold");
        this.addLocalization(config, "item.itemDustCoal.name", "Coal Dust");
        this.addLocalization(config, "item.itemDustIron.name", "Iron Dust");
        this.addLocalization(config, "item.itemDustGold.name", "Gold Dust");
        this.addLocalization(config, "item.itemDustCopper.name", "Copper Dust");
        this.addLocalization(config, "item.itemDustTin.name", "Tin Dust");
        this.addLocalization(config, "item.itemDustBronze.name", "Bronze Dust");
        this.addLocalization(config, "item.itemDustIronSmall.name", "Small Pile of Iron Dust");
        this.addLocalization(config, "item.itemIngotAdvIron.name", "Refined Iron");
        this.addLocalization(config, "item.itemIngotCopper.name", "Copper");
        this.addLocalization(config, "item.itemIngotTin.name", "Tin");
        this.addLocalization(config, "item.itemIngotBronze.name", "Bronze");
        this.addLocalization(config, "item.itemIngotAlloy.name", "Mixed Metal Ingot");
        this.addLocalization(config, "item.itemIngotUran.name", "Refined Uranium");
        this.addLocalization(config, "item.itemOreUran.name", "Uranium Ore");
        this.addLocalization(config, "item.itemBatRE.name", "RE-Battery");
        this.addLocalization(config, "item.itemBatSU.name", "Single-Use Battery");
        this.addLocalization(config, "item.itemBatCrystal.name", "Energy Crystal");
        this.addLocalization(config, "item.itemBatLamaCrystal.name", "Lapotron Crystal");
        this.addLocalization(config, "item.itemCellEmpty.name", "Empty Cell");
        this.addLocalization(config, "item.itemCellLava.name", "Lava Cell");
        this.addLocalization(config, "item.itemToolDrill.name", "Mining Drill");
        this.addLocalization(config, "item.itemToolDDrill.name", "Diamond Drill");
        this.addLocalization(config, "item.itemToolChainsaw.name", "Chainsaw");
        this.addLocalization(config, "item.itemFuelCan.name", "Filled Fuel Can");
        this.addLocalization(config, "item.itemFuelCanEmpty.name", "(Empty) Fuel Can");
        this.addLocalization(config, "item.itemCellCoal.name", "H. Coal Cell");
        this.addLocalization(config, "item.itemCellCoalRef.name", "Coalfuel Cell");
        this.addLocalization(config, "item.itemCellBio.name", "Bio Cell");
        this.addLocalization(config, "item.itemCellBioRef.name", "Biofuel Cell");
        this.addLocalization(config, "item.itemFuelCoalDust.name", "Hydrated Coal Dust");
        this.addLocalization(config, "item.itemFuelCoalCmpr.name", "H. Coal");
        this.addLocalization(config, "item.itemFuelPlantBall.name", "Plantball");
        this.addLocalization(config, "item.itemFuelPlantCmpr.name", "Compressed Plants");
        this.addLocalization(config, "item.itemTinCan.name", "Tin Can");
        this.addLocalization(config, "item.itemTinCanFilled.name", "(Filled) Tin Can");
        this.addLocalization(config, "item.itemScanner.name", "OD Scanner");
        this.addLocalization(config, "item.itemScannerAdv.name", "OV Scanner");
        this.addLocalization(config, "item.itemCellWater.name", "Water Cell");
        this.addLocalization(config, "item.itemHarz.name", "Sticky Resin");
        this.addLocalization(config, "item.itemRubber.name", "Rubber");
        this.addLocalization(config, "item.itemDynamite.name", "Dynamite");
        this.addLocalization(config, "item.itemDynamiteSticky.name", "Sticky Dynamite");
        this.addLocalization(config, "item.itemRemote.name", "Dynamite-O-Mote");
        this.addLocalization(config, "item.itemTreetap.name", "Treetap");
        this.addLocalization(config, "item.itemArmorRubBoots.name", "Rubber Boots");
        this.addLocalization(config, "item.itemArmorJetpack.name", "Jetpack");
        this.addLocalization(config, "item.itemArmorJetpackElectric.name", "Electric Jetpack");
        this.addLocalization(config, "item.itemToolMiningLaser.name", "Mining Laser");
        this.addLocalization(config, "item.itemCellUran.name", "Uranium Cell");
        this.addLocalization(config, "item.itemCellCoolant.name", "Coolant Cell");
        this.addLocalization(config, "item.itemReactorPlating.name", "Integrated Reactor Plating");
        this.addLocalization(config, "item.itemReactorCooler.name", "Integrated Heat Disperser");
        this.addLocalization(config, "item.itemCellUranDepleted.name", "Depleted Isotope Cell");
        this.addLocalization(config, "item.itemCellUranEnriched.name", "Re-Enriched Uranium Cell");
        this.addLocalization(config, "item.itemCellUranEmpty.name", "Near-depleted Uranium Cell");
        this.addLocalization(config, "item.itemToolBronzePickaxe.name", "Bronze Pickaxe");
        this.addLocalization(config, "item.itemToolBronzeAxe.name", "Bronze Axe");
        this.addLocalization(config, "item.itemToolBronzeSword.name", "Bronze Sword");
        this.addLocalization(config, "item.itemToolBronzeSpade.name", "Bronze Shovel");
        this.addLocalization(config, "item.itemToolBronzeHoe.name", "Bronze Hoe");
        this.addLocalization(config, "item.itemArmorBronzeHelmet.name", "Bronze Helmet");
        this.addLocalization(config, "item.itemArmorBronzeChestplate.name", "Bronze Chestplate");
        this.addLocalization(config, "item.itemArmorBronzeLegs.name", "Bronze Legs");
        this.addLocalization(config, "item.itemArmorBronzeBoots.name", "Bronze Boots");
        this.addLocalization(config, "item.itemPartCircuit.name", "Electronic Circuit");
        this.addLocalization(config, "item.itemPartCircuitAdv.name", "Advanced Circuit");
        this.addLocalization(config, "item.itemPartAlloy.name", "Advanced Alloy");
        this.addLocalization(config, "item.itemScrap.name", "Scrap");
        this.addLocalization(config, "item.itemMatter.name", "UU-Matter");
        this.addLocalization(config, "item.itemCoin.name", "Industrial Credit");
        this.addLocalization(config, "item.itemDoorAlloy.name", "Reinforced Door");
        this.addLocalization(config, "itemCable.name", "Copper Cable");
        this.addLocalization(config, "itemCableO.name", "Uninsulated Copper Cable");
        this.addLocalization(config, "itemGoldCable.name", "Gold Cable");
        this.addLocalization(config, "itemGoldCableI.name", "Insulated Gold Cable");
        this.addLocalization(config, "itemGoldCableII.name", "2xIns. Gold Cable");
        this.addLocalization(config, "itemIronCable.name", "HV Cable");
        this.addLocalization(config, "itemIronCableI.name", "Insulated HV Cable");
        this.addLocalization(config, "itemIronCableII.name", "2xIns. HV Cable");
        this.addLocalization(config, "itemIronCableIIII.name", "4xIns. HV Cable");
        this.addLocalization(config, "itemGlassCable.name", "Glass Fibre Cable");
        this.addLocalization(config, "itemTinCable.name", "Ultra-Low-Current Cable");
        this.addLocalization(config, "itemDetectorCable.name", "EU-Detector Cable");
        this.addLocalization(config, "itemSplitterCable.name", "EU-Splitter Cable");
        this.addLocalization(config, "item.itemToolWrench.name", "Wrench");
        this.addLocalization(config, "item.itemToolMeter.name", "EU-Reader");
        this.addLocalization(config, "item.itemCellWaterElectro.name", "Electrolyzed Water Cell");
        this.addLocalization(config, "item.itemArmorBatpack.name", "BatPack");
        this.addLocalization(config, "item.itemArmorAlloyChestplate.name", "Composite Vest");
        this.addLocalization(config, "item.itemArmorNanoHelmet.name", "NanoSuit Helmet");
        this.addLocalization(config, "item.itemArmorNanoChestplate.name", "NanoSuit Bodyarmor");
        this.addLocalization(config, "item.itemArmorNanoLegs.name", "NanoSuit Leggings");
        this.addLocalization(config, "item.itemArmorNanoBoots.name", "NanoSuit Boots");
        this.addLocalization(config, "item.itemArmorQuantumHelmet.name", "QuantumSuit Helmet");
        this.addLocalization(config, "item.itemArmorQuantumChestplate.name", "QuantumSuit Bodyarmor");
        this.addLocalization(config, "item.itemArmorQuantumLegs.name", "QuantumSuit Leggings");
        this.addLocalization(config, "item.itemArmorQuantumBoots.name", "QuantumSuit Boots");
        this.addLocalization(config, "item.itemToolPainter.name", "Painter");
        this.addLocalization(config, "item.itemToolCutter.name", "Insulation Cutter");
        this.addLocalization(config, "item.itemPartCarbonFibre.name", "Raw Carbon Fibre");
        this.addLocalization(config, "item.itemPartCarbonMesh.name", "Raw Carbon Mesh");
        this.addLocalization(config, "item.itemPartCarbonPlate.name", "Carbon Plate");
        this.addLocalization(config, "item.itemNanoSaber.name", "Nano Saber");
        this.addLocalization(config, "item.itemPartIridium.name", "Iridium Plate");
        this.addLocalization(config, "item.itemTFBP.name", "TFBP - Empty");
        this.addLocalization(config, "item.itemTFBPCultivation.name", "TFBP - Cultivation");
        this.addLocalization(config, "item.itemTFBPIrrigation.name", "TFBP - Irrigation");
        this.addLocalization(config, "item.itemTFBPDesertification.name", "TFBP - Desertification");
        this.addLocalization(config, "item.itemTFBPChilling.name", "TFBP - Chilling");
        this.addLocalization(config, "item.itemTFBPFlatification.name", "TFBP - Flatification");
        this.addLocalization(config, "item.itemTFBPMushroom.name", "TFBP - Mushroom");
        this.addLocalization(config, "item.itemToolWrenchElectric.name", "Electric Wrench");
        this.addLocalization(config, "item.itemTreetapElectric.name", "Electric Treetap");
        this.addLocalization(config, "item.itemScrapbox.name", "Scrap Box");
        this.addLocalization(config, "item.itemPartCoalBall.name", "Coal Ball");
        this.addLocalization(config, "item.itemPartCoalBlock.name", "Compressed Coal Ball");
        this.addLocalization(config, "item.itemPartCoalChunk.name", "Coal Chunk");
        this.addLocalization(config, "item.itemPartIndustrialDiamond.name", "Industrial Diamond");
        this.addLocalization(config, "item.itemFreq.name", "Frequency Transmitter");
        this.addLocalization(config, "item.itemDustClay.name", "Clay Dust");
        this.addLocalization(config, "item.itemPartPellet.name", "CF Pellet");
        this.addLocalization(config, "item.itemFoamSprayer.name", "CF Sprayer");
        this.addLocalization(config, "item.itemDustSilver.name", "Silver Dust");
        this.addLocalization(config, "item.itemArmorCFPack.name", "CF Backpack");
        this.addLocalization(config, "item.itemOreIridium.name", "Iridium Ore");
        this.addLocalization(config, "item.itemArmorLappack.name", "Lappack");
        this.addLocalization(config, "item.cropSeedUn.name", "Unknown Seeds");
        this.addLocalization(config, "item.cropSeedInvalid.name", "Seed is missing data - bug?");
        this.addLocalization(config, "item.itemCropnalyzer.name", "Cropnalyzer");
        this.addLocalization(config, "item.itemFertilizer.name", "Fertilizer");
        this.addLocalization(config, "item.itemCellHydrant.name", "Hydration Cell");
        this.addLocalization(config, "item.itemToolHoe.name", "Electric Hoe");
        this.addLocalization(config, "overclockerUpgrade.name", "Overclocker Upgrade");
        this.addLocalization(config, "transformerUpgrade.name", "Transformer Upgrade");
        this.addLocalization(config, "energyStorageUpgrade.name", "Energy Storage Upgrade");
        this.addLocalization(config, "item.itemToolbox.name", "Tool Box");
        this.addLocalization(config, "item.itemSolarHelmet.name", "Solar Helmet");
        this.addLocalization(config, "item.itemStaticBoots.name", "Static Boots");
        this.addLocalization(config, "item.itemTerraWart.name", "Terra Wart");
        this.addLocalization(config, "item.itemCoffeeBeans.name", "Coffee Beans");
        this.addLocalization(config, "item.itemCoffeePowder.name", "Coffee Powder");
        this.addLocalization(config, "item.itemMugEmpty.name", "Stone Mug");
        this.addLocalization(config, "item.itemMugCoffee0.name", "Cold Coffee");
        this.addLocalization(config, "item.itemMugCoffee1.name", "Dark Coffee");
        this.addLocalization(config, "item.itemMugCoffee2.name", "Coffee");
        this.addLocalization(config, "item.itemHops.name", "Hops");
        this.addLocalization(config, "item.itemGrinPowder.name", "Grin Powder");
        this.addLocalization(config, "item.itemWeedEx.name", "Weed-EX");
        this.addLocalization(config, "container.electricBlock.level", "Power Level:");
        this.addLocalization(config, "container.electricBlock.output", "Out: %1$s EU/t");
        this.addLocalization(config, "container.induction.heat", "Heat:");
        this.addLocalization(config, "container.matter.progress", "Progress:");
        this.addLocalization(config, "container.matter.amplifier", "Amplifier:");
        this.addLocalization(config, "container.personalTrader.want", "Want:");
        this.addLocalization(config, "container.personalTrader.offer", "Offer:");
        this.addLocalization(config, "container.personalTrader.totalTrades0", "Performed");
        this.addLocalization(config, "container.personalTrader.totalTrades1", "Trades:");

        //IC2Achievements.addLocalization(config, this);
        //IC2DamageSource.addLocalization(config, this);
        //Keyboard.registerKeys(this);

        mod_RocketScience.missileModelID = ModLoader.getUniqueBlockModelID(this, true);
        mod_RocketScience.warheadModelID = ModLoader.getUniqueBlockModelID(this, true);
        mod_RocketScience.machineModelID = ModLoader.getUniqueBlockModelID(this, true);

        if (config != null)
        {
            config.save();
        }
    }

    public void addLocalization(Configuration config, String var2, String var3)
    {
        String var4 = var3;

        if (config != null)
        {
            var4 = config.getOrCreateProperty(var2, "general", var3).value;
        }

        ModLoader.addLocalization(var2, var4);
    }

    @Override
    public void addRenderer(Map map)
    {
        map.put(EntityMissileBooster.class, new RenderMissile(1));
        map.put(EntityMissileWarhead.class, new RenderMissileWarhead(1));
        map.put(EntityMissilePassengerBooster.class, new RenderMissilePassenger(1));
        map.put(EntityMissilePassengerWarhead.class, new RenderMissilePassengerWarhead(1));
        map.put(EntityParachute.class, new RenderParachute(1));
        map.put(EntityMissileMinerBooster.class, new RenderMissileMinerBooster(1));
        map.put(EntityDefenseLaser.class, new RenderDefenseLaser());
    }

    @Override
    public void renderInvBlock(RenderBlocks renderer, Block block, int metadata, int modelID)
    {
        //System.out.println("renderInvBlock "+modelID+" "+missileModelID+" "+warheadModelID+" "+machineModelID);
        if (modelID == mod_RocketScience.missileModelID)
        {
            RenderMissileInInv(renderer, block, metadata);
        }
        else if (modelID == mod_RocketScience.warheadModelID)
        {
            RenderMissileInInv(renderer, block, metadata);
        }
        else if (modelID == mod_RocketScience.machineModelID)
        {
            RenderMachineInInv(renderer, block, metadata);
        }
    }

    @Override
    public boolean renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int x, int y, int z, Block block, int modelId)
    {
        //System.out.println("renderWorldBlock");
	if (modelId == mod_RocketScience.missileModelID)
        {
            return RenderMissileInWorld(renderblocks, iblockaccess, x, y, z, block);
        }
        else if (modelId == mod_RocketScience.warheadModelID)
        {
            return RenderWarheadInWorld(renderblocks, iblockaccess, x, y, z, block);
        }
        else if (modelId == mod_RocketScience.machineModelID)
        {
            return RenderMachineInWorld(renderblocks, iblockaccess, x, y, z, block);
        }

        return false;
    }

    private boolean RenderMissileInWorld(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block)
    {
        block.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, .9375F);
        renderer.renderStandardBlock(block, x, y, z);
        block.setBlockBounds(0.4375F, 0.75F, 0.0625F, 0.5625F, 0F, 0F);
        renderer.renderStandardBlock(block, x, y, z);
        block.setBlockBounds(0.4375F, 0.75F, 1.0F, 0.5625F, 0F, 0.9375F);
        renderer.renderStandardBlock(block, x, y, z);
        block.setBlockBounds(1.0F, 0.75F, 0.4375F, 0.9375F, 0F, 0.5625F);
        renderer.renderStandardBlock(block, x, y, z);
        block.setBlockBounds(.0625F, 0.75F, 0.4375F, 0F, 0F, 0.5625F);
        renderer.renderStandardBlock(block, x, y, z);
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return true;
    }

    private boolean RenderWarheadInWorld(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block)
    {
        if (world.getBlockMetadata(x, y, z) != 12 && world.getBlockMetadata(x, y, z) != 0 && world.getBlockMetadata(x, y, z) != 15)
        {
            block.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.7F, .9F);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0.2F, 0.7F, 0.2F, 0.8F, 0.9F, 0.8F);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0.3F, 0.9F, 0.3F, 0.7F, 1F, 0.7F);
            renderer.renderStandardBlock(block, x, y, z);
        }
        else
        {
            block.setBlockBounds(0.0F, 0.0F, 0F, 1F, 0.1F, 1F);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0F, 0.1F, 0F, 1F, 1F, .1F);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0F, 0.1F, 0.9F, 1F, 1F, 1F);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0F, 0.1F, 0.1F, 0.1F, 1F, 0.9F);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0.9F, 0.1F, 0.1F, 1F, 1F, .9F);
            renderer.renderStandardBlock(block, x, y, z);
        }

        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return true;
    }

    private boolean RenderMachineInWorld(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block)
    {
        //System.out.println("RederMachineInWorld");
        if (world.getBlockMetadata(x, y, z) < 4)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 1F, 1F);
            renderer.renderStandardBlock(block, x, y, z);
        }
        else if (world.getBlockMetadata(x, y, z) == 4)
        {
            block.setBlockBounds(0, 0, 0, 1, 0.5f, 1);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0.25f, 0.5f, 0.25f, 0.75f, 0.625f, 0.75f);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0.625f, 0.625f, 0.625f, 0.375f, 1, 0.375f);
            renderer.renderStandardBlock(block, x, y, z);
        }
        else if (world.getBlockMetadata(x, y, z) == 5)
        {
            block.setBlockBounds(0, 0, 0, 1, 0.125f, 1);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0.625f, .125f, 0.625f, 0.375f, 0.375f, 0.375f);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0, 0.375f, 0, 1, 0.5f, 1);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0, 0.5f, 0.875f, 1, 0.875f, 1);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0, 0.5f, 0, 1, 0.875f, 0.125f);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0, 0.5f, 0.875f, 1, 0.875f, 1);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0, 0.5f, 0.125f, 0.125f, 0.875f, 0.875f);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0.875f, 0.5f, 0.125f, 1, 0.875f, 0.875f);
            renderer.renderStandardBlock(block, x, y, z);
            block.setBlockBounds(0.375f, 0.5f, 0.375f, 0.625f, 1, 0.625f);
            renderer.renderStandardBlock(block, x, y, z);
        }
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 1F, 1F);
        return true;
    }

    private void RenderMissileInInv(RenderBlocks renderer, Block block, int metadata)
    {
        Tessellator tesselator = Tessellator.instance;
        int sideTex = 0;
        int topTex = 0;

        for (int i = 0; i < 6; i++)
        {
            //Get bounds for each rectangle
            if (i == 0)
            {
                block.setBlockBounds(0.4F, 0.95F, 0.4F, 0.6F, 1.0F, 0.6F);
            }
            else if (i == 1)
            {
                block.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.95F, 0.7F);
            }
            else if (i == 2)
            {
                block.setBlockBounds(0.4F, 0F, 0.1F, 0.6F, 0.4F, 0.3F);
            }
            else if (i == 3)
            {
                block.setBlockBounds(0.4F, 0F, 0.7F, 0.6F, 0.4F, 0.9F);
            }
            else if (i == 4)
            {
                block.setBlockBounds(0.1F, 0F, 0.4F, 0.3F, 0.4F, 0.6F);
            }
            else if (i == 5)
            {
                block.setBlockBounds(0.7F, 0F, 0.4F, 0.9F, 0.4F, 0.6F);
            }

            //Get textures
            if (i == 0 && metadata != 0 && metadata != 15)
            {
                topTex = 15;
                sideTex = block.getBlockTextureFromSideAndMetadata(1, metadata);
            }
            else if (i == 0)
            {
                topTex = 18;
                sideTex = block.getBlockTextureFromSideAndMetadata(1, metadata);
            }
            else if (i == 1 && metadata != 0 && metadata != 15)
            {
                sideTex = block.getBlockTextureFromSideAndMetadata(1, 1);
                topTex = block.getBlockTextureFromSideAndMetadata(1, 1);
            }
            else if (i == 1)
            {
                sideTex = block.getBlockTextureFromSideAndMetadata(1, metadata);
                topTex = block.getBlockTextureFromSideAndMetadata(1, metadata);
            }
            else if (metadata != 0 && metadata != 15)
            {
                sideTex = topTex = 15;
            }
            else
            {
                sideTex = topTex = 18;
            }

            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            tesselator.startDrawingQuads();
            tesselator.setNormal(0.0F, -1.0F, 0.0F);
            renderer.renderBottomFace(block, 0.0D, 0.0D, 0.0D, sideTex);
            tesselator.draw();
            tesselator.startDrawingQuads();
            tesselator.setNormal(0.0F, 1.0F, 0.0F);
            renderer.renderTopFace(block, 0.0D, 0.0D, 0.0D, topTex);
            tesselator.draw();
            tesselator.startDrawingQuads();
            tesselator.setNormal(0.0F, 0.0F, -1.0F);
            renderer.renderEastFace(block, 0.0D, 0.0D, 0.0D, sideTex);
            tesselator.draw();
            tesselator.startDrawingQuads();
            tesselator.setNormal(0.0F, 0.0F, 1.0F);
            renderer.renderWestFace(block, 0.0D, 0.0D, 0.0D, sideTex);
            tesselator.draw();
            tesselator.startDrawingQuads();
            tesselator.setNormal(-1.0F, 0.0F, 0.0F);
            renderer.renderNorthFace(block, 0.0D, 0.0D, 0.0D, sideTex);
            tesselator.draw();
            tesselator.startDrawingQuads();
            tesselator.setNormal(1.0F, 0.0F, 0.0F);
            renderer.renderSouthFace(block, 0.0D, 0.0D, 0.0D, sideTex);
            tesselator.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }

        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    private void RenderMachineInInv(RenderBlocks renderer, Block block, int metadata)
    {
        Tessellator tesselator = Tessellator.instance;
        block.setBlockBounds(0, 0, 0, 1, 1, 1);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tesselator.startDrawingQuads();
        tesselator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(0, metadata));
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, metadata));
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, metadata));
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, metadata));
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, metadata));
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, metadata));
        tesselator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    @Override
    public boolean onTickInGame(float tickTime, Minecraft client)
    {
        return mod_RocketScience.OnTickInGame(client.theWorld.playerEntities, client.theWorld);
    }

}
