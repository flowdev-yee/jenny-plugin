package com.yourname.npclistener;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Register this class as the listener since it implements Listener
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("NPC Skin Swapper Enabled!");
    }

    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // 1. Check if the player is holding an Emerald
        if (itemInHand.getType() == Material.EMERALD) {
            NPC npc = event.getNPC();
            
            // 2. Get the SkinTrait (Citizens handles the data)
            SkinTrait skinTrait = npc.getOrAddTrait(SkinTrait.class);

            // 3. Define your custom skin data (from MineSkin.org)
            // Replace these with your actual generated strings!
            String texture = "PASTE_YOUR_TEXTURE_VALUE_HERE";
            String signature = "PASTE_YOUR_SIGNATURE_VALUE_HERE";

            // 4. Update the skin persistently
            skinTrait.setSkinPersistent("new_skin_set", signature, texture);

            // Notify and consume emerald
            player.sendMessage("§aThe NPC has been disguised!");
            itemInHand.setAmount(itemInHand.getAmount() - 1);
        }
    }
}
