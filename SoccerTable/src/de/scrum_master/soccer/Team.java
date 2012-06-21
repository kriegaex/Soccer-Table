package de.scrum_master.soccer;

class Team implements Comparable<Team>
{
	private String id;
	private String name;

	Team(String id, String name)
	{
		this.setId(id);
		this.setName(name);
	}

	public int compareTo(Team other)
	{
		return id.compareTo(other.id);
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

	@Override
	public String toString()
	{
		return name;
	}

	String getId()
	{
		return id;
	}

	void setId(String id)
	{
		this.id = id;
	}

	String getName()
	{
		return name;
	}

	void setName(String name)
	{
		this.name = name;
	}
}
