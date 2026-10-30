import java.io.File

fun main(args: Array<String>) {
    val problemNumber = args.firstOrNull()?.toInt() ?: 4
    val inputDirectory = File("data/$problemNumber")
    val inputFiles = inputDirectory.listFiles { file -> file.isFile && file.extension == "txt" }
        ?: error("Unable to read input directory: ${inputDirectory.path}")
    require(inputFiles.size == 1) {
        "Expected exactly one .txt input file in ${inputDirectory.path}, found ${inputFiles.size}"
    }

    val input = inputFiles.single().readText()
    val answer = when (problemNumber) {
        1 -> Problem1.countRejectedReports(input)
        2 -> Problem2.countAliens(input)
        3 -> Problem3.countTapesAtStore(input)
        4 -> Problem4.maximumPeopleSaved(input)
        else -> error("No solution is registered for problem $problemNumber")
    }
    println(answer)
}
