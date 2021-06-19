package fail.mercury.client.api.util;

public class Rotation {
   private float yaw;
   private float pitch;
   private boolean active;

   public Rotation(float yaw, float pitch) {
      this.yaw = yaw;
      this.pitch = pitch;
   }

   public Rotation add(float yaw, float pitch) {
      this.yaw += yaw;
      this.pitch += pitch;
      return this;
   }

   public Rotation subtract(float yaw, float pitch) {
      this.yaw -= yaw;
      this.pitch -= pitch;
      return this;
   }

   public float getYaw() {
      return this.yaw;
   }

   public Rotation setYaw(float yaw) {
      this.active = true;
      this.yaw = yaw;
      return this;
   }

   public float getPitch() {
      return this.pitch;
   }

   public Rotation setPitch(float pitch) {
      this.active = true;
      this.pitch = pitch;
      return this;
   }

   public boolean isActive() {
      return this.active;
   }
}
