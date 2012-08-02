package de.scrum_master.soccer.ranking;

import java.util.Comparator;

import de.scrum_master.soccer.Config;
import de.scrum_master.soccer.Match;
import de.scrum_master.soccer.Table;
import de.scrum_master.soccer.Table.Row;

public abstract class TableRowComparator implements Comparator<Table.Row> {
	private TableRowComparator child;
	private TableRowComparator successor;
	private Table subTable;

	protected TableRowComparator(TableRowComparator child, TableRowComparator successor) {
		this.child = child;
		this.successor = successor;
	}

	@Override
	public final int compare(Row row1, Row row2) {
		if (row1 == null && row2 == null)
			return 0;
		if (row1 == null && row2 != null)
			return -1;
		if (row1 != null && row2 == null)
			return 1;
		if (getComparisonValue(row1) < getComparisonValue(row2))
			return -1;
		if (getComparisonValue(row1) > getComparisonValue(row2))
			return 1;
		if (child != null) {
			subTable = new Table(child);
			for (Row row : row1.getOuterTable().getRows()) {
				if (getComparisonValue(row1) == getComparisonValue(row))
					subTable.addTeam(row.getTeam());
			}
			for (Match match : row1.getOuterTable().getMatches()) {
				if (subTable.getRow(match.getHomeTeam()) != null && subTable.getRow(match.getGuestTeam()) != null)
					subTable.addMatch(match);
			}
			subTable.refresh();
			if (Config.DEBUG_STREAM != null) {
				// Attention: sub-table might be printed multiple times, depending on how often 'compare' is called.
				// So this is just for tracing/debugging the system, not anything beautiful.
				subTable.print(Config.DEBUG_STREAM, "    ");
				Config.DEBUG_STREAM.println();
			}
			int result = child.compare(subTable.getRow(row1.getTeam()), subTable.getRow(row2.getTeam()));
			if (result != 0)
				return result;
		}
		if (successor != null)
			return successor.compare(row1, row2);
		return 0;
	}

	/**
	 * @param row Table row to derive comparison value from
	 * @return Numeric value implying a sort order for {@link #compare(Row, Row)}. <b>Important:</b> a smaller value
	 *         means a higher rank to {@code compareTo}, i.e. you need to take care of returning negative values for
	 *         points, goals differences and wherever else the natural sort order is high to low.
	 */
	abstract int getComparisonValue(Row row);
}
