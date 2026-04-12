
package pro3.lab;

import java.io.File;
import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author aya
 */
public class Student implements Serializable{
    private String id;
    private String name;
    private String gender;
    private ArrayList<String> pl =new ArrayList<>();
    static int counter;
   
    public Student(String name, String gender) {
        this.name = name;
        this.gender = gender;
        String std_id = this.gender.equals("male")?"1":"2";
        this.id =  std_id+Year.now().getValue()+String.format(Locale.US,"%04d", counter++); 
        
    }

    public String getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getPl() {
        return pl;
    }

    public void setPl(ArrayList<String> pl) {
        this.pl = pl;
    }

    @Override
    public String toString() {
        return this.name+" - "+this.id;
    }
    
    
    
}
