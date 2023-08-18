package com.example.dndcontentapp
enum class School {ABJURATION, CONJURATION, DIVINATION, ENCHANTMENT, EVOCATION, ILLUSION, NECROMANCY, TRANSMUTATION}
enum class PlayerClass {ARTIFICER, BAR, CLERIC, DRUID, PALADIN, RANGER, SORCERER, WARLOCK, WIZARD}
class Component (val vocal: Boolean, val somatic: Boolean, val material: Boolean, val materialItems: String) {
}

// playerClass is commented out for future use
class Spell (val name: String, val level: Int, val castTime: String, val range: String, val area: String, val components: Component, val duration: String, val school: School, val concentration: Boolean, val ritual: Boolean, /*val playerClass: List<PlayerClass>,*/ val spellText: String){

    // Temporary function to fit all the spell details in a single textView
    override fun toString(): String {
        var text: String = name + "\n"
        if (this.level == 0) {
            // Capitalize the school for cantrips since it comes first
            text += "${schoolToString(true)} cantrip"
        }
        else {
            text += "${getLevelSuffix()}-level ${schoolToString(false)}"
        }
        if (this.ritual) {
            text += " (ritual)"
        }
        text += "\n"
        text += "Casting Time: $castTime\n"
        text += "Range: $range\n"
        text += "Area: $area\n"
        text += "Components: ${componentToString()}\n"
        text += "Duration: "
        if (concentration) text += "Concentration, up to "
        text += "$duration\n"
//        text += "Available For: "
//        for (i in playerClass) {
//            text += playerClass.indexOf(i).to
//        }
        text += "$spellText"
        return text
    }

    // returns the level of the spell with a suffix
    fun getLevelSuffix(): String {
        return when (this.level) {
            0 -> "cantrip"
            1 -> "1st"
            2 -> "2nd"
            3 -> "3rd"
            4 -> "4th"
            5 -> "5th"
            6 -> "6th"
            7 -> "7th"
            8 -> "8th"
            9 -> "9th"
            else -> "error"
        }
    }

    // returns the text form of the school enum, can be capitalized
    fun schoolToString (capital: Boolean): String {
        if (capital) {
            return when (this.school) {
                School.ABJURATION -> "Abjuration"
                School.CONJURATION -> "Conjuration"
                School.DIVINATION -> "Divination"
                School.ENCHANTMENT -> "Enchantment"
                School.EVOCATION -> "Evocation"
                School.ILLUSION -> "Illusion"
                School.NECROMANCY -> "Necromancy"
                School.TRANSMUTATION -> "Transmutation"
            }
        }
        return when (this.school) {
            School.ABJURATION -> "abjuration"
            School.CONJURATION -> "conjuration"
            School.DIVINATION -> "divination"
            School.ENCHANTMENT -> "enchantment"
            School.EVOCATION -> "evocation"
            School.ILLUSION -> "illusion"
            School.NECROMANCY -> "necromancy"
            School.TRANSMUTATION -> "transmutation"
        }
    }

    // returns the text form of the components
    fun componentToString (): String {
        var text: String = ""
        if (components.vocal) text += "V"
        if (components.somatic) {
            if (!text.contentEquals("")) text += ", "
            text += "S"
        }
        if (components.material) {
            if (!text.contentEquals("")) text += ", "
            text += "M (${components.materialItems})"
        }
        return text
    }
}