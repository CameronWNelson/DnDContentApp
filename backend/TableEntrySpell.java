public class TableEntrySpell
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

    public TableEntrySpell(Spell spell)
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

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append("\n");
        sb.append(this.level);
        sb.append("-level ");
        sb.append(this.school);
        sb.append("\n");
        sb.append("Casting Time: ");
        sb.append(this.castTime);
        sb.append("\n");
        sb.append("Range: ");
        sb.append(this.range);
        sb.append("\n");
        sb.append("Components: ");
        sb.append(this.components.toString());
        sb.append("\n");
        sb.append("Duration: ");
        sb.append(this.duration);
        if(this.concentration)
        {
            sb.append(" (concentration)");
        }
        sb.append("\n");
        sb.append("Classes: ");
        sb.append(this.classes);
        sb.append("\n");
        sb.append("Subclasses: ");
        sb.append(this.subclasses);
        sb.append("\n");
        sb.append(this.spellText);

        return sb.toString();
    }
}
