# Assignment 2 – CO2: Strings, Collections Framework, Generics & Comparators

**Student Name:** Moksha Dude (dudemoksha)  
**Register Number:** 192372374  
**Course:** Programming in Java (CSA09)  
**Course Outcome:** CO2  
**Java Version:** OpenJDK 17.0.21.0.2 LTS  

---

## 📌 Overview
This assignment evaluates engineering decisions in Java collections, string manipulation, hashing contracts, priority queues, and generics. Rather than simply using classes, each question examines the algorithmic performance, memory trade-offs, and failure boundaries under stress workloads.

---

## 📁 Directory Structure

```
Assignment2/
├── Q1/   # String, StringBuilder, StringBuffer Benchmark (Railway e-Tickets)
│   └── TicketBuilderBenchmark.java
├── Q2/   # List Collection Comparison (ArrayList, LinkedList, ArrayDeque)
│   └── PlaylistBenchmark.java
├── Q3/   # hashCode-equals Contract & De-duplication (Attendance System)
│   ├── Attendance.java
│   └── ContractInvestigation.java
├── Q4/   # Map Comparison for Stock & Reports (Hospital Pharmacy)
│   └── PharmacyInventory.java
├── Q5/   # Generic Ranking Component with Comparator Chains (Sports Leaderboard)
│   └── LeaderboardDemo.java
├── Q6/   # PriorityQueue & Fairness/Aging (Emergency Room Triage)
│   └── Triage.java
└── Q7/   # Day-4 Design Challenge: Diagnose, Redesign & Validate
    └── DesignChallengeRedesign.java
```

---

## 🚀 Compilation and Execution Instructions

From the `Assignment2` directory:

```bash
# Q1: Railway Ticket String Performance
javac Q1/TicketBuilderBenchmark.java
java Q1.TicketBuilderBenchmark

# Q2: Playlist Collection Engineering
javac Q2/PlaylistBenchmark.java
java Q2.PlaylistBenchmark

# Q3: hashCode-equals Contract
javac Q3/Attendance.java Q3/ContractInvestigation.java
java Q3.Attendance
java Q3.ContractInvestigation

# Q4: Pharmacy Map Lookup & Ordering
javac Q4/PharmacyInventory.java
java Q4.PharmacyInventory

# Q5: Generic Leaderboard Ranking
javac Q5/LeaderboardDemo.java
java Q5.LeaderboardDemo

# Q6: Emergency Room Triage
javac Q6/Triage.java
java Q6.Triage

# Q7: Design Challenge Redesign
javac Q7/DesignChallengeRedesign.java
java Q7.DesignChallengeRedesign
```

---

## 📋 Summary of Questions & Engineering Decisions

| Question | Assessed Area | Key Result / Engineering Conclusion |
|---|---|---|
| **Q1** | String vs StringBuilder vs StringBuffer | `StringBuilder` is ~30-50x faster than `+` string concatenation on high-volume loads by avoiding intermediate string object churn. Recommend `StringBuilder` for thread-confined ticket construction, and synchronized logging/blocking queues for shared loggers. |
| **Q2** | ArrayList vs LinkedList vs ArrayDeque | `ArrayList` wins decisively on random access ($O(1)$) and iteration due to CPU cache line locality. For queue workloads with front/back operations, `ArrayDeque` outperforms `LinkedList` by a factor of 4x without linked node allocation overhead. |
| **Q3** | hashCode–equals Contract | Overrode `hashCode()` consistent with `equals()` on `regNo`. Demonstrated that omitting `hashCode()` causes identical student keys to scatter across different buckets, causing duplicate entries in `HashSet`. |
| **Q4** | Pharmacy Inventory Map Selection | `HashMap` offers fastest $O(1)$ lookup for pharmacists. `TreeMap` naturally produces alphabetical code reports without re-sorting ($O(\log N)$). `LinkedHashMap` preserves chronological insertion sequence. |
| **Q5** | Generics & Multi-tier Comparator | Designed bounded `Leaderboard<T>` using `Comparator.comparingInt(...).reversed().thenComparing(...)`. Explained lexicographical tie-breaking where Bisons ranks above Panthers. |
| **Q6** | PriorityQueue & Starvation Prevention | Implemented two-key ordering (Severity $\to$ Arrival Timestamp). Analyzed low-priority starvation under heavy triage and designed an Aging rule to escalate priority over wait thresholds. |
| **Q7** | Day-4 Design Challenge | Diagnosed $O(N)$ front-insertion bottleneck in `ArrayList`. Engineered an `OptimizedDualEndedPlaylist` using circular ring buffer with modulo arithmetic, achieving $O(1)$ amortized front insertion (35x speedup) with zero memory shifting. |
