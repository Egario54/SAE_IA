package gps.initData;

import java.io.*;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class DataHandler {
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

  private record ProtoVille(String nom,int population) {}

  public static Map<String, Integer> getXPlusPopulation(Map<String, Integer> map, int x) {
    //initialisation
    ProtoVille[] resultTab = new ProtoVille[x];
    Object[] keys = map.keySet().toArray();
    String minName = (String) keys[0];
    int min = map.get(minName);
    int index = 0;
    resultTab[0] = new ProtoVille(minName, min);
    //remplissage du tableau avec les x premiers éléments
    for (int i = 1; i < x; i++) {
      resultTab[i] = new ProtoVille((String) keys[i], map.get((String) keys[i]));
      //stockage du min
      if (resultTab[i].population < min) {
        min = resultTab[i].population;
        index = i;
      }
    }
    //parcours du reste de la map pour trouver les x plus grandes valeurs
    for (int i = x; i < keys.length; i++) {
      if (map.get((String) keys[i]) > min) {
        resultTab[index] = new ProtoVille((String) keys[i], map.get((String) keys[i]));
        //recherche du nouveau min
        min = resultTab[0].population;
        index = 0;
        for (int j = 1; j < x; j++) {
          if (resultTab[j].population < min) {
            min = resultTab[j].population;
            index = j;
          }
        }
      }
    }
    //transfert du tableau dans la hashmap
    HashMap<String, Integer> result = new HashMap<String, Integer>();
    for (int i = 0; i < x; i++) {
      result.put(resultTab[i].nom, resultTab[i].population);
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
        coos = map.get(key)+ "," + JSONReader.getField(json, "coordinates");
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
      if(!(coos.length < 3))
      result.add(new Ville(key.replace("\"",""), Double.parseDouble(coos[0]), Double.parseDouble(coos[1]), Integer.parseInt(coos[2])));
      else System.out.println("coordonnées manquantes pour " + key);
    }
    return result;
  }

  public static List<Ville> mergeLists(List<Ville> l1, List<Ville> l2) {
    List<Ville> result = new ArrayList<>();
    result.addAll(l1);
    result.addAll(l2);
    return result;
  }

  public static int getLowestPop(List<Ville> list) {
    int result = list.get(0).population();
    for (Ville ville : list) {
      if (ville.population() < result) result = ville.population();
    }
    return result;
  }
  public static List<Ville>[] initLists() throws IOException {
    APICaller caller2 = new APICaller("https://geo.api.gouv.fr/communes?fields=nom,population", "");
    caller2.start();
    try {
      caller2.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
    StringBuilder out = new StringBuilder();
    InputStreamReader reader = new InputStreamReader(caller2.getResponse().body());
    char[] buffer = new char[4096];
    for (int rsz; (rsz = reader.read(buffer, 0, buffer.length)) > 0; ) {
      out.append(buffer, 0, rsz);
    }
    String json = out.toString();
    HashMap<String, Integer> map = makeHashMap(json.toString());
    Map<String, Integer> map2 = getXPlusPopulation(map, 100);
    Map<String, Integer> mapCoos50Premiers = getXPlusPopulation(map2, 50);
    map2 = removeKey(map2, mapCoos50Premiers);
    Map<String, Integer> mapCoos50Suivants = getXPlusPopulation(map2, 50);
    List<Ville> villes50PlusPeuplees = getDoubleCoos(getcoords(mapCoos50Premiers));
    List<Ville> villes50a100PlusPeuplees = getDoubleCoos(getcoords(mapCoos50Suivants));
    return new List[]{villes50PlusPeuplees, villes50a100PlusPeuplees};
  }
}
