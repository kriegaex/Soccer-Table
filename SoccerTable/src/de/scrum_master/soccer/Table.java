package de.scrum_master.soccer;

import java.io.PrintStream;
import java.util.SortedSet;
import java.util.TreeSet;

public class Table
{
	public SortedSet<Row> rows = new TreeSet<Row>();
	private SortedSet<Team> teams = new TreeSet<Team>();
	private SortedSet<Match> matches = new TreeSet<Match>();

	public void removeRanks()
	{
		for (Object obj : rows.toArray()) {
			Row row = (Row) obj;
			row.setRank(0);
			refreshRow(row);
		}
	}

	public void printTable(PrintStream out, String indent)
	{
		printTable(out, indent, false);
	}

	public void printTable(PrintStream out, String indent, boolean isSubTable)
	{
		if (! isSubTable) {
			removeRanks();
			Table subTable = new Table();
			int currentPoints = rows.first().getPoints();
			int currentRank = 1;
			for (Row row : rowsToSortedArray()) {
				if (row.getPoints() == currentPoints) {
					subTable.addTeam(row.team);
					continue;
				}
				currentRank = calculateSubTable(out, indent, subTable, currentRank);
				currentPoints = row.getPoints();
				subTable = new Table();
				subTable.addTeam(row.team);
			}
			calculateSubTable(out, indent, subTable, currentRank);
		}
		out.println(indent + "Pos  Team          Pld    W    D    L   GF   GA   GD  Pts");
		out.println(indent + "---  ------------  ---  ---  ---  ---  ---  ---  ---  ---");
		for (Row row : rows)
			row.print(out, indent);
	}

	private int calculateSubTable(PrintStream out, String indent, Table subTable, int currentRank)
	{
		for (Match match : matches) {
			try {
				subTable.addMatch(match);
			}
			catch (IllegalArgumentException e) {
				// Only add matches between sub-table teams, ignore others
			}
		}

		if (Config.DEBUG && subTable.rows.size() > 1) {
			out.println(indent + "  ======================= Sub-table =======================");
			subTable.printTable(out, indent + "  ", true);
			out.println(indent + "  =========================================================\n");
		}

		Row previousSubRow;
		Row subRow = null;
		int skipRank = 0;
		for (Object obj : subTable.rows.toArray()) {
			previousSubRow = subRow;
			subRow = (Row) obj;
			// Take care of identical ranks
			if (previousSubRow != null && subRow.matchDataEquals(previousSubRow)) {
				currentRank--;
				skipRank++;
			}
			Row row = getRow(subRow.team);
			row.setRank(currentRank++);
			refreshRow(row);
		}
		return currentRank + skipRank;
	}

	private Row[] rowsToSortedArray()
	{
		Row[] rowArray = new Row[rows.size()];
		int i = 0;
		for (Row row : rows)
			rowArray[i++] = row;
		return rowArray;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder
			.append("Table [rows=")
			.append(rows).append("]");
		return builder.toString();
	}

	public Table(Iterable<Team> teams, Iterable<Match> matches)
	{
		for (Team team : teams)
			addTeam(team);
		for (Match match : matches)
			addMatch(match);
	}

	public Table() { }

	public void addTeam(Team team)
	{
		teams.add(team);
		if (getRow(team) == null)
			rows.add(new Row(team));
	}

	private Row getRow(Team team)
	{
		for (Row row : rows) {
			if (row.team.equals(team))
				return row;
		}
		return null;
	}

	public void addMatch(Match match)
	{
		if (! teams.contains(match.homeTeam))
			throw new IllegalArgumentException("Home team \"" + match.homeTeam + "\" not found in team list");
		if (! teams.contains(match.guestTeam))
			throw new IllegalArgumentException("Guest  team \"" + match.guestTeam + "\" not found in team list");
		if (matches.add(match)) {
			refreshRow(getRow(match.homeTeam));
			refreshRow(getRow(match.guestTeam));
		}
	}

	private void refreshRow(Row row)
	{
		rows.remove(row);
		row.updateMatchStatistics();
		rows.add(row);
	}

	public class Row implements Comparable<Row>
	{
		public Team team;

