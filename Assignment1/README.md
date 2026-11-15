# Assignment 1 – CO1: Java Fundamentals and Object-Oriented Programming
## Campus Smart-Parking System (SIMATS Engineering)

**Student Name:** Moksha Dude (dudemoksha)  
**Register Number:** 192372374  
**Course:** Programming in Java (CSA09)  
**Course Outcome:** CO1  
**Java Version:** OpenJDK 17.0.21.0.2 LTS  

---

## 📌 Problem Scenario
The university campus operates a multi-level smart parking facility used by students, staff, and visitors. The facility features multiple floors with designated numbered slots for two-wheelers, cars, and electric vehicles (EVs). When a vehicle enters, the system allocates a suitable free slot, records entry details, and issues a ticket. Upon departure, the slot is released, and parking fees are calculated based on vehicle type, duration, and pass status (monthly passes grant free parking).

---

## 📁 Repository & Task Structure

```
Assignment1/
├── Task01/   # Task 1: Understand the Problem & Identify Candidate Objects
│   └── Task1.java
├── Task02/   # Task 2: Choose Data Representation & Types
│   └── Task2.java
├── Task03/   # Task 3: Control Logic & Slot Allocation
│   ├── FastFacility.java
│   └── ParkingFacility.java
├── Task04/   # Task 4: Classes, Objects & Constructors
│   └── Task4.java
├── Task05/   # Task 5: Static Members & Object Lifecycle
│   └── Task5.java
├── Task06/   # Task 6: Inheritance Hierarchy
│   └── Task6.java
├── Task07/   # Task 7: Method Overriding & Specialization
│   └── Task7.java
├── Task08/   # Task 8: Polymorphism & Fee Calculation
│   ├── FeeDemo.java
│   └── Task8Poly.java
├── Task09/   # Task 9: Abstraction & Abstract Base Classes
│   └── Task9.java
├── Task10/   # Task 10: Packages & Encapsulation
│   └── parking/
│       ├── app/Main.java
│       ├── model/ParkingTicket.java
│       └── service/ParkingService.java
├── Task11/   # Task 11: End-to-End System Integration & Testing
│   └── Task11.java
└── Task12/   # Task 12: Day-4 Design Challenge (Adapting to E-Bikes, Delivery Vans & Jagged Floors)
    ├── Task11.java
    └── Task12.java
```

---

## 🚀 Compilation and Execution Instructions

Ensure JDK 17+ is installed and available in your `PATH`.

```bash
# Navigate to the respective task folder:
cd Assignment1/Task01
javac Task1.java
java Task1

cd ../Task02
javac Task2.java
java Task2

cd ../Task03
javac ParkingFacility.java
java ParkingFacility

cd ../Task04
javac Task4.java
java Task4

cd ../Task05
javac Task5.java
java Task5

cd ../Task06
javac Task6.java
java Task6

cd ../Task07
javac Task7.java
java Task7

cd ../Task08
javac FeeDemo.java
java FeeDemo

cd ../Task09
javac Task9.java
java Task9

cd ../Task10
javac parking/model/*.java parking/service/*.java parking/app/*.java
java parking.app.Main

cd ../Task11
javac Task11.java
java Task11

cd ../Task12
javac Task12.java
java Task12
```

---

## 📋 Task Summaries & Results

| Task | Topic | Key Findings / Result Summary |
|---|---|---|
| **Task 01** | Problem Analysis & Objects | Identified core classes (`Vehicle`, `Slot`, `Ticket`, `ParkingFacility`) and responsibilities. Analyzed OOP vs parallel arrays. |
| **Task 02** | Data Representation | Justified primitive types, jagged arrays for floor-slot representation, and avoided precision loss in billing. |
| **Task 03** | Control Logic & Slot Allocation | Implemented first-fit allocation and slot release logic (`ParkingFacility`). FastFacility optimization. |
| **Task 04** | Classes & Constructors | Encapsulated vehicle attributes and ticket initialization with proper constructor overloading. |
| **Task 05** | Static Members & Lifecycle | Used static counters for thread-safe sequential ticket numbering and overall occupancy tracking. |
| **Task 06** | Inheritance Hierarchy | Built `Vehicle` base class extended by `Car`, `TwoWheeler`, and `ElectricVehicle`. |
| **Task 07** | Method Overriding | Overrode fee calculation methods tailored to specific vehicle characteristics and EV charging additions. |
| **Task 08** | Polymorphism | Dynamic method dispatch enables uniform fee computation across heterogeneous vehicle collections. |
| **Task 09** | Abstraction | Defined abstract `Vehicle` base class requiring concrete subclasses to provide type-specific fee logic. |
| **Task 10** | Packages & Encapsulation | Structured code into modular packages (`parking.model`, `parking.service`, `parking.app`) with appropriate access modifiers. |
| **Task 11** | System Integration & Testing | Full end-to-end simulation covering entry, exit, fee calculation, boundary conditions, and monthly passes. |
| **Task 12** | Day-4 Design Challenge | Extended model for e-bikes (flat rate), delivery vans (time-of-day surcharge), and jagged floors without modifying existing base classes. |
