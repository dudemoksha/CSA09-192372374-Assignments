package Q6;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Q6 - Priority, Order and Fairness in a Queue
 * Problem Context: Hospital Emergency Triage
 * Student: Moksha Dude (192372374)
 */
class Patient {
    final String name;
    final int severity;
    final long arrival;

    Patient(String name, int severity, long arrival) {
        this.name = name;
        this.severity = severity;
        this.arrival = arrival;
    }

    public int getSeverity() { return severity; }
    public long getArrival() { return arrival; }

    @Override
    public String toString() {
        return name + "(S" + severity + ")";
    }
}

public class Triage {
    private long counter = 0;

    // TODO 1: Create a PriorityQueue that serves the lowest severity number first and,
    //   for equal severity, the earliest arrival first
    private final PriorityQueue<Patient> queue = new PriorityQueue<>(
            Comparator.comparingInt(Patient::getSeverity)
                      .thenComparingLong(Patient::getArrival)
    );

    void admit(String name, int severity) {
        queue.add(new Patient(name, severity, counter++));
    }

    Patient next() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        Triage er = new Triage();
        er.admit("Ravi", 3);
        er.admit("Meena", 1);
        er.admit("John", 3);
        er.admit("Asha", 5);
        er.admit("Kumar", 1);
        er.admit("Lata", 2);

        System.out.println("Underlying heap array traversal (unordered): " + er.queue);
        System.out.println("Note: Printing PriorityQueue directly does NOT show treatment order");
        System.out.println("because the internal array is organized as a min-heap, not a fully sorted list.\n");

        StringBuilder order = new StringBuilder();
        while (!er.isEmpty()) {
            if (order.length() > 0) order.append(" -> ");
            order.append(er.next());
        }
        System.out.println("Treatment Order: " + order);
        System.out.println("Expected:        Meena(S1) -> Kumar(S1) -> Lata(S2) -> Ravi(S3) -> John(S3) -> Asha(S5)");

        System.out.println("\n--- Starvation Analysis & Fair-Rule Aging ---");
        System.out.println("Problem: Severity-5 patients may starve indefinitely during continuous critical arrivals.");
        System.out.println("Fair Solution (Aging): Effective priority = Severity - (CurrentTime - ArrivalTime) / AgeThreshold.");
        System.out.println("This dynamically elevates patient priority over time to guarantee a maximum bounded wait time.");
    }
}
