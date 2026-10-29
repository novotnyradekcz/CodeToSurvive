import java.math.BigInteger

object Problem3 {
    private const val EVENINGS = 7

    fun countTapesAtStore(input: String): BigInteger {
        val tokens = input.trim().split(Regex("\\s+"))
        var index = 0

        fun nextInt(): Int = tokens.getOrNull(index++)?.toInt()
            ?: error("Unexpected end of input while reading delivery routes")

        val storeCount = nextInt()
        val routeCount = nextInt()
        val targetStore = nextInt()
        require(storeCount >= 1) { "Store count must be positive" }
        require(targetStore in 1..storeCount) { "Target store must be between 1 and $storeCount" }

        val routes = Array(storeCount + 1) { mutableListOf<Int>() }
        repeat(routeCount) {
            val from = nextInt()
            val to = nextInt()
            require(from in 1..storeCount && to in 1..storeCount) {
                "Store IDs must be between 1 and $storeCount"
            }
            routes[from].add(to)
        }

        var tapes = Array(storeCount + 1) { BigInteger.ZERO }
        tapes[1] = BigInteger.ONE
        repeat(EVENINGS) {
            val nextTapes = tapes.copyOf()
            for (store in 1..storeCount) {
                for (destination in routes[store]) {
                    nextTapes[destination] = nextTapes[destination].add(tapes[store])
                }
            }
            tapes = nextTapes
        }

        return tapes[targetStore]
    }
}
