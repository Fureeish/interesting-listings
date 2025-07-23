import java.io.File

const val MATH_MODE = "$$"
const val COLUMN_SEPARATOR = "|"

interface FactoryFor<T> {
    fun fromLine(line: String): T
}

class SeasonStandingsPlayerRecord(
    val nameAndSurname: String,
    val scores: MutableList<Int?> = mutableListOf(),
) : Comparable<SeasonStandingsPlayerRecord> {
    val totalPoints: Int
        get() = scores.filterNotNull().sum()

    override fun compareTo(other: SeasonStandingsPlayerRecord): Int {
        return other.totalPoints.compareTo(totalPoints)
    }

    override fun toString(): String {
        val formattedPoints = scores.joinToString(separator = " $COLUMN_SEPARATOR ") { it?.toString().orEmpty() }

        return listOf(nameAndSurname, "$MATH_MODE $totalPoints $MATH_MODE", formattedPoints)
            .joinToString(
                prefix = "$COLUMN_SEPARATOR ",
                postfix = " $COLUMN_SEPARATOR",
                separator = " $COLUMN_SEPARATOR "
            )
    }

    companion object : FactoryFor<SeasonStandingsPlayerRecord> {
        override fun fromLine(line: String): SeasonStandingsPlayerRecord {
            val rowCells = line.split(COLUMN_SEPARATOR)
            val rawPoints = rowCells.drop(4).dropLast(1)

            val nameAndSurname = rowCells[2].trim()
            val points = rawPoints.map { it.trim().toIntOrNull() }

            val result = SeasonStandingsPlayerRecord(nameAndSurname, points.toMutableList())
            return result
        }
    }
}

class LeagueStandingsPlayerRecord(val nameAndSurname: String, val score: Score) {
    val pointsScored: Int
        get() = score.wins * 3 + score.draws

    companion object : FactoryFor<LeagueStandingsPlayerRecord> {
        override fun fromLine(line: String): LeagueStandingsPlayerRecord {
            val (_, _, nameAndSurname, score) = line
                .split(COLUMN_SEPARATOR)
                .map { it.replace("$", "") }
                .map(String::trim)
            return LeagueStandingsPlayerRecord(nameAndSurname, Score.from(score))
        }
    }

    data class Score(val wins: Int, val losses: Int, val draws: Int) {
        companion object {
            fun from(text: String) = text
                .split('-')
                .map(String::toInt)
                .let { (wins, losses, draws) ->
                    Score(wins, losses, draws)
                }
        }
    }
}

fun main() {
    val playerSeasonStandingsPath = "path/to/your/standings/file.md"
    val newestLeagueStandingsPath = "path/to/your/new/league/file.md"

    val playerSeasonStandings = getPlayerStandings(playerSeasonStandingsPath, SeasonStandingsPlayerRecord)
    val playerLeagueStandings = getPlayerStandings(newestLeagueStandingsPath, LeagueStandingsPlayerRecord)

    playerSeasonStandings.forEach { it.scores += null }
    val totalLeaguesSoFar = playerSeasonStandings.first().scores.size

    for (leagueRecord in playerLeagueStandings) {
        /*
         * I'm using `contains` rather than `==` for comparing names
         * because sometimes the file with standings contains some extra HTML
         * for meme purposes.
         */
        val seasonRecord = playerSeasonStandings.find { it.nameAndSurname.contains(leagueRecord.nameAndSurname) } ?: run {
            val emptyScoresForAllLeaguesPlayedSoFar = MutableList<Int?>(totalLeaguesSoFar) { null }
            val standingsForNewPlayer = SeasonStandingsPlayerRecord(
                leagueRecord.nameAndSurname, emptyScoresForAllLeaguesPlayedSoFar
            )

            playerSeasonStandings.add(standingsForNewPlayer)

            standingsForNewPlayer
        }

        seasonRecord.scores.let { it[it.lastIndex] = leagueRecord.pointsScored }
    }

    playerSeasonStandings.sort()

    for ((index, player) in playerSeasonStandings.withIndex()) {
        println("$COLUMN_SEPARATOR $MATH_MODE ${index + 1}. $MATH_MODE $player")
    }
}

private fun <T> getPlayerStandings(path: String, factory: FactoryFor<T>): MutableList<T> {
    return File(path).useLines { lines ->
        lines
            .filter { it.startsWith(COLUMN_SEPARATOR) }
            .drop(2)
            .map(factory::fromLine)
            .toMutableList()
    }
}
