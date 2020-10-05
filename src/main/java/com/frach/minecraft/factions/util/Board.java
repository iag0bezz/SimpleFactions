package com.frach.minecraft.factions.util;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;
import java.util.stream.IntStream;

@Data
public class Board {

	private static String[] CACHE = new String[ChatColor.values().length];

	private Player receiver;
	private String title;
	private List<String> lines = Lists.newArrayList();

	private Objective objective;
	private Team team;

	public Board(Player receiver) {
		this.receiver = receiver;

		this.objective = this.getScoreboard().getObjective("board");

		if(this.objective == null) {
			this.objective = this.getScoreboard().registerNewObjective("board", "dummy");
		}

		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

		this.team = this.getScoreboard().getTeam("board");

		if(this.team == null) {
			this.team = this.getScoreboard().registerNewTeam("board");
		}

		this.team.setAllowFriendlyFire(true);
		this.team.setCanSeeFriendlyInvisibles(false);
		this.team.setPrefix("");
		this.team.setSuffix("");
	}

	public void setTitle(String title) {
		this.title = title;

		if (this.title.length() > 40) {
			this.title = this.title.substring(0, 40);
		}
	}

	public void addLine(String text) {
		this.lines.add(text);
	}

	public void update() {
		if(!this.receiver.isOnline()) {
			return;
		}

		if(this.getScoreboard() == null) {
			return;
		}

		this.objective.setDisplayName(this.title);

		if(this.getScoreboard().getEntries().size() != this.lines.size()) {
			this.getScoreboard().getEntries().forEach(id -> this.getScoreboard().resetScores(id));
		}

		for (int i = 0; i < this.lines.size(); i++) {
			String line = this.lines.get(i);

			Entry entry = Entry.toEntry(line);
			Team team = this.getScoreboard().getTeam(Board.CACHE[i]);

			if(team == null) {
				team = this.getScoreboard().registerNewTeam(CACHE[i]);

				team.addEntry(team.getName());
			}

			team.setPrefix(entry.getPrefix());
			team.setSuffix(entry.getSuffix());

			this.objective.getScore(team.getName()).setScore(15 - i);
		}
	}

	public Scoreboard getScoreboard() {
		return (this.receiver != null) ? this.receiver.getScoreboard() : null;
	}

	@Data
	public static class Entry {

		private String prefix;
		private String suffix;

		public Entry(String prefix, String suffix) {
			this.prefix = prefix;
			this.suffix = suffix;
		}

		public static Entry toEntry(String input) {
			if(input.isEmpty()) {
				return new Entry("", "");
			}
			if(input.length() <= 16) {
				return new Entry(input, "");
			} else {
				String prefix = input.substring(0, 16);
				String suffix = "";

				if (prefix.endsWith("\u00a7")) {
					prefix = prefix.substring(0, prefix.length() - 1);
					suffix = "\u00a7" + suffix;
				}

				suffix = StringUtils.left(ChatColor.getLastColors(prefix) + suffix + input.substring(16), 16);
				return new Entry(prefix, suffix);
			}
		}
	}

	public void reset() {
		this.receiver.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}

	static {
		IntStream.range(0, 15).forEach(i -> CACHE[i] = ChatColor.values()[i].toString() + ChatColor.RESET);
	}

}