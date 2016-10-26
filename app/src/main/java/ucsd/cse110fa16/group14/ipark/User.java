package ucsd.cse110fa16.group14.ipark;

import android.widget.EditText;


public class User {

    private Name name;
    private String email;
    private String license;
    private String username;
    private String password;
    private boolean owner;


    public User() {
        this.owner = false;
    }

    public User(String userType) {
        userType = userType.toLowerCase();
        if (userType == "owner") {
            owner = true;
        } else {
            owner = false;
        }
    }

    protected class Name {
        String firstName;
        String lastName;

        protected Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        protected Name(EditText firstName, EditText lastName) {
            this.firstName = firstName.getText().toString();
            this.lastName = lastName.getText().toString();
        }

        protected String getFirstName() {
            return this.firstName;
        }

        protected String getLastName() {
            return this.lastName;
        }

        protected String fullName() {
            return (this.firstName + " " + this.lastName);
        }
    }

    /*
        protected void setName(String firstName, String lastName) {
            this.name = new Name(firstName, lastName);
        }
    */
    protected void setName(EditText firstName, EditText lastName) {
        this.name = new Name(firstName.getText().toString(), lastName.getText().toString());
    }

    protected String getName() {
        return name.fullName();
    }

    protected String getEmail() {
        return this.email;
    }

    /*
        protected void setEmail(String email){
            this.email= email;
        }
    */
    protected void setEmail(EditText email) {
        this.email = email.getText().toString();
    }

    protected String getLicense() {
        return this.license;
    }

    /*
    protected void setLicense(String license){
        this.license = license;
    }
*/
    protected void setLicense(EditText license) {
        this.license = license.getText().toString();
    }

    protected String getUsername() {
        return this.username;
    }

    /*
        protected void setUsername(String username){
            this.username = username;
        }
    */
    protected void setUsername(EditText username) {
        String userNameString = username.getText().toString();
        this.username = userNameString;
    }

    protected String getPassword() {
        return this.password;
    }

    /*
        protected void setPassword(String password){
            this.password = password;
        }
    */
    protected void setPassword(EditText password) {
        this.password = password.getText().toString();
    }

    protected boolean isOwner() {
        if (this.owner)
            return true;
        else
            return false;
    }


}
