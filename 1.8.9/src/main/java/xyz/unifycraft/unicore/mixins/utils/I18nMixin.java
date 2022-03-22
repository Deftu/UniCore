package xyz.unifycraft.unicore.mixins.utils;

/* import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.unifycraft.unicore.api.UniCore;

@Mixin({I18n.class})
public class I18nMixin {
    @Inject(method = "format", at = @At("HEAD"))
    private static void onFormat(String translateKey, Object[] parameters, CallbackInfoReturnable<String> cir) {
        int index = translateKey.indexOf(".");
        if (index == -1) return;
        String id = translateKey.substring(index).replace(".", "");
        if (!Loader.isModLoaded(id)) return;
        String translated = UniCore.getTranslationRegistry().translate(id, translateKey.substring(index), parameters);
        if (translated == null) return;
        cir.setReturnValue(translated);
    }
}*/