package net.dkcraft.dkcore.vote;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dkcraft.dkcore.Main;

public class Vote implements CommandExecutor {

	private Main plugin;

	private VoteMethods vote;

	public Vote(Main plugin) {
		this.plugin = plugin;
		this.vote = this.plugin.vote;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("vote") ||
				cmd.getName().equalsIgnoreCase("votepoints") ||
				cmd.getName().equalsIgnoreCase("votestore") ||
				cmd.getName().equalsIgnoreCase("votelist")) {
			if (args.length == 0) {

				Player player = (Player) sender;

				vote.openVoteGUI(player);
			}
		}
		return true;
	}
}
