/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package me.crashdemons.korra.abilities;

import com.projectkorra.ProjectKorra.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.potion.*;
import org.bukkit.util.Vector;
import java.util.*;
/**
 *
 * @author Crash
 */
public class KnifeThrowTask extends BukkitRunnable  {
    final KnifeThrowListener listener;
    private final Item throwItem;
    private final Material material;
    private final Player player;
    public final double damage;
    
    public KnifeThrowTask(KnifeThrowListener listener, Item item, Material material, Player player, double damage){
        super();
        this.listener=listener;
        this.throwItem = item;
        this.material = material;
        this.player=player;
        this.damage=damage;
    }
    private void eventStep(){
        Methods.removeSpouts(throwItem.getLocation(), 3, player);
    }
    private void eventHitBlock(){
        throwItem.remove();
        cancel();
    }
    private void eventHitPlayer(Player p){
         p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 4 * 20, 3));//slowness 3 for 4 seconds
    }
    private void eventHitEntity(Entity entity){
        Methods.damageEntity(player, entity, damage);
        if ((entity instanceof Player)) eventHitPlayer((Player)entity);
        throwItem.remove();
        cancel();
    }

    public void run()
    {
        if(throwItem.isDead()) cancel();
        if(KnifeThrowListener.currentBlockSolid(throwItem)) eventHitBlock();
        eventStep();
        for(Iterator iterator = Methods.getEntitiesAroundPoint(throwItem.getLocation(), 3D).iterator(); iterator.hasNext();)
        {
            Entity entity = (Entity)iterator.next();
            if((entity instanceof LivingEntity) && entity.getEntityId() != player.getEntityId())
            {
                eventHitEntity(entity);
                break;
            }
        }

    }




            
}
