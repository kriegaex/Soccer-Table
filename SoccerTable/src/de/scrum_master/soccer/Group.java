package de.scrum_master.soccer;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import de.scrum_master.soccer.ranking.TableRowComparator;

class Group implements Comparable<Group> {
	private String id;
	private String name;
	private SortedSet<Team>  teams   = new TreeSet<Team>();
	private SortedSet<Match> matches = new TreeSet<Match>();
	private Table table;

	Group(String id, String name, TableRowComparator comparator) {
		this.id = id;
		this.name = name;
		table = new Table(comparator);
	}

	/**
	 * Create a Group from raw text data representing a cross-table of each team's match results against
	 * each other team, i.e. the usual two matches per pair, one home and one guest match.
	 *
	 * @param id unique group/league ID
	 * @param name full group/league name
	 * @param rawData cross-table raw data (comma-separated) with one row/line per team. The first
	 * table column is the team name, the remaining ones are the home match results, e.g.
	 * <pre>
	 * Team A,?,3,0,2,1
	 * Team B,1,2,?,2,2
	 * Team C,0,1,2,3,?
	 * </pre>
	 * Each "?" is a dummy value because no team plays against itself. In the above example Team A
	 * beat B at home by 3-0 and as guest team by 1-2. Team C lost against A at home by 0-1 and also
	 * 2-1 as guest team. Team B tied C at home by 2-2.
	 * <p>
	 * <b>Please note:</b> The simplified cross-table reformat does <i>not</i> contain any match dates,
	 * so this method creates synthetic, identical ones without any real-world significance.
	 * @param comparator table row comparator to use for ranking the resulting table
	 * @return the Group generated from the cross-table
	 */
	static Group parseCrossTable(String id, String name, String rawData, TableRowComparator comparator) {
		Group group = new Group(id, name, comparator);

		// Determine teams
		List<Team> teams = new ArrayList<Team>();
		Scanner scanner = new Scanner(rawData);
		scanner.useDelimiter("[,\n]");
		while (scanner.hasNext()) {
			String teamName = scanner.next();
			group.addTeam(new Team(teamName, teamName));
			teams.add(group.getTeam(teamName));
			scanner.nextLine();
		}
		scanner.close();

		// Parse match results
		scanner = new Scanner(rawData);
		scanner.useDelimiter("[,\n]");
		Calendar dummyMatchDate = Calendar.getInstance();
		for (Team homeTeam : teams) {
			scanner.next();
			for (Team guestTeam : teams) {
				if (homeTeam.equals(guestTeam)) {
					scanner.next();
					continue;
				}
				group.addMatch(new Match(
					dummyMatchDate,
					homeTeam, guestTeam,
					scanner.nextInt(), scanner.nextInt()
				));
			}
			scanner.nextLine();
		}
		scanner.close();
		return group;
	}

	public int compareTo(Group group) {
		return id.compareTo(group.id);
	}

	void addTeam(Team team) {
		teams.add(team);
		table.addTeam(team);
	}

	void addMatch(Match match) {
		matches.add(match);
		table.addMatch(match);
	}

	Team getTeam(String id) {
		for (Team team : teams) {
			if (id.equals(team.getId()))
				return team;
		}
		return null;
	}

	Match getMatch(String id) {
		for (Match match : matches) {
			if (id.equals(match.getId()))
				return match;
		}
		return null;
	}

	void print(PrintStream out) {
		print(out, false, false);
	}

	void print(PrintStream out, boolean showTeams, boolean showMatches) {
		out.println(name);
		out.println();
		if (showTeams) {
			for (Team team : teams)
				out.println("  " + team + " (" + team.getId() + ")");
			out.println();
		}
		if (showMatches) {
			for (Match match : matches) {
				out.println(String.format(
					"  %tF %tR    %-12s  -  %-12s    %2d : %2d",
					match.getDate(), match.getDate(),
					match.getHomeTeam(), match.getGuestTeam(),
					match.getHomeScore(), match.getGuestScore()
				));
			}
			out.println();
		}
		table.print(out, "  ");
		out.println();
	}

	@Override
	public String toString() {
		return name;
	}
}
