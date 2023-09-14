import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonElement;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpellDatabase
{

    class FileHandler implements HttpHandler
    {

        // Handler for the HTTP Server, sends the spelldatabase.db file when it receives a GET request
        @Override
        public void handle(HttpExchange exchange) throws IOException
        {
            System.out.println("Request received.");

            exchange.getResponseHeaders().set("Content-Type", "text/plain");

            OutputStream os = exchange.getResponseBody();

            File dbFile = new File(dbFilename);
            byte[] fileBytes = new byte[(int) dbFile.length()];
            FileInputStream fis = new FileInputStream(dbFile);
            fis.read(fileBytes);

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, fileBytes.length);

            os.write(fileBytes);
            os.close();
            fis.close();

            System.out.println("Request handled successfulyy.");
        }
    }

    Connection connection;
    int port = 9000;
    String dbFilename = "spelldatabase.db";

    public SpellDatabase()
    {
        try {
            //To-Do: Set database url based on argument maybe?
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
            createDatabase();

            System.out.println("Connected to " + dbFilename + " successfully.");


            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/spelldb", new FileHandler());
            server.start();

            System.out.println("Server started on port " + port);


        } catch(SQLException e)
        {
            System.err.println("Caught SQLException in connecting to the database file.");
        } catch(IOException e)
        {
            System.err.println("Caught IOException in creating HttpServer.");
        }
    }

    // Remove all the data from the database and leave an empty table
    public void emptyDatabase()
    {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE spells");
            createDatabase();
            System.out.println("Database successfully emptied.");
        } 
        catch(SQLException e) {
            System.err.println("Caught SQLException in drop table");
        }
    }

    // Create the table for spell data
    public void createDatabase()
    {
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS spells(id INTEGER PRIMARY KEY, name TEXT, level INTEGER, school TEXT, ritual BOOLEAN, concentration BOOLEAN, verbal BOOLEAN, somatic BOOLEAN, material BOOLEAN, materialText TEXT, range TEXT, duration TEXT, castTime TEXT, spellText TEXT, classes TEXT, subclasses TEXT)");
        } 
        catch(SQLException e) {
            System.err.println("Caught SQLException on create table");
        }
    }

    // SQL query to insert a spell object into the database
    public boolean insertSpellIntoDatabase(Spell spell)
    {
        //TODO: Check if spell is already in database before adding

        TableEntrySpell dbSpell = spell.getTableEntrySpell();

        String query = "SELECT COUNT(*) FROM spells WHERE name = ?";
        
        

        String input = String.format("INSERT INTO spells (name, level, school, ritual, concentration, verbal, somatic, material, materialText, range, duration, castTime, spellText, classes, subclasses) VALUES (\"%s\", %d, \"%s\", %b, %b, %b, %b, %b, \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\")", 
        dbSpell.name, dbSpell.level, dbSpell.school, dbSpell.ritual, dbSpell.concentration, dbSpell.components.hasVerbalComponents(), dbSpell.components.hasSomaticComponents(), dbSpell.components.hasMaterialComponents(), dbSpell.components.getMaterialComponentsText(), dbSpell.range, dbSpell.duration, dbSpell.castTime, dbSpell.spellText, dbSpell.classes, dbSpell.subclasses);
        try
        {
            PreparedStatement queryStatement = connection.prepareStatement((query));
            queryStatement.setString(1, spell.getName());
            ResultSet resultSet = queryStatement.executeQuery();
            if(resultSet.next())
            {
                int count = resultSet.getInt(1);
                if(count > 0)
                {
                    System.out.println("Unable to insert spell. Entry with name " + spell.getName() + " already exists.");
                    return false;
                }
            }

            Statement statement = connection.createStatement();
            statement.execute(input);
            System.out.println("Entry with name " + spell.getName() + " inserted successfully.");
            return true;
        } catch(SQLException e)
        {
            System.err.println("Unable to access database. Failed to insert " + spell.getName());
            return false;
        }
        
    }

    // Print every spell in the database
    public void printSpells()
    {
        try 
        {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM spells");
            while(resultSet.next())
            {
                System.out.println(resultSet.getString("name"));
            }
            //TODO: Print database entries
        } catch(SQLException e)
        {
            System.err.println("Unable to access database. Read failed.");
        }
    }

    // Populate the database with every spell in the SRD
    public void populateWithSpells()
    {
        String[] spellIndices = fetchAllSpellIndices();
        
        // Only gets the first spell, change end condition to spellIndices.length to get all spells
        for(int i = 0; i < spellIndices.length; i++) 
        {
            Spell spell = getSpell(spellIndices[i]);
            insertSpellIntoDatabase(spell);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.err.println(e.getStackTrace());
            }
        }

        //System.out.println(spells.get(0).getSpellToJson().toString());
    }

    // Given a spell index, returns a Spell object
    public static Spell getSpell(String index)
    {
        return parseJson(fetchSpell(index));
    }

    // Given a json representation of a spell, returns a Spell object
    public static Spell parseJson(String jsonString)
    {
        Gson gson = new Gson();        
        Spell newSpell = gson.fromJson(jsonString, Spell.class);
        newSpell.finalize();
        return newSpell;
    }

    // Given a spell index, returns a json representation of a spell
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

    // Fetch from the SRD API a json containing all spell indices, returns a string array of spell indices
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
    
    // Main method
    public static void main(String[] args)
    {
        SpellDatabase sd = new SpellDatabase();

        // Reset database to original state
        //PLEASE BE CAREFUL WITH CALLING populateWithSpells() AS IT RESULTS IN 320 API CALLS
        if(Arrays.asList(args).contains("-r"))
        {
            sd.emptyDatabase();
            sd.populateWithSpells();
        }

        //Spell animalMessenger = getSpell("animal-messenger");
        //String spellString = animalMessenger.toString();
        //System.out.println(animalMessenger.toString());

        //sd.emptyDatabase();
        //sd.populateWithSpells();
        //sd.printSpells();


        //Spell spell = getSpell("wish");
        //System.out.println(spell.toString());
        
        
        

        

    }
}
