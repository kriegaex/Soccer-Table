package de.scrum_master.soccer;

import java.io.PrintStream;
import java.util.SortedSet;
import java.util.TreeSet;

import de.scrum_master.util.UpdateableTreeSet;
import de.scrum_master.util.UpdateableTreeSet.Updateable;

public class Table
{
	private UpdateableTreeSet<Row>   rows    = new UpdateableTreeSet<Row>();
	private SortedSet<Team>          teams   = new TreeSet<Team>();
	private SortedSet<Match>         matches = new TreeSet<Match>();

	public Table() { }

	public Table(Iterable<Team> teams, Iterable<Match> matches) {
		for (Team team : teams)
			addTeam(team);
		for (Match match : matches)
			addMatch(match);
	}

	public void calculate() {
		// Reset explicit ranking to enable automatic ranking via compareTo
		clearRanks();

		// Main ranking + sub-tables
		Table subTable = new Table();
		int currentPoints = rows.first().points;
		int currentRank = 1;
		int originalOrder = 0;
		for (Row row : rows) {
			// Only needed for subTable -> no need to markForUpdate here
			row.mainTableOrder = originalOrder++;
			if (row.points == currentPoints) {
				subTable.addTeam(row.team);
				continue;
			}
			// Sub-row ranking (direct comparison)
			currentRank = calculateSubTable(subTable, currentRank);
			currentPoints = row.points;
			subTable = new Table();
			subTable.addTeam(row.team);
		}
		// Sub-row ranking (direct comparison), continued
		calculateSubTable(subTable, currentRank);
		rows.updateAllMarked();

		// Fine ranking for identical sub-table ranks: goal difference and goals scored *overall*
		Row previousRow = null;
		for (Row row : rows) {
			if (previousRow != null) {
				if (row.rank < previousRow.rank) {
					row.rank = previousRow.rank;
					rows.markForUpdate(row);
				}
				if (row.rank == previousRow.rank && ! row.matchDataEquals(previousRow)) {
					row.rank++;
					rows.markForUpdate(row);
				}
			}
			previousRow = row;
		}
		rows.updateAllMarked();
	}

	public void clearRanks() {
		for (Row row : rows) {
			row.rank = 0;
			// Clearing the "main table order" helper field is optional, but it might be safer to do it anyway.
			row.mainTableOrder = 0;
			rows.markForUpdate(row);
		}
		rows.updateAllMarked();
	}

	private int calculateSubTable(Table subTable, int currentRank) {
		for (Match match : matches) {
			try {
				subTable.addMatch(match);
			}
			catch (IllegalArgumentException e) {
				// Only add matches between sub-table teams, ignore others
			}
		}

		PrintStream out = Config.DEBUG_STREAM;
		if (out != null && subTable.rows.size() > 1) {
			out.println("    ======================= Sub-table =======================");
			subTable.print(out, "    ");
			out.println("    =========================================================\n");
		}

		Row previousSubRow = null;
		int skipRank = 0;
		for (Row subRow : subTable.rows) {
			// Take care of identical ranks
			if (previousSubRow != null && subRow.matchDataEquals(previousSubRow)) {
				currentRank--;
				skipRank++;
			}
			Row row = getRow(subRow.team);
			row.rank = currentRank++;
			rows.markForUpdate(row);
			previousSubRow = subRow;
		}
		return currentRank + skipRank;
	}

	public void print(PrintStream out, String indent) {
		out.println(indent + "Pos  Team          Pld    W    D    L   GF   GA   GD  Pts");
		out.println(indent + "---  ------------  ---  ---  ---  ---  ---  ---  ---  ---");
		for (Row row : rows)
			row.print(out, indent);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder
			.append("Table [rows=")
			.append(rows).append("]");
		return builder.toString();
	}

	public void addTeam(Team team) {
		teams.add(team);
		if (getRow(team) == null)
			rows.add(new Row(team));
	}

