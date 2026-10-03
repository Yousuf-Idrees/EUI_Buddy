# EUI-Buddy: Scala Conversational Assistant

**EUI-Buddy** is a functional rule-based conversational chatbot implemented in Scala 3 for prospective and current students of Egypt University of Informatics (EUI). The system provides information on university faculties, degree programs, study plans, course prerequisites, contact information, and campus locations. It also includes an interactive GPA calculation tool, string tokenization with fuzzy matching (Levenshtein Distance), and session interaction analytics.

---

## Key Features

- **Faculty & Program Navigation:** Detailed inquiries regarding EUI's four primary faculties:
  - Faculty of Computer Science
  - Faculty of Business Informatics
  - Faculty of Engineering
  - Faculty of Digital Arts & Design
- **NLP & Fuzzy Tokenization:** Custom tokenization pipeline featuring contraction expansion, stop-word removal, sliding phrase detection, and **Levenshtein Distance** fuzzy matching for typos and varied input phrasing.
- **Dynamic GPA Calculator:** Interactive prompt parsing credit hours and letter grades (A+ through F) to compute weighted Grade Point Averages.
- **Course Prerequisites & Study Plans:** Query academic programs by year and inspect course dependencies across faculties.
- **Interaction Logging & Analytics:** Tracks user intents throughout the session and provides on-demand usage statistics via administrative command.

---

## System Architecture & NLP Workflow

EUI-Buddy uses pattern matching, intent classification, and fuzzy token matching to route user inputs:

```text
User Input Text
  └──> Tokenizer & Contraction Normalization
        └──> Stop-Word Removal & Phrase Extractor
              └──> Intent Detector (Pattern Matching + Fuzzy Match)
                    ├──> Information Responses (Faculty, Program, Contact, Location)
                    ├──> Prerequisite / Study Plan Engine
                    ├──> Interactive GPA Calculation Engine
                    └──> Session Interaction Logger (Analytics Engine)

Intent Detection & Logic
Information Intent Matching: Evaluates direct pattern matches for pre-configured questions regarding faculties, course programs, contact numbers, and campus location.
Fuzzy Matching Fallback: When exact patterns are not matched, the Tokenizations module evaluates input similarity against pre-defined phrases using similarity ratio calculations derived from Levenshtein Distance.
Interactive Tools:
GPA Calculation: Converts grade inputs formatted as Hours/Grade (e.g., 4/A 3/B+ 2/C) into standard grade point weights and calculates the cumulative average:
GPA= 
∑Hours
∑(Hours×Grade Points)
​	
 
Analytics Command: Typing show analytics outputs total session interactions, intent distributions, and top-queried intents.
File Structure
.
├── Main.scala       # Core Scala application (Tokenizations, Intent Detection, Study Plans, Main CLI loop)
├── build.sbt        # SBT Build Configuration file for Scala 3
└── README.md        # Project documentation

Technical Requirements & Dependencies
Scala: Version 3.x
Build Tool: SBT (Scala Build Tool) or standard Scala CLI launcher
Libraries: Uses standard Scala libraries (scala.io.StdIn, scala.collection.immutable.Vector, scala.util.Random, scala.math)
Getting Started
1. Clone the Repository
git clone [https://github.com/YourUsername/EUI-Buddy-Scala-Chatbot.git](https://github.com/YourUsername/EUI-Buddy-Scala-Chatbot.git)
cd EUI-Buddy-Scala-Chatbot

2. Run with Scala CLI / SBT
Using Scala CLI:
scala Main.scala

Using SBT:
sbt run

Usage Example:
Hello, I am EUI-Buddy. How can I help you?
> What are the faculties of EUI?
There are 4 faculties in EUI : Faculty of Computer science, Faculty of Business informatics, Faculty of Engineering, Faculty of Arts & Design. (Double-check the official EUI pages; details may change.)

> calculate my gpa
Please number of hours/grades separated by spaces (e.g., 4/A 3/B+ 2/C 1/A-):
> 3/A 4/B+ 2/A-
Your GPA is: 3.61 (Formula: GPA = sum(hours × grade points) / sum(hours))

> show analytics
Total interactions: 2
Intent counts:
Faculty: 1
GPA: 1
Top intents: Faculty: 1, GPA: 1


Disclaimer
Details provided by the chatbot regarding university programs, contact information, and study plans are for general informational purposes. Official information should always be verified directly through EUI's official portal (https://eui.edu.eg/).
