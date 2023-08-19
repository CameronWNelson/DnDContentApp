import java.lang.StringBuilder;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SpellDatabase
{
    //private ArrayList<Spell> spells = new ArrayList<Spell>();

    public SpellDatabase()
    {
        
    }

    /*
    public void addSpell(Spell spell)
    {
        spells.add(spell);
    }

    /* 
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
    } //*/
    public static void main(String[] args)
    {
        SpellDatabase sd = new SpellDatabase();

        StringBuilder sb = null;
        try{
            String urlText = "https://www.dnd5eapi.co/api/spells/flame-strike";
            URL url = new URL(urlText);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                
                sb = new StringBuilder();
                while((line = in.readLine()) != null)
                {
                    sb.append(line);
                }
                in.close();
                conn.disconnect();
            }
        } catch(MalformedURLException e)
        {

        } catch(IOException e)
        {

        }

        System.out.println("Obtained JSON: " + sb.toString());

        Gson gson = new Gson();        
        String jsonString = sb.toString();
        JsonSpell newSpell = gson.fromJson(jsonString, JsonSpell.class);
        newSpell.finalize();

        System.out.println("Complete.");
        System.out.println();
        System.out.println(newSpell.toString());

    }
}
