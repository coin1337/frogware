package me.kix.lotus.property;

import java.lang.reflect.Field;

public abstract class AbstractProperty<T> implements IProperty<T> {
   private final String label;
   private final Object parentObject;
   private final Field value;

   public AbstractProperty(String label, Object parentObject, Field value) {
      this.label = label;
      this.parentObject = parentObject;
      this.value = value;
   }

   public T getValue() {
      Object foundValue = null;
      boolean accessible = this.value.isAccessible();
      this.value.setAccessible(true);

      try {
         foundValue = this.value.get(this.parentObject);
      } catch (IllegalAccessException var4) {
         var4.printStackTrace();
      }

      this.value.setAccessible(accessible);
      return foundValue;
   }

   public void setValue(T value) {
      boolean accessible = this.value.isAccessible();
      this.value.setAccessible(true);

      try {
         this.value.set(this.parentObject, value);
      } catch (IllegalAccessException var4) {
         var4.printStackTrace();
      }

      this.value.setAccessible(accessible);
   }

   public String getLabel() {
      return this.label;
   }
}
