package xyz.deftu.unicore.mixins.gui;

import com.google.common.collect.ObjectArrays;
import net.minecraft.client.gui.GuiChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.deftu.unicore.api.UniCore;

@Mixin({GuiChat.class})
public class GuiChatMixin {
    @Unique private String[] autoCompletion;

    @Inject(method = "sendAutocompleteRequest", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ClientCommandHandler;autoComplete(Ljava/lang/String;Ljava/lang/String;)V", remap = false, shift = At.Shift.AFTER))
    private void onAutocompleteRequestSent(String p_146405_1_, String p_146405_2_, CallbackInfo ci) {
        UniCore.getCommandRegistry().processAutoComplete(p_146405_1_);
    }

    @Inject(method = "onAutocompleteResponse", at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V", remap = false))
    private void concatenateAutocomplete(String[] complete, CallbackInfo ci) {
        this.autoCompletion = ObjectArrays.concat(UniCore.getCommandRegistry().getAutoCompletion(), complete, String.class);
    }

    @ModifyVariable(method = "onAutocompleteResponse", at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V", remap = false))
    private String[] modifyAutocomplete(String[] original) {
        return autoCompletion == null ? original : autoCompletion;
    }
}