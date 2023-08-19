import java.util.ArrayList;
import java.util.Arrays;

public class Components
{
    private boolean verbal;
    private boolean somatic;
    private boolean material;
    private String materialText;
    

    public Components(String[] components, String materialText)
    {
        this.verbal = false;
        this.somatic = false;
        this.material = false;
        this.materialText = "";
        ArrayList<String> componentsAL = new ArrayList<String>(Arrays.asList(components));
        if(componentsAL.contains("V"))
        {
            this.verbal = true;
        }
        if(componentsAL.contains("S"))
        {
            this.somatic = true;
        }
        if(componentsAL.contains("M"))
        {
            this.material = true;
            this.materialText = materialText;
        }
    }

    public Components(boolean verbal, boolean somatic, boolean material, String materialText)
    {
        this.verbal = verbal;
        this.somatic = somatic;
        this.material = material;
        this.materialText = materialText;
    }

    public boolean hasVerbalComponents()
    {
        return this.verbal;
    }

    public boolean hasSomaticComponents()
    {
        return this.somatic;
    }

    public boolean hasMaterialComponents()
    {
        return this.material;
    }

    public String getMaterialComponentsText()
    {
        return this.materialText;
    }
}
