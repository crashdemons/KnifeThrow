package me.crashdemons.korra.abilities;

import com.projectkorra.ProjectKorra.ProjectKorra;
import com.projectkorra.ProjectKorra.Element;
import com.projectkorra.ProjectKorra.Ability.AbilityModule;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;


public class KnifeThrow extends AbilityModule {

	/*
	 * This registers the ability with the AbilityModule class. It is important that the string is EXACTLY how you want the ability to appear in-game.
	 */
   private Plugin pk;
    public void loadConfig()
    {
        PluginManager pm = Bukkit.getPluginManager();
        pk = pm.getPlugin("ProjectKorra");
        FileConfiguration config = pk.getConfig();
        config.addDefault("ExtraAbilities.crashdemons.KnifeThrow.damage", Double.valueOf(3D));
        config.addDefault("ExtraAbilities.crashdemons.KnifeThrow.speed", Double.valueOf(2D));
        config.addDefault("ExtraAbilities.crashdemons.KnifeThrow.distance", Integer.valueOf(2));
        config.addDefault("ExtraAbilities.crashdemons.KnifeThrow.cooldown", Long.valueOf(2000L));
        pk.saveConfig();

    }



	public KnifeThrow() {
		super("KnifeThrow");
	}

	/*
	 * This returns the Description that will be seen when someone does /bending help [YourAbilityName]
	 */
	public String getDescription() {
		return "Throw chi-blocked knives at opponents. Useful for countering ranged users in a pinch - by Crashdemons";
	}

	/*
	 * This returns the name of the Author of the Ability. Use your own username if it is your own project.
	 * This will be used for debugging.
	 */

	public String getAuthor() {
		return "Crashdemons";
	}

	/*
	 * Much like the getAuthor(), mainly used for Debugging. Also allows you to sort of set a release pattern for your ability.
	 */

	public String getVersion() {
		return "v1.0.0";
	}

	/*
	 * Returns the element as a string. It is important you use the Element class here. If you do not specify an element, the ability will NOT load properly.
	 */

	public String getElement() {
		return Element.Chi.toString();
	}

	/*
	 * This just checks whether or not it is a sneak ability. Reason is, some other abilities wont work if you are sneaking, so this is our simple check for it.
	 */
	public boolean isShiftAbility() {
		return false;
	}

	/*
	 * This method is ran each and every time the ability loads. So if you need to register any events, schedule any tasks, this is where you do it.
	 */
    public void onThisLoad()
    {
        //ProjectKorra.plugin.
        ProjectKorra.plugin.getLogger().info("KnifeThrow Developed By crashdemons Has Loaded");
        loadConfig();
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new KnifeThrowListener(), ProjectKorra.plugin);
        ProjectKorra.plugin.getServer().getPluginManager().addPermission((new KnifeThrowPermissions()).KnifeThrowDefault);
        ProjectKorra.plugin.getServer().getPluginManager().getPermission("bending.ability.KnifeThrow").setDefault(PermissionDefault.TRUE);
    }

	/*
	 * Stops any progressing Bending.
	 */
	public void stop() {

	}
}