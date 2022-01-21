package com.opum2.abilityrings.data;

import java.util.function.Supplier;

import com.google.common.base.Preconditions;
import com.opum2.abilityrings.ModInfo;
import com.opum2.abilityrings.init.ModItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.RegistryObject;

public class AbilityRingsItemModelProvider extends ItemModelProvider {
    private static final String LAYER_0 = "layer0";

    public AbilityRingsItemModelProvider(final DataGenerator generator, final ExistingFileHelper existingFileHelper) {
        super(generator, ModInfo.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return ModInfo.MOD_NAME + "ItemModels";
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
            if (item == ModItems.FLIGHT_RING) {
                withGeneratedParentAndProperties(item.get(), "flying", 1.0F);
            } else {
                withGeneratedParentAndDefaultTexture(item.get());
            }
        }
    }

    private ResourceLocation registryName(final Item item) {
        return Preconditions.checkNotNull(item.getRegistryName(), "Item %s has a null registry name", item);
    }

    private String name(final Item item) {
        return registryName(item).getPath();
    }

    private ResourceLocation itemTexture(final Item item) {
        final ResourceLocation name = registryName(item);
        return new ResourceLocation(name.getNamespace(), ITEM_FOLDER + "/" + name.getPath());
    }

    private ItemModelBuilder withGeneratedParentAndDefaultTexture(final Item item) {
        return withGeneratedParent(name(item)).texture(LAYER_0, itemTexture(item));
    }

    private ItemModelBuilder withGeneratedParent(final String name) {
        return withExistingParent(name, mcLoc("generated"));
    }

    private ItemModelBuilder withGeneratedParentAndProperties(final Item item, final String propertyKey, float propertyValue) {
        Supplier<ModelFile> model = Lazy.of(() ->
            withGeneratedParentAndDefaultTexture(item)
                .override()
                .predicate(new ResourceLocation(ModInfo.MOD_ID, propertyKey), propertyValue)
                .model(withExistingParent(name(item) + "_on", item.getRegistryName()).texture(LAYER_0, new ResourceLocation(registryName(item).getNamespace(), ITEM_FOLDER + "/" + registryName(item).getPath() + "_on")))
                .end()
        );
        
        return super.getBuilder(name(item)).parent(model.get());
    }
}
