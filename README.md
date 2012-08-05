Soccer table ranking calculator
===============================

This is a tool for calculating sports (e.g. soccer) table rankings according to
different ranking schemes which can be configured easily by chaining criteria
similar to Java I/O streams. In addition to predecessor/successor relations,
criteria can also have parent-child relations. The two relation types work as
follows:

  * A ranking strategy calls its successor (if any) upon equality, i.e. if the
    strategy's ranking result is identical for two or more competitors.
    For example, two competitors have equal points, so the next ranking
    criterion (e.g. goals difference) in the successor chain is called.

  * A ranking strategy calls its child (if any) upon equality after having
    created a sub-table with the results of all equally ranked competitors only.
    For example, two competitors have equal goals difference, so the next
    ranking criterion (e.g. points in direct comparison) in the child chain is
    called.

  * A ranking strategy might have either a child or a successor or both or
    neither. If the strategy has a child as well as a successor, the child is
    called first and the successor only if the child strategy returns equality.

  * Ranking strategies can nested (children) or chained (successors) to any
    depth/length the ranking algorithm requires. Furthermore the same ranking
    criterion can occur multiple times at different nesting levels. For
    example, points can occur on top level and as a child criterion for direct
    comparison in a sub-table.

Currently configuration is done directly in the code, but it would be simple
to read in configurations from the command line or config files. Feel free to
play with the application. Current configuration and extension points are:

  * Class `Config`: There you can edit static constants in order to influence
    the level of detail for printing tables as well as the set of test data to
    be processed. It is also the place to enter more test data or modify the
    existing ones.

  * Class `Ranking`: There you can create new ranking strategies or modify
    existing ones. See comments for [commit f3959256]
    (/kriegaex/Soccer-Table/commit/f395925614be11001901ea780a48a0bb4ef2e667)
    for a brief syntax description.

  * Class `TableRowComparator`: If you need new ranking criteria which do not
    exist as concrete subclasses of this class yet, just create your own
    subclass. Just copy & paste one of the other subclasses, e.g.
    `PointsComparator` or `GoalsDifferenceComparator`, and change method
    `getComparisonValue`. In many cases it is as simple as changing the return
    value, i.e. a one-line edit. Most relevant criteria used in (inter)national
    leagues and tournaments should already exist, though. So most of the time
    you will just use one of the existing instances of `Ranking` or maybe
    configure a new one from existing criteria.
