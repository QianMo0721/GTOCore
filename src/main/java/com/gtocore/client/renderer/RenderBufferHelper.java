package com.gtocore.client.renderer;

import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public final class RenderBufferHelper {

    public static void renderCylinder(PoseStack poseStack, VertexConsumer buffer, float x, float y, float z,
                                      float radius, float height, int sides, float red, float green, float blue, float alpha) {
        Matrix4f mat = poseStack.last().pose();
        float angleStep = (float) (2.0 * Math.PI / sides);

        for (int i = 0; i < sides; i++) {
            float angle1 = i * angleStep;
            float angle2 = (i + 1) * angleStep;

            float cosAngle1 = Mth.cos(angle1);
            float sinAngle1 = Mth.sin(angle1);
            float cosAngle2 = Mth.cos(angle2);
            float sinAngle2 = Mth.sin(angle2);

            buffer.vertex(mat, x + cosAngle1 * radius, y, z + sinAngle1 * radius)
                    .color(red, green, blue, alpha).endVertex();
            buffer.vertex(mat, x + cosAngle2 * radius, y, z + sinAngle2 * radius)
                    .color(red, green, blue, alpha).endVertex();
            buffer.vertex(mat, x + cosAngle2 * radius, y + height, z + sinAngle2 * radius)
                    .color(red, green, blue, alpha).endVertex();

            buffer.vertex(mat, x + cosAngle1 * radius, y, z + sinAngle1 * radius)
                    .color(red, green, blue, alpha).endVertex();
            buffer.vertex(mat, x + cosAngle2 * radius, y + height, z + sinAngle2 * radius)
                    .color(red, green, blue, alpha).endVertex();
            buffer.vertex(mat, x + cosAngle1 * radius, y + height, z + sinAngle1 * radius)
                    .color(red, green, blue, alpha).endVertex();
        }
    }

    public static void renderCone(PoseStack poseStack, VertexConsumer buffer, float baseRadius, float topRadius, float height,
                                  float curvature, int sides, float red, float green, float blue, float alpha) {
        Matrix4f mat = poseStack.last().pose();
        float angleDelta = (float) (2.0 * Math.PI / sides);

        for (int i = 0; i < sides; i++) {
            float angle1 = i * angleDelta;
            float angle2 = angle1 + angleDelta;

            float cosAngle1 = Mth.cos(angle1);
            float sinAngle1 = Mth.sin(angle1);
            float cosAngle2 = Mth.cos(angle2);
            float sinAngle2 = Mth.sin(angle2);

            float baseX1 = cosAngle1 * baseRadius;
            float baseZ1 = sinAngle1 * baseRadius;
            float baseX2 = cosAngle2 * baseRadius;
            float baseZ2 = sinAngle2 * baseRadius;

            float topX1 = cosAngle1 * topRadius;
            float topZ1 = sinAngle1 * topRadius;
            float topX2 = cosAngle2 * topRadius;
            float topZ2 = sinAngle2 * topRadius;

            for (float j = 0; j <= curvature; j += 1.0f) {
                float lerpFactor = j / curvature;
                float curX1 = baseX1 + lerpFactor * (topX1 - baseX1);
                float curZ1 = baseZ1 + lerpFactor * (topZ1 - baseZ1);
                float curX2 = baseX2 + lerpFactor * (topX2 - baseX2);
                float curZ2 = baseZ2 + lerpFactor * (topZ2 - baseZ2);
                float curY = height * (1 - lerpFactor);

                buffer.vertex(mat, curX1, curY, curZ1).color(red, green, blue, alpha).endVertex();
                buffer.vertex(mat, curX2, curY, curZ2).color(red, green, blue, alpha).endVertex();
            }
        }
    }
}
