class Prime {
    companion object {
        private var primes = ArrayList<Int>()
        private val isPrime: HashMap<Int, Boolean> = HashMap()
        private fun genPrime(max: Int = 1000): ArrayList<Int> {
            if (primes.isNotEmpty()) return primes;
            for (i in 2..max) {
                if (isPrime[i] != false) {
                    primes+= i
                    for (j in 2..max/i) {
                        isPrime[i * j] = false;
                    }
                }
            }
            return primes;
        }
        fun primeFactor(n: Int): ArrayList<Int> {
            genPrime()
            var i = 0
            val factors = ArrayList<Int>();
            var nTemp = n
            while (nTemp > 1 && i < 1000) {
                val lastFactor = factors.lastOrNull()
                if (nTemp % primes[i] == 0) {
                    if (lastFactor != null && lastFactor == primes[i]) {

                    } else {
                        factors += primes[i]
                    }
                    nTemp = (nTemp / primes[i])
                } else {
                    i++;
                }
            }
            return factors;
        }
    }
}