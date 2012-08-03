package de.scrum_master.soccer.ranking;

import de.scrum_master.soccer.Table.Row;

public class GoalsDifferenceComparator extends TableRowComparator {
	public GoalsDifferenceComparator() {
		super();
	}

	public GoalsDifferenceComparator(TableRowComparator child, TableRowComparator successor) {
		super(child, successor);
	}

	@Override
	int getComparisonValue(Row row) {
		return -row.getGoalsDifference();
	}
}
