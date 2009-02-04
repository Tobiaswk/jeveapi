/**
 * JEVEAPI 0.1 library
 * by Tobias W. Kjeldsen - tobias at wkjeldsen.dk
 * free usage - I just want the credit :)
 */
package jeveapi;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Tobias W. Kjeldsen
 */
public class Item {

    private String id;
    private String name;
    private String description;
    private String radius;
    private String mass;
    private String volume;
    private String capacity;
    private String basePrice;
    private ImageIcon image;

    public Item(String id, String itemname, String itemdescription, String radius, String mass, String volume, String capacity, String basePrice, ImageIcon image) {
        this.id = id;
        this.name = itemname;
        this.description = itemdescription;
        this.radius = radius;
        this.mass = mass;
        this.volume = volume;
        this.capacity = capacity;
        this.basePrice = basePrice;
        this.image = image;
    }

    /**
     * 
     * @return image of item - null if no image
     * @throws jeveapi.JEVEAPIException
     */
    public ImageIcon getImage() throws JEVEAPIException {
        return image;
    }

    public String getId() {
        return id;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getDescription() {
        return description;
    }

    public String getMass() {
        return mass;
    }

    public String getName() {
        return name;
    }

    public String getRadius() {
        return radius;
    }

    public String getVolume() {
        return volume;
    }
}
