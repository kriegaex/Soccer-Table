package de.scrum_master.soccer;

import java.util.GregorianCalendar;
import java.util.SortedSet;
import java.util.TreeSet;

public class Config
{
	public SortedSet<Group> groups = new TreeSet<Group>();

	static final boolean REAL_GROUPS  = false;  
	static final boolean DUMMY_GROUPS = true;  
	static final boolean DEBUG        = true;  

	public static void main(String[] args)
	{
		Config config = new Config();
		config.initialiseGroups();
		config.printGroups();
	}

	private void initialiseGroups()
	{
		groups.clear();
		if (REAL_GROUPS) {
			initialiseGroup_A();
			initialiseGroup_B();
			initialiseGroup_C();
			initialiseGroup_D();
		}
		if (DUMMY_GROUPS) {
			initialiseGroup_Dummy1();
			initialiseGroup_Dummy2();
			initialiseGroup_Dummy3();
			initialiseGroup_Dummy4();
		}
	}

	private void printGroups()
	{
		for (Group group : groups)
			group.print(System.out);
	}

	private void initialiseGroup_A()
	{
		Group group = new Group("A", "Gruppe A");
		groups.add(group);

		group.addTeam(new Team("CZE", "Tschechien"));
		group.addTeam(new Team("GRE", "Griechenland"));
		group.addTeam(new Team("POL", "Polen"));
		group.addTeam(new Team("RUS", "Russland"));

		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 8, 18, 0),
			group.getTeam("POL"), group.getTeam("GRE"),
			1, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 8, 20, 45),
			group.getTeam("RUS"), group.getTeam("CZE"),
			4, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 12, 18, 0),
			group.getTeam("GRE"), group.getTeam("CZE"),
			1, 2
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 12, 20, 45),
			group.getTeam("POL"), group.getTeam("RUS"),
			1, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 16, 20, 45),
			group.getTeam("GRE"), group.getTeam("RUS"),
			1, 0
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 16, 20, 45),
			group.getTeam("CZE"), group.getTeam("POL"),
			1, 0
		));
	}

	private void initialiseGroup_B()
	{
		Group group = new Group("B", "Gruppe B");
		groups.add(group);

		group.addTeam(new Team("DEN", "Dänemark"));
		group.addTeam(new Team("GER", "Deutschland"));
		group.addTeam(new Team("NED", "Niederlande"));
		group.addTeam(new Team("POR", "Portugal"));

		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 9, 18, 0),
			group.getTeam("NED"), group.getTeam("DEN"),
			0, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 9, 20, 45),
			group.getTeam("GER"), group.getTeam("POR"),
			1, 0
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 13, 18, 0),
			group.getTeam("DEN"), group.getTeam("POR"),
			2, 3
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 13, 20, 45),
			group.getTeam("NED"), group.getTeam("GER"),
			1, 2
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 17, 20, 45),
			group.getTeam("POR"), group.getTeam("NED"),
			2, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 17, 20, 45),
			group.getTeam("DEN"), group.getTeam("GER"),
			1, 2
		));
	}

	private void initialiseGroup_C()
	{
		Group group = new Group("C", "Gruppe C");
		groups.add(group);

		group.addTeam(new Team("CRO", "Kroatien"));
		group.addTeam(new Team("ESP", "Spanien"));
		group.addTeam(new Team("IRL", "Irland"));
		group.addTeam(new Team("ITA", "Italien"));

		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 10, 18, 0),
			group.getTeam("ESP"), group.getTeam("ITA"),
			1, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 10, 20, 45),
			group.getTeam("IRL"), group.getTeam("CRO"),
			1, 3
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 14, 18, 0),
			group.getTeam("ITA"), group.getTeam("CRO"),
			1, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 14, 20, 45),
			group.getTeam("ESP"), group.getTeam("IRL"),
			4, 0
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 18, 20, 45),
			group.getTeam("CRO"), group.getTeam("ESP"),
			0, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 18, 20, 45),
			group.getTeam("ITA"), group.getTeam("IRL"),
			2, 0
		));
	}

	private void initialiseGroup_D()
	{
		Group group = new Group("D", "Gruppe D");
		groups.add(group);

		group.addTeam(new Team("ENG", "England"));
		group.addTeam(new Team("FRA", "Frankreich"));
		group.addTeam(new Team("SWE", "Schweden"));
		group.addTeam(new Team("UKR", "Ukraine"));

		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 11, 18, 0),
			group.getTeam("FRA"), group.getTeam("ENG"),
			1, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 11, 20, 45),
			group.getTeam("UKR"), group.getTeam("SWE"),
			2, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 15, 18, 0),
			group.getTeam("UKR"), group.getTeam("FRA"),
			0, 2
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 15, 20, 45),
			group.getTeam("SWE"), group.getTeam("ENG"),
			2, 3
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 19, 20, 45),
			group.getTeam("SWE"), group.getTeam("FRA"),
			2, 0
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 19, 20, 45),
			group.getTeam("ENG"), group.getTeam("UKR"),
			1, 0
		));
	}

	private void initialiseGroup_Dummy1()
	{
		Group group = new Group("X1", "Gruppe Dummy1");
		groups.add(group);

		group.addTeam(new Team("ENG", "England"));       // #1
		group.addTeam(new Team("FRA", "Frankreich"));    // #1
		group.addTeam(new Team("SWE", "Schweden"));      // #3
		group.addTeam(new Team("UKR", "Ukraine"));       // #3

		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 11, 18, 0),
			group.getTeam("FRA"), group.getTeam("ENG"),
			1, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 11, 20, 45),
			group.getTeam("UKR"), group.getTeam("SWE"),
			1, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 15, 18, 0),
			group.getTeam("UKR"), group.getTeam("FRA"),
			0, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 15, 20, 45),
			group.getTeam("SWE"), group.getTeam("ENG"),
			0, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 19, 20, 45),
			group.getTeam("SWE"), group.getTeam("FRA"),
			0, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 19, 20, 45),
			group.getTeam("ENG"), group.getTeam("UKR"),
			1, 0
		));
	}

	private void initialiseGroup_Dummy2()
	{
		Group group = new Group("X2", "Gruppe Dummy2");
		groups.add(group);

		group.addTeam(new Team("ENG", "England"));       // #1
		group.addTeam(new Team("FRA", "Frankreich"));    // #2
		group.addTeam(new Team("SWE", "Schweden"));      // #2
		group.addTeam(new Team("UKR", "Ukraine"));       // #4

		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 11, 18, 0),
			group.getTeam("FRA"), group.getTeam("ENG"),
			0, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 11, 20, 45),
			group.getTeam("UKR"), group.getTeam("SWE"),
			0, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 15, 18, 0),
			group.getTeam("UKR"), group.getTeam("FRA"),
			0, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 15, 20, 45),
			group.getTeam("SWE"), group.getTeam("ENG"),
			0, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 19, 20, 45),
			group.getTeam("SWE"), group.getTeam("FRA"),
			1, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 19, 20, 45),
			group.getTeam("ENG"), group.getTeam("UKR"),
			1, 0
		));
	}

	private void initialiseGroup_Dummy3()
	{
		Group group = new Group("X3", "Gruppe Dummy3");
		groups.add(group);

		group.addTeam(new Team("ENG", "England"));       // #1
		group.addTeam(new Team("FRA", "Frankreich"));    // #1
		group.addTeam(new Team("SWE", "Schweden"));      // #1
		group.addTeam(new Team("UKR", "Ukraine"));       // #4

		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 11, 18, 0),
			group.getTeam("FRA"), group.getTeam("ENG"),
			0, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 11, 20, 45),
			group.getTeam("UKR"), group.getTeam("SWE"),
			1, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 15, 18, 0),
			group.getTeam("UKR"), group.getTeam("FRA"),
			1, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 15, 20, 45),
			group.getTeam("SWE"), group.getTeam("ENG"),
			1, 0
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 19, 20, 45),
			group.getTeam("SWE"), group.getTeam("FRA"),
			0, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 19, 20, 45),
			group.getTeam("ENG"), group.getTeam("UKR"),
			1, 1
		));
	}

	private void initialiseGroup_Dummy4()
	{
		Group group = new Group("X4", "Gruppe Dummy4");
		groups.add(group);

		group.addTeam(new Team("ENG", "England"));       // #2
		group.addTeam(new Team("FRA", "Frankreich"));    // #1 (goals scored overall)
		group.addTeam(new Team("SWE", "Schweden"));      // #2
		group.addTeam(new Team("UKR", "Ukraine"));       // #4

		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 11, 18, 0),
			group.getTeam("FRA"), group.getTeam("ENG"),
			0, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 11, 20, 45),
			group.getTeam("UKR"), group.getTeam("SWE"),
			1, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 15, 18, 0),
			group.getTeam("UKR"), group.getTeam("FRA"),
			2, 2
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 15, 20, 45),
			group.getTeam("SWE"), group.getTeam("ENG"),
			1, 0
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 19, 20, 45),
			group.getTeam("SWE"), group.getTeam("FRA"),
			0, 1
		));
		group.addMatch(new Match(
			new GregorianCalendar(2012, 6, 19, 20, 45),
			group.getTeam("ENG"), group.getTeam("UKR"),
			1, 1
		));
	}
}
