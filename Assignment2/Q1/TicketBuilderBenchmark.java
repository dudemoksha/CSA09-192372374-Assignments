package Q1;

/**
 * Q1 - Building Text Under Load: String, StringBuilder or StringBuffer?
 * Problem Context: Railway e-Ticket Generation
 * Student: Moksha Dude (192372374)
 */
public class TicketBuilderBenchmark {

    // Helper method to simulate fields
    private static String[] generateFields(int count) {
        String[] fields = new String[count];
        for (int i = 0; i < count; i++) {
            fields[i] = "Field_" + i + ":Val" + (1000 + i);
        }
        return fields;
    }

    // Approach 1: String concatenation with +
    public static String buildWithString(String[] fields) {
        String ticket = "=== E-TICKET ===\n";
        for (String f : fields) {
            ticket += f + " | ";
        }
        ticket += "\n=== END TICKET ===";
        return ticket;
    }

    // Approach 2: StringBuilder (unsynchronized, high performance)
    public static String buildWithStringBuilder(String[] fields) {
        StringBuilder sb = new StringBuilder(fields.length * 20);
        sb.append("=== E-TICKET ===\n");
        for (String f : fields) {
            sb.append(f).append(" | ");
        }
        sb.append("\n=== END TICKET ===");
        return sb.toString();
    }

    // Approach 3: StringBuffer (synchronized, thread-safe)
    public static String buildWithStringBuffer(String[] fields) {
        StringBuffer sb = new StringBuffer(fields.length * 20);
        sb.append("=== E-TICKET ===\n");
        for (String f : fields) {
            sb.append(f).append(" | ");
        }
        sb.append("\n=== END TICKET ===");
        return sb.toString();
    }

    public static void runWorkload(String name, int ticketCount, int fieldCount) {
        String[] sampleFields = generateFields(fieldCount);
        System.out.println("--------------------------------------------------");
        System.out.println("Workload: " + name + " (" + ticketCount + " tickets, " + fieldCount + " fields each)");
        System.out.println("--------------------------------------------------");

        Runtime rt = Runtime.getRuntime();

        // 1. String (+)
        rt.gc();
        long memBefore = rt.totalMemory() - rt.freeMemory();
        long t0 = System.nanoTime();
        for (int i = 0; i < ticketCount; i++) {
            buildWithString(sampleFields);
        }
        long tString = (System.nanoTime() - t0) / 1_000_000;
        long memAfter = rt.totalMemory() - rt.freeMemory();
        System.out.printf("String (+)        : %6d ms | Memory delta: %6d KB\n", tString, (memAfter - memBefore) / 1024);

        // 2. StringBuilder
        rt.gc();
        memBefore = rt.totalMemory() - rt.freeMemory();
        t0 = System.nanoTime();
        for (int i = 0; i < ticketCount; i++) {
            buildWithStringBuilder(sampleFields);
        }
        long tBuilder = (System.nanoTime() - t0) / 1_000_000;
        memAfter = rt.totalMemory() - rt.freeMemory();
        System.out.printf("StringBuilder     : %6d ms | Memory delta: %6d KB\n", tBuilder, (memAfter - memBefore) / 1024);

        // 3. StringBuffer
        rt.gc();
        memBefore = rt.totalMemory() - rt.freeMemory();
        t0 = System.nanoTime();
        for (int i = 0; i < ticketCount; i++) {
            buildWithStringBuffer(sampleFields);
        }
        long tBuffer = (System.nanoTime() - t0) / 1_000_000;
        memAfter = rt.totalMemory() - rt.freeMemory();
        System.out.printf("StringBuffer      : %6d ms | Memory delta: %6d KB\n", tBuffer, (memAfter - memBefore) / 1024);
    }

    public static void main(String[] args) {
        System.out.println("=== Railway e-Ticket Generation Benchmark ===");
        System.out.println("Prediction: StringBuilder will outperform String (+) by an order of magnitude");
        System.out.println("due to avoiding intermediate immutable String allocations (O(n) vs O(n^2)).");
        System.out.println("StringBuffer will have slight lock synchronization overhead compared to StringBuilder.\n");

        // Workload 1: Single small ticket (1 ticket, 10 fields)
        runWorkload("Small Ticket", 1, 10);

        // Workload 2: Standard load (10,000 tickets, 50 fields)
        runWorkload("Standard High-Volume", 10_000, 50);

        // Workload 3: Heavy tickets (5,000 tickets, 100 fields)
        runWorkload("Heavy Ticket Payload", 5_000, 100);

        System.out.println("\n--- Architectural Recommendation for Multithreaded Booking Server ---");
        System.out.println("1. Per-Ticket Generation: Use StringBuilder. Each ticket is thread-confined,");
        System.out.println("   so synchronization overhead of StringBuffer is wasteful.");
        System.out.println("2. Shared Booking Log: Use StringBuffer or BlockingQueue/Concurrent logging.");
        System.out.println("   When multiple threads append to a single shared destination, synchronization is required.");
    }
}
