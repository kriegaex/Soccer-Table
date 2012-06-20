package de.scrum_master.soccer;

import java.util.SortedSet;
import java.util.TreeSet;

public class TableRow implements Comparable<TableRow>
{
	public Team team;
	public SortedSet<Match> matches = new TreeSet<Match>();

	public int rank            = 0;  // set by Group while sorting ###
	public int points          = 0;  // redundant
	public int matchesPlayed   = 0;
	public int matchesWon      = 0;
	public int matchesDrawn    = 0;
	public int matchesLost     = 0;  // redundant
	public int goalsFor        = 0;
	public int goalsAgainst    = 0;
	public int goalsDifference = 0;  // redundant
	
	public TableRow(Team team, SortedSet<Match> matches)
	{
		this.team = team;
		this.matches = matches;
		updateMatchStatistics();
	}

	public void updateMatchStatistics()
	{
		rank            = 0;
		points          = 0;
		matchesPlayed   = 0;
		matchesWon      = 0;
		matchesDrawn    = 0;
		matchesLost     = 0;
		goalsFor        = 0;
		goalsAgainst    = 0;
		goalsDifference = 0;

		for (Match match : matches) {
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

	public int compareTo(TableRow other)
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

	@Override
	public String toString()
	{
		return "TableRow [team=" + team + ", rank=" + rank + ", points=" +
			points + ", matchesPlayed=" + matchesPlayed + ", matchesWon=" +
			matchesWon + ", matchesDrawn=" + matchesDrawn + ", matchesLost=" +
			matchesLost + ", goalsFor=" + goalsFor + ", goalsAgainst=" +
			goalsAgainst + ", goalsDifference=" + goalsDifference + "]";
	}
}
