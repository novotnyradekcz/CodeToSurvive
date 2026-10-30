import kotlin.random.Random

object Problem5Test {
    @JvmStatic
    fun main(args: Array<String>) {
        check(Problem5.countAssignments("3\n2 1 2\n2 2 3\n2 1 3") == 2L)
        check(Problem5.countAssignments("1\n1 1") == 1L)
        check(Problem5.countAssignments("2\n1 1\n1 1") == 0L)
        check(Problem5.countAssignments("3\n3 1 2 3\n3 1 2 3\n3 1 2 3") == 6L)
        check(
            Problem5.countAssignments(
                "20\n" + List(20) { "20 ${ (1..20).joinToString(" ") }" }.joinToString("\n")
            ) == 2432902008176640000L
        )

        val random = Random(5)
        repeat(200) {
            val n = random.nextInt(1, 9)
            val options = List(n) {
                (1..n).filter { random.nextBoolean() }
            }
            val input = buildString {
                appendLine(n)
                for (bodies in options) {
                    append(bodies.size)
                    for (body in bodies) append(" $body")
                    appendLine()
                }
            }
            val expected = countBySearch(options, 0, 0)
            check(Problem5.countAssignments(input) == expected) { "Failed for input:\n$input" }
        }
    }

    private fun countBySearch(options: List<List<Int>>, soul: Int, usedBodies: Int): Long {
        if (soul == options.size) return 1L
        return options[soul].sumOf { body ->
            val bodyBit = 1 shl (body - 1)
            if ((usedBodies and bodyBit) == 0) {
                countBySearch(options, soul + 1, usedBodies or bodyBit)
            } else {
                0L
            }
        }
    }
}
