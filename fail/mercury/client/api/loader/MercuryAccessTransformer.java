package fail.mercury.client.api.loader;

import java.io.IOException;
import net.minecraftforge.fml.common.asm.transformers.AccessTransformer;

public class MercuryAccessTransformer extends AccessTransformer {
   public MercuryAccessTransformer() throws IOException {
      super("mercury_at.cfg");
   }
}