		public int rank            = 0;  // set by Group while sorting ###
		public int points          = 0;  // redundant
		public int matchesPlayed   = 0;
		public int matchesWon      = 0;
		public int matchesDrawn    = 0;
		public int matchesLost     = 0;  // redundant
		public int goalsFor        = 0;
		public int goalsAgainst    = 0;
		public int goalsDifference = 0;  // redundant
		
		public Row(Team team)
		{
			this.team = team;
			updateMatchStatistics();
		}

		public void updateMatchStatistics()
		{
			//### rank            = 0;
			points          = 0;
			matchesPlayed   = 0;
			matchesWon      = 0;
			matchesDrawn    = 0;
			matchesLost     = 0;
			goalsFor        = 0;
			goalsAgainst    = 0;
			goalsDifference = 0;

			for (Match match : Table.this.matches) {
				if (
					match.homeScore < 0 || match.guestScore < 0 ||
					(! team.equals(match.homeTeam) && ! team.equals(match.guestTeam))
				)
					continue;

				matchesPlayed++;
				if (team.equals(match.homeTeam)) {
					goalsFor = goalsFor + match.homeScore; 
					goalsAgainst = goalsAgainst + match.guestScore;
					if (match.homeScore > match.guestScore) {
						matchesWon++;
						points = points + 3;
					}
					else if (match.homeScore == match.guestScore) {
						matchesDrawn++;
						points++;
					}
					else {
						matchesLost++;
					}
				}
				else {
					goalsFor = goalsFor + match.guestScore; 
					goalsAgainst = goalsAgainst + match.homeScore;
					if (match.homeScore < match.guestScore) {
						matchesWon++;
						points = points + 3;
					}
					else if (match.homeScore == match.guestScore) {
						matchesDrawn++;
						points++;
					}
					else {
						matchesLost++;
					}
				}
				goalsDifference = goalsFor - goalsAgainst;
			}
		}

		public int compareTo(Row other)
		{
			// Sort order: ascending by rank (if != 0), then descending by points/goals

			// Rank (if calculated already)
			if (rank > 0 && other.rank > 0) {
				if (rank < other.rank)
					return -1;
				if (rank > other.rank)
					return 1;
			} else {
				// Points difference
				if (points > other.points)
					return -1;
				if (points < other.points)
					return 1;

				// Goals difference
				if (goalsDifference > other.goalsDifference)
					return -1;
				if (goalsDifference < other.goalsDifference)
					return 1;

				// Goals scored
				if (goalsFor > other.goalsFor)
					return -1;
				if (goalsFor < other.goalsFor)
					return 1;

				// TODO: UEFA coefficient, fair-play score
			}

			// If TableRows are part of a [Sorted]Set, their team IDs differentiate them
			// even if the scores are identical. Otherwise it would be impossible to have
			// two teams with identical scores in one Set.
			return team.id.compareTo(other.team.id);
		}

		public boolean matchDataEquals(Row other)
		{
			if (this == other)
				return true;
			if (other == null)
				return false;
			if (matchesPlayed != other.matchesPlayed)
				return false;
			if (matchesWon != other.matchesWon)
				return false;
			if (matchesDrawn != other.matchesDrawn)
				return false;
			if (goalsFor != other.goalsFor)
				return false;
			if (goalsAgainst != other.goalsAgainst)
				return false;
			return true;
		}

		public int getRank()
		{
			return rank;
		}

		public void setRank(int rank)
		{
			this.rank = rank;
		}

		public int getPoints()
		{
			return points;
		}

		public void print(PrintStream out, String indent)
		{
			out.println(String.format(
				indent + "%3d  %-12s  %3d  %3d  %3d  %3d  %3d  %3d  %3d  %3d",
				rank, team.name,
				matchesPlayed, matchesWon, matchesDrawn, matchesLost,
				goalsFor, goalsAgainst, goalsDifference,
				points
			));
		}

		@Override
		public String toString()
		{
			return "Table.Row [team=" + team + ", rank=" + rank + ", points=" +
				points + ", matchesPlayed=" + matchesPlayed + ", matchesWon=" +
				matchesWon + ", matchesDrawn=" + matchesDrawn + ", matchesLost=" +
				matchesLost + ", goalsFor=" + goalsFor + ", goalsAgainst=" +
				goalsAgainst + ", goalsDifference=" + goalsDifference + "]";
		}
	}
}
