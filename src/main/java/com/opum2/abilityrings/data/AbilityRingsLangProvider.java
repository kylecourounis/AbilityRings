package com.opum2.abilityrings.data;

import com.opum2.abilityrings.ModInfo;
import com.opum2.abilityrings.config.ConfigHandler;
import com.opum2.abilityrings.init.ModItems;
import com.opum2.abilityrings.items.ModItem;
import com.opum2.abilityrings.util.AbilityRingsLang;

import net.minecraft.ChatFormatting;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class AbilityRingsLangProvider extends LanguageProvider {
    public AbilityRingsLangProvider(final DataGenerator generator) {
        super(generator, ModInfo.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addItems();
        this.addTooltips();

        super.add("itemGroup." + ModInfo.MOD_ID, "Ability Rings");
    }

    @Override
    public String getName() {
        return ModInfo.MOD_NAME + super.getName();
    }

    private void addItems() {
        for (RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
            if (item.get() instanceof ModItem) {
                ModItem modItem = (ModItem) item.get();
                this.addItem(item, modItem.getLocalizedName());
            }
        }
    }

    private void addTooltips() {
        String consumptionItemName = ForgeRegistries.ITEMS.getValue(
                ConfigHandler.COMMON.flightRingProperties.getFlightRingItem()
        ).getDefaultInstance().getDisplayName().getString().replace("[", "").replace("]", "").trim();

        this.add(AbilityRingsLang.ITEM_TOOLTIP_HOLD_SHIFT, ChatFormatting.GRAY + "Hold Shift For Info");

        this.add(
                AbilityRingsLang.ITEM_EXTENDED_TOOLTIP_FLIGHT_RING,
                ChatFormatting.GRAY + "Consumes one Feather every "
                        + (ConfigHandler.COMMON.flightRingProperties.consumeTicks.get() / 20) + " seconds as fuel.\n"
                        + ChatFormatting.GRAY + "You can hold up to " + ChatFormatting.YELLOW
                        + ConfigHandler.COMMON.flightRingProperties.maxStorage.get() + " " + consumptionItemName
                        + ChatFormatting.GRAY + " in the ring." + ChatFormatting.GREEN + " [Shift+Right Click] "
                        + ChatFormatting.GRAY + "to consume.\n" + ChatFormatting.GRAY
                        + "Must be in a slot in your hotbar to fly."
        );
        this.add(
                AbilityRingsLang.ITEM_TOOLTIP_FOOD_RING,
                ChatFormatting.YELLOW + "Gives Saturation when placed anywhere in the player's inventory."
        );
        this.add(
                AbilityRingsLang.ITEM_TOOLTIP_SPEED_RING,
                ChatFormatting.YELLOW
                        + "Gives a Speed and Jump Boost, negates fall damage, and gives step-assist when placed in any slot in the your hotbar."
        );
        this.add(
                AbilityRingsLang.ITEM_TOOLTIP_HEALING_RING,
                ChatFormatting.YELLOW + "Gives Regeneration & Absorption when placed anywhere in your inventory."
        );
    }

    private void add(final AbilityRingsLang lang, final String value) {
        add(lang.getTranslationKey(), value);
    }
}
