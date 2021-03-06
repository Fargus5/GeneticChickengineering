package space.kiichan.geneticchickengineering.items;

import io.github.thebusybiscuit.cscorelib2.inventory.ItemUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import java.util.Optional;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import space.kiichan.geneticchickengineering.GeneticChickengineering;
import space.kiichan.geneticchickengineering.genetics.DNA;
import space.kiichan.geneticchickengineering.items.GCEItems;

public class ResourceEgg extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {
    private Material resource;

    public ResourceEgg(GeneticChickengineering plugin, Category category, SlimefunItemStack item, Material resource, RecipeType recipeType) {
        super(category, item, recipeType, new ItemStack[] {null, null, null, null, makeFakeChicken(plugin, resource), null, null, null, null});
        this.resource = resource;
    }

    private static ItemStack makeFakeChicken (GeneticChickengineering plugin, Material resource) {
        ItemStack fake = GCEItems.POCKET_CHICKEN.clone();
        DNA dna;
        if (resource == Material.WATER) {
            dna = new DNA(62);
        } else {
            dna = new DNA(41);
        }
        plugin.pocketChicken.setLore(fake, null, dna);
        return fake;
    }

    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            e.cancel();

            Optional<Block> block = e.getClickedBlock();

            if (block.isPresent()) {
                Block b = block.get();
                Block place = b.getRelative(e.getClickedFace());
                if (place.isReplaceable()) {
                    place.setType(this.resource);
                    if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                        ItemUtils.consumeItem(e.getItem(), false);
                    }
                }
            }
        };
    }
}
