package com.gamingmesh.jobs.commands.list;

import org.bukkit.command.CommandSender;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.JobsPlugin;
import com.gamingmesh.jobs.commands.Cmd;
import com.gamingmesh.jobs.commands.JobCommand;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.stuff.ChatColor;

public class expboost implements Cmd {

    @JobCommand(2300)
    public boolean perform(JobsPlugin plugin, final CommandSender sender, final String[] args) {
	if (args.length > 2 || args.length <= 1) {
	    Jobs.getCommandManager().sendUsage(sender, "expboost");
	    return true;
	}

	double rate = 1.0;
	if (!args[1].equalsIgnoreCase("all") && !args[0].equalsIgnoreCase("reset"))
	    try {
		rate = Double.parseDouble(args[1]);
	    } catch (NumberFormatException e) {
		Jobs.getCommandManager().sendUsage(sender, "expboost");
		return true;
	    }

	String PlayerName = sender.getName();
	String jobName = args[0];
	Job job = Jobs.getJob(jobName);

	if (PlayerName == null) {
	    Jobs.getCommandManager().sendUsage(sender, "expboost");
	    return true;
	}

	if (args[0].equalsIgnoreCase("reset") && args[1].equalsIgnoreCase("all")) {
	    for (Job one : Jobs.getJobs()) {
		one.setExpBoost(1.0);
	    }
	    sender.sendMessage(ChatColor.GREEN + Jobs.getLanguage().getMessage("command.expboost.output.allreset"));
	    return true;
	} else if (args[0].equalsIgnoreCase("reset")) {
	    boolean found = false;
	    for (Job one : Jobs.getJobs()) {
		if (one.getName().equalsIgnoreCase(args[1])) {
		    one.setExpBoost(1.0);
		    found = true;
		    break;
		}
	    }

	    if (found) {
		sender.sendMessage(ChatColor.RED + Jobs.getLanguage().getMessage("command.expboost.output.jobsboostreset", "%jobname%", job.getName()));
		return true;
	    }
	}

	if (args[0].equalsIgnoreCase("all")) {

	    for (Job one : Jobs.getJobs()) {
		one.setExpBoost(rate);
	    }

	    sender.sendMessage(ChatColor.GREEN + Jobs.getLanguage().getMessage("command.expboost.output.boostalladded", "%boost%", rate));
	    return true;
	} else {
	    if (job == null) {
		sender.sendMessage(ChatColor.GREEN + Jobs.getLanguage().getMessage("general.error.job"));
		return true;
	    }
	    job.setExpBoost(rate);
	    sender.sendMessage(ChatColor.GREEN + Jobs.getLanguage().getMessage("command.expboost.output.boostadded", "%boost%", rate, "%jobname%", job.getName()));
	    return true;
	}
    }
}
