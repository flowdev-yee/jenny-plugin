package com.yourname.npclistener;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class ExamplePlugin extends JavaPlugin implements Listener, CommandExecutor {

    // --- CONFIGURATION ---
    private final String START_TEXTURE = "ewogICJ0aW1lc3RhbXAiIDogMTc3ODE3NTA2ODI0MSwKICAicHJvZmlsZUlkIiA6ICI0MDU4NDhjMmJjNTE0ZDhkOThkOTJkMGIwYzhiZDQ0YiIsCiAgInByb2ZpbGVOYW1lIiA6ICJMaWFtX1NhZ2UiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMyM2FhOWUzMTFjZjI2ZjJmYjgzYWM5YTQ0MjE5MzQzYTQ0ZmZiZWFlMjU2YmEzY2JjODg2ZjJhMTg5MGVjOCIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";
    private final String START_SIGNATURE = "NE52mIvAwpKtMIx28iHZc8BRAaImGwM31jp2L+CC1QdF+Wm1i3qBt1gG5S7RbAVVlhWT9bqEAwrJReD7T2tPLRvXJuaP+DCAW2NJqLRIxm8yY7IHtNFeNXFxJHWxSbp5+XfxdUZUs6VMBfGYSzoBUyoq9TVmPCLAWBzNyjK1mX6ed8G6rMr3FO5RIzRu37wWe4ctreVq1xG6XvwGAVSyHbxxLBiTaJA/t+S/OnBPj9cAxGVGWDQwFxWswfaV8m7cvcSTIiwf2QeHXHeiv9oww+hK+ml4OZcTMpNMBCQPdL7bAfzyl1nUSMTnPM7XHHoWzkdaX2DD9KOGIC98pMX8ll77r6K4/7ZuXRcMvMetLaZ1IDQ/OgTtaXjxODrgmQSLIjXlsn2kjiROO5tS6AAg3oMxmOlulW7DHGi8Jaooh2Kr2QnJBnl/c8M/bEMCCHEPCQ5eOVqOrwc1Yzu8rSKb2V2igmR9BT9s8iRzmwPiP6p+xlBbeNASg6O7FqVJ9LDzBVxUJ3XgGs0GiF5zYOwGpcT3NjCa1nB7gzuv2HSkiVvGo/nVq49G2nowBkVHW2WxK2Lz1q+Lbw8GnIMMsbV83ZweWqcAk7K7k3PSQqHY4dH3xOdkHmbc6ElTgNG+vu1vkJx1IAL+nzbvisX3D0+Wru1seB985qqII+W+AR0swSs=";
    
    private final String TARGET_TEXTURE = "ewogICJ0aW1lc3RhbXAiIDogMTc3NzExNzk1Nzk3NSwKICAicHJvZmlsZUlkIiA6ICIxMjE4YWNiNDJiYzA0MzY4YjIxOTU4ZTZiYWU2NDMyMCIsCiAgInByb2ZpbGVOYW1lIiA6ICJQYXRhdGplTUMiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTk1N2E0MDU1Y2EzMjMzNTAxZTA4MjRmYTgyYTU5NTZjZGIyZWU3MGFhYWM2ODA5NmQwMjVkZmE3ZjEzZDNkMyIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";
    private final String TARGET_SIGNATURE = "gGpTOwPx5g0I4rsgec/qii6L/I//0adHXwJ3WQnxt/tH6Csj0SM0ySlOtqhvYw6Ll8i9xflfPo39r4gJ0nLKwor8Yv0dtWQ7kXt6lwi5BYRs2Ny5j/1T/FYP8zcCFXaXvdFNzlNwTJVkw20//xtbddHzblTo6WYmQhyJbmj5Ar6FKMXBnkvYNCzFTyg+mKQ3fE9cDG6dbdmimmhCbptnURIM1Gbr9vbjZp+kK+6W78bW8frGvpGwbe9bYec73RodxPwbpbkciI/7B3CgnFlyNEd16Kor2YnmX/fJEdGspJmWazg81gsZ2A0bEyDb7YSvcs+vazXkxmRtICPpdPEmSunM/aVXuBmnHuwcZdQKQpYejIbFOi1pARrpT2BfKJd7CSBMzfT9W41fH9jRTUWARfJ4YvvD4JGNtWwvmsXnDN3/H77JrlWX/0xxG3tvSuyObqEhKxgLvcSDwrQiRiTgWFo3I1daTSpxUa27cw71hDBw26kxsz63CqPqJofEqTTDOKJs7y1GKaRVLGxOkTQ3uJaCmo64q6jhVse+EWpLjbIoMCEkMSuGDz56DSP+NL8WP6vsIps0YEhoHnXvR9I9fLkCMqxfR6G/RQWHRp8Qn3s1ncIbwrC2J3ER8shSepKHPrtSImULcWSkOdyaVtXhPf4I6QENkoakknvZXTrWxLs=";
    // ---------------------

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("jenny").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;

        // Create NPC and apply the STARTING skin immediately
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Jenny");
        SkinTrait skin = npc.getOrAddTrait(SkinTrait.class);
        skin.setSkinPersistent("start_skin", START_SIGNATURE, START_TEXTURE);
        
        npc.spawn(player.getLocation());
        player.sendMessage("§b[Jenny] Hello! I have arrived.");
        return true;
    }

    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        ItemStack hand = player.getInventory().getItemInMainHand();

        if (hand.getType() == Material.EMERALD) {
            NPC npc = event.getNPC();
            player.sendMessage("§e[Jenny] Watch this move!");
            hand.setAmount(hand.getAmount() - 1);

            new BukkitRunnable() {
                float yaw = npc.getStoredLocation().getYaw();
                int ticks = 0;

                @Override
                public void run() {
                    if (ticks >= 20) {
                        this.cancel();
                        return;
                    }

                    // Spinning logic
                    yaw += 18f;
                    Location loc = npc.getStoredLocation();
                    loc.setYaw(yaw);
                    npc.teleport(loc, org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.PLUGIN);

                    // Halfway mark (Tick 10)
                    if (ticks == 10) {
                        // Change Skin
                        SkinTrait skin = npc.getOrAddTrait(SkinTrait.class);
                        skin.setSkinPersistent("target_skin", TARGET_SIGNATURE, TARGET_TEXTURE);
                        
                        // Spawn Heart Particles
                        Location particleLoc = npc.getStoredLocation().add(0, 1, 0);
                        npc.getEntity().getWorld().spawnParticle(Particle.HEART, particleLoc, 5, 0.5, 0.5, 0.5);
                        
                        player.sendMessage("§d[NPC] Transformation complete!");
                    }
                    ticks++;
                }
            }.runTaskTimer(this, 0, 1);
        }
    }
}
