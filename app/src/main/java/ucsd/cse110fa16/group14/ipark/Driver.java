package ucsd.cse110fa16.group14.ipark;

import android.widget.EditText;

/**
 * Created by Abhigya Ghimire on 10/19/2016.
 */

public class Driver {
    private Name name;
    private String email;
    private String license;
    private String username;
    private String password;

    public class Name {
        String firstName;
        String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Name(EditText firstName, EditText lastName){
            this.firstName = firstName.getText().toString();
            this.lastName = lastName.getText().toString();
        }

        public String getFirstName() {
            return this.firstName;
        }

        public String getLastName() {
            return this.lastName;
        }

        public String fullName(){
            return (this.firstName + " " + this.lastName);
        }

    }

    public void setName(String firstName, String lastName) {
        this.name = new Name(firstName, lastName);
    }

    public void setName(EditText firstName, EditText lastName){
        this.name = new Name(firstName.getText().toString(), lastName.getText().toString());
    }

    public String getName() {
        return name.fullName();
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email){
        this.email= email;
    }

    public void setEmail(EditText email){
        this.email = email.getText().toString();
    }

    public String getLicense() {
        return this.license;
    }

    public void setLicense(String license){
        this.license = license;
    }

    public void setLicense(EditText license){
        this.license = license.getText().toString();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setUsername(EditText username){
        this.username = username.getText().toString();
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setPassword(EditText password){
        this.password = password.getText().toString();
    }

}