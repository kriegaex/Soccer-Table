package de.scrum_master.soccer;

import java.util.GregorianCalendar;

public class Match implements Comparable<Match>
{
	public String id;
	public GregorianCalendar date;
	public Team homeTeam;
	public Team guestTeam;
	public int homeScore = -1;
	public int guestScore = -1;

	public Match(GregorianCalendar date, Team homeTeam, Team guestTeam, int homeScore, int guestScore)
	{
		this.date = date;
		this.homeTeam = homeTeam;
		this.guestTeam = guestTeam;
		this.homeScore = homeScore;
		this.guestScore = guestScore;
		id = homeTeam.id + "-" + guestTeam.id;
	}

	public Match(GregorianCalendar date, Team homeTeam, Team guestTeam)
	{
		this(date, homeTeam, guestTeam, -1, -1);
	}

	@Override
	public String toString()
	{
		return id;
	}

	@Override
	public int compareTo(Match match)
	{
		int result = date.compareTo(match.date);
		if (result != 0)
			return result;
		return id.compareTo(match.id);
	}
}
