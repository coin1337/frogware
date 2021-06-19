package fail.mercury.client.client.modules.misc;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.PacketEvent;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.network.login.client.CPacketEncryptionResponse;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEnchantItem;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlaceRecipe;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketSeenAdvancements;
import net.minecraft.network.play.client.CPacketSpectate;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.status.client.CPacketPing;
import net.minecraft.network.status.client.CPacketServerQuery;

@ModuleManifest(
   label = "PacketCancel",
   fakelabel = "Packet Cancel",
   category = Category.MISC,
   description = "Cancels certain packets for various purposes."
)
public class PacketCancel extends Module {
   @Property("Animation")
   public boolean animation = false;
   @Property("KeepAlive")
   public boolean keepalive = false;
   @Property("ChatMessage")
   public boolean chatmessage = false;
   @Property("ClickWindow")
   public boolean clickwindow = false;
   @Property("ClientSettings")
   public boolean clientsettings = false;
   @Property("ClientStatus")
   public boolean clientstatus = false;
   @Property("CloseWindow")
   public boolean closewindow = false;
   @Property("ConfirmTeleport")
   public boolean confirmteleport = false;
   @Property("ConfirmTransaction")
   public boolean confirmtransaction = false;
   @Property("CreativeInventoryAction")
   public boolean creativeinventoryaction = false;
   @Property("CustomPayload")
   public boolean custompayload = false;
   @Property("HeldItemChange")
   public boolean helditemchange = false;
   @Property("EnchantItem")
   public boolean enchantitem = false;
   @Property("EntityAction")
   public boolean entityaction = false;
   @Property("PlaceRecipe")
   public boolean placerecipe = false;
   @Property("Input")
   public boolean input = false;
   @Property("Player")
   public boolean player = false;
   @Property("PlayerAbilities")
   public boolean playerabilities = false;
   @Property("PlayerTryUseItem")
   public boolean tryuseitem = false;
   @Property("PlayerTryUseItemOnBlock")
   public boolean tryuseitemonblock = false;
   @Property("PlayerDigging")
   public boolean playerdigging = false;
   @Property("RecipeInfo")
   public boolean recipeinfo = false;
   @Property("ResourcePackStatus")
   public boolean resourcepackstatus = false;
   @Property("SeenAdvancements")
   public boolean seendadvancements = false;
   @Property("Spectate")
   public boolean spectate = false;
   @Property("SteerBoat")
   public boolean steerboat = false;
   @Property("TabComplete")
   public boolean tabcomplete = false;
   @Property("UpdateSign")
   public boolean updatesign = false;
   @Property("VehicleMove")
   public boolean vehiclemove = false;
   @Property("EncryptionResponse")
   public boolean encryptionresponse = false;
   @Property("Ping")
   public boolean ping = false;
   @Property("ServerQuery")
   public boolean serverquery = false;

   @EventHandler
   public void onPacket(PacketEvent e) {
      if (this.animation && e.getPacket() instanceof CPacketAnimation) {
         e.setCancelled(true);
      }

      if (this.keepalive && e.getPacket() instanceof CPacketKeepAlive) {
         e.setCancelled(true);
      }

      if (this.chatmessage && e.getPacket() instanceof CPacketChatMessage) {
         e.setCancelled(true);
      }

      if (this.clickwindow && e.getPacket() instanceof CPacketClickWindow) {
         e.setCancelled(true);
      }

      if (this.clientsettings && e.getPacket() instanceof CPacketClientSettings) {
         e.setCancelled(true);
      }

      if (this.clientstatus && e.getPacket() instanceof CPacketClientStatus) {
         e.setCancelled(true);
      }

      if (this.closewindow && e.getPacket() instanceof CPacketCloseWindow) {
         e.setCancelled(true);
      }

      if (this.confirmteleport && e.getPacket() instanceof CPacketConfirmTeleport) {
         e.setCancelled(true);
      }

      if (this.confirmtransaction && e.getPacket() instanceof CPacketConfirmTransaction) {
         e.setCancelled(true);
      }

      if (this.creativeinventoryaction && e.getPacket() instanceof CPacketCreativeInventoryAction) {
         e.setCancelled(true);
      }

      if (this.custompayload && e.getPacket() instanceof CPacketCustomPayload) {
         e.setCancelled(true);
      }

      if (this.enchantitem && e.getPacket() instanceof CPacketEnchantItem) {
         e.setCancelled(true);
      }

      if (this.entityaction && e.getPacket() instanceof CPacketEntityAction) {
         e.setCancelled(true);
      }

      if (this.helditemchange && e.getPacket() instanceof CPacketHeldItemChange) {
         e.setCancelled(true);
      }

      if (this.input && e.getPacket() instanceof CPacketInput) {
         e.setCancelled(true);
      }

      if (this.placerecipe && e.getPacket() instanceof CPacketPlaceRecipe) {
         e.setCancelled(true);
      }

      if (this.player && e.getPacket() instanceof CPacketPlayer) {
         e.setCancelled(true);
      }

      if (this.playerabilities && e.getPacket() instanceof CPacketPlayerAbilities) {
         e.setCancelled(true);
      }

      if (this.playerdigging && e.getPacket() instanceof CPacketPlayerDigging) {
         e.setCancelled(true);
      }

      if (this.tryuseitem && e.getPacket() instanceof CPacketPlayerTryUseItem) {
         e.setCancelled(true);
      }

      if (this.tryuseitemonblock && e.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
         e.setCancelled(true);
      }

      if (this.recipeinfo && e.getPacket() instanceof CPacketRecipeInfo) {
         e.setCancelled(true);
      }

      if (this.resourcepackstatus && e.getPacket() instanceof CPacketResourcePackStatus) {
         e.setCancelled(true);
      }

      if (this.seendadvancements && e.getPacket() instanceof CPacketSeenAdvancements) {
         e.setCancelled(true);
      }

      if (this.spectate && e.getPacket() instanceof CPacketSpectate) {
         e.setCancelled(true);
      }

      if (this.steerboat && e.getPacket() instanceof CPacketSteerBoat) {
         e.setCancelled(true);
      }

      if (this.tabcomplete && e.getPacket() instanceof CPacketTabComplete) {
         e.setCancelled(true);
      }

      if (this.updatesign && e.getPacket() instanceof CPacketUpdateSign) {
         e.setCancelled(true);
      }

      if (this.vehiclemove && e.getPacket() instanceof CPacketVehicleMove) {
         e.setCancelled(true);
      }

      if (this.encryptionresponse && e.getPacket() instanceof CPacketEncryptionResponse) {
         e.setCancelled(true);
      }

      if (this.ping && e.getPacket() instanceof CPacketPing) {
         e.setCancelled(true);
      }

      if (this.serverquery && e.getPacket() instanceof CPacketServerQuery) {
         e.setCancelled(true);
      }

   }
}
