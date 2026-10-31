import kotlin.random.Random

object Problem6Test {
    @JvmStatic
    fun main(args: Array<String>) {
        check(Problem6.maximumIlluminatedRectangle("4 6\n..#...\n......\n.##...\n......") == 12)
        check(Problem6.maximumIlluminatedRectangle("1 1\n#") == 0)
        check(Problem6.maximumIlluminatedRectangle("1 5\n.....") == 5)
        check(Problem6.maximumIlluminatedRectangle("3 3\n...\n...\n...") == 9)
        check(Problem6.maximumIlluminatedRectangle("3 3\n.#.\n###\n.#.") == 1)

        val random = Random(6)
        repeat(300) {
            val rows = random.nextInt(1, 7)
            val columns = random.nextInt(1, 7)
            val grid = List(rows) {
                CharArray(columns) { if (random.nextBoolean()) '.' else '#' }.concatToString()
            }
            val input = "$rows $columns\n${grid.joinToString("\n")}"
            check(Problem6.maximumIlluminatedRectangle(input) == bruteForceMaximum(grid)) {
                "Failed for grid:\n${grid.joinToString("\n")}"
            }
        }
    }

    private fun bruteForceMaximum(grid: List<String>): Int {
        var maximumArea = 0
        for (top in grid.indices) {
            for (left in grid[0].indices) {
                for (bottom in top until grid.size) {
                    for (right in left until grid[0].length) {
                        var allIlluminated = true
                        for (row in top..bottom) {
                            for (column in left..right) {
                                if (grid[row][column] != '.') allIlluminated = false
                            }
                        }
                        if (allIlluminated) {
                            maximumArea = maxOf(maximumArea, (bottom - top + 1) * (right - left + 1))
                        }
                    }
                }
            }
        }
        return maximumArea
    }
}
