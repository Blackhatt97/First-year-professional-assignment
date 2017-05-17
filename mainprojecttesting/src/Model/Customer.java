package Model;

import java.sql.Date;

/**
 * Created by blackhatt on 14/05/2017.
 */
public class Customer {

    private int id;
    private String fname;
    private String lname;
    private Date dateBirth;
    private String address;
    private String email;

    public Customer(int id,
                    String fname,
                    String lname,
                    Date dateBirth,
                    String address,
                    String email){
        this.fname = fname;
        this.lname = lname;
        this.dateBirth = dateBirth;
        this.address = address;
        this.email = email;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
