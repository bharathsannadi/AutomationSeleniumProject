package databuilder;

/**
 * Created by Sagari on 8/16/2014.
 */
public class AccountInfo {
    private String firstName = "Bharath";
    private String lastName = "Sannadi";

    public AccountInfo(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }
}
