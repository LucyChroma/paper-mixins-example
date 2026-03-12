package tk.chroma.lucy.papermixinsexample.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Cat.class)
public class CatMixin {
    @Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V", at = @At("HEAD"))
    private static void constructorMixin(EntityType<? extends Cat> type, Level level, CallbackInfo ci) {
        System.out.println("meow");
    }
}
