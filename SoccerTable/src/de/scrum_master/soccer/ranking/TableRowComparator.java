package de.scrum_master.soccer.ranking;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.scrum_master.soccer.Match;
import de.scrum_master.soccer.Table;
import de.scrum_master.soccer.Table.Row;

public abstract class TableRowComparator implements Comparator<Row> {
	private TableRowComparator child;
	private TableRowComparator successor;

	protected TableRowComparator(TableRowComparator child, TableRowComparator successor) {
		this.child = child;
		this.successor = successor;
	}

	protected TableRowComparator() { }

	public static TableRowComparator fromStrategy(String strategy) {
		// Remove optional spaces
		strategy = strategy.replaceAll(" ", "");
		// Anything left to parse?
		if ("".equals(strategy))
			return null;

		// Find via regex match
		//   - \1 = comparator short name
		//   - \2 = optional comma or opening bracket (designating successor or child)
		//   - \3 = optional tail of string
		Pattern pattern = Pattern.compile("(\\w+)([,(]?)(.*)");
		Matcher matcher = pattern.matcher(strategy);
		if (!matcher.matches())
			throw new IllegalArgumentException("illegal strategy (sub)string \"" + strategy + "\"");

		// Create TableRowComparator object via reflection
		TableRowComparator comparator;
		String packageName = TableRowComparator.class.getPackage().getName();
		try {
			// Concrete comparator must be in this package, end with "Comparator" and have a default constructor
			Class<?> comparatorClass = Class.forName(packageName + "." + matcher.group(1) + "Comparator");
			comparator = (TableRowComparator) comparatorClass.getConstructor().newInstance();
		}
		catch (Exception e) {
			// Class/constructor not found or similar
			throw new RuntimeException(e);
		}

		// Case 1: successor, no child
		if (",".equals(matcher.group(2))) {
			// Construct successor strategy from everything following the comma
			comparator.setSuccessor(fromStrategy(matcher.group(3)));
		}
		// Case 2: child + (optional) successor
		else if ("(".equals(matcher.group(2))) {
			// Find closing bracket + optional following comma, taking care of nested brackets
			int openBracketIndex = matcher.end(2);
			int closeBracketIndex = matcher.start(3);
			if (closeBracketIndex == -1)
				throw new IllegalArgumentException("missing closing bracket in strategy (sub)string \"" + strategy + "\"");
			int openBracketCount = 1;
			while (openBracketCount > 0) {
				char character = strategy.charAt(closeBracketIndex);
				if (character == '(')
					openBracketCount++;
				else if (character == ')')
					openBracketCount--;
				if (++closeBracketIndex > strategy.length())
					throw new IllegalArgumentException("missing closing bracket in strategy (sub)string \"" + strategy + "\"");
			}
			// Construct child strategy from everything between the brackets
			comparator.setChild(fromStrategy(strategy.substring(openBracketIndex, closeBracketIndex - 1)));
			// Construct successor strategy from everything following the closing bracket + comma
			if (closeBracketIndex < strategy.length())
				comparator.setSuccessor(fromStrategy(strategy.substring(closeBracketIndex + 1)));
		}
		return comparator;
	}

	public final void setChild(TableRowComparator child) {
		this.child = child;
	}

	public final void setSuccessor(TableRowComparator successor) {
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
			Table mainTable = row1.getOuterTable();
			Table subTable = new Table(child, mainTable);
			for (Row row : mainTable.getRows()) {
				if (getComparisonValue(row1) == getComparisonValue(row))
					subTable.addTeam(row.getTeam());
			}
			for (Match match : mainTable.getMatches()) {
				if (subTable.getRow(match.getHomeTeam()) != null && subTable.getRow(match.getGuestTeam()) != null)
					subTable.addMatch(match);
			}
			subTable.refresh();
			if (mainTable.isShowSubTables()) {
				ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
				PrintStream printStream = new PrintStream(byteArrayStream);
				subTable.print(printStream, true);
				mainTable.addDumpedSubTable(byteArrayStream.toString());
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
