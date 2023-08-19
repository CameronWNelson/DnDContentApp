public class SpellToJson
{
    public String name;
    public int level;
    public String school;
    public boolean ritual;
    public boolean concentration;
    public Components components;
    public String range;
    public String duration;
    public String castTime;
    public String spellText;
    public String classes;
    public String subclasses;

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
