package gps;

import java.util.List;

public class StringFormater {

  public static String removeChar(String s){
    List<Character> list = List.of('[',']','}','{');
    String result = "";
    for(int i = 0; i < s.length(); i++){
      if(!list.contains(s.charAt(i))) result += s.charAt(i);
    }
    return result;
  }
}
