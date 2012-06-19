package de.scrum_master.soccer;

import java.util.GregorianCalendar;
import java.util.SortedSet;
import java.util.TreeSet;

public class Config
{
	public SortedSet<Group> groups = new TreeSet<Group>();

	public static void main(String[] args)
	{
		Config config = new Config();
		config.initialiseGroups();
		config.updateTables();
		config.printGroups();
	}

	private void initialiseGroups()
	{
		groups.clear();
		initialiseGroup_A();
		initialiseGroup_B();
		initialiseGroup_C();
		initialiseGroup_D();
	}

	private void updateTables()
	{
		for (Group group : groups)
			group.updateTable();
	}

	private void printGroups()
	{
		for (Group group : groups) {
			print(group);
			print("");
			for (Team team : group.teams) {
				print(team + " (" + team.id + ")", 1);
			}
			print("");
			for (Match match : group.matches) {
				print(String.format(
					"%tF %tR    %-12s  -  %-12s    %2d : %2d",
					match.date, match.date,
					match.homeTeam, match.guestTeam,
					match.homeScore, match.guestScore
				), 1);
			}
			print("");
			group.printTable();
			print("");
		}
	}

	private void print(Object text, int indent)
	{
		for (int i = 0; i < indent; i++)
			System.out.print("  ");
		System.out.println(text);
	}

	private void print(Object text)
	{
		print(text, 0);
	}

	public Group getGroup(String id)
	{
		for (Group group : groups) {
			if (id.equals(group.id))
				return group;
		}
		return null;
	}

	public Group getGroup(int index)
	{
		int i = 1;
		for (Group group : groups) {
			if (i == index)
				return group;
			i++;
		}
		return null;
	}

	private void initialiseGroup_A()
	{
		Group group = new Group("A", "Gruppe A");
		groups.add(group);

		group.teams.add(new Team("CZE", "Tschechien"));
		group.teams.add(new Team("GRE", "Griechenland"));
		group.teams.add(new Team("POL", "Polen"));
		group.teams.add(new Team("RUS", "Russland"));

		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 8, 18, 0),
			group.getTeam("POL"),
			group.getTeam("GRE"),
			1, 1
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 8, 20, 45),
			group.getTeam("RUS"),
			group.getTeam("CZE"),
			4, 1
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 12, 18, 0),
			group.getTeam("GRE"),
			group.getTeam("CZE"),
			1, 2
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 12, 20, 45),
			group.getTeam("POL"),
			group.getTeam("RUS"),
			1, 1
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 16, 20, 45),
			group.getTeam("GRE"),
			group.getTeam("RUS"),
			1, 0
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 16, 20, 45),
			group.getTeam("CZE"),
			group.getTeam("POL"),
			1, 0
		));
	}

	private void initialiseGroup_B()
	{
		Group group = new Group("B", "Gruppe B");
		groups.add(group);

		group.teams.add(new Team("DEN", "Dänemark"));
		group.teams.add(new Team("GER", "Deutschland"));
		group.teams.add(new Team("NED", "Niederlande"));
		group.teams.add(new Team("POR", "Portugal"));

		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 9, 18, 0),
			group.getTeam("NED"),
			group.getTeam("DEN"),
			0, 1
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 9, 20, 45),
			group.getTeam("GER"),
			group.getTeam("POR"),
			1, 0
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 13, 18, 0),
			group.getTeam("DEN"),
			group.getTeam("POR"),
			2, 3
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 13, 20, 45),
			group.getTeam("NED"),
			group.getTeam("GER"),
			1, 2
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 17, 20, 45),
			group.getTeam("POR"),
			group.getTeam("NED"),
			2, 1
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 17, 20, 45),
			group.getTeam("DEN"),
			group.getTeam("GER"),
			1, 2
		));
	}

	private void initialiseGroup_C()
	{
		Group group = new Group("C", "Gruppe C");
		groups.add(group);

		group.teams.add(new Team("CRO", "Kroatien"));
		group.teams.add(new Team("ESP", "Spanien"));
		group.teams.add(new Team("IRL", "Irland"));
		group.teams.add(new Team("ITA", "Italien"));

		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 10, 18, 0),
			group.getTeam("ESP"),
			group.getTeam("ITA"),
			1, 1
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 10, 20, 45),
			group.getTeam("IRL"),
			group.getTeam("CRO"),
			1, 3
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 14, 18, 0),
			group.getTeam("ITA"),
			group.getTeam("CRO"),
			1, 1
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 14, 20, 45),
			group.getTeam("ESP"),
			group.getTeam("IRL"),
			4, 0
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 18, 20, 45),
			group.getTeam("CRO"),
			group.getTeam("ESP"),
			0, 1
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 18, 20, 45),
			group.getTeam("ITA"),
			group.getTeam("IRL"),
			2, 0
		));
	}

	private void initialiseGroup_D()
	{
		Group group = new Group("D", "Gruppe D");
		groups.add(group);

		group.teams.add(new Team("ENG", "England"));
		group.teams.add(new Team("FRA", "Frankreich"));
		group.teams.add(new Team("SWE", "Schweden"));
		group.teams.add(new Team("UKR", "Ukraine"));

		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 11, 18, 0),
			group.getTeam("FRA"),
			group.getTeam("ENG"),
			1, 1
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 11, 20, 45),
			group.getTeam("UKR"),
			group.getTeam("SWE"),
			2, 1
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 15, 18, 0),
			group.getTeam("UKR"),
			group.getTeam("FRA"),
			0, 2
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 15, 20, 45),
			group.getTeam("SWE"),
			group.getTeam("ENG"),
			2, 3
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 19, 20, 45),
			group.getTeam("SWE"),
			group.getTeam("FRA"),
			2, 0
		));
		group.matches.add(new Match(
			new GregorianCalendar(2012, 6, 19, 20, 45),
			group.getTeam("ENG"),
			group.getTeam("UKR"),
			1, 0
		));
	}
}
