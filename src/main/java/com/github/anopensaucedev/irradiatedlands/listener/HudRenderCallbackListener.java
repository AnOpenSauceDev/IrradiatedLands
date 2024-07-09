package com.github.anopensaucedev.irradiatedlands.listener;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;

public class HudRenderCallbackListener implements HudRenderCallback {
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        //TODO: yellow irradiated glow vignette using nausea/custom texture.
    }
}
