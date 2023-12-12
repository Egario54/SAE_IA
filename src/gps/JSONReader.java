package gps;

public class JSONReader {

  public static String format(String json){
    String result = "";
    int indent = 0;
    for(int i = 0; i < json.length(); i++){
      char c = json.charAt(i);
      if(c == '{' || c == '['){
        result += c + "\n";
        indent++;
        for(int j = 0; j < indent; j++){
          result += "  ";
        }
      }
      else if(c == '}' || c == ']'){
        result += "\n";
        indent--;
        for(int j = 0; j < indent; j++){
          result += "  ";
        }
        result += c;
      }
      else if(c == ','){
        result += c + "\n";
        for(int j = 0; j < indent; j++){
          result += "  ";
        }
      }
      else{
        result += c;
      }
    }
    return result;
  }

  public static String getField(String json, String field){
    String result = "";
    int index = json.indexOf(field);
    if(index != -1){
      index += field.length() + 3;
      char c = json.charAt(index);
      while(c != '"'){
        result += c;
        index++;
        c = json.charAt(index);
      }
    }
    return result;
  }
}
