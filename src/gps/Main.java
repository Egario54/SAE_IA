package gps;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

  public static void main(String[] args) throws IOException {
    /*APICaller caller = new APICaller("https://api-adresse.data.gouv.fr/search/?q=nancy", "");
    caller.start();
    try {
      caller.join();
    } catch (Exception e) {
      e.printStackTrace();
    }*/
    //System.out.println(caller.getResponse());
    //System.out.println(JSONReader.format(caller.getResponse().body().toString()));
    //System.out.println(JSONReader.getField(caller.getResponse().body().toString(), "label"));
    //System.out.println(Arrays.toString(StringFormater.removeChar(JSONReader.getField(caller.getResponse().body().toString(), "coordinates")).split(",")));

    APICaller caller2 = new APICaller("https://geo.api.gouv.fr/communes?fields=nom,population", "");
    caller2.start();
    try {
      caller2.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
    //System.out.println(caller2.getResponse());
    //System.out.println(caller2.getResponse().body().toString());
    StringBuilder out = new StringBuilder();
    InputStreamReader reader = new InputStreamReader(caller2.getResponse().body());
    char[] buffer = new char[4096];
    for (int rsz; (rsz = reader.read(buffer, 0, buffer.length)) > 0; ) {
      out.append(buffer, 0, rsz);
    }
    String json = out.toString();
    HashMap<String, Integer> map = HashMapMaker.makeHashMap(json.toString());
    //System.out.println(map);
    //System.out.println(map.keySet().size());
    Map<String, Integer> map2 = HashMapMaker.getXPlusPopulation(map, 100);
    //System.out.println(map2.toString().replace(", ", "\n").replace("{", "").replace("}", ""));

    //Map<String, String> mapCoos = HashMapMaker.getcoords(map2);

    System.out.println("");
    Map<String, Integer> mapCoos50Premiers = HashMapMaker.getXPlusPopulation(map2, 50);
    map2 = HashMapMaker.removeKey(map2, mapCoos50Premiers);
    Map<String, Integer> mapCoos50Suivants = HashMapMaker.getXPlusPopulation(map2, 50);
    //map2 = HashMapMaker.removeKey(map2, mapCoos50Suivants);
    System.out.println(HashMapMaker.mergeLists(HashMapMaker.getDoubleCoos(HashMapMaker.getcoords(mapCoos50Premiers)), HashMapMaker.getDoubleCoos(HashMapMaker.getcoords(mapCoos50Suivants))));
  }
}
