package fail.mercury.client.api.util;

public class MouseUtil {
   public static boolean withinBounds(int mouseX, int mouseY, float x, float y, float width, float height) {
      return (float)mouseX >= x && (float)mouseX <= x + width && (float)mouseY >= y && (float)mouseY <= y + height;
   }
}
