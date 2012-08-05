package de.scrum_master.soccer.ranking;

import java.util.Comparator;

import de.scrum_master.soccer.Table.Row;

/**
 * This class is used to maintain an alphabetic sort order by team ID if table rows are mathematically equal.
 * It wraps around a {@link TableRowComparator}, implementing a decorator pattern.
 *
 * @author Alexander Kriegisch, http://scrum-master.de
 */
public class TeamIdComparator implements Comparator<Row> {
	private TableRowComparator comparator;

	public TeamIdComparator(TableRowComparator comparator) {
		this.comparator = comparator;
	}

	@Override
	public int compare(Row row1, Row row2) {
		int result = comparator.compare(row1, row2);
		if (result != 0)
			return result;
		return row1.getTeam().getId().compareTo(row2.getTeam().getId());
	}
}
