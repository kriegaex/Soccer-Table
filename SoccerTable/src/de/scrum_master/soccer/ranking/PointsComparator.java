package de.scrum_master.soccer.ranking;

import de.scrum_master.soccer.Table.Row;

public class PointsComparator extends TableRowComparator {
	public PointsComparator() {
		super();
	}

	public PointsComparator(TableRowComparator child, TableRowComparator successor) {
		super(child, successor);
	}

	@Override
	int getComparisonValue(Row row) {
		return -row.getPoints();
	}
}
