package Q4;

import java.util.*;

/**
 * Q4 - Choosing a Map for a Pharmacy Inventory
 * Problem Context: Hospital Pharmacy Stock Lookup & Reports
 * Student: Moksha Dude (192372374)
 */
public class PharmacyInventory {

    static class Medicine {
        String code;
        String name;
        int stock;
        double unitPrice;

        Medicine(String code, String name, int stock, double unitPrice) {
            this.code = code;
            this.name = name;
            this.stock = stock;
            this.unitPrice = unitPrice;
        }

        @Override
        public String toString() {
            return String.format("[%s: %s, Stock: %d, Rs. %.2f]", code, name, stock, unitPrice);
        }
    }

    public static void evaluateMaps(int medicineCount, int lookupCount) {
        System.out.println("==================================================================");
        System.out.printf("Evaluating Maps with %d Medicines & %d Lookups\n", medicineCount, lookupCount);
        System.out.println("==================================================================");

        List<Medicine> dataset = new ArrayList<>();
        Random rnd = new Random(101);
        for (int i = 0; i < medicineCount; i++) {
            String code = String.format("MED%05d", rnd.nextInt(medicineCount * 5));
            dataset.add(new Medicine(code, "Drug_" + i, 50 + rnd.nextInt(500), 10.0 + rnd.nextDouble() * 100));
        }

        // 1. HashMap
        Map<String, Medicine> hashMap = new HashMap<>();
        long t0 = System.nanoTime();
        for (Medicine m : dataset) hashMap.put(m.code, m);
        long tPutHM = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < lookupCount; i++) {
            String q = dataset.get(rnd.nextInt(dataset.size())).code;
            Medicine m = hashMap.get(q);
        }
        long tGetHM = System.nanoTime() - t0;

        // 2. TreeMap (Natural code order)
        Map<String, Medicine> treeMap = new TreeMap<>();
        t0 = System.nanoTime();
        for (Medicine m : dataset) treeMap.put(m.code, m);
        long tPutTM = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < lookupCount; i++) {
            String q = dataset.get(rnd.nextInt(dataset.size())).code;
            Medicine m = treeMap.get(q);
        }
        long tGetTM = System.nanoTime() - t0;

        // 3. LinkedHashMap (Preserves insertion order)
        Map<String, Medicine> linkedHashMap = new LinkedHashMap<>();
        t0 = System.nanoTime();
        for (Medicine m : dataset) linkedHashMap.put(m.code, m);
        long tPutLHM = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < lookupCount; i++) {
            String q = dataset.get(rnd.nextInt(dataset.size())).code;
            Medicine m = linkedHashMap.get(q);
        }
        long tGetLHM = System.nanoTime() - t0;

        System.out.printf("HashMap       : Put = %6.2f ms | %d Lookups = %6.2f ms\n", tPutHM / 1e6, lookupCount, tGetHM / 1e6);
        System.out.printf("TreeMap       : Put = %6.2f ms | %d Lookups = %6.2f ms\n", tPutTM / 1e6, lookupCount, tGetTM / 1e6);
        System.out.printf("LinkedHashMap : Put = %6.2f ms | %d Lookups = %6.2f ms\n", tPutLHM / 1e6, lookupCount, tGetLHM / 1e6);

        // Verification of Evening Reports:
        System.out.println("\nReport Generation Capability:");
        System.out.println("1. Code Order (Alphabetical): TreeMap directly yields keys in sorted order in O(1) start.");
        System.out.println("   HashMap requires copying to a list and sorting: O(N log N) additional overhead.");
        System.out.println("2. Insertion Order: LinkedHashMap maintains a doubly-linked list of entry order in O(1) iteration.");
        System.out.println("   TreeMap destroys insertion order; HashMap makes no order guarantees.");
    }

    public static void main(String[] args) {
        System.out.println("=== Hospital Pharmacy Stock Lookup & Reporting Analysis ===");
        evaluateMaps(100, 100_000);
        evaluateMaps(1_000, 100_000);
        evaluateMaps(5_000, 100_000);

        System.out.println("\n--- Why Hashtable is Obsolete ---");
        System.out.println("Hashtable synchronizes all method calls with coarse-grained locks, crippling concurrent throughput.");
        System.out.println("It does not allow null keys/values and has been superseded by ConcurrentHashMap for concurrency");
        System.out.println("and HashMap for high-performance single-threaded use.");
    }
}
