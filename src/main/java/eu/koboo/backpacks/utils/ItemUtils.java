package eu.koboo.backpacks.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ItemUtils {


    public String toBase64(Inventory inventory) {
        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            int size = inventory.getSize();
            dataOutput.writeInt(size);

            for (int i = 0; i < size; i++) {
                ItemStack content = inventory.getItem(i);
                if (content == null) {
                    content = new ItemStack(Material.AIR);
                }
                dataOutput.writeInt(i);
                dataOutput.writeObject(content);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Unable to save item stacks.", e);
        }
    }

    public Map<Integer, ItemStack> fromBase64(String base64) {
        try {
            if (base64 == null || base64.isEmpty() || base64.trim().equalsIgnoreCase("")) {
                return new HashMap<>();
            }

            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            int size = dataInput.readInt();

            Map<Integer, ItemStack> slotMap = new HashMap<>();
            for (int i = 0; i < size; i++) {
                int slot = dataInput.readInt();
                ItemStack itemStack = (ItemStack) dataInput.readObject();
                slotMap.put(slot, itemStack);
            }

            dataInput.close();
            return slotMap;
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException("Unable to read item stacks.", e);
        }
    }
}
