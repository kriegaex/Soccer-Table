package de.scrum_master.soccer;

import java.io.PrintStream;
import java.util.SortedSet;
import java.util.TreeSet;

public class Group implements Comparable<Group>
{
	private String id;
	private String name;
	private SortedSet<Team> teams = new TreeSet<Team>();
	private SortedSet<Match> matches = new TreeSet<Match>();
	private Table table;

	@Override
	public String toString()
	{
		return name;
	}

	public Group(String id, String name)
	{
		this.id = id;
		this.name = name;
		table = new Table();
	}

	public int compareTo(Group group)
	{
		return id.compareTo(group.id);
	}
	
	public Team getTeam(String id)
	{
		for (Team team : teams) {
			if (id.equals(team.id))
				return team;
		}
		return null;
	}

	public Team getTeam(int index)
	{
		int i = 1;
		for (Team team : teams) {
			if (i == index)
				return team;
			i++;
		}
		return null;
	}

	public Match getMatch(String id)
	{
		for (Match match : matches) {
			if (id.equals(match.id))
				return match;
		}
		return null;
	}

	public Match getMatch(int index)
	{
		int i = 1;
		for (Match match : matches) {
			if (i == index)
				return match;
			i++;
		}
		return null;
	}

	public void addTeam(Team team)
	{
		teams.add(team);
		table.addTeam(team);
	}

	public void addMatch(Match match)
	{
		matches.add(match);
		table.addMatch(match);
	}

	public void print(PrintStream out)
	{
		out.println(name);
		out.println();
		for (Team team : teams)
			out.println("  " + team + " (" + team.id + ")");
		out.println();
		for (Match match : matches) {
			out.println(String.format(
				"  %tF %tR    %-12s  -  %-12s    %2d : %2d",
				match.date, match.date,
				match.homeTeam, match.guestTeam,
				match.homeScore, match.guestScore
			));
		}
		out.println();
		table.printTable(out, "  ");
		out.println();		
	}
}
