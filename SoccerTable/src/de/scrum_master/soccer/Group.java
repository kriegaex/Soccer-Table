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

	public void refreshTable()
	{
		// Create new Table without subtable ranking, general points/goals only
		table = new Table();
		for (Team team: teams)
			table.rows.add(new TableRow(team, matches));
	}

	public void printTable()
	{
		refreshTable();
		Table rankedTable = new Table();
		Table subTable = new Table();
		int currentPoints = -1;
		int currentRank = 1;
		for (TableRow row : table.rows) {
			System.out.println("### " + row);
			if (row.getPoints() == currentPoints) {
				subTable.rows.add(new TableRow(row.team, row.matches));
				continue;
			}
			currentRank = integrateSubTable(rankedTable, subTable, currentRank);
			subTable.rows.add(row);
			currentPoints = row.getPoints(); 
		}
		currentRank = integrateSubTable(rankedTable, subTable, currentRank);
		table = rankedTable;
		System.out.println();
		for (TableRow row : table.rows) {
			System.out.println(row);
		}
	}

	private int integrateSubTable(Table rankedTable, Table subTable,
		int currentRank)
	{
		if (subTable.rows.size() > 1)
			System.out.println("  Subtable:");
		SortedSet<Team> subTableTeams = new TreeSet<Team>();
		SortedSet<Match> subTableMatches = new TreeSet<Match>();

		// Gather sub-table teams
		for (TableRow subRow : subTable.rows)
			subTableTeams.add(subRow.team);

		// Gather sub-table matches
		for (Match subMatch : matches) {
			if (subTableTeams.contains(subMatch.homeTeam) && subTableTeams.contains(subMatch.guestTeam))
				subTableMatches.add(subMatch);
		}

		// Update match list for all sub-table rows
		TableRow[] subTableCopy = new TableRow[subTable.rows.size()];
		subTable.rows.toArray(subTableCopy); 
		for (TableRow subRow : subTableCopy) {
			subRow.matches = subTableMatches;
			subRow.updateMatchStatistics();
			if (subTable.rows.size() > 1)
				System.out.println("  " + subRow);
		}
		subTable.rows.clear();
		for (TableRow subRow : subTableCopy) {
			subTable.rows.add(subRow);
		}

		for (TableRow subRow : subTable.rows) {
			TableRow clonedRow = new TableRow(subRow.team, matches);
			clonedRow.setRank(currentRank++);
			// TODO: take care of identical ranks
			rankedTable.rows.add(clonedRow);
		}
		subTable.rows.clear();
		return currentRank;
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
}
