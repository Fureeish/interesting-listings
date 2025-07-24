object NaturalComparator : Comparator<String> {
    override fun compare(first: String, second: String): Int {
        if (first == second) return 0

        val firstTokenized = first.tokenized()
        val secondTokenized = second.tokenized()

        firstTokenized.zip(secondTokenized)
            .dropWhile { (left, right) -> left == right}
            .firstOrNull()
            ?.let { (left, right) ->
                if (areOfDifferentCategoriesOrBothAreNotNumbers(left, right)) {
                    return left.compareTo(right)
                } else {
                    return left.toInt().compareTo(right.toInt())
                }
            }
            ?: run {
                // The only way we end up here is if one is a prefix of another,
                // and after such prefix there is a sequence which is a new category.
                // Example:
                // abc123def456
                // abc123def

                return first.length.compareTo(second.length)
            }
    }

    private fun areOfDifferentCategoriesOrBothAreNotNumbers(left: String, right: String) =
        !sameCategory(left.first(), right.first()) or !left.first().isDigit()

    private fun String.tokenized(): List<String> {
        if (this.isEmpty()) return emptyList()
        if (this.length == 1) return listOf(this.first().toString())

        return this
            .drop(1)
            .fold(mutableListOf(StringBuilder(this.first().toString()))) { tokens, nextChar ->
                if (!sameCategory(tokens.last().last(), nextChar)) tokens.add(StringBuilder())

                tokens.last().append(nextChar)

                tokens
            }
            .map(StringBuilder::toString)
    }

    private fun sameCategory(c1: Char, c2: Char) = c1.isDigit() == c2.isDigit()
}
