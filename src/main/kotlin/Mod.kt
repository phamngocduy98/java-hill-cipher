import java.lang.RuntimeException

class Mod(private val p: Int) {
    private fun euclideanExtend(a: Int, b: Int): Triple<Int, Int, Int> {
        var d: Int

        var r: Int;
        var q: Int

        var x: Int;
        var y: Int;

        var x1: Int;
        var x2: Int;

        var y1: Int;
        var y2: Int;

        if (b == 0) {
            d = a;
            x = 1;
            y = 0;
            return Triple(d, x, y)
        }
        var tempA = a;
        var tempB = b;
        x2 = 1; x1 = 0; y2 = 1; y1 = 0;
        while (tempB > 0) {
            q = tempA / tempB;
            r = tempA % tempB;
            x = x2 - q * x1;
            y = y2 - q * y1;
            tempA = tempB;
            tempB = r;
            x2 = x1;
            x1 = x;
            y2 = y1;
            y1 = y;
        }
        d = tempA;
        x = x2;
        y = y2;
        return Triple(d, x, y)
    }

    fun modize(n: Int): Int {
        var tempN = n;
        while (tempN < 0) {
            tempN += p;
        }
        return (tempN + p) % p;
//        return (n + p*100) % p;
    }

    fun expMinus1(n: Int): Int {
        val (d, x, y) = euclideanExtend(modize(n), p)
        return modize(x)
    }

    /**
     * invert
     * alias of expMinus1
     */
    fun invert(n: Int): Int {
        return expMinus1(n)
    }
}