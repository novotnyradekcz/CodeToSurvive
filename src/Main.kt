import java.io.File

private const val INPUT_FILE = "hyperskill-dataset-125830147.txt"

fun main() {
    val input = File(INPUT_FILE).readText()
    println(Problem1.countRejectedReports(input))
}
