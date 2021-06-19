package fail.mercury.client.api.friend;

import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import fail.mercury.client.api.manager.type.ListManager;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FriendManager extends ListManager<Friend> {
   private File directory;

   public void load() {
      if (!this.directory.exists()) {
         try {
            this.directory.createNewFile();
         } catch (IOException var2) {
            var2.printStackTrace();
         }
      }

      this.loadFriends();
   }

   public void unload() {
      this.saveFriends();
   }

   public void setDirectory(File directory) {
      this.directory = directory;
   }

   public void saveFriends() {
      if (this.directory.exists()) {
         try {
            Writer writer = new FileWriter(this.directory);
            Throwable var2 = null;

            try {
               writer.write((new GsonBuilder()).setPrettyPrinting().create().toJson(this.getRegistry()));
            } catch (Throwable var12) {
               var2 = var12;
               throw var12;
            } finally {
               if (writer != null) {
                  if (var2 != null) {
                     try {
                        writer.close();
                     } catch (Throwable var11) {
                        var2.addSuppressed(var11);
                     }
                  } else {
                     writer.close();
                  }
               }

            }
         } catch (IOException var14) {
            this.directory.delete();
         }
      }

   }

   public void loadFriends() {
      if (this.directory.exists()) {
         try {
            FileReader inFile = new FileReader(this.directory);
            Throwable var2 = null;

            try {
               this.setRegistry((List)(new GsonBuilder()).setPrettyPrinting().create().fromJson(inFile, (new TypeToken<ArrayList<Friend>>() {
               }).getType()));
               if (this.getRegistry() == null) {
                  this.setRegistry(new ArrayList());
               }
            } catch (Throwable var12) {
               var2 = var12;
               throw var12;
            } finally {
               if (inFile != null) {
                  if (var2 != null) {
                     try {
                        inFile.close();
                     } catch (Throwable var11) {
                        var2.addSuppressed(var11);
                     }
                  } else {
                     inFile.close();
                  }
               }

            }
         } catch (Exception var14) {
         }

      }
   }

   public void addFriend(String name) {
      this.include(new Friend(name));
   }

   public void addFriend(String name, String alias) {
      this.include(new Friend(name, alias));
   }

   public Friend getFriend(String ign) {
      Iterator var2 = this.getRegistry().iterator();

      Friend friend;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         friend = (Friend)var2.next();
      } while(!friend.getName().equalsIgnoreCase(ign));

      return friend;
   }

   public boolean isFriend(String ign) {
      return this.getFriend(ign) != null;
   }

   public void clearFriends() {
      this.clear();
   }

   public void removeFriend(String name) {
      Friend f = this.getFriend(name);
      if (f != null) {
         this.remove(f);
      }

   }
}
