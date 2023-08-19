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
    private ArrayList<JsonSpell> spells = new ArrayList<JsonSpell>();

    public SpellDatabase()
    {
        try{
            //To-Do: Set database url based on argument maybe?
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/alexa/Documents/GitHub/DnDContentApp/backend/spelldatabase.db");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username TEXT, email TEXT)");
            //statement.execute("INSERT INTO users (username, email) VALUES ('john_doe', 'john@example.com')");

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

        }
        
    }

    public void printSpells()
    {
        if(spells.size() == 0)
        {
            System.out.println("Please populate with spells first.");
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
        String[] spellIndices = fetchAllSpellsIndices();
        
        // Only gets the fist 5 spells, change end condition to spellIndices.length to get all spells
        for(int i = 0; i < spellIndices.length; i++) 
        {
            JsonSpell spell = getSpell(spellIndices[i]);
            spells.add(spell);
        }
    }

    public static JsonSpell getSpell(String index)
    {
        return parseJson(fetchSpell(index));
    }

    public static JsonSpell parseJson(String jsonString)
    {
        Gson gson = new Gson();        
        JsonSpell newSpell = gson.fromJson(jsonString, JsonSpell.class);
        newSpell.finalize();
        return newSpell;
    }

    public static String fetchSpell(String index)
    {
        StringBuilder sb = null;
        try{
            String urlText = "https://www.dnd5eapi.co/api/spells/" + index;
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
        return sb.toString();
    }

    public static String[] fetchAllSpellsIndices()
    {
        StringBuilder sb = new StringBuilder();
        try{
            String urlText = "https://www.dnd5eapi.co/api/spells";
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
        //sd.populateWithSpells();
        
        sd.printSpells();


        //JsonSpell spell = getSpell("wish");
        //System.out.println(spell.toString());
        
        
        

        

    }
}
