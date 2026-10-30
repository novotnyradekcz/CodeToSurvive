import kotlin.random.Random

object Problem4Test {
    @JvmStatic
    fun main(args: Array<String>) {
        check(Problem4.maximumPeopleSaved("5\n4 5\n2 6\n3 7\n5 9\n1 8") == 3)
        check(Problem4.maximumPeopleSaved("0") == 0)
        check(Problem4.maximumPeopleSaved("1\n5 5") == 1)
        check(Problem4.maximumPeopleSaved("2\n6 5\n8 7") == 0)
        check(Problem4.maximumPeopleSaved("3\n5 5\n2 6\n2 6") == 2)
        check(Problem4.maximumPeopleSaved("3\n1 3\n1 3\n1 3") == 3)
        check(Problem4.maximumPeopleSaved("2\n2000000000 2000000000\n2000000000 4000000000") == 2)

        val random = Random(4)
        repeat(200) {
            val traps = List(random.nextInt(1, 10)) {
                random.nextInt(1, 12) to random.nextInt(1, 30)
            }
            val input = "${traps.size}\n" + traps.joinToString("\n") { (duration, deadline) ->
                "$duration $deadline"
            }
            val expected = (0 until (1 shl traps.size)).maxOf { mask ->
                val selected = traps.filterIndexed { index, _ -> mask and (1 shl index) != 0 }
                    .sortedBy { it.second }
                var elapsed = 0
                if (selected.all { (duration, deadline) ->
                        elapsed += duration
                        elapsed <= deadline
                    }) selected.size else 0
            }
            check(Problem4.maximumPeopleSaved(input) == expected) { "Failed for input:\n$input" }
        }
    }
}
