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

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpellDatabase
{
    //private ArrayList<Spell> spells = new ArrayList<Spell>();

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
