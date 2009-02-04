/**
 * JEVEAPI 0.1 library
 * by Tobias W. Kjeldsen - tobias at wkjeldsen.dk
 * free usage - I just want the credit :)
 */
package jeveapi;

import java.util.LinkedList;
import javax.swing.ImageIcon;

/**
 *
 * @author Tobias W. Kjeldsen
 */
public class Character {

    //general data
    private String characterID;
    private String characterName;
    private String race;
    private String bloodLine;
    private String gender;
    private String corporationName;
    private String corporationID;
    private String iskbalance;
    //attributes
    private String attributeIntelligence;
    private String attributeMemory;
    private String attributeCharisma;
    private String attributePerception;
    private String attributeWillpower;
    //attribute enhancers
    private LinkedList<String> attributeEnhancers;
    //skill training
    private String trainingEndTime;
    private String trainingStartTime;
    private String trainingTypeID;
    private String trainingStartSP;
    private String trainingDestinationSP;
    private String trainingToLevel;
    private String skillInTraining;
    private Item skillItem;
    //character portrait
    private ImageIcon portrait;

    public Character(String characterID, String characterName, String race, String bloodLine, String gender, String corporationName, String corporationID, String iskbalance, String attributeIntelligence, String attributeMemory, String attributeCharisma, String attributePerception, String attributeWillpower, String trainingEndTime, String trainingStartTime, String trainingTypeID, String trainingStartSP, String trainingDestinationSP, String trainingToLevel, String skillInTraining, Item skillItem, ImageIcon portrait) {
        this.characterID = characterID;
        this.characterName = characterName;
        this.race = race;
        this.bloodLine = bloodLine;
        this.gender = gender;
        this.corporationName = corporationName;
        this.corporationID = corporationID;
        this.iskbalance = iskbalance;
        this.attributeIntelligence = attributeIntelligence;
        this.attributeMemory = attributeMemory;
        this.attributeCharisma = attributeCharisma;
        this.attributePerception = attributePerception;
        this.attributeWillpower = attributeWillpower;
        this.trainingEndTime = trainingEndTime;
        this.trainingStartTime = trainingStartTime;
        this.trainingTypeID = trainingTypeID;
        this.trainingStartSP = trainingStartSP;
        this.trainingDestinationSP = trainingDestinationSP;
        this.trainingToLevel = trainingToLevel;
        this.skillInTraining = skillInTraining;
        this.skillItem = skillItem;
        this.portrait = portrait;
    }

    public Character(String characterID, String characterName, String race, String bloodLine, String gender, String corporationName, String corporationID, String iskbalance, String attributeIntelligence, String attributeMemory, String attributeCharisma, String attributePerception, String attributeWillpower, String skillInTraining, ImageIcon portrait) {
        this.characterID = characterID;
        this.characterName = characterName;
        this.race = race;
        this.bloodLine = bloodLine;
        this.gender = gender;
        this.corporationName = corporationName;
        this.corporationID = corporationID;
        this.iskbalance = iskbalance;
        this.attributeIntelligence = attributeIntelligence;
        this.attributeMemory = attributeMemory;
        this.attributeCharisma = attributeCharisma;
        this.attributePerception = attributePerception;
        this.attributeWillpower = attributeWillpower;
        this.skillInTraining = skillInTraining;
        this.portrait = portrait;
    }

    
    /**
     * 
     * @return character portrait 256x256
     */
    public ImageIcon getPortrait() {
        return portrait;
    }

    public String getAttributeCharisma() {
        return attributeCharisma;
    }

    public LinkedList<String> getAttributeEnhancers() {
        return attributeEnhancers;
    }

    public String getAttributeIntelligence() {
        return attributeIntelligence;
    }

    public String getAttributeMemory() {
        return attributeMemory;
    }

    public String getAttributePerception() {
        return attributePerception;
    }

    public String getAttributeWillpower() {
        return attributeWillpower;
    }

    public String getBloodLine() {
        return bloodLine;
    }

    public String getCharacterID() {
        return characterID;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getCorporationID() {
        return corporationID;
    }

    public String getCorporationName() {
        return corporationName;
    }

    public String getGender() {
        return gender;
    }

    public String getIskbalance() {
        return iskbalance;
    }

    public String getRace() {
        return race;
    }

    public boolean getSkillInTraining() {
        return skillInTraining.equals("1");
    }

    public String getTrainingDestinationSP() {
        return trainingDestinationSP;
    }

    public String getTrainingEndTime() {
        return trainingEndTime;
    }

    public String getTrainingStartSP() {
        return trainingStartSP;
    }

    public String getTrainingStartTime() {
        return trainingStartTime;
    }

    public String getTrainingToLevel() {
        return trainingToLevel;
    }

    public String getTrainingTypeID() {
        return trainingTypeID;
    }

    public Item getSkillItem() {
        return skillItem;
    }
}
