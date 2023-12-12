package gps;

import java.util.Arrays;

public class Main {

  public static void main(String[] args){
    APICaller caller = new APICaller("https://api-adresse.data.gouv.fr/search/?q=nancy","");
    caller.start();
    try{
      caller.join();
    }catch(Exception e){
      e.printStackTrace();
    }
    System.out.println(caller.getResponse());
    System.out.println(JSONReader.format(caller.getResponse().body().toString()));
    System.out.println(JSONReader.getField(caller.getResponse().body().toString(), "label"));
    System.out.println(Arrays.toString(StringFormater.removeChar(JSONReader.getField(caller.getResponse().body().toString(), "coordinates")).split(",")));
  }
}
