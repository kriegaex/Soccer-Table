package de.scrum_master.soccer.ranking;

import de.scrum_master.soccer.Table.Row;

public class GoalsForComparator extends TableRowComparator {
	public GoalsForComparator() {
		super();
	}

	public GoalsForComparator(TableRowComparator child, TableRowComparator successor) {
		super(child, successor);
	}

	@Override
	int getComparisonValue(Row row) {
		return -row.getGoalsFor();
	}
}
