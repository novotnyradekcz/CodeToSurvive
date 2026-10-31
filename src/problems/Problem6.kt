object Problem6 {
    fun maximumIlluminatedRectangle(input: String): Int {
        val lines = input.lineSequence().filter { it.isNotBlank() }.iterator()
        require(lines.hasNext()) { "Missing grid dimensions" }

        val dimensions = lines.next().trim().split(Regex("\\s+"))
        require(dimensions.size == 2) { "Grid dimensions must contain row and column counts" }
        val rowCount = dimensions[0].toInt()
        val columnCount = dimensions[1].toInt()
        require(rowCount >= 0 && columnCount >= 0) { "Grid dimensions must not be negative" }

        val heights = IntArray(columnCount)
        val stack = IntArray(columnCount + 1)
        var maximumArea = 0

        repeat(rowCount) { row ->
            require(lines.hasNext()) { "Missing grid row ${row + 1}" }
            val cells = lines.next().trim()
            require(cells.length == columnCount) {
                "Grid row ${row + 1} must contain exactly $columnCount characters"
            }

            for (column in 0 until columnCount) {
                when (cells[column]) {
                    '.' -> heights[column]++
                    '#' -> heights[column] = 0
                    else -> error("Grid cells must be '.' or '#'")
                }
            }

            var stackSize = 0
            for (column in 0..columnCount) {
                val currentHeight = if (column == columnCount) 0 else heights[column]
                while (stackSize > 0 && heights[stack[stackSize - 1]] > currentHeight) {
                    val rectangleHeight = heights[stack[--stackSize]]
                    val leftBoundary = if (stackSize == 0) 0 else stack[stackSize - 1] + 1
                    val rectangleWidth = column - leftBoundary
                    maximumArea = maxOf(maximumArea, rectangleHeight * rectangleWidth)
                }
                if (column < columnCount) {
                    stack[stackSize++] = column
                }
            }
        }

        return maximumArea
    }
}
