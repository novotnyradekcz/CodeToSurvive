import java.util.PriorityQueue

object Problem4 {
    fun maximumPeopleSaved(input: String): Int {
        val tokens = input.trim().split(Regex("\\s+"))
        var index = 0

        fun nextToken(): String = tokens.getOrNull(index++)
            ?: error("Unexpected end of input while reading traps")

        val trapCount = nextToken().toInt()
        require(trapCount >= 0) { "Trap count must not be negative" }

        val traps = List(trapCount) {
            val duration = nextToken().toLong()
            val deadline = nextToken().toLong()
            require(duration >= 0 && deadline >= 0) {
                "Trap durations and deadlines must not be negative"
            }
            Trap(duration, deadline)
        }.sortedBy { it.deadline }

        val selectedDurations = PriorityQueue<Long>(compareByDescending { it })
        var elapsedTime = 0L

        for (trap in traps) {
            selectedDurations.add(trap.duration)
            elapsedTime += trap.duration
            if (elapsedTime > trap.deadline) {
                elapsedTime -= selectedDurations.remove()
            }
        }

        return selectedDurations.size
    }

    private data class Trap(val duration: Long, val deadline: Long)
}
