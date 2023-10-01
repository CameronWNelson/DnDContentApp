package com.example.dndcontentapp

class ExtraUtilities {
    companion object {
        // turn an int of 0 or 1 into a boolean
        fun intToBool(i: Int): Boolean {
            if (i == 0) {
                return false
            }
            if (i == 1) {
                return true
            }
            throw IllegalArgumentException("$i cannot be converted to boolean, expected 0 or 1")
        }

        // turn a boolean into a 0 or 1
        fun boolToInt(b: Boolean): Int {
            if (b) return 1
            return 0
        }
    }
}