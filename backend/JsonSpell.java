import java.lang.StringBuilder;
import java.util.ArrayList;





public class JsonSpell implements Comparable<JsonSpell>
{
    private String index;
    private String name;
    private String[] desc;
    private String[] higher_level;
    private String range;
    private String[] components;
    private String material;
    private boolean ritual;
    private String duration;
    private boolean concentration;
    private String casting_time;
    private int level;

    private PlayerClass[] classes;
    private School school;


    private String spellText;
    private String[] classNames;
    private String componentsText;
    private String spellLevelText;

    
    public void finalize()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getOrdinalNumber(level));
        sb.append("-level");
        if(ritual)
        {
            sb.append(" (Ritual)");
        }
        spellLevelText = sb.toString();
        sb = new StringBuilder();
        sb.append(desc[0]);
        for(int i = 1; i < desc.length; i++)
        {
            sb.append("\n\n");
            sb.append(desc[i]);
        }
        spellText = sb.toString();
        if(higher_level.length != 0)
        {
            spellText += "\n\nAt Higher Levels: " + higher_level[0];
        }
        classNames = new String[classes.length];
        for(int i = 0; i < classes.length; i++)
        {
            classNames[i] = classes[i].getName();
        }
        sb = new StringBuilder();
        for(int i = 0; i < components.length; i++)
        {
            sb.append(components[i]);
        }
        
        if(material != null)
        {
            sb.append(" (");
            sb.append(material);
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
        }
        componentsText = sb.toString();
    }

    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append("\n");
        sb.append(this.spellLevelText);
        sb.append("\n");
        sb.append("Casting Time: ");
        sb.append(this.casting_time);
        sb.append("\n");
        sb.append("Range: ");
        sb.append(this.range);
        sb.append("\n");
        sb.append("Components: ");
        sb.append(this.componentsText);
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
        sb.append(classNames[0]);
        for(int i = 1; i < classNames.length; i++)
        {
            sb.append(", ");
            sb.append(classNames[i]);
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
        sb.append(this.casting_time);
        sb.append("\",\n\"Range\":\"");
        sb.append(this.range);
        sb.append("\",\n\"Components\":\"");
        sb.append(this.componentsText);
        sb.append("\",\n\"Duration\":\"");
        sb.append(this.duration);
        sb.append("\",\n\"Concentration\":");
        sb.append(String.valueOf(this.concentration));
        sb.append(",\n\"Classes\":[\"");
        sb.append(classNames[0]);
        sb.append("\"");
        for(int i = 1; i < classNames.length; i++)
        {
            sb.append(",\"");
            sb.append(classNames[i]);
            sb.append("\"");
        }
        sb.append("],\n\"SpellText\":\"");
        sb.append(this.spellText);
        sb.append("\"\n}");

        return sb.toString();
    }

    //*/
    @Override
    public int compareTo(JsonSpell other)
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
    //*/

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