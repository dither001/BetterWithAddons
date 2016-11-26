package betterwithaddons.interaction;

import betterwithaddons.BetterWithAddons;
import betterwithaddons.block.ModBlocks;
import betterwithaddons.item.ModItems;
import betterwithmods.BWMBlocks;
import betterwithmods.api.BWMRecipeHelper;
import betterwithmods.blocks.BlockBUD;
import betterwithmods.blocks.BlockRope;
import betterwithmods.blocks.BlockUrn;
import betterwithmods.craft.bulk.CraftingManagerCauldron;
import betterwithmods.items.ItemMaterial;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.List;

public class InteractionBWM implements IInteraction {
    final String modid = "betterwithmods";
    public static boolean ENABLED = true;
    public static boolean MILL_CLAY = true;
    public static boolean CHORUS_IN_CAULDRON = true;


    @Override
    public boolean isActive() {
        return ENABLED && Loader.isModLoaded(modid);
    }

    @Override
    public void setEnabled(boolean active) {
        ENABLED = active;
    }

    @Override
    public List<IInteraction> getDependencies() {
        return null;
    }

    @Override
    public List<IInteraction> getIncompatibilities() {
        return null;
    }

    @Override
    public void preInit() {

    }

    @Override
    public void init() {
        if(!isActive())
            return;

        BlockBUD.addBlacklistBlock(ModBlocks.pcbwire);

        ItemStack arrowhead = ModItems.material.getMaterial("arrowhead");
        ItemStack haft = ItemMaterial.getMaterial("haft");
        ItemStack string = new ItemStack(BWMBlocks.ROPE);
        ItemStack feather = new ItemStack(Items.FEATHER);
        String oreIronIngot = "ingotIron";
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.material.getMaterial("arrowhead")," o ","ooo","o o",'o',"nuggetSoulforgedSteel"));
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.greatarrow,1),"a","b","c",'a',arrowhead,'b',haft,'c',feather);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.greatbow,1)," bc","b c"," bc",'b',haft,'c',string));
        BWMRecipeHelper.addStokedCauldronRecipe(new ItemStack(ModBlocks.pcbblock),null,new Object[] { new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Blocks.STONEBRICK), "dustPotash" });
        BWMRecipeHelper.addCauldronRecipe(new ItemStack(ModItems.cookedBeetroot),null,new Object[] { new ItemStack(Items.BEETROOT) });
        BWMRecipeHelper.addCauldronRecipe(new ItemStack(ModItems.cookedCarrot),null,new Object[] { new ItemStack(Items.CARROT) });
        BWMRecipeHelper.addCauldronRecipe(new ItemStack(ModItems.cookedPotato),null,new Object[] { new ItemStack(Items.POTATO) });
        BWMRecipeHelper.addCauldronRecipe(new ItemStack(ModItems.cookedEgg),null,new Object[] { new ItemStack(Items.EGG) });

        BWMRecipeHelper.addCauldronRecipe(ModItems.material.getMaterial("bone_ingot"),null,new Object[] { new ItemStack(Items.BONE,2),new ItemStack(Items.DYE,8,15) });
        BWMRecipeHelper.addCauldronRecipe(ModItems.material.getMaterial("midori_popped"),null,new Object[] { ModItems.material.getMaterial("midori") });
        BWMRecipeHelper.addCauldronRecipe(new ItemStack(ModItems.meatballs),null,new Object[] { new ItemStack(ModItems.groundMeat,3) });
        BWMRecipeHelper.addMillRecipe(new ItemStack(ModItems.groundMeat,3),null,new Object[] { new ItemStack(Items.BEEF) });
        BWMRecipeHelper.addMillRecipe(new ItemStack(ModItems.groundMeat,2),null,new Object[] { new ItemStack(Items.MUTTON) });
        BWMRecipeHelper.addMillRecipe(new ItemStack(ModItems.groundMeat,1),null,new Object[] { new ItemStack(Items.CHICKEN) });
        BWMRecipeHelper.addMillRecipe(new ItemStack(ModItems.groundMeat,3),null,new Object[] { new ItemStack(Items.PORKCHOP) });
        BWMRecipeHelper.addMillRecipe(new ItemStack(ModItems.groundMeat,1),null,new Object[] { new ItemStack(Items.RABBIT) });

        BWMRecipeHelper.addMillRecipe(new ItemStack(ModBlocks.worldScale,1),null,new Object[] { new ItemStack(ModBlocks.worldScaleOre,1,1) });

        //Thorn Vines
        ItemStack rosebush = new ItemStack(Blocks.DOUBLE_PLANT, 4, BlockDoublePlant.EnumPlantType.ROSE.getMeta());
        ItemStack thornrose = ModItems.material.getMaterial("thornrose",2);
        ItemStack soulurn = new ItemStack(BWMBlocks.URN,1,BlockUrn.EnumUrnType.FULL.getMeta());
        ItemStack cactus = new ItemStack(Blocks.CACTUS,1);
        ItemStack dung = ItemMaterial.getMaterial("dung",1);
        BWMRecipeHelper.addCauldronRecipe(new ItemStack(ModBlocks.thornrose),null,new Object[] {cactus,rosebush,dung,soulurn});
        BWMRecipeHelper.addCauldronRecipe(new ItemStack(ModBlocks.thornrose),null,new Object[] {cactus,thornrose,dung,soulurn});

        if(MILL_CLAY) {
            BWMRecipeHelper.addMillRecipe(new ItemStack(Items.BRICK, 4),null,new Object[] { new ItemStack(Blocks.HARDENED_CLAY, 1) });

            EnumDyeColor[] dyes = EnumDyeColor.values();
            int len = dyes.length;

            for (int i = 0; i < len; ++i) {
                EnumDyeColor dye = dyes[i];
                ItemStack brick = new ItemStack(ModItems.stainedBrick, 1, dye.getMetadata());
                BWMRecipeHelper.addMillRecipe(new ItemStack(ModItems.stainedBrick, 4, dye.getMetadata()), null, new Object[]{new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, dye.getMetadata())});
                GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.coloredBrick, 1, dye.getMetadata()), "bb", "bb", 'b', brick);
            }
        }

        if(CHORUS_IN_CAULDRON)
            BetterWithAddons.instance.removeSmeltingRecipe(new ItemStack(Items.CHORUS_FRUIT_POPPED));
        else
            GameRegistry.addSmelting(ModItems.material.getMaterial("midori"),ModItems.material.getMaterial("midori_popped"),0.1f);

        fixRecipes();
    }

    @Override
    public void postInit() {
        //Fixes baked stuff showing up in the cauldron
        removeCauldronRecipe(new ItemStack(Items.BAKED_POTATO));
        removeCauldronRecipe(new ItemStack(ModItems.bakedCarrot));
        removeCauldronRecipe(new ItemStack(ModItems.bakedBeetroot));
    }

    public void fixRecipes()
    {
        /*ItemStack ingotSoulforgedSteel = InteractionHelper.findItem(modid,"material",1,14);
        ItemStack ingotIron = new ItemStack(Items.IRON_INGOT,1);
        ItemStack nuggedSoulforgedSteel = InteractionHelper.findItem(modid,"material",9,31);
        ItemStack nuggetIron = InteractionHelper.findItem(modid,"material",9,30);
        registerCompressRecipe(nuggedSoulforgedSteel,ingotSoulforgedSteel,"nuggetSoulforgedSteel","ingotSoulforgedSteel");
        registerCompressRecipe(nuggetIron,ingotIron,"nuggetIron","ingotIron");*/
    }

    private static void registerCompressRecipe(ItemStack small, ItemStack big, String oreSmall, String oreBig) {
        // ingot -> block
        GameRegistry.addRecipe(new ShapedOreRecipe(big, "###", "###", "###", '#', oreSmall));
        // block -> 9 ingot
        small = small.copy();
        small.stackSize = 9;
        GameRegistry.addRecipe(new ShapelessOreRecipe(small, oreBig));
    }

    private static void removeCauldronRecipe(ItemStack output)
    {
        CraftingManagerCauldron.getInstance().getRecipes().removeIf(r -> r.getOutput().isItemEqual(output));
    }
}
