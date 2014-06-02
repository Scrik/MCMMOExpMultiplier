/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package mcmmoExpMultiplier;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;

public class ExpMultiplier extends JavaPlugin implements Listener {

	private HashMap<String, Integer> multipliers = new HashMap<String, Integer>();

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		int multiplier = 1;
		if (player.hasPermission("mcmmoexpmultiplier.x2")) {
			multiplier = 2;
		} else if (player.hasPermission("mcmmoexpmultiplier.x3")) {
			multiplier = 3;
		} else if (player.hasPermission("mcmmoexpmultiplier.x5")) {
			multiplier = 5;
		} else if (player.hasPermission("mcmmoexpmultiplier.x10")) {
			multiplier = 10;
		}
		if (multiplier != 1) {
			multipliers.put(player.getName(), multiplier);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		multipliers.remove(event.getPlayer().getName());
	}

	@EventHandler
	public void onMCMMOExpReceive(McMMOPlayerXpGainEvent event) {
		String playername = event.getPlayer().getName();
		if (multipliers.containsKey(playername)) {
			event.setRawXpGained(event.getRawXpGained() * multipliers.get(playername));
		}
	}

}
