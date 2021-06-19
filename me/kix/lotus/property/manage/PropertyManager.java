package me.kix.lotus.property.manage;

import fail.mercury.client.api.manager.type.HashMapManager;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.kix.lotus.property.IProperty;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import me.kix.lotus.property.impl.BooleanProperty;
import me.kix.lotus.property.impl.NumberProperty;
import me.kix.lotus.property.impl.string.StringProperty;
import me.kix.lotus.property.impl.string.impl.ModeStringProperty;

public class PropertyManager extends HashMapManager<Object, List<IProperty>> {
   public void scan(Object object) {
      Field[] var2 = object.getClass().getDeclaredFields();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Field field = var2[var4];
         boolean accessibility = field.isAccessible();
         if (field.isAnnotationPresent(Property.class)) {
            field.setAccessible(true);
            Property property = (Property)field.getAnnotation(Property.class);

            try {
               if (field.get(object) instanceof Boolean) {
                  this.register(object, new BooleanProperty(property.value(), object, field));
               }

               if (field.get(object) instanceof String) {
                  if (field.isAnnotationPresent(Mode.class)) {
                     Mode mode = (Mode)field.getAnnotation(Mode.class);
                     this.register(object, new ModeStringProperty(property.value(), object, field, mode.value()));
                  } else {
                     this.register(object, new StringProperty(property.value(), object, field));
                  }
               }

               if (field.get(object) instanceof Number && field.isAnnotationPresent(Clamp.class)) {
                  Clamp clamp = (Clamp)field.getAnnotation(Clamp.class);
                  if (field.get(object) instanceof Integer) {
                     this.register(object, new NumberProperty(property.value(), object, field, Integer.parseInt(clamp.minimum()), Integer.parseInt(clamp.maximum())));
                  } else if (field.get(object) instanceof Double) {
                     this.register(object, new NumberProperty(property.value(), object, field, Double.parseDouble(clamp.minimum()), Double.parseDouble(clamp.maximum())));
                  } else if (field.get(object) instanceof Float) {
                     this.register(object, new NumberProperty(property.value(), object, field, Float.parseFloat(clamp.minimum()), Float.parseFloat(clamp.maximum())));
                  } else if (field.get(object) instanceof Long) {
                     this.register(object, new NumberProperty(property.value(), object, field, Long.parseLong(clamp.minimum()), Long.parseLong(clamp.maximum())));
                  } else if (field.get(object) instanceof Short) {
                     this.register(object, new NumberProperty(property.value(), object, field, Short.parseShort(clamp.minimum()), Short.parseShort(clamp.maximum())));
                  }
               }
            } catch (IllegalAccessException var9) {
               var9.printStackTrace();
            }

            field.setAccessible(accessibility);
         }
      }

   }

   public void register(Object object, IProperty property) {
      this.getRegistry().computeIfAbsent(object, (collection) -> {
         return new ArrayList();
      });
      ((List)this.pull(object)).add(property);
   }

   public List<IProperty> getPropertiesFromObject(Object object) {
      return this.getRegistry().get(object) != null ? (List)this.getRegistry().get(object) : null;
   }

   public Optional<IProperty> getProperty(Object object, String label) {
      return this.getPropertiesFromObject(object).stream().filter((property) -> {
         return property.getLabel().equalsIgnoreCase(label);
      }).findFirst();
   }
}
