package com.frach.minecraft.factions.util;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

@Data
public class ScoreboardBuilder {
	
	private Player receiver;
	private String title;

	public ScoreboardBuilder(Player receiver) {
		this.receiver = receiver;
	}
	
	public void setTitle(String title) {
		this.title = title;

		if (this.title.length() > 40) {
			this.title = this.title.substring(0, 40);
		}
	}
	
	public void setLine(String text, int line) {
		if(this.getScore(this.getObjective(), line)) {
			if(this.getEntire(this.getObjective(), line).equalsIgnoreCase(text))
				return;
			if(!this.getScore(this.getObjective(), line))
				this.getObjective().getScoreboard().resetScores(this.getEntire(this.getObjective(), line));
		}
		this.getObjective().getScore(text).setScore(line);
	}
	
	public void build() {
		if (this.receiver == null) {
			return;
		}
			
		if(this.receiver.getScoreboard().equals(Bukkit.getServer().getScoreboardManager().getMainScoreboard()))
			this.receiver.setScoreboard(Bukkit.getServer().getScoreboardManager().getNewScoreboard());
				
		this.getObjective().setDisplayName(this.title);
			    
		if (this.getObjective().getDisplaySlot() != DisplaySlot.SIDEBAR)
			this.getObjective().setDisplaySlot(DisplaySlot.SIDEBAR);

		this.receiver.setScoreboard(this.receiver.getScoreboard());
	}
	
	private String getEntire(Objective objective, int score) {
	    if(objective == null || objective.getScoreboard() == null)
	    	return null;

	    if(!getScore(objective, score))
	    	return null;

	    for (String entire : objective.getScoreboard().getEntries()) {
	        if(objective.getScore(entire).getScore() == score)
	        	return objective.getScore(entire).getEntry();
	    }
	    return null;
	}

	private boolean getScore(Objective objective, int score) {
		if(objective.getScoreboard() == null) {
			return false;
		}

	    for (String entire : objective.getScoreboard().getEntries()) {
	        if(objective.getScore(entire).getScore() == score)
	        	return true;
	    }

	    return false;
	}

	private Objective getObjective() {
		Scoreboard score = this.receiver.getScoreboard();

		return score.getObjective(this.receiver.getName()) == null ? score.registerNewObjective(this.receiver.getName(), "dummy") : score.getObjective(this.receiver.getName());
	}

}