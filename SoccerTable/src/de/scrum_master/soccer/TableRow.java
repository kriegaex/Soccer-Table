package de.scrum_master.soccer;

import java.util.SortedSet;
import java.util.TreeSet;

public class TableRow implements Comparable<TableRow>
{
	public Team team;
	public SortedSet<Match> matches = new TreeSet<Match>();

	private int points          = 0;  // redundant
	private int matchesPlayed   = 0;
	private int matchesWon      = 0;
	private int matchesDrawn    = 0;
	private int matchesLost     = 0;  // redundant
	private int goalsFor        = 0;
	private int goalsAgainst    = 0;
	private int goalsDifference = 0;  // redundant
	
	public TableRow(Team team, SortedSet<Match> matches)
	{
		this.team = team;
		this.matches = matches;

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

	@Override
	public int compareTo(TableRow other)
	{
		// Attention: sort order is *descending*

		// Points difference
		if (points > other.points)
			return -1;
		else if (points < other.points)
			return 1;

		// Goals difference
		if (goalsDifference > other.goalsDifference)
			return -1;
		else if (goalsDifference < other.goalsDifference)
			return 1;

		// Goals scored
		if (goalsFor > other.goalsFor)
			return -1;
		else if (goalsFor < other.goalsFor)
			return 1;

		// TODO: UEFA coefficient, fair-play score

		// If TableRows are part of a [Sorted]Set, their team IDs differentiate them
		// even if the scores are identical. Otherwise it would be impossible to have
		// two teams with identical scores in one Set.
		return team.id.compareTo(other.team.id);
	}

	@Override
	public String toString()
	{
		return "TableRow [team=" + team + ", points=" + points +
			", matchesPlayed=" + matchesPlayed + ", matchesWon=" + matchesWon +
			", matchesDrawn=" + matchesDrawn + ", matchesLost=" + matchesLost +
			", goalsFor=" + goalsFor + ", goalsAgainst=" + goalsAgainst +
			", goalDifference=" + goalsDifference + "]";
	}
}
