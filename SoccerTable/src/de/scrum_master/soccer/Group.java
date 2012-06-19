package de.scrum_master.soccer;

import java.util.SortedSet;
import java.util.TreeSet;

public class Group implements Comparable<Group>
{
	public String id;
	public String name;
	public SortedSet<Team> teams = new TreeSet<Team>();
	public SortedSet<Match> matches = new TreeSet<Match>();
	public Table table;

	@Override
	public String toString()
	{
		return name;
	}

	public Group(String id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public void updateTable()
	{
		table = new Table();
		for (Team team: teams)
			table.rows.add(new TableRow(team, matches));
	}

	public void printTable()
	{
		table = new Table();
		for (Team team : teams)
			table.rows.add(new TableRow(team, matches));
		int pointsPrevious = -1;
		Table subTable = new Table();
		for (TableRow row : table.rows) {
			System.out.println(row);
		}
	}

	@Override
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
}
