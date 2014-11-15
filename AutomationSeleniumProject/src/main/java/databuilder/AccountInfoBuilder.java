package databuilder;

/**
 * Created by Sagari on 8/16/2014.
 */
public class AccountInfoBuilder {

    private String firstName;
    private String lastName;

    public AccountInfo build(){
        return new AccountInfo(firstName, lastName);
    }

    public AccountInfoBuilder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }
    public AccountInfoBuilder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }
}
