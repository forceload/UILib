package io.github.forceload.uilib.wrapper

data class Point2D<T1: Number, T2: Number>(val x: T1, val y: T2) {
    override fun equals(other: Any?): Boolean {
        return if (other is Point2D<*, *>) {
            hashCode() == other.hashCode()
        } else { false }
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}