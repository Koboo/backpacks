package eu.koboo.backpacks.command;

import com.jeff_media.morepersistentdatatypes.DataType;
import eu.koboo.backpacks.BackpackPlugin;
import eu.koboo.backpacks.utils.BackpackColor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.Locale;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommandBackpack implements CommandExecutor {

    BackpackPlugin plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission(plugin.getPermissions().getCommandUsage())) {
            sender.sendMessage(plugin.getMessages().getCommandNoPermissions());
            return false;
        }
        if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if(!sender.hasPermission(plugin.getPermissions().getCommandReload())) {
                sender.sendMessage(plugin.getMessages().getCommandNoPermissions());
                return false;
            }
            plugin.reloadConfig();
            sender.sendMessage(plugin.getMessages().getCommandReloadSuccess());
            return false;
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("give")) {
            if(!sender.hasPermission(plugin.getPermissions().getCommandGive())) {
                sender.sendMessage(plugin.getMessages().getCommandNoPermissions());
                return false;
            }
            giveBackpack(sender, args[1], BackpackColor.NONE);
            return false;
        }
        if(args.length == 3 && args[0].equalsIgnoreCase("give")) {
            if(!sender.hasPermission(plugin.getPermissions().getCommandGive())) {
                sender.sendMessage(plugin.getMessages().getCommandNoPermissions());
                return false;
            }
            BackpackColor color = null;
            try {
                color = BackpackColor.valueOf(args[2].toUpperCase(Locale.ROOT));
            } catch (Exception e) {
                sender.sendMessage(plugin.getMessages().getCommandColorNotFound().replaceAll("%color%", args[2]));
            }
            giveBackpack(sender, args[1], color);
            return false;
        }
        return false;
    }

    public void giveBackpack(CommandSender sender, String targetName, BackpackColor color) {
        Player target = Bukkit.getPlayer(targetName);
        if(target == null) {
            sender.sendMessage(plugin.getMessages().getCommandPlayerNotOnline().replaceAll("%name%", targetName));
            return;
        }

        ItemStack backpackItem = plugin.createBackpack(color);
        ItemMeta resultMeta = backpackItem.getItemMeta();
        PersistentDataContainer resultPDC = resultMeta.getPersistentDataContainer();

        // Assign a new backpackId if the result doesn't have an id
        UUID resultBackpackId = UUID.randomUUID();
        resultPDC.set(plugin.getItemUnstackableKey(), DataType.UUID, resultBackpackId);

        // Setting the owners id on the backpack
        resultPDC.set(plugin.getItemOwnerKey(), DataType.UUID, target.getUniqueId());

        backpackItem.setItemMeta(resultMeta);

        if(target.getInventory().firstEmpty() == -1) {
            target.getWorld().dropItem(target.getLocation(), backpackItem);
        } else {
            target.getInventory().addItem(backpackItem);
        }

        target.sendMessage(plugin.getMessages().getCommandGiveSuccess());
        if(!sender.getName().equalsIgnoreCase(target.getName())) {
            sender.sendMessage(plugin.getMessages().getCommandGiveSuccessOther().replaceAll("%name%", ""));
        }
    }
}
