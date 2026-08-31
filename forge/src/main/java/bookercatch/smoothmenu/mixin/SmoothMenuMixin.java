package bookercatch.smoothmenu.mixin;

import com.mojang.blaze3d.platform.FramerateLimitTracker;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FramerateLimitTracker.class, remap = false)
public abstract class SmoothMenuMixin {
    @Shadow(remap = false)
    @Final
    private Minecraft minecraft;

    @Shadow(remap = false)
    private int framerateLimit;

    @Shadow(remap = false)
    public abstract FramerateLimitTracker.FramerateThrottleReason getThrottleReason();

    @Inject(method = "getFramerateLimit", at = @At("HEAD"), cancellable = true, remap = false)
    private void smoothmenu$uncapMenu(CallbackInfoReturnable<Integer> cir) {
        if (this.getThrottleReason() == FramerateLimitTracker.FramerateThrottleReason.OUT_OF_LEVEL_MENU
                || this.minecraft.screen != null && this.minecraft.screen.isPauseScreen()) {
            cir.setReturnValue(this.framerateLimit);
        }
    }
}
