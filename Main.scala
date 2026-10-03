import scala.io.StdIn
import scala.collection.immutable.Vector
import scala.math
import scala.util.Random


object Tokenizations:
  // Common words and sets
  private val commonWords: List[String] = List(
    "a", "an", "as", "at", "be", "by", "do", "go", "he", "if", "in", "is", "it","me", "my", "no", "of", "on", "or", "so", "to", "up", "us", "we", "ye", "for",
    "and", "the", "but", "not", "you", "all", "any", "are", "can", "had", "has", "her","him", "his", "how", "man", "new", "now", "old", "one", "our", "out", "own", "put",
    "say", "see", "she", "two", "way", "who", "why", "yes", "yet", "you","also", "back", "best", "both", "call", "come", "each", "even", "find", "from",
    "give", "good", "have", "here", "just", "know", "like", "look", "make", "many", "more", "most", "much", "need", "only", "other", "over", "some", "such", "take",
    "tell", "than", "that", "them", "then", "they", "this", "time", "want", "well", "were", "what", "when", "will", "with", "would", "your"
  )

  // Response and emotion-related sets
  private val greetingSet: List[String] = List("hi","hey","hello","yo", "hiya", "greetings", "salutations", "aloha", "sup", "howdy","good morning","good afternoon","good evening")
  private val appreciatSet: List[String] = List("thank you","thank","thanks","thankful","grateful","appreciate")
  private val loveSet: List[String] = List("love you","love")
  private val unsatisfactSet: List[String] = List("hate","disappointed","unsatisfied","not satisfied")
  private val exitSet: List[String] = List("bye","goodbye","see you later","talk to you later","good night","nightie")
  private val phraseSet: List[String] = List("good morning","good afternoon","good evening","thank you","love you","not satisfied","see you")


  // Response templates
  private val greetingResponses: List[String] = List(
    "Welcome to EUI! How can I help you learn about our university today?",
    "Hello! Ready to explore Egypt University of Informatics?",
    "Hi there! What would you like to know about EUI?",
    "Greetings! How can I assist you with information about our programs?",
    "Welcome! Need help with admissions, programs, or campus information?"
  )

  private val thanksResponses: List[String] = List(
    "You're welcome! Feel free to ask more questions about EUI anytime.",
    "Glad I could help! Let me know if you need more information about our university.",
    "My pleasure! Don't hesitate to ask about our programs or services.",
    "Anytime! I'm here to help you learn more about EUI.",
    "Happy to help! Come back anytime for more information about our university."
  )

  private val loveResponses: List[String] = List(
    "That's very kind! I enjoy helping prospective students learn about EUI!",
    "Aww, thanks! I love helping people discover what EUI has to offer!",
    "You're too kind! I'm just happy to help you explore our university!",
    "That means a lot! I enjoy sharing information about our amazing programs!",
    "Thank you! It's a pleasure helping you learn about EUI!"
  )

  private val unsatisfiedResponses: List[String] = List(
    "I'm sorry you're not satisfied. How can I help you better with EUI information?",
    "Let me try to help you in a different way. What specific information are you looking for?",
    "I apologize for not meeting your expectations. Could you tell me more about what you need to know about EUI?",
    "Sorry about that. Let's try a different approach to find the information you need.",
    "I want to help you better. Could you tell me more about your questions regarding our university?"
  )

  private val exitResponses: List[String] = List(
    "Goodbye! Feel free to come back anytime for more information about EUI!",
    "See you later! Come back when you have more questions about our university!",
    "Take care! Looking forward to helping you learn more about EUI!",
    "Bye! Don't hesitate to return for more information about our programs!",
    "Until next time! Best of luck with your educational journey!"
  )

  private val defaultResponses: List[String] = List(
    "I'm not sure I understand. You can ask about admissions, programs, tuition, campus facilities, or faculty at EUI.",
    "Could you rephrase that? I can help with information about programs, admissions, campus life, or university services.",
    "I didn't quite catch that. Try asking about specific programs, admission requirements, or campus facilities!",
    "I'm here to help with information about EUI! What would you like to know?",
    "Not sure what you're looking for. Want to try asking about our academic programs, admission process, or campus life?"
  )


  // Getter methods for the sets
  def getGreetingSet: List[String] = greetingSet
  def getAppreciatSet: List[String] = appreciatSet
  def getLoveSet: List[String] = loveSet
  def getUnsatisfactSet: List[String] = unsatisfactSet
  def getExitSet: List[String] = exitSet
  def getPhraseSet: List[String] = phraseSet

  /**
   * Process input text and return a Response based on tokenization analysis
   * @param input The input text to process
   * @return Response with appropriate response type and message
   */
  def processInput(input: String): String =
    val tokens = tokenize(input.toLowerCase)
    val phrases = identifyPotentialTitleOrName(tokens)
    
    // Check for greetings
    if tokens.exists(token => greetingSet.contains(token)) || phrases.exists(phrase => greetingSet.contains(phrase)) then
      greetingResponses(Random.nextInt(greetingResponses.length))
    // Check for appreciation
    else if tokens.exists(token => appreciatSet.contains(token)) || phrases.exists(phrase => appreciatSet.contains(phrase)) then
     thanksResponses(Random.nextInt(thanksResponses.length))
    // Check for love expressions
    else if tokens.exists(token => loveSet.contains(token)) || phrases.exists(phrase => loveSet.contains(phrase)) then
      loveResponses(Random.nextInt(loveResponses.length))
    // Check for dissatisfaction
    else if tokens.exists(token => unsatisfactSet.contains(token)) || phrases.exists(phrase => unsatisfactSet.contains(phrase)) then
     unsatisfiedResponses(Random.nextInt(unsatisfiedResponses.length))
    // Check for exit phrases
    else if tokens.exists(token => exitSet.contains(token)) || phrases.exists(phrase => exitSet.contains(phrase)) then
      exitResponses(Random.nextInt(exitResponses.length))
    // Default response for unrecognized input
    else
      defaultResponses(Random.nextInt(defaultResponses.length))

  /**
   * Tokenizes input text and removes common words
   * @param txt The input text to tokenize
   * @return List of tokenized words with common words removed
   */
  def tokenize(txt: String): List[String] =
    val txt2 = txt
      .toLowerCase                        // Convert all text to lowercase
      .replaceAll("n't", " not")         // Expand contractions (e.g., "don't" → "do not")
      .replaceAll("'m", " am")           // "I'm" → "I am"
      .replaceAll("'s", " is")           // "he's" → "he is"
      .replaceAll("'re", " are")         // "they're" → "they are"
      
    txt2
      .replaceAll("[^a-zA-Z0-9\\s]", "") // Remove punctuation/special characters
      .split("\\s+")                     // Split by whitespace
      .filter(_.nonEmpty)                // Remove empty strings
      .toList                            // Convert to List
      .filter(token => !commonWords.contains(token))



  /**
   * Calculate Levenshtein distance between two strings
   * @param s1 First string
   * @param s2 Second string
   * @return The edit distance between the strings
   */
  def levenshteinDistance(s1: String, s2: String): Int =
    val dist = Array.ofDim[Int](s1.length + 1, s2.length + 1)
    
    for i <- 0 to s1.length do dist(i)(0) = i
    for j <- 0 to s2.length do dist(0)(j) = j
    
    for
      j <- 1 to s2.length
      i <- 1 to s1.length
    do
      val cost = if s1(i-1) == s2(j-1) then 0 else 1
      dist(i)(j) = math.min(math.min(
        dist(i-1)(j) + 1,     // deletion
        dist(i)(j-1) + 1),    // insertion
        dist(i-1)(j-1) + cost // substitution
      )
    
    dist(s1.length)(s2.length)

  /**
   * Calculate similarity ratio between two strings
   * @param s1 First string
   * @param s2 Second string
   * @return Similarity ratio between 0.0 and 1.0
   */
  def similarityRatio(s1: String, s2: String): Double =
    val maxLength = math.max(s1.length, s2.length)
    if maxLength == 0 then 1.0 else (maxLength - levenshteinDistance(s1, s2)).toDouble / maxLength

  /**
   * Find best fuzzy matches from a list of candidates
   * @param query The string to match
   * @param candidates List of candidate strings to match against
   * @param threshold Minimum similarity ratio to consider a match
   * @return List of matches above the threshold
   */
  def findBestFuzzyMatches(query: String, candidates: List[String], threshold: Double): List[String] =
    if query.length < 3 then return List() 
    
    val similarities = candidates.map(candidate => (candidate, similarityRatio(query.toLowerCase, candidate.toLowerCase)))
    
    val matches = similarities
      .filter(_._2 >= threshold)
      .sortBy(-_._2)
      .map(_._1)
    
    if matches.isEmpty then List(query) else matches

  /**
   * Identify potential multi-word titles or names
   * @param tokens List of tokens to analyze
   * @return List of potential multi-word phrases
   */
  def identifyPotentialTitleOrName(tokens: List[String]): List[String] =
    if tokens.length < 2 then return List()
    
    // For pairs of tokens
    val potentialPhrases = tokens.sliding(2).flatMap { pair =>
      if !commonWords.contains(pair(0)) && 
         !commonWords.contains(pair(1)) && 
         pair(0).length > 2 && 
         pair(1).length > 2 then
        
        val phrase = pair.mkString(" ")
        val fuzzyMatches = findBestFuzzyMatches(phrase, phraseSet, 0.8)
        
        List(phrase) ++ fuzzyMatches
      else
        List()
    }.toList
    
    // For triplets of tokens
    val tripleTokenPhrases = if tokens.length >= 3 then
      tokens.sliding(3).flatMap { triple =>
        if !commonWords.contains(triple(0)) && 
           !commonWords.contains(triple(2)) && 
           triple(0).length > 2 && 
           triple(2).length > 2 then
          
          val phrase = triple.mkString(" ")
          val fuzzyMatches = findBestFuzzyMatches(phrase, phraseSet, 0.8)
          
          List(phrase) ++ fuzzyMatches
        else
          List()
      }.toList
    else List()
    
    potentialPhrases ++ tripleTokenPhrases
