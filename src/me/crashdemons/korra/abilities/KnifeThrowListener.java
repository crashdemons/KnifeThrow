// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   KnifeThrowListener.java

package me.crashdemons.korra.abilities;

import com.projectkorra.ProjectKorra.*;
import java.util.Iterator;
import java.util.List;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class KnifeThrowListener
    implements Listener
{

    private Location p;
    public final double damage;
    public final double speed;
    public final int distance;
    public final long cooldown;
    
    public KnifeThrowListener()
    {
        damage = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.crashdemons.KnifeThrow.damage");
        speed = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.crashdemons.KnifeThrow.speed");
        distance = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.crashdemons.KnifeThrow.distance");
        cooldown = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.crashdemons.KnifeThrow.cooldown");
    }

    public static boolean currentBlockSolid(Entity entity)
    {
        Block north = entity.getLocation().getBlock().getRelative(BlockFace.NORTH);
        Block east = entity.getLocation().getBlock().getRelative(BlockFace.EAST);
        Block south = entity.getLocation().getBlock().getRelative(BlockFace.SOUTH);
        Block west = entity.getLocation().getBlock().getRelative(BlockFace.WEST);
        if(north.getType() != Material.AIR) return true;
        if(east.getType() != Material.AIR) return true;
        if(south.getType() != Material.AIR) return true;
        return west.getType() != Material.AIR;
    }

    @EventHandler
    public void onLeftClick(PlayerAnimationEvent event)
    {
        Player player = event.getPlayer();
        BendingPlayer bPlayer = Methods.getBendingPlayer(player.getName());
        String ability = Methods.getBoundAbility(player);

        if(ability == null || !ability.equalsIgnoreCase("KnifeThrow")) return;//ability disabled.
        if(!Methods.canBend(player.getName(), "KnifeThrow")) return;//player can't bend
        if(Methods.isRegionProtectedFromBuild(player, "KnifeThrow", player.getLocation())) return;//region doesn't allow ability
        if(bPlayer.isOnCooldown("KnifeThrow")) return;// cooldown between abilities
        else{//no other negativity, let the user fire this ability
            throwItem(player, Material.ARROW, Material.ARROW);
            bPlayer.addCooldown("KnifeThrow", cooldown);
            return;
        }
    }

    public void throwItem(final Player player, final Material material, Material requiredItem)
    {
            final Item throwItem = player.getLocation().getWorld().dropItem(player.getEyeLocation(), new ItemStack(material));
            throwItem.setVelocity(player.getEyeLocation().getDirection().multiply(speed));

            BukkitTask task = new KnifeThrowTask(this, throwItem, material, player, damage).runTaskTimer(ProjectKorra.plugin, 0L, 1L);

    }


}
