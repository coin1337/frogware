package fail.mercury.client.api.loader;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

@Name("Mercury")
@SortingIndex(29384)
@MCVersion("1.12.2")
public class MercuryLoader implements IFMLLoadingPlugin {
   public MercuryLoader() {
      MixinBootstrap.init();
      Mixins.addConfiguration("mixins.mercury.json");
   }

   public String[] getASMTransformerClass() {
      return new String[0];
   }

   public String getModContainerClass() {
      return null;
   }

   public String getSetupClass() {
      return null;
   }

   public void injectData(Map<String, Object> data) {
   }

   public String getAccessTransformerClass() {
      return MercuryAccessTransformer.class.getName();
   }
}