end Tokenizations
import Tokenizations._
//1.Campus,Faculty Section(Pattern Matching)
//Faculties Section
val FacultyQuestions : List[String] = List("What are the faculties of EUI University?","List the faculties at EUI.","Which faculties does EUI University have?"
,"EUI University faculties?","Faculties in EUI?","What subjects are taught at EUI?","EUI Uni departments?","Faculties of EUI","EUI Uni faculties",
 "EUI departments", "What departments does EUI have?","Give me the faculty list of EUI.")
val CSQuestions : List[String] = List("What is the program of the Computer Science faculty in EUI University?", "What are the programmes for CS at EUI Uni?","Can you give me the curriculum of the Computer Science dept at EUI Univ?","Info about the syllabus for CS faculty in EUI University.","Course list for Computer Science in EUI Uni?","Study plan for CS dept at EUI.",
"Skedule for Computer Science faculty EUI Universty?","Prog of Computer Scince dep in EUI Universty?","pls give cources for CS faculty eui.","CS prog @ eui uni?")
val EngQuestions :List[String] =List("What is the program of the Engineering faculty in EUI University?","What's the programm for Engineering at EUI Uni?" , "Can you give me the curriculum of the Engineering dept at EUI Univ?","Info about the syllabus for Engineering faculty in EUI University.", "Course list for Engineering in EUI Uni?","Study plan for Engineering dept at EUI.","Skedule for Engineering faculty EUI Universty?",
"Prog of Engineering dep in EUI Universty?","pls give cources for Engineering faculty eui.","Engineering prog @ eui uni?")
val BisQuestions :List[String] =List("What is the program of the Business Informatics faculty in EUI University?","What’s the programm for Business Informatics at EUI Uni?","Can you give me the curriculum of the Business Informatics dept at EUI Univ?","Info about the syllabus for Business Informatics faculty in EUI University.","Course list for Business Informatics in EUI Uni?","Study plan for Business Informatics dept at EUI.","Skedule for Business Informatics faculty EUI Universty?","Prog of Business Informatics dep in EUI Universty?","pls give cources for Business Informatics faculty eui.","Business Informatics prog @ eui uni?")
val ArtQuestions :List[String] =List("What is the program of the Digital Arts and Design faculty in EUI University?","What’s the programm for Digital Arts and Design at EUI Uni?" , "Can you give me the curriculum of the Digital Arts and Design dept at EUI Univ?","Info about the syllabus for Digital Arts and Design faculty in EUI University.", "Course list for Digital Arts and Design in EUI Uni?","Study plan for Digital Arts and Design dept at EUI.","Skedule for Digital Arts and Design faculty EUI Universty?", "Prog of Digital Arts and Design dep in EUI Universty?","pls give cources for Digital Arts and Design faculty eui.","Digital Arts and Design prog @ eui uni?")
val LocationQuestions :List[String] =List("What is the location of EUI University?","Where is EUI University located?","Where can I find EUI University?","Address of EUI University?","Where's EUI Uni?","EUI Uni location?","Location of EUI?","Where’s EUI at?","Give me the address of EUI.","EUI Uni address please.")
val ContactQuestions :List[String] =List("What is the contact information of EUI University?","Contact details of EUI University?","How can I contact EUI University?","Give me the contact info of EUI.","EUI Uni contact?","Contact EUI?","How do I contact EUI?","EUI phone number?","How do I get in touch with EUI University?","Who do I call at EUI?","Is there a helpline for EUI University?")
val InfoQuestions :List[String] =List("Where can I find information about EUI University?","How do I access information on EUI?","Give me the info section of EUI University.","Info about EUI?","EUI Uni info?","Got info on EUI?","Where do I get details about EUI?","How do I check EUI details?","EUI University details?","How can I check more details on EUI?","Yo, where do I look up EUI stuff?","How do I check out EUI info?")
val gpaInputs: List[String] = List(
  "calculate my gpa", "compute my gpa", "show me my gpa","can you calculate gpa for me", "help me with gpa calculation","gpa?",
  "gpa calc", "my gpa please", "what's my gpa", "need gpa", "can you figure out my gpa from these grades", "i want to know my gpa based on my results",
  "please calculate gpa from my marks", "how do i know my gpa i got these grades", "can you compute my grade point average",
  "compute grade point average", "what's my grade average", "calculate weighted average of my grades", "show me my average score gpa",
  "calclate gpa", "compute gpa plz", "how 2 calculet my gpa", "gpaa calculation", "gpa calcluation", "yo what's my gpa",
  "can you crunch my gpa numbers","need my gpa bro", "do the gpa math for me","drop my gpa"
)
val Grades= List("A+","A","A-","B+","B","B-","C+","C","C-","D+","D","D-","F")
// Simple string-based intent system
case class Interaction(input: String, response: String, intent: String)


