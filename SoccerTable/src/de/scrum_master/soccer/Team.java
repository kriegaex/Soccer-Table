package de.scrum_master.soccer;

public class Team implements Comparable<Team>
{
	public String id;
	public String name;

	@Override
	public String toString()
	{
		return name;
	}

	public Team(String id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public int compareTo(Team team)
	{
		return id.compareTo(team.id);
	}

	@Override
	public int hashCode()
	{
		return id == null ? 0 : id.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}
}
