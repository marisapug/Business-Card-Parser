// ContactInfo.java
// A simple class for the contact information for a person.
// Used in the BusinessCardParser class

public class ContactInfo {
    private String name;
    private String phoneNumber;
    private String emailAddress;
    
    public ContactInfo(String n, String p, String e){
        this.name = n;
        this.phoneNumber = p;
        this.emailAddress = e;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    
    public String getEmailAddress(){
        return this.emailAddress;
    }
    
    public void setName(String n){
        this.name = n;
    }
    
    public void setPhoneNumber(String p){
        this.phoneNumber = p;
    }
    
    public void setEmailAddress(String e){
        this.emailAddress = e;
    }
}
