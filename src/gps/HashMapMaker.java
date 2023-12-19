package gps;

import java.io.*;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class HashMapMaker {
  //n'arrêtez jamais d'hashmaper

  public static HashMap<String, Integer> makeHashMap(String s) {
    HashMap<String, Integer> result = new HashMap<String, Integer>();
    String[] tab = s.split("\\{");
    for (int i = 1; i < tab.length; i++) {
      String[] tab2 = tab[i].split(",");
      tab2[0] = tab2[0].split(":")[1];
      tab2[1] = StringFormater.keepNumbers(tab2[1].split(":")[1]);
      result.put(tab2[0], Integer.parseInt(tab2[1]));
    }
    return result;
  }

  private static class Couple {
    public String key;
    public int value;

    public Couple(String key, int value) {
      this.key = key;
      this.value = value;
    }
  }

  public static Map<String, Integer> getXPlusPopulation(Map<String, Integer> map, int x) {
    //initialisation
    Couple[] resultTab = new Couple[x];
    Object[] keys = map.keySet().toArray();
    String minName = (String) keys[0];
    int min = map.get(minName);
    int index = 0;
    resultTab[0] = new Couple(minName, min);
    //remplissage du tableau avec les x premiers éléments
    for (int i = 1; i < x; i++) {
      resultTab[i] = new Couple((String) keys[i], map.get((String) keys[i]));
      //stockage du min
      if (resultTab[i].value < min) {
        min = resultTab[i].value;
        index = i;
      }
    }
    //parcours du reste de la map pour trouver les x plus grandes valeurs
    for (int i = x; i < keys.length; i++) {
      if (map.get((String) keys[i]) > min) {
        resultTab[index] = new Couple((String) keys[i], map.get((String) keys[i]));
        //recherche du nouveau min
        min = resultTab[0].value;
        index = 0;
        for (int j = 1; j < x; j++) {
          if (resultTab[j].value < min) {
            min = resultTab[j].value;
            index = j;
          }
        }
      }
    }
    //transfert du tableau dans la hashmap
    HashMap<String, Integer> result = new HashMap<String, Integer>();
    for (int i = 0; i < x; i++) {
      result.put(resultTab[i].key, resultTab[i].value);
    }
    return result;
  }

  public static Map<String, Integer> removeKey(Map<String, Integer> map, Map<String, Integer> map2remove) {
    map.keySet().removeAll(map2remove.keySet());
    return map;
  }

  public static HashMap<String, String> getcoords(Map<String, Integer> map) {
    HashMap<String, String> result = new HashMap<String, String>();
    for (String key : map.keySet()) {
      try {
        String coos;
        APICaller caller;
        String json;
        caller = new APICaller("https://api-adresse.data.gouv.fr/search/?q=" + key.replace("\"", "").replace(" ", "+"), "");
        caller.start();
        caller.join();
        HttpResponse<InputStream> response = caller.getResponse();
        if (response.headers().firstValue("Content-Encoding").orElse("no encoding").equals("gzip")) {
          InputStream in = new GZIPInputStream(new ByteArrayInputStream(response.body().readAllBytes()));
          InputStreamReader reader = new InputStreamReader(in);
          StringBuilder out = new StringBuilder();
          char[] buffer = new char[4096];
          for (int rsz; (rsz = reader.read(buffer, 0, buffer.length)) > 0; ) {
            out.append(buffer, 0, rsz);
          }
          json = out.toString();
        } else {
          StringBuilder out = new StringBuilder();
          InputStreamReader reader = new InputStreamReader(response.body());
          char[] buffer = new char[4096];
          for (int rsz; (rsz = reader.read(buffer, 0, buffer.length)) > 0; ) {
            out.append(buffer, 0, rsz);
          }
          json = out.toString();
        }
        coos = JSONReader.getField(json, "coordinates");
        result.put(key, StringFormater.invertCoords(StringFormater.keepNumbersAndPointsAndComma(coos)));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  public static List<Ville> getDoubleCoos(Map<String, String> map) {
    List<Ville> result = new ArrayList<>();
    for (String key : map.keySet()) {
      String[] coos = map.get(key).split(",");
      result.add(new Ville(key.replace("\"",""), Double.parseDouble(coos[0]), Double.parseDouble(coos[1])));
    }
    return result;
  }

  public static List<Ville> mergeLists(List<Ville> l1, List<Ville> l2) {
    List<Ville> result = new ArrayList<>();
    result.addAll(l1);
    result.addAll(l2);
    return result;
  }
}
