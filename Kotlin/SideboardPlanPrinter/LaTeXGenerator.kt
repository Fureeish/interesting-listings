object LaTeXGenerator {
    private val preamble = """
        \documentclass[9pt]{extarticle}
        \usepackage[lining]{ebgaramond}
        \usepackage[margin=0.75in]{geometry}
        \usepackage{array}
    """.trimIndent()

    fun generateLaTeXFrom(
        matchups: Map<String, List<Card>>,
        columnCount: Int,
        options: Iterable<Options> = setOf(Options.AlignToLeft)
    ): String {
        val matchupsSortedByPlanItems = matchups
            .map { (matchup, sideboardPlan) ->
                val (cardsToAdd, cardsToRemove) = sideboardPlan.partition { it.count > 0 }

                val inOutPlan = zipWithPadding(
                    cardsToAdd.sortedByDescending(Card::count),
                    cardsToRemove.sortedBy(Card::count)
                ).map { (`in`, out) ->
                    val inAsText = `in`?.toAbbreviated()?.toString().orEmpty()
                    val outAsText = out?.toAbbreviated()?.toString().orEmpty()
                    inAsText to outAsText
                }

                matchup to inOutPlan
            }.sortedByDescending { (_, inoutPlan) ->
                inoutPlan.size
            }

        val latexRepresentation = matchupsSortedByPlanItems
            .chunked(columnCount)
            .joinToString(
                prefix = "\\noindent\n",
                separator = "\n\\vspace{1em}\n\n\\noindent\n"
            ) { matchupsRow ->
                matchupsRow
                    .joinToString(separator = "\\hfill\n") { (matchup, sideboardPlan) ->
                        val singlePlanWidth = String.format("%.3f", 1.0 / columnCount / 1.05)

                        buildString {
                            append("\\begin{minipage}[t]{$singlePlanWidth\\textwidth}\n")
                            append("\\centering\n")
                            append("\\textbf{\\textsc{$matchup}}\\vphantom{Jy} \\\\\n")

                            val leftColumnAlignment =
                                if (options.contains(Options.AlignToLeft)) "\\raggedright" else "\\raggedleft"

                            append("\\begin{tabular}{@{}>{$leftColumnAlignment\\arraybackslash}p{0.45\\linewidth}|>{\\raggedright\\arraybackslash}p{0.45\\linewidth}@{}}")

                            if (options.contains(Options.WithInOut)) {
                                append("\\textbf{\\textsc{in}} & \\textbf{\\textsc{out}} \\\\\n")
                            }

                            append("\\hline\n")

                            sideboardPlan.forEach { (`in`, out) ->
                                append("$`in` & $out\\\\\n")
                            }

                            append("\\end{tabular}\n")
                            append("\\end{minipage}\n")
                        }
                }
            }

        return """
            $preamble
            \begin{document}
            $latexRepresentation
            \end{document}
        """.trimIndent()
    }

    private fun <T, U> zipWithPadding(
        list1: List<T>, list2: List<U>
    ): List<Pair<T?, U?>> {
        val maxSize = maxOf(list1.size, list2.size)

        return (0 until maxSize).map { i ->
            val first = list1.getOrNull(i)
            val second = list2.getOrNull(i)
            first to second
        }
    }

    enum class Options {
        WithInOut, AlignToCenter, AlignToLeft
    }

}