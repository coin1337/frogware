package fail.mercury.client.api.manager.type;

import fail.mercury.client.api.manager.IManager;
import java.util.ArrayList;
import java.util.List;

public abstract class ListManager<T> implements IManager {
   protected List<T> registry = new ArrayList();
   protected List<Class> classRegistry;

   public List<T> getRegistry() {
      return this.registry;
   }

   public void setRegistry(List<T> registry) {
      this.registry = registry;
   }

   public List<Class> getClassRegistry() {
      return this.classRegistry;
   }

   public boolean has(T check) {
      return this.registry.contains(check);
   }

   public boolean has(Class check) {
      return this.classRegistry.contains(check);
   }

   public void include(T add) {
      if (!this.has(add)) {
         this.registry.add(add);
      }

   }

   public void include(Class add) {
      if (!this.has(add)) {
         this.classRegistry.add(add);
      }

   }

   public void remove(T remove) {
      if (this.has(remove)) {
         this.registry.remove(remove);
      }

   }

   public void remove(Class remove) {
      if (this.has(remove)) {
         this.classRegistry.remove(remove);
      }

   }

   public void register(T... queue) {
      Object[] var2 = queue;
      int var3 = queue.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         T type = var2[var4];
         this.include(type);
      }

   }

   public void register(Class... queue) {
      Class[] var2 = queue;
      int var3 = queue.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class type = var2[var4];
         this.include(type);
      }

   }

   public T pull(Class<? extends T> clazz) {
      return this.getRegistry().stream().filter((m) -> {
         return m.getClass() == clazz;
      }).findFirst().orElse((Object)null);
   }

   public void clear() {
      if (!this.getRegistry().isEmpty()) {
         this.getRegistry().clear();
      }

   }
}
