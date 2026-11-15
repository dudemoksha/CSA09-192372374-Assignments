package Q7;

import java.util.*;

/**
 * Q7 - Day-4 Design Challenge: Diagnose, Redesign and Validate
 * Student: Moksha Dude (192372374)
 *
 * A. DIAGNOSIS OF BOTTLENECK:
 * In Q2 (Playlist Editor), ArrayList is used for maintaining track order. When a user frequently
 * performs "Add Next", inserts at the front, or modifies queue heads, ArrayList requires
 * System.arraycopy to shift all N elements right by one position (O(N) time).
 * As playlist size N grows to 50,000+, front inserts degrade catastrophically (quadratic O(N^2)).
 *
 * B. DESIGN OBJECTIVE:
 * Design a Hybrid Playlist Data Structure (Circular Chunked Deque / Gap Buffer) that provides:
 * 1. O(1) amortized insertion/removal at both ends (head and tail).
 * 2. O(1) index-based random access.
 * 3. Near-zero memory shift overhead for head insertions without the high pointer overhead of LinkedList.
 *
 * C. REVISED SOLUTION:
 * Implement a Circular ArrayDeque-backed Playlist with dual-ended indexing.
 */
public class DesignChallengeRedesign {

    // Original Naive Solution
    static class NaivePlaylist {
        private final List<String> songs = new ArrayList<>();

        public void addFront(String song) {
            songs.add(0, song); // O(N) shift
        }

        public void addEnd(String song) {
            songs.add(song); // O(1)
        }

        public String get(int index) {
            return songs.get(index); // O(1)
        }

        public int size() {
            return songs.size();
        }
    }

    // Redesigned Optimized Solution using Circular Ring Buffer
    static class OptimizedDualEndedPlaylist {
        private String[] buffer;
        private int head;
        private int tail;
        private int count;

        public OptimizedDualEndedPlaylist(int capacity) {
            buffer = new String[capacity];
            head = 0;
            tail = 0;
            count = 0;
        }

        private void ensureCapacity() {
            if (count == buffer.length) {
                int newCap = buffer.length * 2;
                String[] newBuf = new String[newCap];
                for (int i = 0; i < count; i++) {
                    newBuf[i] = buffer[(head + i) % buffer.length];
                }
                buffer = newBuf;
                head = 0;
                tail = count;
            }
        }

        public void addFront(String song) {
            ensureCapacity();
            head = (head - 1 + buffer.length) % buffer.length;
            buffer[head] = song;
            count++;
        }

        public void addEnd(String song) {
            ensureCapacity();
            buffer[tail] = song;
            tail = (tail + 1) % buffer.length;
            count++;
        }

        public String get(int index) {
            if (index < 0 || index >= count) throw new IndexOutOfBoundsException();
            return buffer[(head + index) % buffer.length];
        }

        public int size() {
            return count;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Day-4 Design Challenge: Playlist Front-Insert Optimization ===");
        int operations = 30_000;

        System.out.println("\n[D. PREDICTION]");
        System.out.println("Predicting that OptimizedDualEndedPlaylist will execute 30,000 front insertions in < 15ms,");
        System.out.println("whereas ArrayList will take > 500ms due to repeated memory shifting (150+ million element copies).\n");

        // Benchmark Naive ArrayList
        NaivePlaylist naive = new NaivePlaylist();
        long t0 = System.nanoTime();
        for (int i = 0; i < operations; i++) {
            naive.addFront("Track_" + i);
        }
        long tNaive = (System.nanoTime() - t0) / 1_000_000;

        // Benchmark Optimized Dual-Ended Buffer
        OptimizedDualEndedPlaylist optimized = new OptimizedDualEndedPlaylist(1024);
        t0 = System.nanoTime();
        for (int i = 0; i < operations; i++) {
            optimized.addFront("Track_" + i);
        }
        long tOptimized = (System.nanoTime() - t0) / 1_000_000;

        System.out.println("[E. VALIDATION RESULTS]");
        System.out.printf("Naive ArrayList Front Insertions   : %5d ms\n", tNaive);
        System.out.printf("Optimized Dual-Ended Front Inserts : %5d ms\n", tOptimized);
        if (tOptimized > 0) {
            System.out.printf("Speedup Factor: %.1fx faster!\n", (double) tNaive / tOptimized);
        }

        // Verify Correctness
        boolean matches = true;
        for (int i = 0; i < 100; i++) {
            if (!naive.get(i).equals(optimized.get(i))) {
                matches = false;
                break;
            }
        }
        System.out.println("Integrity Check (element matching): " + (matches ? "PASSED" : "FAILED"));

        System.out.println("\n[F. DEFENCE OF THE FINAL DESIGN]");
        System.out.println("The circular ring buffer decouples insertion position from element shifting.");
        System.out.println("Front additions execute in strict O(1) amortized time while preserving O(1) random access");
        System.out.println("via modulo arithmetic `(head + index) % capacity`. Memory overhead remains contiguous");
        System.out.println("and cache-friendly, completely eliminating garbage collector pressure seen with LinkedList nodes.");
    }
}
