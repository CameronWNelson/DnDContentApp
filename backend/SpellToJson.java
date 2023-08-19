public class SpellToJson
{
    private String name;
    private int level;
    private String school;
    private boolean ritual;
    private boolean concentration;
    private Components components;
    private String range;
    private String duration;
    private String castTime;
    private String spellText;
    private String classes;
    private String subclasses;

    public SpellToJson(Spell spell)
    {
        this.name = spell.getName();
        this.level = spell.getLevel();
        this.school = spell.getSchool();
        this.ritual = spell.isRitual();
        this.concentration = spell.isConcentration();
        this.components = spell.getComponents();
        this.range = spell.getRange();
        this.duration = spell.getDuration();
        this.castTime = spell.getCastTime();
        this.spellText = spell.getSpellText();
        this.classes = spell.getClasses();
        this.subclasses = spell.getSubclasses();
    }
}
