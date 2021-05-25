
import democracy.Ballot
import democracy.Candidate
import democracy.Election
import democracy.Grade

val bad = Grade.Bad
bad.ordinal < Grade.Excellent.ordinal

Grade.median(Seq(Grade.Excellent, Grade.Bad))
Grade.median(Seq(Grade.Excellent, Grade.Bad, Grade.Mediocre))

val election = Election("EU", Set(Candidate("Roland"), Candidate("Alexandros")))
val ballots = Seq(
  Ballot(Map(Candidate("Roland") -> Grade.Bad, Candidate("Alexandros") -> Grade.Good)),
  Ballot(Map(Candidate("Roland") -> Grade.Excellent, Candidate("Alexandros") -> Grade.Bad)),
  Ballot(Map(Candidate("Roland") -> Grade.Inadequate, Candidate("Alexandros") -> Grade.Excellent))
)

val allGrades: Seq[(Candidate, Grade)] =
  ballots
    .flatMap(_.grades)
val gradesPerCandidate: Map[Candidate, Seq[Grade]] =
  allGrades
    .groupMap(_._1)(_._2)
val bestMedianGrade: Grade =
  gradesPerCandidate
    .values
    .filterNot(_.isEmpty)
    .map(Grade.median(_))
    .maxBy(_.ordinal)
val bestCandidates: Map[Candidate, Seq[Grade]] =
  gradesPerCandidate
    .filter((_, gs) => Grade.median(gs) == bestMedianGrade)
bestCandidates.head._1

val bestCandidatesMinusOneMedianGrade: Map[Candidate, Seq[Grade]] =
  bestCandidates
    .map((c, gs) => (c, gs.diff(Seq(bestMedianGrade))))
