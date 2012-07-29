package de.scrum_master.soccer;

import java.io.PrintStream;
import java.util.GregorianCalendar;
import java.util.SortedSet;
import java.util.TreeSet;

class Config
{
	private SortedSet<Group> groups = new TreeSet<Group>();

	// Real match tables of Primera División (Spain), downloaded from http://fussballdaten.de
	private static final boolean PRIMERA_DIVISION_GROUPS = true;
	// Real Euro 2012 groups
	private static final boolean EURO_2012_GROUPS = false;
	// Dummy groups (Euro 2012, group D, manipulated results) for special ranking situations
	private static final boolean DUMMY_GROUPS = false;

	// Set to null if no sub-table printing is required
	static final PrintStream DEBUG_STREAM = System.out;

	public static void main(String[] args)
	{
		Config config = new Config();
		config.initialiseGroups();
		config.printGroups();
	}

	private void initialiseGroups()
	{
		groups.clear();
		if (PRIMERA_DIVISION_GROUPS) {
			initialiseGroup_PrimeraDivision_2007_08();
			initialiseGroup_PrimeraDivision_2008_09();
			initialiseGroup_PrimeraDivision_2009_10();
			initialiseGroup_PrimeraDivision_2010_11();
			initialiseGroup_PrimeraDivision_2011_12();
		}
		if (EURO_2012_GROUPS) {
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

	private void initialiseGroup_PrimeraDivision_2007_08()
	{
		String rawData =
			"R. Madrid,?,3,2,4,1,2,1,3,1,3,1,4,3,3,1,3,1,2,3,3,0,2,1,2,0,0,1,7,0,2,0,2,0,2,0,1,0,5,2\n" +
			"Villarreal,0,5,?,3,1,3,0,3,2,0,0,1,1,1,1,4,3,3,0,1,0,2,0,0,1,2,0,2,0,1,1,0,0,2,0,2,0,3,0\n" +
			"FC Barcelona,0,1,1,2,?,3,0,2,1,1,0,2,3,2,0,2,1,6,0,3,1,0,0,3,0,0,0,4,1,3,0,1,0,4,1,4,0,5,1\n" +
			"A. Madrid,0,2,3,4,4,2,?,4,3,4,0,1,1,6,3,1,0,1,0,1,2,1,2,1,3,1,0,4,3,3,0,2,0,4,0,1,1,3,0\n" +
			"Sevilla,2,0,2,0,1,1,1,2,?,4,1,1,2,1,4,0,1,3,0,4,1,2,3,3,0,4,1,2,0,4,1,2,1,5,0,3,1,2,1\n" +
			"Santander,0,2,0,2,0,0,0,2,0,3,?,3,1,1,0,1,3,1,0,1,0,1,1,3,0,2,0,2,0,2,0,1,0,2,2,3,2,1,0\n" +
			"Mallorca,1,1,0,1,0,2,1,0,2,3,3,1,?,0,0,1,0,0,2,0,0,2,2,1,1,4,2,4,2,7,1,2,1,3,2,1,1,3,0\n" +
			"Almeria,2,0,1,0,2,2,0,0,1,0,0,1,1,1,?,1,0,1,2,1,1,1,0,1,1,0,2,1,0,0,2,2,0,0,1,1,0,2,1\n" +
			"La Coruna,1,0,0,2,2,0,0,3,2,1,0,1,1,1,0,3,?,2,4,3,0,2,0,1,0,1,1,3,1,0,2,1,2,1,1,3,1,1,0\n" +
			"Valencia,1,5,0,3,0,3,3,1,1,2,1,2,0,3,0,1,2,2,?,0,3,1,2,3,1,2,1,2,1,1,1,3,0,1,0,3,0,0,0\n" +
			"Bilbao,0,1,1,2,1,1,0,2,2,0,0,0,1,2,1,1,2,2,5,1,?,1,0,0,0,1,0,2,0,2,0,0,0,1,1,1,1,1,0\n" +
			"Es Barcelona,2,1,3,0,1,1,0,2,2,4,0,3,2,1,1,3,1,0,2,0,2,1,?,1,2,1,0,0,1,1,2,0,1,1,1,0,0,1,0\n" +
			"Betis,2,1,0,1,3,2,0,2,0,2,1,1,3,0,3,1,0,1,1,2,1,2,2,2,?,3,2,1,1,1,1,0,3,2,1,4,0,0,1\n" +
			"Getafe,0,1,1,3,2,0,1,1,3,2,2,1,3,3,4,2,0,0,0,0,2,0,0,1,1,1,?,0,3,1,1,0,2,0,0,2,0,2,1\n" +
			"Valladolid,1,1,2,0,1,1,1,1,0,0,0,1,1,1,1,0,2,2,0,2,1,2,2,1,0,0,0,0,?,3,1,0,0,2,1,1,4,1,0\n" +
			"Huelva,2,3,0,2,2,2,0,0,1,2,0,0,0,2,1,1,3,2,0,1,1,1,2,1,1,1,1,3,1,1,?,1,0,2,1,4,2,2,0\n" +
			"Osasuna,1,2,3,2,0,0,3,1,1,1,0,2,3,1,2,1,0,1,0,0,2,0,1,2,0,1,0,2,2,2,0,1,?,1,0,2,1,4,1\n" +
			"Saragossa,2,2,4,1,1,2,2,1,2,0,1,1,2,2,1,1,1,0,2,2,1,0,3,3,0,3,1,1,2,3,3,0,2,1,?,3,1,3,0\n" +
			"R. Murcia,1,1,0,1,3,5,1,1,0,0,2,1,1,4,0,1,0,2,1,0,1,2,4,0,0,0,0,3,0,1,1,0,2,0,2,1,?,2,3\n" +
			"Levante,0,2,1,2,1,4,0,1,0,2,1,1,2,2,3,0,0,1,1,5,1,2,1,1,4,3,3,1,0,3,0,2,2,1,2,1,0,0,?\n";
		groups.add(Group.parseCrossTable("PD_2007_08", "Primera División 2007/08", rawData));
	}

	private void initialiseGroup_PrimeraDivision_2008_09()
	{
		String rawData =
			"FC Barcelona,?,2,0,4,0,6,1,3,3,4,0,5,0,6,0,3,1,1,2,5,0,1,1,2,0,3,1,6,0,0,1,1,1,3,2,4,1,2,0\n" +
			"R. Madrid,2,6,?,3,4,1,1,1,0,1,0,1,0,4,3,1,3,2,2,3,0,1,0,3,2,7,1,2,0,3,1,3,2,6,1,4,3,1,0\n" +
			"Sevilla,0,3,2,4,?,1,0,1,0,0,0,1,0,0,1,3,1,2,0,2,1,0,2,4,0,4,3,4,1,1,1,0,1,1,2,1,0,1,0\n" +
			"A. Madrid,4,3,1,2,0,1,?,3,2,1,0,4,1,4,0,2,0,3,2,3,0,4,1,2,3,3,1,1,2,2,4,1,1,2,0,3,0,4,0\n" +
			"Villarreal,1,2,3,2,0,2,4,4,?,3,1,1,0,0,2,2,0,1,0,2,1,2,0,2,0,2,1,0,3,1,1,3,3,2,1,2,1,2,1\n" +
			"Valencia,2,2,3,0,3,1,3,1,3,3,?,4,2,1,1,3,0,2,1,3,2,2,4,2,0,2,3,1,2,1,0,4,1,3,2,4,0,1,1\n" +
			"La Coruna,1,1,2,1,1,3,1,2,3,0,1,1,?,2,0,0,0,1,0,2,0,5,3,3,1,0,3,1,0,0,0,1,1,1,1,1,0,4,1\n" +
			"Malaga,1,4,0,1,2,2,1,1,2,2,0,2,1,1,?,1,1,4,0,3,2,1,0,0,0,1,0,2,1,4,2,2,1,1,1,2,0,0,2\n" +
			"Mallorca,2,1,0,3,0,0,2,0,2,3,3,1,1,1,2,2,?,3,0,2,0,1,0,3,3,0,2,2,0,1,1,2,1,3,3,2,0,2,3\n" +
			"Es Barcelona,1,2,0,2,0,2,2,3,0,0,3,0,3,1,3,0,3,3,?,2,2,1,0,1,0,0,1,1,0,1,0,1,1,2,0,3,4,1,1\n" +
			"Almeria,0,2,1,1,0,1,1,1,3,0,2,2,0,1,1,0,2,1,0,3,?,1,1,2,1,3,1,3,2,2,1,2,1,1,0,2,1,1,0\n" +
			"Santander,1,2,0,2,1,1,5,1,1,1,0,1,0,0,1,1,1,2,3,0,0,2,?,1,1,1,0,3,2,1,1,1,1,2,3,5,0,1,1\n" +
			"Bilbao,0,1,2,5,1,2,1,4,1,4,3,2,0,1,3,2,2,1,1,1,1,3,2,1,?,3,0,2,0,2,0,0,1,1,0,2,0,1,1\n" +
			"Gijon,1,6,0,4,1,0,2,5,0,1,2,3,3,2,2,1,0,1,0,3,1,0,0,2,1,1,?,2,1,2,1,1,2,1,2,3,1,2,1\n" +
			"Valladolid,0,1,1,0,3,2,2,1,0,0,0,1,3,0,1,3,3,0,1,1,2,0,0,1,2,1,1,2,?,0,0,1,0,1,3,0,0,1,1\n" +
			"Osasuna,2,3,2,1,0,0,0,0,1,1,1,0,0,0,2,3,1,0,1,0,3,1,0,1,2,1,1,2,3,3,?,5,2,0,2,2,0,1,2\n" +
			"Getafe,0,1,3,1,0,2,1,2,1,2,0,3,1,2,1,2,4,1,1,1,2,2,0,1,1,1,5,1,1,0,3,0,?,0,0,1,0,2,1\n" +
			"Betis,2,2,1,2,0,0,0,2,2,2,1,2,0,3,1,2,3,0,1,1,2,0,3,1,0,1,2,0,1,1,0,0,2,2,?,3,3,0,1\n" +
			"Numancia,1,0,0,2,0,2,1,1,1,2,2,1,0,1,2,0,0,1,0,0,2,1,2,1,1,2,2,1,4,3,0,0,2,0,2,4,?,1,0\n" +
			"Huelva,0,2,0,1,0,1,0,3,1,2,1,1,1,2,0,4,2,4,0,1,1,1,0,1,1,1,2,0,2,3,1,0,1,1,1,0,3,1,?\n";
		groups.add(Group.parseCrossTable("PD_2008_09", "Primera División 2008/09", rawData));
	}

	private void initialiseGroup_PrimeraDivision_2009_10()
	{
		String rawData =
			"FC Barcelona,?,1,0,3,0,4,0,4,2,2,1,1,1,4,1,5,2,3,0,1,0,2,0,1,0,6,1,3,0,4,0,2,1,4,1,4,0,3,1\n" +
			"R. Madrid,0,2,?,2,0,3,2,2,0,2,0,6,2,5,1,3,2,3,2,3,0,3,2,4,2,6,0,3,1,1,0,2,0,3,0,4,2,5,0\n" +
			"Valencia,0,0,2,3,?,2,0,1,1,2,1,4,1,2,0,2,2,1,0,1,0,3,0,2,0,3,1,2,2,0,0,1,0,1,0,2,0,3,1\n" +
			"Sevilla,2,3,2,1,2,1,?,2,0,1,2,3,2,0,0,3,1,1,1,0,0,1,0,1,0,4,1,3,0,1,2,2,2,3,0,1,1,1,1\n" +
			"Mallorca,0,1,1,4,3,2,1,3,?,3,1,1,0,2,0,4,1,2,0,2,0,2,0,3,1,4,1,3,0,1,0,1,1,4,0,3,0,2,0\n" +
			"Getafe,0,2,2,4,3,1,4,3,3,0,?,3,0,2,0,1,0,0,2,1,1,2,1,2,2,0,2,1,1,0,0,2,1,2,1,1,0,5,1\n" +
			"Villarreal,1,4,0,2,2,0,3,0,1,1,3,2,?,2,1,2,1,1,0,0,0,0,2,1,1,4,2,1,0,2,0,2,1,5,0,3,1,2,0\n" +
			"Bilbao,1,1,1,0,1,2,0,4,1,3,2,2,3,2,?,1,0,2,0,1,0,2,0,4,1,0,0,1,2,4,3,1,1,4,1,2,0,3,2\n" +
			"A. Madrid,2,1,2,3,4,1,2,1,1,1,0,3,1,2,2,0,?,3,0,4,0,1,0,2,2,2,1,3,2,1,1,0,2,3,1,3,1,1,2\n" +
			"La Coruna,1,3,1,3,0,0,1,0,1,0,1,3,1,0,3,1,2,1,?,2,3,1,0,0,0,0,1,1,1,1,1,1,0,3,1,0,2,2,1\n" +
			"Es Barcelona,0,0,0,3,0,2,2,0,1,1,0,2,0,0,1,0,3,0,2,0,?,2,1,2,0,2,1,0,0,0,4,2,1,2,1,1,1,0,0\n" +
			"Osasuna,1,1,0,0,1,3,0,2,0,1,0,0,1,1,0,0,3,0,3,1,2,0,?,1,0,2,0,1,0,1,3,2,2,1,0,1,1,1,1\n" +
			"Almeria,2,2,1,2,0,3,2,3,1,1,1,0,4,2,1,4,1,0,1,1,0,1,2,0,?,1,0,3,1,2,2,1,0,1,1,0,0,1,0\n" +
			"Saragossa,2,4,1,2,3,0,2,1,1,1,3,0,3,3,1,2,1,1,0,0,1,0,0,1,2,1,?,1,3,2,2,2,0,1,0,1,2,0,0\n" +
			"Gijon,0,1,0,0,1,1,0,1,4,1,1,0,1,0,0,0,1,1,2,1,1,0,3,2,1,0,1,1,?,0,1,2,2,0,2,0,2,2,2\n" +
			"Santander,1,4,0,2,0,1,1,5,0,0,1,4,1,2,0,2,1,1,0,1,3,1,1,1,0,2,0,0,2,0,?,0,3,2,0,1,1,3,2\n" +
			"Malaga,0,2,1,1,0,1,1,2,2,1,1,0,2,0,1,1,3,0,0,0,2,1,1,1,1,2,1,1,1,1,1,2,?,1,1,0,0,2,4\n" +
			"Teneriffa,0,5,1,5,0,0,1,2,1,0,3,2,2,2,1,0,1,1,0,1,4,1,2,1,2,2,1,3,2,1,2,1,2,2,?,0,0,1,0\n" +
			"Valladolid,0,3,1,4,2,4,2,1,1,2,0,0,0,2,2,2,0,4,4,0,0,0,1,2,1,1,1,1,2,1,2,1,1,1,3,3,?,0,0\n" +
			"Xerez,0,2,0,3,1,3,0,2,2,1,0,1,2,1,0,1,0,2,0,3,1,1,1,2,2,1,3,2,0,0,2,2,1,1,2,1,3,0,?\n";
		groups.add(Group.parseCrossTable("PD_2009_10", "Primera División 2009/10", rawData));
	}

	private void initialiseGroup_PrimeraDivision_2010_11()
	{
		String rawData =
			"FC Barcelona,?,5,0,2,1,3,1,5,0,2,1,3,0,2,0,2,0,1,0,4,1,3,0,1,0,5,0,2,1,2,1,1,1,0,0,0,2,3,1\n" +
			"R. Madrid,1,1,?,2,0,4,2,1,0,5,1,2,0,3,0,1,0,0,1,7,0,6,1,2,3,4,1,2,0,4,0,1,0,6,1,2,0,8,1\n" +
			"Valencia,0,1,3,6,?,5,0,0,1,2,1,1,1,2,1,3,3,0,0,4,3,1,0,1,1,3,0,0,0,2,0,1,2,2,0,2,0,2,1\n" +
			"Villarreal,0,1,1,3,1,1,?,1,0,4,1,2,0,4,0,4,2,1,1,1,1,2,0,1,0,2,1,0,1,2,1,3,1,1,0,1,0,2,0\n" +
			"Sevilla,1,1,2,6,2,0,3,2,?,4,3,3,1,1,2,1,0,3,0,0,0,1,1,3,1,3,1,4,1,1,3,1,2,0,0,1,0,1,3\n" +
			"Bilbao,1,3,0,3,1,2,0,1,2,0,?,1,2,2,1,1,0,3,0,1,1,2,1,2,1,2,1,3,2,3,0,3,0,1,2,3,0,1,0\n" +
			"A. Madrid,1,2,1,2,1,2,3,1,2,2,0,2,?,2,3,3,0,4,0,0,3,0,0,1,0,3,0,4,1,2,0,3,0,2,0,2,1,1,1\n" +
			"Es Barcelona,1,5,0,1,2,2,0,1,2,3,2,1,2,2,?,1,0,1,0,1,0,1,2,4,0,4,1,2,1,3,1,1,2,2,0,3,0,1,0\n" +
			"Osasuna,0,3,1,0,1,0,1,0,3,2,1,2,2,3,4,0,?,1,0,3,0,3,1,0,0,3,1,1,1,0,0,1,1,0,0,3,0,0,0\n" +
			"Gijon,1,1,0,1,0,2,1,1,2,0,2,2,1,0,1,0,1,0,?,1,2,2,1,0,0,1,3,1,1,2,0,2,0,2,2,2,0,1,0\n" +
			"Malaga,1,3,1,4,1,3,2,3,1,2,1,1,0,3,2,0,0,1,2,0,?,4,1,1,2,1,2,1,0,2,2,3,0,0,0,3,1,3,1\n" +
			"Santander,0,3,1,3,1,1,2,2,3,2,1,2,2,1,0,0,4,1,1,1,1,2,?,2,0,2,1,1,1,0,1,2,0,1,0,0,0,1,0\n" +
			"Saragossa,0,2,1,3,4,0,0,3,1,2,2,1,0,1,1,0,1,3,2,2,3,5,1,1,?,2,1,1,0,2,1,3,2,1,0,0,0,1,0\n" +
			"Sociedad,2,1,1,2,1,2,1,0,2,3,2,0,2,4,1,0,1,0,2,1,0,2,1,0,2,1,?,1,1,1,1,1,0,3,0,1,3,2,0\n" +
			"Levante,1,1,0,0,0,1,1,2,1,4,1,2,2,0,1,0,2,1,0,0,3,1,3,1,1,2,2,1,?,2,0,1,1,1,2,2,1,1,0\n" +
			"Getafe,1,3,2,3,2,4,1,0,1,0,2,2,1,1,1,3,2,0,3,0,0,2,0,1,1,1,0,4,4,1,?,3,0,4,1,3,0,2,0\n" +
			"Mallorca,0,3,0,0,1,2,0,0,2,2,1,0,3,4,0,1,2,0,0,4,2,0,0,1,1,0,2,0,2,1,2,0,?,0,0,3,0,4,1\n" +
			"La Coruna,0,4,0,0,0,2,1,0,3,3,2,1,0,1,3,0,0,0,1,1,3,0,2,0,0,0,2,1,0,1,2,2,2,1,?,1,0,0,2\n" +
			"H. Alicante,0,3,1,3,1,2,2,2,2,0,0,1,4,1,0,0,0,4,0,0,4,1,2,3,2,1,2,1,3,1,0,0,2,2,1,0,?,1,2\n" +
			"Almeria,0,8,1,1,0,3,0,0,0,1,1,3,2,2,3,2,3,2,1,1,1,1,1,1,1,1,2,2,0,1,2,3,3,1,1,1,1,1,?\n";
		groups.add(Group.parseCrossTable("PD_2010_11", "Primera División 2010/11", rawData));
	}

	private void initialiseGroup_PrimeraDivision_2011_12()
	{
		String rawData =
			"R. Madrid,?,1,3,0,0,1,1,4,1,4,2,7,1,4,1,3,0,4,1,5,1,4,2,4,1,5,0,6,2,3,1,5,1,3,0,3,1,4,0\n" +
			"FC Barcelona,1,2,?,5,1,4,1,5,0,5,0,8,0,5,0,0,0,2,0,2,1,4,0,4,2,4,0,4,0,4,0,5,3,5,0,3,1,3,0\n" +
			"Valencia,2,3,2,2,?,2,0,1,0,1,1,4,0,2,2,1,2,1,1,0,1,3,1,4,0,2,1,4,1,1,2,1,0,1,0,4,0,4,3\n" +
			"Malaga,0,4,1,4,1,0,?,0,0,1,0,1,1,3,1,2,1,1,0,1,1,3,2,0,2,2,1,4,2,5,1,4,0,2,1,1,0,3,0\n" +
			"A. Madrid,1,4,1,2,0,0,2,1,?,3,2,0,0,1,1,0,0,2,1,1,1,3,0,0,2,3,1,3,1,3,1,2,0,3,0,4,0,4,0\n" +
			"Levante,1,0,1,2,0,2,3,0,2,0,?,0,2,0,0,1,0,3,0,3,2,1,2,3,1,3,1,3,5,0,0,3,1,1,0,4,0,1,1\n" +
			"Osasuna,1,5,3,2,1,1,1,1,0,1,2,0,?,2,2,0,0,2,1,1,0,0,0,2,1,2,0,0,0,3,0,2,1,2,1,2,1,0,2\n" +
			"Mallorca,1,2,0,2,1,1,0,1,2,1,1,0,1,1,?,0,0,1,1,2,1,1,2,1,0,1,0,1,0,1,0,0,0,4,0,1,2,2,1\n" +
			"Sevilla,2,6,0,2,1,0,2,1,1,1,1,1,2,0,3,1,?,1,2,1,0,3,0,1,2,0,0,5,2,3,0,1,2,1,2,2,1,2,2\n" +
			"Bilbao,0,3,2,2,0,3,3,0,3,0,3,0,3,1,1,0,1,0,?,2,0,0,0,2,3,3,3,1,1,2,1,0,1,1,1,1,1,1,1\n" +
			"Sociedad,0,1,2,2,1,0,3,2,0,4,1,3,0,0,1,0,2,0,1,2,?,0,0,1,1,0,0,4,0,3,0,1,0,1,1,5,1,3,0\n" +
			"Getafe,0,1,1,0,3,1,1,3,3,2,1,1,2,2,1,3,5,1,0,0,1,0,?,1,0,1,1,0,1,0,2,1,0,0,0,2,0,1,1\n" +
			"Betis,2,3,2,2,2,1,0,0,2,2,0,1,1,0,1,0,1,1,2,1,2,3,1,1,?,1,1,0,2,4,3,1,2,3,1,2,0,1,1\n" +
			"Es Barcelona,0,4,1,1,4,0,1,2,4,2,1,2,1,2,1,0,1,1,2,1,2,2,1,0,1,0,?,5,1,0,2,3,0,0,0,0,3,3,1\n" +
			"Vallecano,0,1,0,7,1,2,2,0,0,1,1,2,6,0,0,1,2,1,2,3,4,0,2,0,3,0,0,1,?,0,0,1,0,0,2,1,3,4,2\n" +
			"Saragossa,0,6,1,4,0,1,0,0,1,0,1,0,1,1,0,1,0,1,2,0,2,0,1,1,0,2,2,1,1,2,?,1,0,2,1,2,2,2,1\n" +
			"Granada,1,2,0,1,0,1,2,1,0,0,2,1,1,1,2,2,0,3,2,2,4,1,1,0,0,1,2,1,1,2,1,0,?,1,0,2,1,0,0\n" +
			"Villarreal,1,1,0,0,2,2,2,1,0,1,0,3,1,1,2,0,2,2,2,2,1,1,1,2,1,0,0,0,2,0,2,2,3,1,?,3,0,1,1\n" +
			"Gijon,0,3,0,1,0,1,2,1,1,1,3,2,1,1,2,3,1,0,1,1,1,2,2,1,2,1,1,2,2,1,1,2,2,0,2,3,?,0,0\n" +
			"Santander,0,0,0,2,2,2,1,3,0,0,0,0,2,4,0,3,0,3,0,1,0,0,1,2,1,0,0,1,1,1,1,0,0,1,1,0,1,1,?\n";
		groups.add(Group.parseCrossTable("PD_2011_12", "Primera División 2011/12", rawData));
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
