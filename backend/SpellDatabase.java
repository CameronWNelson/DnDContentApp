import java.lang.StringBuilder;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonElement;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpellDatabase
{
    private ArrayList<Spell> spells = new ArrayList<Spell>();

    public SpellDatabase()
    {
        try{
            //To-Do: Set database url based on argument maybe?
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/alexa/Documents/GitHub/DnDContentApp/backend/spelldatabase.db");
            Statement statement = connection.createStatement();
            statement.execute("Create TABLE IF NOT EXISTS spells(id INTEGER PRIMARY KEY, name TEXT, level INTEGER, school TEXT, ritual BOOLEAN, concentration BOOLEAN, verbal BOOLEAN, somatic BOOLEAN, material BOOLEAN, materialText TEXT DEFAULT '', range TEXT, duration TEXT, castTime TEXT, spellText TEXT, classes TEXT, subclasses TEXT)");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while(resultSet.next())
            {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");

                System.out.println(id + " " + username + " " + email);
            }

        } catch(SQLException e)
        {
            System.err.println("Caught SQLException");
        }
        
    }

    // SQL query to insert a spell object into the database
    public void insertSpellIntoDatabase(Spell spell)
    {
        SpellToJson dbSpell = spell.getSpellToJson();

        String input = String.format("INSERT INTO spells (name, level, school, ritual, concentration, verbal, somatic, material, materialText, range, duration, castTime, spellText, classes, subclasses) VALUES (%s, %d, %s, %b, %b, %b, %b, %b, '%s', '%s', '%s', '%s', '%s', '%s', '%s')", 
        dbSpell.name, dbSpell.level, dbSpell.school, dbSpell.ritual, dbSpell.concentration, dbSpell.components.hasVerbalComponents(), dbSpell.components.hasSomaticComponents(), dbSpell.components.hasMaterialComponents(), dbSpell.components.getMaterialComponentsText(), dbSpell.range, dbSpell.duration, dbSpell.castTime, dbSpell.spellText, dbSpell.classes, dbSpell.subclasses);
    
        statement.execute(input);
    }

    public void printSpells()
    {
        if(spells.size() == 0)
        {
            System.out.println("Printing spells failed. Please populate with spells first.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(spells.get(0).toString());
        for(int i = 1; i < spells.size(); i++)
        {
            sb.append("\n\n");
            sb.append(spells.get(i).toString());
        }
        System.out.println(sb.toString());
    }

    public void populateWithSpells()
    {
        String[] spellIndices = fetchAllSpellIndices();
        
        // Only gets the first spell, change end condition to spellIndices.length to get all spells
        for(int i = 0; i < 1; i++) 
        {
            Spell spell = getSpell(spellIndices[i]);
            spells.add(spell);
        }
    }

    public static Spell getSpell(String index)
    {
        return parseJson(fetchSpell(index));
    }

    public static Spell parseJson(String jsonString)
    {
        Gson gson = new Gson();        
        Spell newSpell = gson.fromJson(jsonString, Spell.class);
        newSpell.finalize();
        return newSpell;
    }

    public static String fetchSpell(String index)
    {
        StringBuilder sb = null;
        try{
            String urlText = "https://www.dnd5eapi.co/api/spells/" + index;
            URL url = new URL(urlText);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            //System.out.println("Response Code: " + responseCode);

            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                
                sb = new StringBuilder();
                while((line = in.readLine()) != null)
                {
                    sb.append(line);
                }
                in.close();
                connection.disconnect();
            }
        } catch(MalformedURLException e)
        {
            System.err.println("Malformed URL exception. This should never happen as I write the URLs.");
        } catch(IOException e)
        {
            System.err.println("API fetch failed.");
        }

        //System.out.println("Obtained JSON: " + sb.toString());
        return sb.toString();
    }

    public static String[] fetchAllSpellIndices()
    {
        StringBuilder sb = new StringBuilder();
        try{
            String urlText = "https://www.dnd5eapi.co/api/spells";
            URL url = new URL(urlText);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            //System.out.println("Response Code: " + responseCode);

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
        String allSpellsJson = sb.toString();
        JsonObject jsonObject = JsonParser.parseString(allSpellsJson).getAsJsonObject();
        int count = jsonObject.get("count").getAsInt();
        JsonArray results = jsonObject.get("results").getAsJsonArray();
        String[] spellIndices = new String[count];
        for(int i = 0; i < count; i++)
        {
            spellIndices[i] = results.get(i).getAsJsonObject().get("index").getAsString();
        }


        return spellIndices;
    }
    
    public static void main(String[] args)
    {
        SpellDatabase sd = new SpellDatabase();

        //PLEASE BE CAREFUL WITH CALLING THIS AS IT RESULTS IN 320 API CALLS
        // sd.populateWithSpells();
        
        // sd.printSpells();


        //JsonSpell spell = getSpell("wish");
        //System.out.println(spell.toString());
        
        
        

        

    }
}
