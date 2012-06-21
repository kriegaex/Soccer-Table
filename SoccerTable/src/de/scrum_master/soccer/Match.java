package de.scrum_master.soccer;

import java.util.GregorianCalendar;

class Match implements Comparable<Match>
{
	private String id;
	private GregorianCalendar date;
	private Team homeTeam;
	private Team guestTeam;
	private int homeScore = -1;
	private int guestScore = -1;

	Match(GregorianCalendar date, Team homeTeam, Team guestTeam, int homeScore, int guestScore)
	{
		this.setDate(date);
		this.setHomeTeam(homeTeam);
		this.setGuestTeam(guestTeam);
		this.setHomeScore(homeScore);
		this.setGuestScore(guestScore);
		setId(homeTeam.getId() + "-" + guestTeam.getId());
	}

	Match(GregorianCalendar date, Team homeTeam, Team guestTeam)
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

	void setId(String id)
	{
		this.id = id;
	}

	GregorianCalendar getDate()
	{
		return date;
	}

	void setDate(GregorianCalendar date)
	{
		this.date = date;
	}

	Team getHomeTeam()
	{
		return homeTeam;
	}

	void setHomeTeam(Team homeTeam)
	{
		this.homeTeam = homeTeam;
	}

	Team getGuestTeam()
	{
		return guestTeam;
	}

	void setGuestTeam(Team guestTeam)
	{
		this.guestTeam = guestTeam;
	}

	int getHomeScore()
	{
		return homeScore;
	}

	void setHomeScore(int homeScore)
	{
		this.homeScore = homeScore;
	}

	int getGuestScore()
	{
		return guestScore;
	}

	void setGuestScore(int guestScore)
	{
		this.guestScore = guestScore;
	}
}