object InteractionLog {
  private var log: Vector[Interaction] = Vector.empty
  def add(interaction: Interaction): Unit = log = log :+ interaction
  def all: Vector[Interaction] = log
  def countByIntent: Map[String, Int] = log.groupBy(_.intent).view.mapValues(_.size).toMap
  def topIntents(n: Int = 3): Seq[(String, Int)] = countByIntent.toSeq.sortBy(-_._2).take(n)
  def total: Int = log.size
}


def detectIntent(input: String): String = {
  val lower = input.toLowerCase
  if (FacultyQuestions.contains(input)) "Faculty"
  else if (CSQuestions.contains(input)) "CS"
  else if (EngQuestions.contains(input)) "Engineering"
  else if (BisQuestions.contains(input)) "Business Informatics"
  else if (ArtQuestions.contains(input)) "Arts"
  else if (LocationQuestions.contains(input)) "Location"
  else if (ContactQuestions.contains(input)) "Contact"
  else if (InfoQuestions.contains(input)) "Info"
  else if (gpaInputs.contains(input)) "GPA"
  else if (lower.contains("prerequisite") || lower.contains("prereq")) "Prerequisite"
  else if (
    lower.contains("study plan") ||
    (lower.contains("courses") && (lower.contains("year") || lower.contains("computer science") || lower.contains("engineering") || lower.contains("business informatics") || lower.contains("art") || lower.contains("design")))
  ) "StudyPlan"
  else if (lower.contains("result")) "ResultsOverview"
  else "Unknown"
}

