package fail.mercury.client.api.module;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.annotations.Replace;
import fail.mercury.client.api.module.category.Category;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.kix.lotus.property.AbstractProperty;
import net.minecraft.client.Minecraft;

public class Module {
   private String label;
   private String[] alias;
   private String fakelabel;
   private String suffix;
   private String description;
   private int bind;
   private boolean hidden;
   private boolean enabled;
   private boolean persistent;
   private List<AbstractProperty> properties = new ArrayList();
   private Category category;
   public static Minecraft mc = Minecraft.func_71410_x();

   public Module() {
      if (this.getClass().isAnnotationPresent(Replace.class)) {
         Replace replace = (Replace)this.getClass().getAnnotation(Replace.class);
         if (Mercury.INSTANCE.getModuleManager().find(replace.value()) != null) {
            String name = Mercury.INSTANCE.getModuleManager().find(replace.value()).getLabel();
            Mercury.INSTANCE.getModuleManager().exclude(name);
         }
      }

      if (this.getClass().isAnnotationPresent(ModuleManifest.class)) {
         ModuleManifest moduleManifest = (ModuleManifest)this.getClass().getAnnotation(ModuleManifest.class);
         this.label = moduleManifest.label();
         this.category = moduleManifest.category();
         this.alias = moduleManifest.aliases();
         this.fakelabel = moduleManifest.fakelabel();
         this.hidden = moduleManifest.hidden();
         this.description = moduleManifest.description();
         this.persistent = moduleManifest.persistent();
      }

   }

   public void init() {
      Mercury.INSTANCE.getPropertyManager().scan(this);
   }

   public AbstractProperty find(String term) {
      Iterator var2 = this.properties.iterator();

      AbstractProperty property;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         property = (AbstractProperty)var2.next();
      } while(!property.getLabel().equalsIgnoreCase(term));

      return property;
   }

   public Category getCategory() {
      return this.category;
   }

   public List<AbstractProperty> getProperties() {
      return this.properties;
   }

   public String[] getAlias() {
      return this.alias;
   }

   public String getLabel() {
      return this.label;
   }

   public String getFakeLabel() {
      return this.fakelabel;
   }

   public void setFakeLabel(String fakelabel) {
      this.fakelabel = fakelabel;
   }

   public void setSuffix(String suffix) {
      this.suffix = suffix;
   }

   public String getSuffix() {
      return this.suffix;
   }

   public String getDescription() {
      return this.description;
   }

   public int getBind() {
      return this.bind;
   }

   public void setBind(int bind) {
      this.bind = bind;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public boolean isPersistent() {
      return this.persistent;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
      if (enabled) {
         Mercury.INSTANCE.getEventManager().registerListener(this);
         this.onEnable();
         this.onToggle();
      } else {
         Mercury.INSTANCE.getEventManager().deregisterListener(this);
         this.onDisable();
         this.onToggle();
      }

   }

   public boolean isHidden() {
      return this.hidden;
   }

   public void setHidden(boolean hidden) {
      this.hidden = hidden;
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void onToggle() {
   }

   public void toggle() {
      this.setEnabled(!this.isEnabled());
   }

   public void save(JsonObject destination) {
      if (Mercury.INSTANCE.getPropertyManager().getPropertiesFromObject(this) != null) {
         Mercury.INSTANCE.getPropertyManager().getPropertiesFromObject(this).forEach((property) -> {
            destination.addProperty(property.getLabel(), property.getValue().toString());
         });
      }

      destination.addProperty("Bind", this.getBind());
      if (!this.isPersistent()) {
         destination.addProperty("Enabled", this.isEnabled());
      }

      destination.addProperty("Hidden", this.isHidden());
      destination.addProperty("FakeLabel", this.getFakeLabel());
   }

   public void load(JsonObject source) {
      source.entrySet().forEach((entry) -> {
         if (Mercury.INSTANCE.getPropertyManager().getPropertiesFromObject(this) != null) {
            source.entrySet().forEach((entri) -> {
               Mercury.INSTANCE.getPropertyManager().getProperty(this, (String)entri.getKey()).ifPresent((property) -> {
                  property.setValue(((JsonElement)entri.getValue()).getAsString());
               });
            });
         }

         String var3 = (String)entry.getKey();
         byte var4 = -1;
         switch(var3.hashCode()) {
         case -2133620278:
            if (var3.equals("Hidden")) {
               var4 = 1;
            }
            break;
         case -1275517825:
            if (var3.equals("FakeLabel")) {
               var4 = 3;
            }
            break;
         case 2070621:
            if (var3.equals("Bind")) {
               var4 = 2;
            }
            break;
         case 55059233:
            if (var3.equals("Enabled")) {
               var4 = 0;
            }
         }

         switch(var4) {
         case 0:
            if (!this.isPersistent() && ((JsonElement)entry.getValue()).getAsBoolean()) {
               this.setEnabled(((JsonElement)entry.getValue()).getAsBoolean());
            }
            break;
         case 1:
            if (((JsonElement)entry.getValue()).getAsBoolean()) {
               this.setHidden(((JsonElement)entry.getValue()).getAsBoolean());
            }
            break;
         case 2:
            this.setBind(((JsonElement)entry.getValue()).getAsInt());
            break;
         case 3:
            this.setFakeLabel(((JsonElement)entry.getValue()).getAsString());
         }

      });
   }
}
