/**
 * JEVEAPI 0.1 library
 * by Tobias W. Kjeldsen - tobias at wkjeldsen.dk
 * free usage - I just want the credit :)
 */
package jeveapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import javax.swing.ImageIcon;

/**
 *
 * @author Tobias W. Kjeldsen
 */
public class FetchAPIData {

    private long characterID,  userID;
    private String apiKey;
    private boolean cache;
    /* these finals are just the urls for the xml data from the EVE API */
    private final String charactersheet = "http://api.eve-online.com/char/CharacterSheet.xml.aspx";
    private final String skillintrainingsheet = "http://api.eve-online.com/char/SkillInTraining.xml.aspx";

    /* for fetching data not needing vertification, no caching */
    public FetchAPIData() {
        this(false);
    }

    /* constructors area */
    /** for fetching data not needing vertification, caching flag
     * @param cache define if cache should be enabled
     */
    public FetchAPIData(boolean cache) {
        this.cache = cache;
    }

    /** for fetching data were characterID is not used
     * @param userID define userid
     * @param apiKey define api-key
     * @param cache define if cache should be enabled
     */
    public FetchAPIData(long userID, String apiKey, boolean cache) {
        this.userID = userID;
        this.apiKey = apiKey;
        this.cache = cache;
    }

    /** for fetching data needing characterID, userID and apiKey
     * @param characterID define characterid
     * @param userID  define userid
     * @param apiKey define api-key
     * @param cache define if cache should be enabled
     */
    public FetchAPIData(long characterID, long userID, String apiKey, boolean cache) {
        this.characterID = characterID;
        this.userID = userID;
        this.apiKey = apiKey;
        this.cache = cache;
    }

    /**
     * @param characterID the character you want to fetch
     * @return Character portrait 256x256
     * @throws jeveapi.JEVEAPIException
     */
    private ImageIcon getCharacterPortrait() throws JEVEAPIException {
        URL url = null;
        try {
            url = new URL("http://img.eve.is/serv.asp?s=256&c=" + this.characterID);
        } catch (MalformedURLException ex) {
            throw new JEVEAPIException("Could not fetch character portrait - server possible down!");
        }
        return new ImageIcon(url);
    }

    /**
     * @param characterID the character you want to fetch
     * @return Character-object containing data
     * @throws jeveapi.JEVEAPIException
     */
    public Character getCharacter(long characterID) throws JEVEAPIException {
        this.characterID = characterID;
        Character character = null;
        String charxml = checkCache(charactersheet, "CharacterSheet");
        String skillxml = checkCache(skillintrainingsheet, "SkillInTrainingSheet");
        try {
            if (ParseAPIData.getValue(skillxml, "skillInTraining").equals("1")) {
                character = new Character(
                        ParseAPIData.getValue(charxml, "characterID"),
                        ParseAPIData.getValue(charxml, "name"),
                        ParseAPIData.getValue(charxml, "race"),
                        ParseAPIData.getValue(charxml, "bloodLine"),
                        ParseAPIData.getValue(charxml, "gender"),
                        ParseAPIData.getValue(charxml, "corporationName"),
                        ParseAPIData.getValue(charxml, "corporationID"),
                        ParseAPIData.getValue(charxml, "balance"),
                        ParseAPIData.getValue(charxml, "intelligence"),
                        ParseAPIData.getValue(charxml, "memory"),
                        ParseAPIData.getValue(charxml, "charisma"),
                        ParseAPIData.getValue(charxml, "perception"),
                        ParseAPIData.getValue(charxml, "willpower"),
                        ParseAPIData.getValue(skillxml, "trainingEndTime"),
                        ParseAPIData.getValue(skillxml, "trainingStartTime"),
                        ParseAPIData.getValue(skillxml, "trainingTypeID"),
                        ParseAPIData.getValue(skillxml, "trainingStartSP"),
                        ParseAPIData.getValue(skillxml, "trainingDestinationSP"),
                        ParseAPIData.getValue(skillxml, "trainingToLevel"),
                        ParseAPIData.getValue(skillxml, "skillInTraining"),
                        getItem(Integer.parseInt(ParseAPIData.getValue(skillxml, "trainingTypeID"))),
                        getCharacterPortrait());
            } else {
                character = new Character(
                        ParseAPIData.getValue(charxml, "characterID"),
                        ParseAPIData.getValue(charxml, "name"),
                        ParseAPIData.getValue(charxml, "race"),
                        ParseAPIData.getValue(charxml, "bloodLine"),
                        ParseAPIData.getValue(charxml, "gender"),
                        ParseAPIData.getValue(charxml, "corporationName"),
                        ParseAPIData.getValue(charxml, "corporationID"),
                        ParseAPIData.getValue(charxml, "balance"),
                        ParseAPIData.getValue(charxml, "intelligence"),
                        ParseAPIData.getValue(charxml, "memory"),
                        ParseAPIData.getValue(charxml, "charisma"),
                        ParseAPIData.getValue(charxml, "perception"),
                        ParseAPIData.getValue(charxml, "willpower"),
                        ParseAPIData.getValue(skillxml, "skillInTraining"),
                        getCharacterPortrait());
            }
        } catch (Exception ex) {
            throw new JEVEAPIException("Could not fetch data from EVE API! Check your authentication arguments!");
        }
        return character;
    }