val disclaimer = " (Double-check the official EUI pages; details may change.)"

def GenerateResponse(input: String): (String, String) = {
  detectIntent(input) match {
    case "Faculty" => ("There are 4 faculties in EUI : Faculty of Computer science, Faculty of Business informatics, Faculty of Engineering, Faculty of Arts & Design." + disclaimer, "Faculty")
    case "CS" => ("There are 6 Programs which are 'CyberSecurity','Artificial Intelligence','Computer Science','Data Science and Engineering','Software Engineering (starting soon)','Graphics and Game Programming (starting soon)'" + disclaimer, "CS")
    case "Engineering" => ("There are 4 Programs which are 'Electronics & Communication Engineering','Computer Engineering','Mechatronics Engineering','Industrial Engineering (starting soon)'" + disclaimer, "Engineering")
    case "Business Informatics" => ("There are 6 Programs which are 'Business Analytics','Business Technology Management','Digital Marketing & E-commerce','Finance','Accounting (starting soon)','Entrepreurship & Innovation (starting soon)'" + disclaimer, "Business Informatics")
    case "Arts" => ("There are 7 Programs which are 'Game Design','User Experience Design','Digital Interior Design','Animation Arts','Graphic and Media Design (starting soon)','Interaction Design Arts (starting soon)' , 'New Media Arts (starting soon)'" + disclaimer, "Arts")
    case "Location" => ("Eui University is at New Administrative Capital, Knowledge City." + disclaimer, "Location")
    case "Contact" => ("You can contact EUI university through : Phone: 15702, Admission Phone1 : 01030080940, Admission Phone2 : 01030090509, Email:contact@eui.edu.eg" + disclaimer, "Contact")
    case "Info" => ("You can find information about EUI University on our official website at https://eui.edu.eg/ or by contacting their admissions office (Double‑check the official EUI pages; details may change.)", "Info")
    case "GPA" => {
      println("Please number of hours/grades separated by spaces (e.g., 4/A 3/B+ 2/C 1/A-):")
      val gradesInput = StdIn.readLine()
      val grades = gradesInput.split(" ").toList
      val gradeTuples = grades.map(TupleCreator)
      val SumOfPoints = gradeTuples.map((a, b) => a * b).sum
      val SumOfHours = gradeTuples.map((a, _) => a).sum
      val gpa = SumOfPoints / SumOfHours
      (f"Your GPA is: $gpa%.2f (Formula: GPA = sum(hours × grade points) / sum(hours))", "GPA")
    }
    case "Prerequisite" => {
      val lower = input.toLowerCase
      val coursePattern = raw"prerequisites? for (.+)".r
      val rawCourse = coursePattern.findFirstMatchIn(lower).map(_.group(1)).getOrElse {
        prereqs.keys.find(k => lower.contains(k.toLowerCase)).getOrElse("unknown")
      }
      // Clean the course name by removing punctuation at the end
      val course = rawCourse.replaceAll("[?.,!]+$", "").trim
      (checkPrereq(course), "Prerequisite")
    }
    case "StudyPlan" => {
      val lower = input.toLowerCase
      val faculty =
        if lower.contains("computer") then "Computer Science"
        else if lower.contains("business") then "Business Informatics"
        else if lower.contains("engineering") then "Engineering"
        else if lower.contains("art") || lower.contains("design") then "Digital Arts & Design"
        else "Computer Science" // default fallback

      val year = raw"year\s*(\d)".r.findFirstMatchIn(lower).map(_.group(1).toInt)
        .orElse(raw"in year (\d)".r.findFirstMatchIn(lower).map(_.group(1).toInt))
        .getOrElse(1)
      (getCourses(faculty, year), "StudyPlan")
    }
    case "ResultsOverview" => ("This is a non-official results overview. For official results, always check your university portal. If you want a GPA calculation, please provide your grades.", "ResultsOverview")
    case _ => (Tokenizations.processInput(input), "Unknown")
  }
}

