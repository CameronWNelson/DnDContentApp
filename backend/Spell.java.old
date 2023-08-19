import java.lang.StringBuilder;
import java.util.ArrayList;





public class Spell implements Comparable<Spell>
{
    private String name;
    private int level;
    private School school;
    private String castTime;
    private String range;
    private String components;
    private String duration;
    private boolean concentration;
    private ArrayList<PlayerClass> playerClasses;
    private boolean ritual;
    private String spellText;

    public Spell(String name, int level, School school, String castTime, String range, String components, String duration, boolean concentration, ArrayList<PlayerClass> playerClasses, boolean ritual, String spellText)
    {
        this.name = name;
        this.level = level;
        this.school = school;
        this.castTime = castTime;
        this.range = range;
        this.components = components;
        this.duration = duration;
        this.concentration = concentration;
        this.playerClasses = playerClasses;
        this.ritual = ritual;
        this.spellText = spellText;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append("\n");
        sb.append(getOrdinalNumber(this.level));
        sb.append("-level ");
        sb.append(this.school.getSchoolName());
        sb.append("\n");
        sb.append("Casting Time: ");
        sb.append(this.castTime);
        sb.append("\n");
        sb.append("Range: ");
        sb.append(this.range);
        sb.append("\n");
        sb.append("Components: ");
        sb.append(this.components);
        sb.append("\n");
        sb.append("Duration: ");
        sb.append(this.duration);
        if(this.concentration)
        {
            sb.append(" (concentration)");
        }
        sb.append("\n");
        //do classes
        sb.append("Classes: ");
        sb.append(playerClasses.get(0));
        for(int i = 1; i < playerClasses.size(); i++)
        {
            sb.append(", ");
            playerClasses.get(i).getClassName();
        }
        sb.append("\n");
        sb.append(this.spellText);

        return sb.toString();
    }

    public String toJSON()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"Name\":\"");
        sb.append(this.name);
        sb.append("\",\n\"Level\":");
        sb.append(this.level);
        sb.append(",\n\"School\":\"");
        sb.append(this.school);
        sb.append("\",\n\"CastingTime\":\"");
        sb.append(this.castTime);
        sb.append("\",\n\"Range\":\"");
        sb.append(this.range);
        sb.append("\",\n\"Components\":\"");
        sb.append(this.components);
        sb.append("\",\n\"Duration\":\"");
        sb.append(this.duration);
        sb.append("\",\n\"Concentration\":");
        sb.append(String.valueOf(this.concentration));
        sb.append(",\n\"Classes\":[\"");
        sb.append(playerClasses.get(0));
        sb.append("\"");
        for(int i = 1; i < playerClasses.size(); i++)
        {
            sb.append(",\"");
            sb.append(playerClasses.get(i));
            sb.append("\"");
        }
        sb.append("],\n\"SpellText\":\"");
        sb.append(this.spellText);
        sb.append("\"\n}");

        return sb.toString();
    }


    @Override
    public int compareTo(Spell other)
    {
        if(this.level < other.level)
        {
            return -1;
        } else if(this.level > other.level)
        {
            return 1;
        }
        return this.name.compareTo(other.name);
        
    }

    private static String getOrdinalNumber(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Ordinal indicators are not defined for negative numbers.");
        }
        
        int lastDigit = number % 10;
        int secondLastDigit = (number / 10) % 10;

        if (secondLastDigit == 1) {
            return number + "th";
        } else {
            switch (lastDigit) {
                case 1:
                    return number + "st";
                case 2:
                    return number + "nd";
                case 3:
                    return number + "rd";
                default:
                    return number + "th";
            }
        }
    }
}