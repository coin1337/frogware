package fail.mercury.client.api.manager.type;

import fail.mercury.client.api.manager.IManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HashMapManager<K, V> implements IManager {
   protected Map<K, V> registry = new HashMap();

   public Map<K, V> getRegistry() {
      return this.registry;
   }

   public Collection<V> getValues() {
      return this.registry.values();
   }

   public boolean has(K check) {
      return this.registry.containsKey(check);
   }

   public void include(K key, V val) {
      if (!this.has(key)) {
         this.registry.put(key, val);
      }

   }

   public void exclude(K key) {
      if (this.has(key)) {
         this.registry.remove(key);
      }

   }

   public V pull(K key) {
      return this.registry.get(key);
   }

   public void load() {
   }

   public void unload() {
   }
}
