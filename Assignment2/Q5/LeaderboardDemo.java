package Q5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Q5 - Derive a Generic Ranking Component from a Principle
 * Problem Context: Inter-College Sports Leaderboard
 * Student: Moksha Dude (192372374)
 */
class Team {
    final String name;
    final int points;
    final int goalDiff;

    Team(String name, int points, int goalDiff) {
        this.name = name;
        this.points = points;
        this.goalDiff = goalDiff;
    }

    public String getName() { return name; }
    public int getPoints() { return points; }
    public int getGoalDiff() { return goalDiff; }

    @Override
    public String toString() {
        return name + "(" + points + "," + goalDiff + ")";
    }
}

class Leaderboard<T> {
    private final List<T> items = new ArrayList<>();
    private final Comparator<? super T> order;

    Leaderboard(Comparator<? super T> order) {
        this.order = order;
    }

    void add(T item) {
        items.add(item);
    }

    List<T> ranking() {
        // TODO 1: Return a new sorted copy of items using order, without modifying items
        List<T> sortedCopy = new ArrayList<>(items);
        sortedCopy.sort(order);
        return Collections.unmodifiableList(sortedCopy);
    }
}

public class LeaderboardDemo {
    public static void main(String[] args) {
        // TODO 2: Build the comparator: points (high first), then goal difference (high first), then name (A-Z)
        Comparator<Team> byRank = Comparator
                .comparingInt(Team::getPoints).reversed()
                .thenComparing(Comparator.comparingInt(Team::getGoalDiff).reversed())
                .thenComparing(Team::getName);

        Leaderboard<Team> board = new Leaderboard<>(byRank);
        board.add(new Team("Panthers", 9, 4));
        board.add(new Team("Eagles", 9, 6));
        board.add(new Team("Cobras", 6, 1));
        board.add(new Team("Bisons", 9, 4));

        System.out.println(board.ranking());

        System.out.println("\n--- Why Bisons ranks above Panthers ---");
        System.out.println("1. Points tie: Both Eagles, Panthers, and Bisons have 9 points.");
        System.out.println("2. Goal Difference: Eagles has +6, so Eagles is #1. Panthers and Bisons both have +4.");
        System.out.println("3. Name Tie-Breaker: 'Bisons' precedes 'Panthers' lexicographically (A-Z).");
        System.out.println("   Hence Bisons takes rank #2 and Panthers takes rank #3.");
    }
}
