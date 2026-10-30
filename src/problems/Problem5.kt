object Problem5 {
    private const val MAX_SOULS = 20

    fun countAssignments(input: String): Long {
        val tokens = input.trim().split(Regex("\\s+"))
        var index = 0

        fun nextInt(): Int = tokens.getOrNull(index++)?.toInt()
            ?: error("Unexpected end of input while reading compatibility data")

        val soulCount = nextInt()
        require(soulCount in 1..MAX_SOULS) { "Soul count must be between 1 and $MAX_SOULS" }

        val compatibleBodies = IntArray(soulCount)
        repeat(soulCount) { soul ->
            val compatibleCount = nextInt()
            require(compatibleCount in 0..soulCount) {
                "Compatibility count must be between 0 and $soulCount"
            }

            var bodyMask = 0
            repeat(compatibleCount) {
                val body = nextInt()
                require(body in 1..soulCount) { "Body IDs must be between 1 and $soulCount" }
                val bodyBit = 1 shl (body - 1)
                require((bodyMask and bodyBit) == 0) { "Compatible body IDs must be distinct" }
                bodyMask = bodyMask or bodyBit
            }
            compatibleBodies[soul] = bodyMask
        }

        val stateCount = 1 shl soulCount
        val ways = LongArray(stateCount)
        ways[0] = 1L

        for (usedBodies in 0 until stateCount) {
            val soul = Integer.bitCount(usedBodies)
            if (soul == soulCount || ways[usedBodies] == 0L) continue

            var availableBodies = compatibleBodies[soul] and usedBodies.inv()
            while (availableBodies != 0) {
                val bodyBit = availableBodies and -availableBodies
                availableBodies = availableBodies xor bodyBit
                ways[usedBodies or bodyBit] += ways[usedBodies]
            }
        }

        return ways[stateCount - 1]
    }
}