	public void addMatch(Match match) {
		if (! teams.contains(match.getHomeTeam()))
			throw new IllegalArgumentException("Home team \"" + match.getHomeTeam() + "\" not found in team list");
		if (! teams.contains(match.getGuestTeam()))
			throw new IllegalArgumentException("Guest  team \"" + match.getGuestTeam() + "\" not found in team list");
		if (matches.add(match)) {
			rows.update(getRow(match.getHomeTeam()));
			rows.update(getRow(match.getGuestTeam()));
		}
	}

	private Row getRow(Team team) {
		for (Row row : rows) {
			if (row.team.equals(team))
				return row;
		}
		return null;
	}

	class Row implements Comparable<Row>, Updateable {
		private Team team;

		// Rank calculated from match statistics in main & sub-tables
		private int rank            = 0;

		// Match statistics
		private int points          = 0;  // redundant
		private int matchesPlayed   = 0;
		private int matchesWon      = 0;
		private int matchesDrawn    = 0;
		private int matchesLost     = 0;  // redundant
		private int goalsFor        = 0;
		private int goalsAgainst    = 0;
		private int goalsDifference = 0;  // redundant

		// Helper field for remembering original order in main table if sub-table ranking
		// results in multiple identical ranks and we have to consider overall goals.
		private int mainTableOrder  = 0;

		private Row(Team team) {
			this.team = team;
			update();
		}

		/**
		 * @param newValue is currently ignored and only needed to fulfil the
		 * {@link de.scrum_master.util.UpdateableTreeSet.Updateable Updateable} contract
		 */
		public void update(Object newValue) {
			update();
		}

		public void update() {
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
					match.getHomeScore() < 0 || match.getGuestScore() < 0 ||
					(! team.equals(match.getHomeTeam()) && ! team.equals(match.getGuestTeam()))
				)
					continue;

				matchesPlayed++;
				if (team.equals(match.getHomeTeam())) {
					goalsFor = goalsFor + match.getHomeScore();
					goalsAgainst = goalsAgainst + match.getGuestScore();
					if (match.getHomeScore() > match.getGuestScore()) {
						matchesWon++;
						points = points + 3;
					}
					else if (match.getHomeScore() == match.getGuestScore()) {
						matchesDrawn++;
						points++;
					}
					else {
						matchesLost++;
					}
				}
				else {
					goalsFor = goalsFor + match.getGuestScore();
					goalsAgainst = goalsAgainst + match.getHomeScore();
					if (match.getHomeScore() < match.getGuestScore()) {
						matchesWon++;
						points = points + 3;
					}
					else if (match.getHomeScore() == match.getGuestScore()) {
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

		public int compareTo(Row other) {
			if (this.equals(other))
				return 0;

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

			// In sub-tables we want to keep up the main table's original sort order
			// if everything else is identical.
			if (mainTableOrder < other.mainTableOrder)
				return -1;
			if (mainTableOrder > other.mainTableOrder)
				return 1;

			// If TableRows are part of a [Sorted]Set, their team IDs differentiate them
			// even if the scores are identical. Otherwise it would be impossible to have
			// two teams with identical scores in one Set.
			return team.getId().compareTo(other.team.getId());
		}

		private boolean matchDataEquals(Row other) {
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

		private void print(PrintStream out, String indent) {
			out.println(String.format(
				indent + "%3d  %-12s  %3d  %3d  %3d  %3d  %3d  %3d  %3d  %3d",
				rank, team.getName(),
				matchesPlayed, matchesWon, matchesDrawn, matchesLost,
				goalsFor, goalsAgainst, goalsDifference,
				points
			));
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder
				.append("Row [team=").append(team).append(", rank=")
				.append(rank).append(", points=").append(points)
				.append(", matchesPlayed=").append(matchesPlayed)
				.append(", matchesWon=").append(matchesWon)
				.append(", matchesDrawn=").append(matchesDrawn)
				.append(", matchesLost=").append(matchesLost)
				.append(", goalsFor=").append(goalsFor)
				.append(", goalsAgainst=").append(goalsAgainst)
				.append(", goalsDifference=").append(goalsDifference)
				.append(", mainTableOrder=").append(mainTableOrder).append("]");
			return builder.toString();
		}
	}
}
