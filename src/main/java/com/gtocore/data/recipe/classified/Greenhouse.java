package com.gtocore.data.recipe.classified;

import com.gtolib.api.recipe.RecipeBuilder;
import com.gtolib.utils.RegistriesUtils;

import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import org.jetbrains.annotations.Nullable;

import static com.gtocore.common.data.GTORecipeTypes.GREENHOUSE_RECIPES;

final class Greenhouse {

    public static void init() {
        GREENHOUSE_RECIPES.recipeBuilder("azure_bluet")
                .notConsumable(new ItemStack(Blocks.AZURE_BLUET.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.AZURE_BLUET.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("blue_orchid_fertilizer")
                .notConsumable(new ItemStack(Blocks.BLUE_ORCHID.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.BLUE_ORCHID.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("sweet_berries_fertilizer")
                .notConsumable(new ItemStack(Blocks.SWEET_BERRY_BUSH.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.SWEET_BERRY_BUSH.asItem(), 32))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("red_tulip")
                .notConsumable(new ItemStack(Blocks.RED_TULIP.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.RED_TULIP.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("cactus_fertilizer")
                .notConsumable(new ItemStack(Blocks.CACTUS.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.CACTUS.asItem(), 24))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("wither_rose")
                .notConsumable(new ItemStack(Blocks.WITHER_ROSE.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.WITHER_ROSE.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("beetroot_seeds_fertilizer")
                .notConsumable(new ItemStack(Blocks.BEETROOTS.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Items.BEETROOT.asItem(), 32))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("spore_blossom_fertilizer")
                .notConsumable(new ItemStack(Blocks.SPORE_BLOSSOM.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.SPORE_BLOSSOM.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("orange_tulip_fertilizer")
                .notConsumable(new ItemStack(Blocks.ORANGE_TULIP.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.ORANGE_TULIP.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("lilac")
                .notConsumable(new ItemStack(Blocks.LILAC.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.LILAC.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("pink_petals_fertilizer")
                .notConsumable(new ItemStack(Blocks.PINK_PETALS.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.PINK_PETALS.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("sea_pickle")
                .notConsumable(new ItemStack(Blocks.SEA_PICKLE.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.SEA_PICKLE.asItem(), 16))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("pink_petals")
                .notConsumable(new ItemStack(Blocks.PINK_PETALS.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.PINK_PETALS.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("pitcher_plant")
                .notConsumable(new ItemStack(Blocks.PITCHER_PLANT.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.PITCHER_PLANT.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("orange_tulip")
                .notConsumable(new ItemStack(Blocks.ORANGE_TULIP.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.ORANGE_TULIP.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("carrot")
                .notConsumable(new ItemStack(Blocks.CARROTS.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.CARROTS.asItem(), 12))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("tall_grass")
                .notConsumable(new ItemStack(Blocks.TALL_GRASS.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.TALL_GRASS.asItem(), 12))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("dandelion")
                .notConsumable(new ItemStack(Blocks.DANDELION.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.DANDELION.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("torchflower")
                .notConsumable(new ItemStack(Items.TORCHFLOWER_SEEDS.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.TORCHFLOWER.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("nether_wart_fertilizer")
                .notConsumable(new ItemStack(Blocks.NETHER_WART.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.NETHER_WART.asItem(), 24))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("poppy")
                .notConsumable(new ItemStack(Blocks.POPPY.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.POPPY.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("oxeye_daisy_fertilizer")
                .notConsumable(new ItemStack(Blocks.OXEYE_DAISY.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.OXEYE_DAISY.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("carrot_fertilizer")
                .notConsumable(new ItemStack(Blocks.CARROTS.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.CARROTS.asItem(), 24))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("red_mushroom_fertilizer")
                .notConsumable(new ItemStack(Blocks.RED_MUSHROOM.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.RED_MUSHROOM.asItem(), 24))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("sugar_cane_fertilizer")
                .notConsumable(new ItemStack(Blocks.SUGAR_CANE.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.SUGAR_CANE.asItem(), 24))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("white_tulip")
                .notConsumable(new ItemStack(Blocks.WHITE_TULIP.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.WHITE_TULIP.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("sweet_berries")
                .notConsumable(new ItemStack(Blocks.SWEET_BERRY_BUSH.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.SWEET_BERRY_BUSH.asItem(), 16))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("lily_of_the_valley")
                .notConsumable(new ItemStack(Blocks.LILY_OF_THE_VALLEY.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.LILY_OF_THE_VALLEY.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("blue_orchid")
                .notConsumable(new ItemStack(Blocks.BLUE_ORCHID.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.BLUE_ORCHID.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("wheat_seeds_fertilizer")
                .notConsumable(new ItemStack(Blocks.WHEAT.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Items.WHEAT.asItem(), 32))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("rose_bush_fertilizer")
                .notConsumable(new ItemStack(Blocks.ROSE_BUSH.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.ROSE_BUSH.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("potato_fertilizer")
                .notConsumable(new ItemStack(Blocks.POTATOES.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.POTATOES.asItem(), 24))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("kelp_fertilizer")
                .notConsumable(new ItemStack(Blocks.KELP.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.KELP.asItem(), 24))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("pumpkin_seeds_fertilizer")
                .notConsumable(new ItemStack(Blocks.PUMPKIN_STEM.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.PUMPKIN.asItem(), 12))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("wither_rose_fertilizer")
                .notConsumable(new ItemStack(Blocks.WITHER_ROSE.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.WITHER_ROSE.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("wheat_seeds")
                .notConsumable(new ItemStack(Blocks.WHEAT.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Items.WHEAT.asItem(), 16))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("fern")
                .notConsumable(new ItemStack(Blocks.FERN.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.FERN.asItem(), 16))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("cocoa_beans_fertilizer")
                .notConsumable(new ItemStack(Blocks.COCOA.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.COCOA.asItem(), 24))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("sugar_cane")
                .notConsumable(new ItemStack(Blocks.SUGAR_CANE.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.SUGAR_CANE.asItem(), 12))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("allium_fertilizer")
                .notConsumable(new ItemStack(Blocks.ALLIUM.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.ALLIUM.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("glow_berries")
                .notConsumable(new ItemStack(Blocks.CAVE_VINES.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.CAVE_VINES.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("brown_mushroom_fertilizer")
                .notConsumable(new ItemStack(Blocks.BROWN_MUSHROOM.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.BROWN_MUSHROOM.asItem(), 24))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("sunflower")
                .notConsumable(new ItemStack(Blocks.SUNFLOWER.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.SUNFLOWER.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("brown_mushroom")
                .notConsumable(new ItemStack(Blocks.BROWN_MUSHROOM.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.BROWN_MUSHROOM.asItem(), 12))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("azure_bluet_fertilizer")
                .notConsumable(new ItemStack(Blocks.AZURE_BLUET.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.AZURE_BLUET.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("grass")
                .notConsumable(new ItemStack(Blocks.GRASS.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.GRASS.asItem(), 24))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("peony_fertilizer")
                .notConsumable(new ItemStack(Blocks.PEONY.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.PEONY.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("dandelion_fertilizer")
                .notConsumable(new ItemStack(Blocks.DANDELION.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.DANDELION.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("sunflower_fertilizer")
                .notConsumable(new ItemStack(Blocks.SUNFLOWER.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.SUNFLOWER.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("oxeye_daisy")
                .notConsumable(new ItemStack(Blocks.OXEYE_DAISY.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.OXEYE_DAISY.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("red_mushroom")
                .notConsumable(new ItemStack(Blocks.RED_MUSHROOM.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.RED_MUSHROOM.asItem(), 12))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("pumpkin_seeds")
                .notConsumable(new ItemStack(Blocks.PUMPKIN_STEM.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.PUMPKIN.asItem(), 6))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("bamboo")
                .notConsumable(new ItemStack(Blocks.BAMBOO.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.BAMBOO.asItem(), 16))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("glow_berries_fertilizer")
                .notConsumable(new ItemStack(Blocks.CAVE_VINES.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.CAVE_VINES.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("tall_grass_fertilizer")
                .notConsumable(new ItemStack(Blocks.TALL_GRASS.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.TALL_GRASS.asItem(), 24))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("lilac_fertilizer")
                .notConsumable(new ItemStack(Blocks.LILAC.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.LILAC.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("grass_fertilizer")
                .notConsumable(new ItemStack(Blocks.GRASS.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.GRASS.asItem(), 48))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("peony")
                .notConsumable(new ItemStack(Blocks.PEONY.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.PEONY.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("kelp")
                .notConsumable(new ItemStack(Blocks.KELP.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.KELP.asItem(), 12))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("melon_seeds")
                .notConsumable(new ItemStack(Blocks.MELON_STEM.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.MELON.asItem(), 6))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("potato")
                .notConsumable(new ItemStack(Blocks.POTATOES.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.POTATOES.asItem(), 12))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("allium")
                .notConsumable(new ItemStack(Blocks.ALLIUM.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.ALLIUM.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("pink_tulip_fertilizer")
                .notConsumable(new ItemStack(Blocks.PINK_TULIP.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.PINK_TULIP.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("rose_bush")
                .notConsumable(new ItemStack(Blocks.ROSE_BUSH.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.ROSE_BUSH.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("vine")
                .notConsumable(new ItemStack(Blocks.VINE.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.VINE.asItem(), 16))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("pink_tulip")
                .notConsumable(new ItemStack(Blocks.PINK_TULIP.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.PINK_TULIP.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("torchflower_fertilizer")
                .notConsumable(new ItemStack(Items.TORCHFLOWER_SEEDS.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.TORCHFLOWER.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("sea_pickle_fertilizer")
                .notConsumable(new ItemStack(Blocks.SEA_PICKLE.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.SEA_PICKLE.asItem(), 32))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("beetroot_seeds")
                .notConsumable(new ItemStack(Blocks.BEETROOTS.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Items.BEETROOT.asItem(), 16))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("poppy_fertilizer")
                .notConsumable(new ItemStack(Blocks.POPPY.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.POPPY.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("pitcher_plant_fertilizer")
                .notConsumable(new ItemStack(Blocks.PITCHER_PLANT.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.PITCHER_PLANT.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("large_fern")
                .notConsumable(new ItemStack(Blocks.LARGE_FERN.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.LARGE_FERN.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("white_tulip_fertilizer")
                .notConsumable(new ItemStack(Blocks.WHITE_TULIP.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.WHITE_TULIP.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("bamboo_fertilizer")
                .notConsumable(new ItemStack(Blocks.BAMBOO.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.BAMBOO.asItem(), 32))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("spore_blossom")
                .notConsumable(new ItemStack(Blocks.SPORE_BLOSSOM.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.SPORE_BLOSSOM.asItem(), 8))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("cocoa_beans")
                .notConsumable(new ItemStack(Blocks.COCOA.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.COCOA.asItem(), 12))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("large_fern_fertilizer")
                .notConsumable(new ItemStack(Blocks.LARGE_FERN.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.LARGE_FERN.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("vine_fertilizer")
                .notConsumable(new ItemStack(Blocks.VINE.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.VINE.asItem(), 32))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("nether_wart")
                .notConsumable(new ItemStack(Blocks.NETHER_WART.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.NETHER_WART.asItem(), 12))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("fern_fertilizer")
                .notConsumable(new ItemStack(Blocks.FERN.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.FERN.asItem(), 32))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("cactus")
                .notConsumable(new ItemStack(Blocks.CACTUS.asItem()))
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.CACTUS.asItem(), 12))
                .EUt(30)
                .duration(1200)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("lily_of_the_valley_fertilizer")
                .notConsumable(new ItemStack(Blocks.LILY_OF_THE_VALLEY.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.LILY_OF_THE_VALLEY.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("melon_seeds_fertilizer")
                .notConsumable(new ItemStack(Blocks.MELON_STEM.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.MELON.asItem(), 12))
                .EUt(60)
                .duration(400)
                .save();

        GREENHOUSE_RECIPES.recipeBuilder("red_tulip_fertilizer")
                .notConsumable(new ItemStack(Blocks.RED_TULIP.asItem()))
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(new ItemStack(Blocks.RED_TULIP.asItem(), 16))
                .EUt(60)
                .duration(400)
                .save();

        add("minecraft", "crimson_fungus", null, 8);
        add("minecraft", "warped_fungus", null, 8);

        add("farmersdelight", "cabbage", "cabbage_seeds", 8);
        add("farmersdelight", "tomato", "tomato_seeds", 8);
        add("farmersdelight", "onion", null, 8);
        add("farmersdelight", "rice_panicle", "rice", 8);

        add("farmersrespite", "coffee_berries", "coffee_beans", 8);
        add("farmersrespite", "green_tea_leaves", null, 8);
        add("farmersrespite", "yellow_tea_leaves", null, 8);
        add("farmersrespite", "black_tea_leaves", null, 8);

        add("ars_nouveau", "sourceberry_bush", null, 8);
        add("ars_nouveau", "magebloom", "magebloom_crop", 4);
    }

    private static void add(String mod, String output, @Nullable String input, int count) {
        ItemStack stack1 = new ItemStack(RegistriesUtils.getItem(mod, output), count);
        ItemStack stack2 = input == null ? stack1.copyWithCount(1) : new ItemStack(RegistriesUtils.getItem(mod, input));
        RecipeBuilder builder = GREENHOUSE_RECIPES.recipeBuilder(output)
                .notConsumable(stack2)
                .circuitMeta(1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(stack1)
                .duration(1200);

        if (mod.equals("botania")) {
            builder.MANAt(4);
        } else {
            builder.EUt(30);
        }
        builder.save();

        builder = GREENHOUSE_RECIPES.recipeBuilder(output + "_fertilizer")
                .notConsumable(stack2)
                .inputItems(GTItems.FERTILIZER.asStack(4))
                .circuitMeta(2)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputItems(stack1.copyWithCount(count << 1))
                .duration(400);

        if (mod.equals("botania")) {
            builder.MANAt(8);
        } else {
            builder.EUt(60);
        }
        builder.save();
    }
}
