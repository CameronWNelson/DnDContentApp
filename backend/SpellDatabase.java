import java.lang.StringBuilder;
import java.util.ArrayList;


public class SpellDatabase
{
    private ArrayList<Spell> spells = new ArrayList<Spell>();

    public SpellDatabase()
    {
        
    }

    public void addSpell(Spell spell)
    {
        spells.add(spell);
    }

    public Spell createSpell(String name, int level, School school, String castTime, String range, String components, String duration, boolean concentration, ArrayList<PlayerClass> playerClasses, boolean ritual, String spellText)
    {
        Spell newSpell = new Spell(name, level, school, castTime, range, components, duration, concentration, playerClasses, ritual, spellText);
        return newSpell;
    }

    public String writeDatabaseToJSON()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"Spells\":[");
        if(spells.size() != 0)
        { 
            sb.append(spells.get(0).toJSON());
            for(int i = 1; i < spells.size(); i++)
            {
                sb.append(",");
                sb.append(spells.get(i).toJSON());
            }
        }
        sb.append("]}");
        return sb.toString();
    }
    public static void main(String[] args)
    {
        SpellDatabase sd = new SpellDatabase();
    }
}
