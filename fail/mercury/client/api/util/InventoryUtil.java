package fail.mercury.client.api.util;

import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtil implements Util {
   public static int getItemCount(Container container, Item item) {
      int itemCount = 0;

      for(int i = 0; i < 45; ++i) {
         if (container.func_75139_a(i).func_75216_d()) {
            ItemStack is = container.func_75139_a(i).func_75211_c();
            if (is.func_77973_b() == item) {
               itemCount += is.func_190916_E();
            }
         }
      }

      return itemCount;
   }

   public static int getItemSlot(Container container, Item item) {
      int slot = 0;

      for(int i = 9; i < 45; ++i) {
         if (container.func_75139_a(i).func_75216_d()) {
            ItemStack is = container.func_75139_a(i).func_75211_c();
            if (is.func_77973_b() == item) {
               slot = i;
            }
         }
      }

      return slot;
   }

   public static int getItemSlotInHotbar(Item item) {
      int slot = 0;

      for(int i = 0; i < 9; ++i) {
         ItemStack is = mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (is.func_77973_b() == item) {
            slot = i;
            break;
         }
      }

      return slot;
   }

   public static void swap(int slot, int hotbarNum) {
      mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, slot, 0, ClickType.PICKUP, mc.field_71439_g);
      mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, hotbarNum, 0, ClickType.PICKUP, mc.field_71439_g);
      mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, slot, 0, ClickType.PICKUP, mc.field_71439_g);
      mc.field_71442_b.func_78765_e();
   }
}
