package me.kix.lotus.property.impl;

import java.lang.reflect.Field;
import me.kix.lotus.property.AbstractProperty;

public class BooleanProperty extends AbstractProperty<Boolean> {
   public BooleanProperty(String label, Object parentObject, Field value) {
      super(label, parentObject, value);
   }

   public void setValue(String value) {
      if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("on")) {
         if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("off")) {
            this.setValue(false);
         }
      } else {
         this.setValue(true);
      }

   }
}
