package com.opum2.abilityrings.data;

import java.util.function.Consumer;

import com.opum2.abilityrings.ModInfo;
import com.opum2.abilityrings.init.ModItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class AbilityRingsRecipeProvider extends RecipeProvider {
    public AbilityRingsRecipeProvider(final DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(final Consumer<FinishedRecipe> recipeConsumer) {
        ShapedRecipeBuilder.shaped(ModItems.FLIGHT_RING.get())
            .pattern("IFI")
            .pattern("F#F")
            .pattern("IFI")
            .define('I', Items.IRON_INGOT)
            .define('#', ModItems.BLUE_ORB.get())
            .define('F', Items.FEATHER)
            .unlockedBy("has_blue_orb", has(ModItems.BLUE_ORB.get()))
            .save(recipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.SWIFTNESS_RING.get())
            .pattern("III")
            .pattern("I#I")
            .pattern("III")
            .define('I', Items.IRON_INGOT)
            .define('#', Blocks.GREEN_CONCRETE)
            .unlockedBy("has_green_concrete", has(Blocks.GREEN_CONCRETE))
            .save(recipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.FOOD_RING.get())
            .pattern("III")
            .pattern("I#I")
            .pattern("III")
            .define('I', Items.IRON_INGOT)
            .define('#', Items.COOKED_BEEF)
            .unlockedBy("has_cooked_beef", has(Items.COOKED_BEEF))
            .save(recipeConsumer);
        
        ShapedRecipeBuilder.shaped(ModItems.HEALING_RING.get())
            .pattern("III")
            .pattern("I#I")
            .pattern("III")
            .define('I', Items.IRON_INGOT)
            .define('#', ModItems.RED_ORB.get())
            .unlockedBy("has_red_orb", has(ModItems.RED_ORB.get()))
            .save(recipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.RESISTANCE_RING.get())
            .pattern("III")
            .pattern("I#I")
            .pattern("III")
            .define('I', Items.IRON_INGOT)
            .define('#', Items.TOTEM_OF_UNDYING)
            .unlockedBy("has_totem_of_undying", has(Items.TOTEM_OF_UNDYING))
            .save(recipeConsumer);
        
        ShapedRecipeBuilder.shaped(ModItems.BLUE_ORB.get())
            .pattern("III")
            .pattern("I#I")
            .pattern("III")
            .define('I', Blocks.IRON_BLOCK)
            .define('#', Blocks.DIAMOND_BLOCK)
            .unlockedBy("has_diamond_block", has(Blocks.DIAMOND_BLOCK))
            .save(recipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.RED_ORB.get())
            .pattern("III")
            .pattern("I#I")
            .pattern("III")
            .define('I', Blocks.IRON_BLOCK)
            .define('#', Blocks.REDSTONE_BLOCK)
            .unlockedBy("has_redstone_block", has(Blocks.REDSTONE_BLOCK))
            .save(recipeConsumer);
    }

    @Override
    public String getName() {
        return ModInfo.MOD_NAME + super.getName();
    }
}
