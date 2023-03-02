package server;

import java.util.HashMap;

/**
 * Singleton class to store key-value pairs using a hashmap.
 */
public class KeyValue {

  private static KeyValue singletonInstance;
  private final HashMap <String, String> keyValStore;

  private KeyValue() {
    keyValStore = new HashMap();
    singletonInstance = null;
  }

  /**
   * Returns instance of this class. This class is instanced only once. This method returns
   * previously created object if the class was already instantiated.
   * @return instance of the class.
   */
  public static KeyValue getInstance() {
    if(singletonInstance == null) {
      singletonInstance = new KeyValue();
    }
    return singletonInstance;
  }

  /**
   * Adds a key-value pair to the store. If is already present, then the value is updated.
   * @param key key to be inserted.
   * @param value value pertaining to the key.
   */
  public void put(String key, String value) {
    keyValStore.put(key, value);
    ServerLogger.info("Added pair " + key + " : " + value + " to hashmap");
  }

  /**
   * Deletes an entry form the key-value store. Does nothing is the key is not present
   * in the hashmap.
   * @param key key pertaining to the entry to be deleted.
   * @return deletion status.
   */
  public boolean delete(String key) {
    if(keyValStore.containsKey(key)) {
      ServerLogger.info("Deleted key: "+ key + " from hashmap");
      keyValStore.remove(key);
      return true;
    }
    ServerLogger.error(String.format("key %s not found in hashmap", key));
    return false;
  }

  /**
   * Retrieves an entry form the key-value store. Returns null is the key is not present
   * in the hashmap.
   * @param key key pertaining to the value to be returned.
   * @return value.
   */
  public String get(String key) {
    if(keyValStore.containsKey(key)) {
      String value = keyValStore.get(key);
      ServerLogger.info("Returned value: "+ value +" for key: "+ key);
      return value;
    }
    ServerLogger.error(String.format("key %s not found in hashmap", key));
    return null;
  }
}
