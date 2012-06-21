package de.scrum_master.soccer;

import java.io.PrintStream;
import java.util.SortedSet;
import java.util.TreeSet;

class Group implements Comparable<Group>
{
	private String id;
	private String name;
	private SortedSet<Team> teams = new TreeSet<Team>();
	private SortedSet<Match> matches = new TreeSet<Match>();
	private Table table;

	Group(String id, String name)
	{
		this.id = id;
		this.name = name;
		table = new Table();
	}

	public int compareTo(Group group)
	{
		return id.compareTo(group.id);
	}
	
	void addTeam(Team team)
	{
		teams.add(team);
		table.addTeam(team);
	}

	void addMatch(Match match)
	{
		matches.add(match);
		table.addMatch(match);
	}

	Team getTeam(String id)
	{
		for (Team team : teams) {
			if (id.equals(team.getId()))
				return team;
		}
		return null;
	}

	Match getMatch(String id)
	{
		for (Match match : matches) {
			if (id.equals(match.getId()))
				return match;
		}
		return null;
	}

	void print(PrintStream out)
	{
		print(out, true, true);
	}

	void print(PrintStream out, boolean showTeams, boolean showMatches)
	{
		out.println(name);
		out.println();
		if (showTeams) {
			for (Team team : teams)
				out.println("  " + team + " (" + team.getId() + ")");
			out.println();
		}
		if (showMatches) {
			for (Match match : matches) {
				out.println(String.format(
					"  %tF %tR    %-12s  -  %-12s    %2d : %2d",
					match.getDate(), match.getDate(),
					match.getHomeTeam(), match.getGuestTeam(),
					match.getHomeScore(), match.getGuestScore()
				));
			}
			out.println();
		}
		table.calculate();
		table.print(out, "  ");
		out.println();		
	}

	@Override
	public String toString()
	{
		return name;
	}
}
