package net.frostedop.frostedopmod;

import com.google.common.base.Function;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;


public class EssentialsHandler
{

	public static final boolean DEBUG = true;
	private static Logger logger;
	private static Function<Player, Boolean> superAdminProvider;

	public static void setLogger(Logger logger)
	{
		EssentialsHandler.logger = logger;
	}

	public static Plugin getFOPM()
	{
		final Plugin fopm = Bukkit.getPluginManager().getPlugin("FrostedOPMod");
		if (fopm == null)
		{
			logger.warning("Could not resolve plugin: FrostedOPMod");
		}

		return fopm;
	}

	@SuppressWarnings("unchecked")
	public static boolean isSuperAdmin(Player player)
	{

		if (superAdminProvider == null)
		{
			final Plugin fopm = getFOPM();
			if (fopm == null)
			{
				return false;
			}

			Object provider = null;
			for (RegisteredServiceProvider<?> serv : Bukkit.getServicesManager().getRegistrations(fopm))
			{
				if (Function.class.isAssignableFrom(serv.getService()))
				{
					provider = serv.getProvider();
				}
			}

			if (provider == null)
			{
				warning("Could not obtain Super Admin service provider!");
				return false;
			}

			superAdminProvider = (Function<Player, Boolean>)provider;
		}

		return superAdminProvider.apply(player);
	}

	public static void debug(String debug)
	{
		if (DEBUG)
		{
			info(debug);
		}
	}

	public static void warning(String warning)
	{
		logger.warning(warning);
	}

	public static void info(String info)
	{
		logger.info(info);
	}

}
