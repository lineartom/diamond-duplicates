package com.github.lineartom.diamond_duplicates.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;

@Mixin(ShapedRecipe.class)
public class CraftingMixin {

    @Inject(method = "craft", at = @At("RETURN"))
    private void onCraft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager, CallbackInfoReturnable<ItemStack> cir) {
        // Now we have access to the fully initialized resultStack
        ItemStack resultStack = cir.getReturnValue();
        ItemStack enchantedItem = null;
        for (int i = 0; i < recipeInputInventory.size(); i++) {
            ItemStack item = recipeInputInventory.getStack(i);
            if (!item.isEmpty() && item.hasEnchantments()) {
                enchantedItem = item;
                break;
            }
        }

        // Transfer all enchantments from the item
        if (enchantedItem != null) {
            EnchantmentHelper.get(enchantedItem).forEach((enchantment, level) -> {
                resultStack.addEnchantment(enchantment, level);
            });
        }
    }
}
