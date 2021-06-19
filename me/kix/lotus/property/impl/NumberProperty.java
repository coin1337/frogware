package me.kix.lotus.property.impl;

import fail.mercury.client.api.util.MathUtil;
import java.lang.reflect.Field;
import me.kix.lotus.property.AbstractProperty;
import org.apache.commons.lang3.math.NumberUtils;

public class NumberProperty<T extends Number> extends AbstractProperty<T> {
   private final Class cls;
   private final T minimum;
   private final T maximum;

   public NumberProperty(String label, Object parentObject, Field value, T minimum, T maximum) {
      super(label, parentObject, value);
      this.minimum = minimum;
      this.maximum = maximum;
      this.cls = value.getType();
   }

   public T getMaximum() {
      return this.maximum;
   }

   public T getMinimum() {
      return this.minimum;
   }

   public void setValue(T value) {
      super.setValue(MathUtil.clamp(value, this.minimum, this.maximum));
   }

   public void setValue(String value) {
      if (this.cls != Integer.class && this.cls != Integer.TYPE) {
         if (this.cls != Double.class && this.cls != Double.TYPE) {
            if (this.cls != Float.class && this.cls != Float.TYPE) {
               if (this.cls == Long.class || this.cls == Long.TYPE) {
                  this.setValue((Number)NumberUtils.createLong(value));
               }
            } else {
               this.setValue((Number)NumberUtils.createFloat(value));
            }
         } else {
            this.setValue((Number)NumberUtils.createDouble(value));
         }
      } else {
         this.setValue((Number)NumberUtils.createInteger(value));
      }

   }
}