    /**
     * 
     * @return Character-object containing data
     * @throws jeveapi.JEVEAPIException
     */
    public Character getCharacter() throws JEVEAPIException {
        return getCharacter(this.characterID);
    }

    /**
     * @note CharacterSheet and SkillInTraining combined as one entity
     */
    public void cacheCharacter() {
        cache = true;
        getData(charactersheet, "CharacterSheet");
        getData(skillintrainingsheet, "SkillInTrainingSheet");
        cache = false;
    }

    /**
     * this function is only used by this class alone -
     * therefore private
     */
    private String getData(String location, String type) {
        String output = null;
        try {
            //prepare a string of key value pairs.
            /* String paramStr =
            "&characterID=" + URLEncoder.encode(characterID, "iso-8859-1") + "&userID=" + URLEncoder.encode(userID, "iso-8859-1") + "&apiKey=" + URLEncoder.encode(apiKey, "iso-8859-1"); */
            String paramStr = "&characterID=" + characterID + "&userID=" + userID + "&apiKey=" + apiKey;

            // POST requests are required to have Content-Length
            String lengthString = String.valueOf(paramStr.length());

            /*
             *Establish a connection to the application.
             */
            URL url = new URL(location);
            URLConnection conn = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            String protocol = url.getProtocol();

            /*
             * configure the connection to allow for the operations necessary.
             * 1. Turn off all caching, so that each new request/response is made
             * from a fresh connection with the servlet.
             * 2. Indicate that this client will attempt to SEND and READ data to/from the servlet.
             */
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            // Set the content type we are POSTing and length of the data.
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length", lengthString);

            OutputStream os = con.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            // send data to servlet
            osw.write(paramStr);
            osw.flush();
            osw.close();

            //collect the response code from the servlet
            int responseCode = -1;
            if (protocol.equalsIgnoreCase("HTTP")) {
                responseCode = ((HttpURLConnection) con).getResponseCode();
            } else if (protocol.equalsIgnoreCase("HTTPS")) {
                responseCode = ((com.sun.net.ssl.HttpsURLConnection) con).getResponseCode();
            }

            //Read the input from that application
            InputStream is = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line = null;
            PrintWriter writecache = null;
            if (cache) {
                writecache = new PrintWriter(new BufferedWriter(new FileWriter(type)));
                while ((line = br.readLine()) != null) {
                    writecache.println(line);
                    output += line;
                }
                writecache.close();
            } else {
                while ((line = br.readLine()) != null) {
                    output += line;
                }
            }
            br.close();
            isr.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return output;
    }

    /*
     * this baby is fetching from my/your own server - instead of from the EVE API
     * - includes complete data of all items in EVE
     * note; php-script and mysql-data is included with source code
     */
    private Item getItem(String type, String lookup) throws Exception {
        Item item = null;
        try {
            /* you could replace this with new server url */
            URL url = new URL("http://wkjeldsen.dk/jeveapi/itemdb/?" + type + "=" + lookup);
            URLConnection urlc = url.openConnection();
            DataInputStream in = new DataInputStream(urlc.getInputStream()); // To download
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            /* this String is filled with data from the DataInputStream
             * data is fetched from my/your own server
             * data read is in xml via phpscript */
            String data = null;
            while (read.ready()) {
                data += read.readLine();
            }
            read.close();
            if (data == null) {
                throw new JEVEAPIException("No item with the provided data: " + lookup);
            }
            /* this image if fetched from eve-online.com - only ship-images are used */
            URL imageurl = new URL("http://www.eve-online.com/bitmaps/icons/itemdb/shiptypes/128_128/" + ParseAPIData.getValue(data, "id") + ".png");
            ImageIcon image = new ImageIcon(imageurl);
            if (image.getImageLoadStatus() != 8) {
                image = null;
            }
            item = new Item(
                    ParseAPIData.getValue(data, "id"),
                    ParseAPIData.getValue(data, "name"),
                    ParseAPIData.getValue(data, "description"),
                    ParseAPIData.getValue(data, "radius"),
                    ParseAPIData.getValue(data, "mass"),
                    ParseAPIData.getValue(data, "volume"),
                    ParseAPIData.getValue(data, "capacity"),
                    ParseAPIData.getValue(data, "baseprice"),
                    image);
        } catch (IOException ex) {
            throw new JEVEAPIException("Could not create item - server possibly down!");
        }
        return item;
    }

    /**
     * 
     * @param typeID ID on item
     * @return Item
     * @throws java.lang.Exception 
     */
    public Item getItem(int typeID) throws Exception {
        return getItem("typeID", "" + typeID);
    }

    /**
     * 
     * @param typeName Name on item
     * @return Item
     * @throws java.lang.Exception 
     */
    public Item getItem(String typeName) throws Exception {
        return getItem("typeName", "" + typeName);
    }

    private String checkCache(String location, String type) {
        if (cache) {
            try {
                Scanner scan = new Scanner(new File(type));
                String output = "";
                while (scan.hasNext()) {
                    output += scan.nextLine();
                }
                scan.close();
                return output;
            } catch (FileNotFoundException ex) {
                System.out.println("Error! No cached file: " + type + "... Fetching from EVE API...");
            }
        }
        return getData(location, type);
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setCharacterID(long characterID) {
        this.characterID = characterID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }
}
