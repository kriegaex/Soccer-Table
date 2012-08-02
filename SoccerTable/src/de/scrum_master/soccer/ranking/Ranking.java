package de.scrum_master.soccer.ranking;

public enum Ranking {
	DIRECT_COMPARISON(
		new PointsComparator(
			new PointsComparator(
				null,
				new GoalsDifferenceComparator(
					null,
					new GoalsForComparator(null, null)
				)
			),
			new GoalsDifferenceComparator(
				null,
				new GoalsForComparator(null, null)
			)
		)
	),
	DIRECT_COMPARISON_WITH_GOALS_AWAY(
		new PointsComparator(
			new PointsComparator(
				null,
				new GoalsDifferenceComparator(
					null,
					new GoalsAwayComparator(null, null)
				)
			),
			new GoalsDifferenceComparator(
				null,
				new GoalsForComparator(null, null)
			)
		)
	),
	GOALS_DIFFERENCE(
		new PointsComparator(
			null,
			new GoalsDifferenceComparator(
				null,
				new GoalsForComparator(
					new PointsComparator(
						null,
						new GoalsDifferenceComparator(
							null,
							new GoalsForComparator(
								null,
								new GoalsAwayComparator(null, null)
							)
						)
					),
					new GoalsAwayComparator(null, null)
				)
			)
		)
	),
	GOALS_AWAY(
		new PointsComparator(
			null,
			new GoalsDifferenceComparator(
				null,
				new GoalsAwayComparator(null, null)
			)
		)
	),
	EURO_2012(DIRECT_COMPARISON.comparator),
	PRIMERA_DIVISION(DIRECT_COMPARISON.comparator),
	BUNDESLIGA(GOALS_DIFFERENCE.comparator),
	CHAMPIONS_LEAGUE_GROUP(DIRECT_COMPARISON_WITH_GOALS_AWAY.comparator),
	CHAMPIONS_LEAGUE_KO(GOALS_AWAY.comparator);

	public final TableRowComparator comparator;

	private Ranking(TableRowComparator comparator) {
		this.comparator = comparator;
	}
}
