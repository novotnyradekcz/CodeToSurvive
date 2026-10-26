import kotlin.math.abs

object Problem1 {
    fun countRejectedReports(input: String): Int {
        val reports = input.lineSequence()
            .filter { it.isNotBlank() }
            .map { line ->
                val values = line.trim().split(Regex("\\s+"))
                values[0].toLong() to values[1].toLong()
            }
            .iterator()

        if (!reports.hasNext()) return 0

        var (lastAcceptedTime, lastAcceptedHouse) = reports.next()
        var rejectedReports = 0

        while (reports.hasNext()) {
            val (time, house) = reports.next()
            val availableTime = time - lastAcceptedTime
            val distance = abs(house - lastAcceptedHouse)

            if (distance > availableTime) {
                rejectedReports++
            } else {
                lastAcceptedTime = time
                lastAcceptedHouse = house
            }
        }

        return rejectedReports
    }
}
