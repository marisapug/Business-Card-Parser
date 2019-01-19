// This is the BusinessCardParser class
// The BCP will take a .txt file name as a command line argument
// The .txt file should contain the contents of the business card,
    // with each piece of information separated by newline (\n)
// The BCP will parse the contents of the .txt file
// The BCP will print out the results in the following format:
// Name:
// Phone:
// Email:

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;

public class BusinessCardParser {
    private ContactInfo contact;
    
    ContactInfo getContactInfo(){
        return this.contact;
    }
    
    public void printContact(){
        System.out.println("Name: " + " " + this.getContactInfo().getName());
        System.out.println("Phone: " + " " + this.getContactInfo().getPhoneNumber());
        System.out.println("Email: " + " " + this.getContactInfo().getEmailAddress());
    }
    
    String removeNonNumeric(String s){
        return s.replaceAll("[^0-9]", "");
    }
    
    public static String readTxtFileToString(String fileName) throws IOException {
        String content = "";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null){
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        br.close();
        content = sb.toString();
        return content;
    }
    
    // Phone regular expression explained
    // (\+\d{1})?[-,\s,(]?(\d{3})[-,\s,)]*(\d{3})[-,\s]?(\d{4})
    // The country code is optional
    // Dashes, spaces, and parenthesis are optional
    // Requires an area code, and then a set of three digits, and a set of four digits
    // .* indicates wildcard characters; there may be anything before or after the regex.
    String extractPhone(String document){
        String[] phoneWords = {"Tel", "Phone", "Cell", "Telephone", "phone", "T"};
        String phoneRegex = ".*(\\+\\d{1})?[-,\\s,(,.]*(\\d{3})[-,\\s,),.]*(\\d{3})[-,\\s,.]*(\\d{4}).*";
        //Pattern p = Pattern.compile(phoneRegex);
        String[] arr = document.split("\n");
        String phone = "";
        List<String> matches = new ArrayList<>();
        int count = 0;
        for (String s: arr){
            if (s.matches(phoneRegex)){
                matches.add(s);
                count++;
            }
        }
        if (count == 1){
            phone = matches.get(0);
        }
        else if (count > 1){
            for (String s: matches){
                for (String p: phoneWords){
                    if (s.contains(p)){
                        phone = s;
                    }
                }
            }
        }
        return removeNonNumeric(phone);
    }
    
    // In order to extract the name, I downloaded a .txt file of most common first names from dominictarr on GitHub.
    // The code checks each piece of information in the business card to see if it contains any of the common first names.
    // This code is my own original work, but "first-names".txt is not mine.
    String extractName(String document){
        String name = "";
        String name_string = "";
        try {
            name_string = readTxtFileToString("first-names.txt");
        } catch (IOException e){
            System.out.println("IO Exception caught.");
        }
        String[] arr = document.split("\n");
        String[] name_list = name_string.split("\n");
        for (String s: arr){
            for (String n: name_list){
                if (s.contains(n)){
                    name = s;
                }
            }
        }
        return name;
    }
    
    // Email regular expression explained
    // (\w)+(@{1})(\w)+(.{1})(\w)+
    // Any combination of numeric or alphabetic letters
    // Followed by exactly one @ symbol
    // Followed by any combination of numeric or alphabetic letters
    // Followed by exactly one period "dot"
    // Followed by any combination of numeric or alphabetic letters
    String extractEmail(String document){
        String emailRegex = "(\\w)+(@{1})(\\w)+(.{1})(\\w)";
        String emailRegex2 = ".*(\\w)+(.{1})(\\w)+(@{1})(\\w)+(.{1})(\\w)+.*";
        String[] arr = document.split("\n");
        String email = "";
        for (String s: arr){
            if (s.matches(emailRegex) || s.matches(emailRegex2)){
                email = s;
            }
        }
        return email;
    }
    
    void setContact(String d){
        String na = extractName(d);
        String ph = extractPhone(d);
        String ea = extractEmail(d);
        ContactInfo c = new ContactInfo(na, ph, ea);
        this.contact = c;
    }
    
    ContactInfo getContact(){
        return this.contact;
    }
    
    
    public static void main(String[] args){
        String file = args[0];
        String businessCard = "";
        try {
            businessCard = readTxtFileToString(file);
        } catch (IOException e){
            System.out.println("IO Exception upon reading txt file.");
        }
        System.out.println("Contents of business card:");
        System.out.println(businessCard);
        BusinessCardParser bcp = new BusinessCardParser();
        bcp.setContact(businessCard);
        System.out.println("Results of parsing:");
        bcp.printContact();
    }

}
