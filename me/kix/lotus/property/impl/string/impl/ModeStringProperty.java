package me.kix.lotus.property.impl.string.impl;

import java.lang.reflect.Field;
import me.kix.lotus.property.impl.string.StringProperty;

public class ModeStringProperty extends StringProperty {
   private final String[] modes;

   public ModeStringProperty(String label, Object parentObject, Field value, String[] modes) {
      super(label, parentObject, value);
      this.modes = modes;
   }

   public void setValue(String value) {
      String[] var2 = this.modes;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String mode = var2[var4];
         if (value.equalsIgnoreCase(mode)) {
            super.setValue(value);
         } else {
            super.setValue(this.getValue());
         }
      }

   }

   public String[] getModes() {
      return this.modes;
   }

   public void increment() {
      String currentMode = (String)this.getValue();
      String[] var2 = this.modes;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String mode = var2[var4];
         if (mode.equalsIgnoreCase(currentMode)) {
            int ordinal = this.getOrdinal(mode, this.modes);
            String newValue;
            if (ordinal == this.modes.length - 1) {
               newValue = this.modes[0];
            } else {
               newValue = this.modes[ordinal + 1];
            }

            this.setValue(newValue);
            return;
         }
      }

   }

   public void decrement() {
      String currentMode = (String)this.getValue();
      String[] var2 = this.modes;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String mode = var2[var4];
         if (mode.equalsIgnoreCase(currentMode)) {
            int ordinal = this.getOrdinal(mode, this.modes);
            String newValue;
            if (ordinal == 0) {
               newValue = this.modes[this.modes.length - 1];
            } else {
               newValue = this.modes[ordinal - 1];
            }

            this.setValue(newValue);
            return;
         }
      }

   }

   private int getOrdinal(String value, String[] array) {
      for(int i = 0; i <= array.length - 1; ++i) {
         String indexString = array[i];
         if (indexString.equalsIgnoreCase(value)) {
            return i;
         }
      }

      return 0;
   }
}
