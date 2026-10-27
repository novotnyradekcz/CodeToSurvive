object Problem2 {
    fun countAliens(input: String): Int {
        val tokens = input.trim().split(Regex("\\s+"))
        var index = 0

        fun nextToken(): String = tokens.getOrNull(index++)
            ?: error("Unexpected end of input while reading crew test data")

        val crewCount = nextToken().toInt()
        val testCount = nextToken().toInt()
        require(crewCount >= 1) { "Crew count must be positive" }

        val species = ParityDisjointSet(crewCount)
        repeat(testCount) {
            val first = nextToken().toInt()
            val second = nextToken().toInt()
            val result = when (nextToken()) {
                "SAME" -> 0
                "DIFFERENT" -> 1
                else -> error("Test result must be SAME or DIFFERENT")
            }
            require(first in 1..crewCount && second in 1..crewCount) {
                "Crew member IDs must be between 1 and $crewCount"
            }
            species.connect(first, second, result)
        }

        val verifiedSpecies = species.parityFromRoot(1)
        return (2..crewCount).count { member ->
            (species.parityFromRoot(member) xor verifiedSpecies) == 1
        }
    }

    private class ParityDisjointSet(size: Int) {
        private val parent = IntArray(size + 1) { it }
        private val componentSize = IntArray(size + 1) { 1 }
        private val parityToParent = IntArray(size + 1)

        private fun find(member: Int): Int {
            if (parent[member] != member) {
                val oldParent = parent[member]
                parent[member] = find(oldParent)
                parityToParent[member] =
                    parityToParent[member] xor parityToParent[oldParent]
            }
            return parent[member]
        }

        fun parityFromRoot(member: Int): Int {
            find(member)
            return parityToParent[member]
        }

        fun connect(first: Int, second: Int, requiredParity: Int) {
            val firstRoot = find(first)
            val secondRoot = find(second)
            val rootParity = parityToParent[first] xor parityToParent[second] xor requiredParity

            if (firstRoot == secondRoot) {
                require((parityToParent[first] xor parityToParent[second]) == requiredParity) {
                    "Contradictory blood test results"
                }
                return
            }

            if (componentSize[firstRoot] < componentSize[secondRoot]) {
                parent[firstRoot] = secondRoot
                parityToParent[firstRoot] = rootParity
                componentSize[secondRoot] += componentSize[firstRoot]
            } else {
                parent[secondRoot] = firstRoot
                parityToParent[secondRoot] = rootParity
                componentSize[firstRoot] += componentSize[secondRoot]
            }
        }
    }
}
