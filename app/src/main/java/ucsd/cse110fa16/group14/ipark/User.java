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


    protected class Name {
        String firstName;
        String lastName;

        protected Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        protected String fullName() {
            return (this.firstName + " " + this.lastName);
        }
    }


    protected void setName(EditText firstName, EditText lastName) {
        this.name = new Name(firstName.getText().toString(), lastName.getText().toString());
    }

    protected String getEmail() {
        return this.email;
    }

    protected void setEmail(EditText email) {
        this.email = email.getText().toString().toLowerCase();
    }


    protected void setLicense(EditText license) {
        this.license = license.getText().toString();
    }

    protected String getUsername() {
        return this.username;
    }


    protected void setUsername(EditText username) {
        String userNameString = username.getText().toString();
        this.username = userNameString.toLowerCase();
    }

    protected String getPassword() {
        return this.password;
    }

    protected void setPassword(EditText password) {
        this.password = password.getText().toString();
    }

}
