package me.rafique.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidExpert {
    private static boolean validate(String regex,String str){
        try{
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            return matcher.matches();
        }
        catch(Exception ex){ex.printStackTrace();return false;}
    }
    public static boolean email(String email){
        String regex="";
        //String regex=="^[A-Za-z0-9+_.-]{1,64}@[A-Za-z0-9.-]{2,6}$";
        regex="^[A-Za-z0-9+_-]+(?:\\.[A-Za-z0-9+_-]+)*@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9]{2,6}+)*$";
        //String regex=="^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        return validate(regex,email);
    }

    public static boolean phone(String phone){
        //String regex="^(\\d{3}[- ]?){2}\\d{4}$";
        String regex="^(\\d{3}[- ]?){2}\\d{4}|\\d{1} (?:\\d{3} ){2}\\d{4}|\\(\\d{3}\\) ?\\d{3} \\d{4}$";
        return validate(regex,phone);
    }

    public static boolean phoneEmail(String phone,String email){
        if(!phone(phone)){return false;}
        if(!email(email)){return false;}
        return true;
    }
    public static boolean phoneAndEmailEx(String phone,String email){
        if(phone.equals("")==false){
            if(!phone(phone)){return false;}
        }
        if(email.equals("")==false){
            if(!email(email)){return false;}
        }
        return true;
    }
}
