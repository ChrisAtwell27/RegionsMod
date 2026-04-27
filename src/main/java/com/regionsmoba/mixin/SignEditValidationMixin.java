package com.regionsmoba.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.FilteredText;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Locale;

/**
 * Edit-time validation for [Nations] signs.
 *
 * Per docs/src-md/reference/signs.md: row 1 must be "[Nations]" (case-insensitive)
 * and row 2 must be a positive integer multiple of 4. The sign-click handler
 * (NationsSignHandler) already validates at click time; this mixin gives the
 * placer immediate feedback at edit time so they don't ship a broken sign.
 *
 * We don't reject the edit (that would need cancelling the packet); we just
 * message the player with a clear status — accepted or rejected reason.
 */
@Mixin(SignBlockEntity.class)
public class SignEditValidationMixin {

    private static final String NATIONS_TAG = "[nations]";

    @Inject(method = "updateSignText", at = @At("RETURN"))
    private void regionsmoba$validate(Player player, boolean front, List<FilteredText> messages, CallbackInfo ci) {
        if (!(player instanceof ServerPlayer sp)) return;
        SignBlockEntity self = (SignBlockEntity) (Object) this;
        SignText text = self.getText(front);
        Component[] lines = text.getMessages(false);
        if (lines.length < 2) return;

        String row1 = lines[0] != null ? lines[0].getString().trim().toLowerCase(Locale.ROOT) : "";
        if (!NATIONS_TAG.equals(row1)) return;

        String row2 = lines[1] != null ? lines[1].getString().trim() : "";
        Integer min = parseMin(row2);
        if (min == null) {
            sp.sendSystemMessage(Component.literal(
                            "[Nations] sign rejected: row 2 must be a positive integer and a multiple of 4 (got '"
                                    + row2 + "')")
                    .withStyle(ChatFormatting.RED));
            return;
        }
        sp.sendSystemMessage(Component.literal(
                        "[Nations] sign accepted — players will join here (min " + min + ")")
                .withStyle(ChatFormatting.GREEN));
    }

    private static Integer parseMin(String s) {
        if (s == null || s.isEmpty()) return null;
        try {
            int v = Integer.parseInt(s);
            if (v <= 0 || v % 4 != 0) return null;
            return v;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
