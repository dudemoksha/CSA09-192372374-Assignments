package Q2;

import java.util.*;

/**
 * Q2 - When Is ArrayList an Appropriate Engineering Choice?
 * Problem Context: Music Playlist Editor & "Up Next" Queue
 * Student: Moksha Dude (192372374)
 */
public class PlaylistBenchmark {

    static class Song {
        String id;
        String title;
        int durationSec;

        Song(String id, String title, int durationSec) {
            this.id = id;
            this.title = title;
            this.durationSec = durationSec;
        }
    }

    public static void benchmarkSize(int size) {
        System.out.println("============================================================");
        System.out.println("Benchmark with Playlist Size N = " + size);
        System.out.println("============================================================");

        List<Song> arrayList = new ArrayList<>();
        List<Song> linkedList = new LinkedList<>();
        ArrayDeque<Song> arrayDeque = new ArrayDeque<>();

        // 1. Add at end
        long t0 = System.nanoTime();
        for (int i = 0; i < size; i++) {
            arrayList.add(new Song("S" + i, "Title " + i, 210));
        }
        long tAddEndAL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < size; i++) {
            linkedList.add(new Song("S" + i, "Title " + i, 210));
        }
        long tAddEndLL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < size; i++) {
            arrayDeque.addLast(new Song("S" + i, "Title " + i, 210));
        }
        long tAddEndAD = System.nanoTime() - t0;

        System.out.printf("Add at End:         AL = %8.3f ms | LL = %8.3f ms | ArrayDeque = %8.3f ms\n",
                tAddEndAL / 1e6, tAddEndLL / 1e6, tAddEndAD / 1e6);

        // 2. Insert at Front (1000 operations)
        int frontOps = Math.min(size, 2000);
        t0 = System.nanoTime();
        for (int i = 0; i < frontOps; i++) {
            arrayList.add(0, new Song("F" + i, "Front", 180));
        }
        long tInsFrontAL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < frontOps; i++) {
            linkedList.add(0, new Song("F" + i, "Front", 180));
        }
        long tInsFrontLL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < frontOps; i++) {
            arrayDeque.addFirst(new Song("F" + i, "Front", 180));
        }
        long tInsFrontAD = System.nanoTime() - t0;

        System.out.printf("Insert at Front:    AL = %8.3f ms | LL = %8.3f ms | ArrayDeque = %8.3f ms (%d ops)\n",
                tInsFrontAL / 1e6, tInsFrontLL / 1e6, tInsFrontAD / 1e6, frontOps);

        // 3. Access k-th element (random access)
        int accessOps = Math.min(size, 5000);
        Random rnd = new Random(42);
        t0 = System.nanoTime();
        for (int i = 0; i < accessOps; i++) {
            Song s = arrayList.get(rnd.nextInt(arrayList.size()));
        }
        long tAccessAL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < accessOps; i++) {
            Song s = linkedList.get(rnd.nextInt(linkedList.size()));
        }
        long tAccessLL = System.nanoTime() - t0;

        System.out.printf("Get(k) Random:      AL = %8.3f ms | LL = %8.3f ms (AL is O(1), LL is O(N))\n",
                tAccessAL / 1e6, tAccessLL / 1e6);

        // 4. Sequential Iteration
        t0 = System.nanoTime();
        long durAL = 0;
        for (Song s : arrayList) {
            durAL += s.durationSec;
        }
        long tIterAL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        long durLL = 0;
        for (Song s : linkedList) {
            durLL += s.durationSec;
        }
        long tIterLL = System.nanoTime() - t0;

        System.out.printf("Iteration (All):    AL = %8.3f ms | LL = %8.3f ms (AL has superior cache locality)\n\n",
                tIterAL / 1e6, tIterLL / 1e6);
    }

    public static void main(String[] args) {
        System.out.println("=== Playlist Collection Engineering Benchmark ===");
        System.out.println("Comparing ArrayList, LinkedList, and ArrayDeque for various access patterns.\n");

        benchmarkSize(1_000);
        benchmarkSize(10_000);
        benchmarkSize(30_000);

        System.out.println("=== Engineering Recommendation ===");
        System.out.println("1. Playlist Editor: Recommend ArrayList when random indexing (jumping to k-th song)");
        System.out.println("   and whole-list iterations dominate. LinkedList has massive node overhead (24-32 bytes per node)");
        System.out.println("   and disastrous cache-miss penalties during traversal.");
        System.out.println("2. 'Up Next' Queue: Strongly recommend ArrayDeque! ArrayDeque provides amortized O(1) insertion");
        System.out.println("   and deletion at both ends using a circular contiguous buffer with no node allocation garbage.");
    }
}
