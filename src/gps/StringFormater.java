package gps;

import java.net.http.HttpResponse;
import java.util.List;

public class StringFormater {

  public static String keepNumbers(String s){
    String result = "";
    for(int i = 0; i < s.length(); i++){
      if(s.charAt(i) >= '0' && s.charAt(i) <= '9') result += s.charAt(i);
    }
    return result;
  }

  public static String keepNumbersAndPointsAndComma(String s){
    String result = "";
    for(int i = 0; i < s.length(); i++){
      if((s.charAt(i) >= '0' && s.charAt(i) <= '9') || s.charAt(i) == '.' || s.charAt(i) == ',') result += s.charAt(i);
    }
    return result;
  }

  public static String invertCoords(String s){
    String result = "";
    String[] tab = s.split(",");
    for(int i = tab.length - 1; i >= 0; i--){
      result += tab[i] + ",";
    }
    return result.substring(0, result.length() - 1);
  }
}
