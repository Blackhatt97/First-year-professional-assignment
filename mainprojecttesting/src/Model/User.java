package Model;


import java.sql.Date;

/**
 * Created by blackhatt on 14/05/2017.
 */
public class User {

    private int id;
    private String fname;
    private String lname;
    private Date dateBirth;
    private String email;
    private String address;
    private String type;

    public User(int id,
                String fname,
                String lname,
                Date dateBirth,
                String email,
                String address,
                String type){

        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.dateBirth = dateBirth;
        this.email = email;
        this.address = address;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
