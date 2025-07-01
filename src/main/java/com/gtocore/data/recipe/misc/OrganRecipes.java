package com.gtocore.data.recipe.misc;

import com.gtocore.api.data.tag.GTOTagPrefix;
import com.gtocore.common.data.GTOItems;
import com.gtocore.common.data.GTOMaterials;
import com.gtocore.common.data.OrganItems;

import com.gtolib.GTOCore;
import com.gtolib.utils.RLUtils;
import com.gtolib.utils.TagUtils;

import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import static com.gregtechceu.gtceu.api.GTValues.VA;
import static com.gregtechceu.gtceu.data.recipe.GTCraftingComponents.*;
import static com.gtocore.common.data.GTORecipeTypes.ASSEMBLER_RECIPES;

public final class OrganRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        // 内脏编辑器
        VanillaRecipeHelper.addShapedRecipe(provider, GTOCore.id("visceral_editor"), OrganItems.VISCERAL_EDITOR.asStack(),
                " B ",
                "CDC",
                "EFF",
                'B', GTItems.VOLTAGE_COIL_MV.asStack(), 'C', GTItems.ROBOT_ARM_MV.asStack(), 'D', new ItemStack(Items.CRAFTING_TABLE.asItem()), 'E', new ItemStack(Items.SLIME_BALL.asItem()), 'F', CustomTags.MV_CIRCUITS);
        VanillaRecipeHelper.addShapedRecipe(provider, GTOCore.id("visceral_editor_wood"), OrganItems.VISCERAL_EDITOR_WOOD.asStack(),
                " S ",
                "LLS",
                " L ",
                'L', TagUtils.createTag(RLUtils.mc("logs")), 'S', Items.STICK);
        VanillaRecipeHelper.addShapedRecipe(provider, GTOCore.id("fragile_and_contaminated_mana_steel_wing"), OrganItems.ORGAN_FRAGILE_AND_CONTAMINATED_MANA_STEEL_WING.asStack(),
                "ABA",
                "CDC",
                "ABA",
                'A', new MaterialEntry(TagPrefix.plateDouble, GTOMaterials.Manasteel), 'B', new MaterialEntry(TagPrefix.foil, GTOMaterials.Manasteel), 'C', new MaterialEntry(GTOTagPrefix.FIELD_GENERATOR_CASING, GTMaterials.Steel), 'D', GTOItems.COLORFUL_MYSTICAL_FLOWER.asItem());
        VanillaRecipeHelper.addShapedRecipe(provider, GTOCore.id("fairy_wing"), OrganItems.ORGAN_FAIRY_WING.asStack(),
                "ABA",
                "ACA",
                "DDD",
                'A', new MaterialEntry(TagPrefix.foil, GTOMaterials.Herbs), 'B', new MaterialEntry(TagPrefix.plateDouble, GTOMaterials.Herbs), 'C', GTItems.FIELD_GENERATOR_MV.asStack(), 'D', GTOItems.COLORFUL_MYSTICAL_FLOWER.asItem());
        VanillaRecipeHelper.addShapedRecipe(provider, GTOCore.id("mechanical_wing_tier_1"), OrganItems.TRANSFORM_MECHANICAL_WING_TIER_1.asStack(),
                "ABA",
                "CBC",
                "DED",
                'A', GTItems.BATTERY_EV_VANADIUM.asStack(), 'B', new MaterialEntry(TagPrefix.foil, GTMaterials.Titanium), 'C', new MaterialEntry(TagPrefix.plateDouble, GTMaterials.Titanium), 'D', GTItems.FIELD_GENERATOR_EV.asStack(), 'E', new MaterialEntry(TagPrefix.ingot, GTMaterials.Titanium));
        VanillaRecipeHelper.addShapedRecipe(provider, GTOCore.id("mechanical_wing_tier_2"), OrganItems.TRANSFORM_MECHANICAL_WING_TIER_2.asStack(),
                "A A",
                "BCB",
                "DED",
                'A', GTItems.BATTERY_LuV_VANADIUM.asStack(), 'B', new MaterialEntry(GTOTagPrefix.CURVED_PLATE, GTMaterials.HSSS), 'C', new MaterialEntry(TagPrefix.ingot, GTOMaterials.Herbs), 'D', GTItems.FIELD_GENERATOR_LuV.asStack(), 'E', GTOItems.COLORFUL_MYSTICAL_FLOWER.asItem());

        // 器官组件

        IntStream.rangeClosed(1, 4).forEachOrdered(OrganTier -> {
            int tier = OrganTier << 1;
            IntUnaryOperator scaleOperator = n -> OrganTier == 1 ? n << GTOCore.difficulty - 1 : n << GTOCore.difficulty; // OrganTier==1
            // 为MV,没自动化，降点难度
            ItemStack motor = (ItemStack) MOTOR.get(tier);
            ItemStack conveyor = (ItemStack) CONVEYOR.get(tier);
            ItemStack pump = (ItemStack) PUMP.get(tier);
            ItemStack piston = (ItemStack) PISTON.get(tier);
            ItemStack robot_arm = (ItemStack) ROBOT_ARM.get(tier);
            ItemStack emitter = (ItemStack) EMITTER.get(tier);
            ItemStack sensor = (ItemStack) SENSOR.get(tier);
            ItemStack field_generator = (ItemStack) FIELD_GENERATOR.get(tier - 1); // 超导超了一个阶段
            @SuppressWarnings("unchecked")
            TagKey<Item> circuitTag = (TagKey<Item>) CIRCUIT.get(tier);
            // arm leg
            ASSEMBLER_RECIPES.builder("organ_right_arm_tier_" + tier)
                    .inputItems(motor, scaleOperator.applyAsInt(2))
                    .inputItems(robot_arm, scaleOperator.applyAsInt(4))
                    .inputItems(sensor, scaleOperator.applyAsInt(2))
                    .inputItems(field_generator, scaleOperator.applyAsInt(1))
                    .inputItems(circuitTag, scaleOperator.applyAsInt(4))
                    .inputItems(piston, scaleOperator.applyAsInt(1))
                    .outputItems(OrganItems.ORGAN_TIER_RIGHT_ARM[OrganTier].asStack())
                    .EUt(VA[tier])
                    .duration(1200)
                    .circuitMeta(1)
                    .save();
            ASSEMBLER_RECIPES.builder("organ_left_arm_tier_" + tier)
                    .inputItems(motor, scaleOperator.applyAsInt(2))
                    .inputItems(robot_arm, scaleOperator.applyAsInt(4))
                    .inputItems(sensor, scaleOperator.applyAsInt(2))
                    .inputItems(field_generator, scaleOperator.applyAsInt(1))
                    .inputItems(circuitTag, scaleOperator.applyAsInt(4))
                    .inputItems(piston, scaleOperator.applyAsInt(1))
                    .outputItems(OrganItems.ORGAN_TIER_LEFT_ARM[OrganTier].asStack())
                    .EUt(VA[tier])
                    .duration(1200)
                    .circuitMeta(2)
                    .save();
            ASSEMBLER_RECIPES.builder("organ_right_leg_tier_" + tier)
                    .inputItems(motor, scaleOperator.applyAsInt(4))
                    .inputItems(conveyor, scaleOperator.applyAsInt(2))
                    .inputItems(robot_arm, scaleOperator.applyAsInt(2))
                    .inputItems(sensor, scaleOperator.applyAsInt(2))
                    .inputItems(field_generator, scaleOperator.applyAsInt(1))
                    .inputItems(circuitTag, scaleOperator.applyAsInt(4))
                    .inputItems(piston, scaleOperator.applyAsInt(1))
                    .outputItems(OrganItems.ORGAN_TIER_RIGHT_LEG[OrganTier].asStack())
                    .EUt(VA[tier])
                    .duration(1200)
                    .circuitMeta(3)
                    .save();
            ASSEMBLER_RECIPES.builder("organ_left_leg_tier_" + tier)
                    .inputItems(motor, scaleOperator.applyAsInt(4))
                    .inputItems(conveyor, scaleOperator.applyAsInt(2))
                    .inputItems(robot_arm, scaleOperator.applyAsInt(2))
                    .inputItems(sensor, scaleOperator.applyAsInt(2))
                    .inputItems(field_generator, scaleOperator.applyAsInt(1))
                    .inputItems(circuitTag, scaleOperator.applyAsInt(4))
                    .inputItems(piston, scaleOperator.applyAsInt(1))
                    .outputItems(OrganItems.ORGAN_TIER_LEFT_LEG[OrganTier].asStack())
                    .EUt(VA[tier])
                    .duration(1200)
                    .circuitMeta(4)
                    .save();
            // heart eyes lungs liver spine
            ASSEMBLER_RECIPES.builder("organ_heart_tier_" + tier)
                    .inputItems(motor, scaleOperator.applyAsInt(2))
                    .inputItems(emitter, scaleOperator.applyAsInt(2))
                    .inputItems(sensor, scaleOperator.applyAsInt(2))
                    .inputItems(field_generator, scaleOperator.applyAsInt(1))
                    .inputItems(circuitTag, scaleOperator.applyAsInt(4))
                    .inputItems(piston, scaleOperator.applyAsInt(2))
                    .inputItems(pump, scaleOperator.applyAsInt(2))
                    .outputItems(OrganItems.ORGAN_TIER_HEART[OrganTier].asStack())
                    .EUt(VA[tier])
                    .duration(1200)
                    .circuitMeta(5)
                    .save();
            ASSEMBLER_RECIPES.builder("organ_eyes_tier_" + tier)
                    .inputItems(motor, scaleOperator.applyAsInt(1))
                    .inputItems(emitter, scaleOperator.applyAsInt(1))
                    .inputItems(sensor, scaleOperator.applyAsInt(2))
                    .inputItems(circuitTag, scaleOperator.applyAsInt(4))
                    .outputItems(OrganItems.ORGAN_TIER_EYES[OrganTier].asStack())
                    .EUt(VA[tier])
                    .duration(1200)
                    .circuitMeta(6)
                    .save();
            ASSEMBLER_RECIPES.builder("organ_lungs_tier_" + tier)
                    .inputItems(motor, scaleOperator.applyAsInt(2))
                    .inputItems(robot_arm, scaleOperator.applyAsInt(4))
                    .inputItems(emitter, scaleOperator.applyAsInt(1))
                    .inputItems(sensor, scaleOperator.applyAsInt(1))
                    .inputItems(piston, scaleOperator.applyAsInt(2))
                    .inputItems(pump, scaleOperator.applyAsInt(2))
                    .inputItems(field_generator, scaleOperator.applyAsInt(1))
                    .inputItems(circuitTag, scaleOperator.applyAsInt(4))
                    .outputItems(OrganItems.ORGAN_TIER_LUNGS[OrganTier].asStack())
                    .EUt(VA[tier])
                    .duration(1200)
                    .circuitMeta(7)
                    .save();
            ASSEMBLER_RECIPES.builder("organ_liver_tier_" + tier)
                    .inputItems(emitter, scaleOperator.applyAsInt(1))
                    .inputItems(sensor, scaleOperator.applyAsInt(2))
                    .inputItems(field_generator, scaleOperator.applyAsInt(2))
                    .inputItems(pump, scaleOperator.applyAsInt(2))
                    .inputItems(circuitTag, scaleOperator.applyAsInt(4))
                    .outputItems(OrganItems.ORGAN_TIER_LIVER[OrganTier].asStack())
                    .EUt(VA[tier])
                    .duration(1200)
                    .circuitMeta(8)
                    .save();
            ASSEMBLER_RECIPES.builder("organ_spine_tier_" + tier)
                    .inputItems(robot_arm, scaleOperator.applyAsInt(4))
                    .inputItems(emitter, scaleOperator.applyAsInt(1))
                    .inputItems(circuitTag, scaleOperator.applyAsInt(1))
                    .outputItems(OrganItems.ORGAN_TIER_SPINE[OrganTier].asStack())
                    .EUt(VA[tier])
                    .duration(1200)
                    .circuitMeta(9)
                    .save();
        });
    }
}
