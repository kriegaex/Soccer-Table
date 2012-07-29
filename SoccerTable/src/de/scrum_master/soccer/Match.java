package de.scrum_master.soccer;

import java.util.Calendar;

class Match implements Comparable<Match>
{
	private String id;
	private Calendar date;
	private Team homeTeam;
	private Team guestTeam;
	private int homeScore = -1;
	private int guestScore = -1;

	Match(Calendar date, Team homeTeam, Team guestTeam, int homeScore, int guestScore)
	{
		this.date = date;
		this.homeTeam = homeTeam;
		this.guestTeam = guestTeam;
		this.homeScore = homeScore;
		this.guestScore = guestScore;
		this.id = homeTeam.getId() + "-" + guestTeam.getId();
	}

	Match(Calendar date, Team homeTeam, Team guestTeam)
	{
		this(date, homeTeam, guestTeam, -1, -1);
	}

	public int compareTo(Match match)
	{
		int result = getDate().compareTo(match.getDate());
		if (result != 0)
			return result;
		return getId().compareTo(match.getId());
	}

	@Override
	public String toString()
	{
		return getId();
	}

	String getId()
	{
		return id;
	}

	Calendar getDate()
	{
		return date;
	}

	Team getHomeTeam()
	{
		return homeTeam;
	}

	Team getGuestTeam()
	{
		return guestTeam;
	}

	int getHomeScore()
	{
		return homeScore;
	}

	int getGuestScore()
	{
		return guestScore;
	}
}