val Points=Grades.map(GradePoints)
def TupleCreator(input:String):(Int,Float)={
  val parts=input.split("/")
  (parts(0).toInt,GradePoints(parts(1)).getOrElse(0.0f))
}
def GradePoints(input: String): Option[Float] = {
  input match {
    case "A+" => Some(4.0f)
    case "A"  => Some(3.7f)
    case "A-" => Some(3.4f)
    case "B+" => Some(3.2f)
    case "B"  => Some(3.0f)
    case "B-" => Some(2.8f)
    case "C+" => Some(2.6f)
    case "C"  => Some(2.4f)
    case "C-" => Some(2.2f)
    case "D+" => Some(2.0f)
    case "D"  => Some(1.5f)
    case "D-" => Some(1.0f)
    case "F"  => Some(0.0f)
    case _    => None
  }
}

// --- Study Plans ---
val StudyPlans: Map[String, List[List[String]]] = Map(
  "Computer Science" -> List(
    List("Intro to Programming", "Discrete Mathematics", "Computer Systems Basics", "Academic Writing / Communication Skills"),
    List("Data Structures & Algorithms", "Databases", "Operating Systems", "Web Development Fundamentals"),
    List("Software Engineering", "Artificial Intelligence", "Networks & Security", "Electives (e.g., Mobile Apps, Cloud Basics)"),
    List("Machine Learning OR Advanced Software Design", "Capstone Project / Thesis", "Internship / Industry Collaboration", "Ethics in Technology")
  ),
  "Business Informatics" -> List(
    List("Intro to Business & Management", "Microeconomics / Macroeconomics", "Basics of Programming", "Math for Business"),
    List("Information Systems", "Databases for Business", "Business Statistics", "Accounting & Finance"),
    List("Enterprise Systems (ERP)", "E-Business & Digital Marketing", "Data Analytics", "Project Management"),
    List("Strategic IT Management", "Business Intelligence Tools", "Capstone Project", "Internship / Practicum")
  ),
  "Digital Arts & Design" -> List(
    List("Intro to Design Principles", "Drawing & Digital Illustration", "History of Art & Design", "Basics of Programming for Creative Media"),
    List("Graphic Design Tools (Photoshop, Illustrator)", "Animation Basics", "Web & UX Design", "Photography & Videography"),
    List("3D Modeling & Motion Graphics", "Game / Interactive Media Design", "Visual Storytelling", "Elective Workshops (Typography, Branding, etc.)"),
    List("Capstone Creative Project", "Internship in Creative Industry", "Design Entrepreneurship", "Final Portfolio Development")
  ),
  "Engineering" -> List(
    List("Calculus I & II", "Physics I & II", "Chemistry for Engineers", "Intro to Engineering & Design"),
    List("Mechanics (Statics & Dynamics)", "Thermodynamics", "Electrical Circuits", "Materials Science"),
    List("Control Systems", "Fluid Mechanics", "Probability & Statistics for Engineers", "Engineering Labs & Workshops"),
    List("Specialization (Civil / Electrical / Mechanical, etc.)", "Project Management & Engineering Economics", "Research Methods", "Industry Internship"),
    List("Final Year Project / Thesis", "Advanced Technical Electives", "Engineering Ethics & Sustainability", "Comprehensive Internship / Industry Placement")
  )
)

