package me.kix.lotus.property.impl.string;

import java.lang.reflect.Field;
import me.kix.lotus.property.AbstractProperty;

public class StringProperty extends AbstractProperty<String> {
   public StringProperty(String label, Object parentObject, Field value) {
      super(label, parentObject, value);
   }
}
