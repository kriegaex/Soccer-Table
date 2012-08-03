package de.scrum_master.soccer.ranking;

public enum Ranking {
	// Ranking strategies
	DIRECT_COMPARISON                 ("Points (Points, GoalsDifference, GoalsFor), GoalsDifference, GoalsFor"),
	DIRECT_COMPARISON_WITH_GOALS_AWAY ("Points (Points, GoalsDifference, GoalsAway), GoalsDifference, GoalsFor"),
	GOALS_DIFFERENCE                  ("Points, GoalsDifference, GoalsFor (Points, GoalsDifference, GoalsFor, GoalsAway), GoalsAway"),
	GOALS_AWAY                        ("Points, GoalsDifference, GoalsAway"),
	// Alias names
	EURO_2012                         (DIRECT_COMPARISON                .comparator),
	PRIMERA_DIVISION                  (DIRECT_COMPARISON                .comparator),
	BUNDESLIGA                        (GOALS_DIFFERENCE                 .comparator),
	CHAMPIONS_LEAGUE_GROUP            (DIRECT_COMPARISON_WITH_GOALS_AWAY.comparator),
	CHAMPIONS_LEAGUE_KO               (GOALS_AWAY                       .comparator);

	public final TableRowComparator comparator;

	private Ranking(TableRowComparator comparator) {
		this.comparator = comparator;
	}

	private Ranking(String strategy) {
		this.comparator = TableRowComparator.fromStrategy(strategy);
	}
}