// --- Prerequisites ---
val prereqs: Map[String, String] = Map(
  "Data Structures & Algorithms" -> "Intro to Programming",
  "Artificial Intelligence" -> "Data Structures & Algorithms",
  "Machine Learning OR Advanced Software Design" -> "Artificial Intelligence",
  "Databases for Business" -> "Basics of Programming",
  "3D Modeling & Motion Graphics" -> "Animation Basics",
  "Control Systems" -> "Mechanics (Statics & Dynamics)"
)



def getCourses(faculty: String, year: Int=0): String =
  StudyPlans.get(faculty) match {
    case Some(years) if year > 0 && year <= years.length =>
      val courses = years(year - 1).mkString(", ")
      s"$faculty - Year $year: $courses $disclaimer"
    case Some(years) if year == 0 =>
      val courses = years.head.mkString(", ")
      s"$faculty - Year 1: $courses $disclaimer"
    case Some(_) =>
      s"No Year $year found for $faculty $disclaimer"
    case None =>
      s"Faculty $faculty not found $disclaimer"
  }

// Helper for canTake
def getYearCourses(faculty: String, year: Int): List[String] =
  StudyPlans.get(faculty).flatMap(years =>
    if (year > 0 && year <= years.length) Some(years(year - 1)) else None
  ).getOrElse(List.empty)


def checkPrereq(course: String): String = {
  val found = prereqs.keys.find(_.equalsIgnoreCase(course))
  found match {
    case Some(key) => s"$key requires you to first complete: ${prereqs(key)}"
    case None      => s"$course has no listed prerequisite at a high level"
  }
}

def canTake(faculty: String, year: Int, course: String): String =
  val coursesThisYear = getYearCourses(faculty, year)
  if coursesThisYear.contains(course) then
    prereqs.get(course) match
      case Some(req) =>
        s"Yes, $course is in Year $year, but you must complete $req first."
      case None =>
        s"Yes, you can take $course in Year $year."
  else
    s"$course is not offered in Year $year of $faculty."


@main def hello(): Unit ={
  println("Hello, I am EUI-Buddy. How can I help you?")
  while(true){
    val input = StdIn.readLine()
    if (input.toLowerCase == "show analytics") {
      val analytics = InteractionLog.countByIntent.map { case (intent, count) => s"$intent: $count" }.mkString("\n")
      val top = InteractionLog.topIntents().map { case (intent, count) => s"$intent: $count" }.mkString(", ")
      println(s"Total interactions: ${InteractionLog.total}\nIntent counts:\n$analytics\nTop intents: $top")
    } else {
      val (response, intent) = GenerateResponse(input)
      InteractionLog.add(Interaction(input, response, intent))
      println(response+"\n")
    }
  }
}
