package Q3;

import java.util.*;

/**
 * Detailed investigation of hashCode-equals contract,
 * hash collision behavior, mutable key anomalies, and alternative structures.
 */
public class ContractInvestigation {

    // Faulty Student without hashCode()
    static class BrokenStudent {
        String regNo;
        String name;

        BrokenStudent(String regNo, String name) {
            this.regNo = regNo;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof BrokenStudent)) return false;
            return regNo.equals(((BrokenStudent) o).regNo);
        }
        // hashCode NOT overridden (uses identity hashCode based on memory address)
    }

    public static void main(String[] args) {
        System.out.println("=== Test 1: Faulty Student without hashCode() ===");
        Set<BrokenStudent> brokenSet = new HashSet<>();
        BrokenStudent s1 = new BrokenStudent("192311001", "Arun");
        BrokenStudent s2 = new BrokenStudent("192311001", "Arun");
        brokenSet.add(s1);
        brokenSet.add(s2);
        System.out.println("Duplicates inserted: 2 | Set size: " + brokenSet.size());
        System.out.println("Reason: Distinct identity hashCodes place them in different buckets; equals() is never even checked!\n");

        System.out.println("=== Test 2: Corrected Student with consistent hashCode() ===");
        Set<Student> correctSet = new HashSet<>();
        correctSet.add(new Student("192311001", "Arun"));
        correctSet.add(new Student("192311001", "Arun"));
        System.out.println("Duplicates inserted: 2 | Set size: " + correctSet.size());
        System.out.println("Result: De-duplication successful!\n");

        System.out.println("=== Test 3: Alternative de-duplication with TreeSet ===");
        Set<Student> treeSet = new TreeSet<>(Comparator.comparing(Student::getRegNo));
        treeSet.add(new Student("192311001", "Arun"));
        treeSet.add(new Student("192311002", "Divya"));
        treeSet.add(new Student("192311001", "Arun"));
        System.out.println("TreeSet size: " + treeSet.size() + " | Maintains sorted order O(log N) per scan.");

        System.out.println("\n=== Test 4: Alternative with HashMap<String, Student> ===");
        Map<String, Student> map = new HashMap<>();
        map.put("192311001", new Student("192311001", "Arun"));
        map.put("192311002", new Student("192311002", "Divya"));
        map.put("192311001", new Student("192311001", "Arun"));
        System.out.println("HashMap keys count: " + map.size() + " | Average O(1) time per scan.");
    }
}
