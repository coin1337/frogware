package fail.mercury.client.client.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.InventoryUtil;
import fail.mercury.client.client.events.UpdateEvent;
import net.b0at.api.event.EventHandler;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

@ModuleManifest(
   label = "CaptchaSolver",
   category = Category.MISC,
   aliases = {"Auto Captcha"},
   fakelabel = "Captcha Solver"
)
public class CaptchaSolver extends Module {
   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (mc.func_147104_D() != null) {
         if (mc.field_71439_g.field_71070_bA instanceof ContainerChest) {
            ContainerChest chest = (ContainerChest)mc.field_71439_g.field_71070_bA;
            IInventory inv = chest.func_85151_d();
            String serverName = mc.func_147104_D().field_78845_b;
            if (inv.func_145818_k_()) {
               String chestName = inv.func_145748_c_().func_150254_d();
               if (StringUtils.containsIgnoreCase(serverName, "endcrystal.me")) {
                  for(int i = 9; i < 45; ++i) {
                     if (chest.func_75139_a(i).func_75216_d()) {
                        ItemStack is = chest.func_75139_a(i).func_75211_c();
                        if (is.func_77973_b() == Items.field_151048_u && is.func_82833_r().contains("Â§a")) {
                           mc.field_71442_b.func_187098_a(chest.field_75152_c, InventoryUtil.getItemSlot(chest, is.func_77973_b()), 0, ClickType.PICKUP, mc.field_71439_g);
                           mc.field_71442_b.func_78765_e();
                        }
                     }
                  }
               }

               if (StringUtils.containsIgnoreCase(serverName, "mc.salc1.com") && chestName.contains("Click ")) {
                  String strippedName = ChatFormatting.stripFormatting(inv.func_70005_c_().replace("Click on the ", ""));
                  if (strippedName.equalsIgnoreCase("Jack_o'_Lantern")) {
                     strippedName = "Jack_o'Lantern";
                  }

                  strippedName = strippedName.replace("spade", "shovel").replace("enchantment", "enchanting").replace("o'_lantern", "o'lantern");
                  if (InventoryUtil.getItemCount(chest, Item.func_111206_d(strippedName.toLowerCase())) > 0) {
                     mc.field_71442_b.func_187098_a(chest.field_75152_c, InventoryUtil.getItemSlot(chest, Item.func_111206_d(strippedName.toLowerCase())), 0, ClickType.PICKUP, mc.field_71439_g);
                     mc.field_71442_b.func_78765_e();
                  }
               }
            }
         }

      }
   }
}
