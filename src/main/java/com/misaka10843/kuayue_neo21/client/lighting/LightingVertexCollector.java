package com.misaka10843.kuayue_neo21.client.lighting;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;

public final class LightingVertexCollector implements VertexConsumer {
    public static final class Vertex {
        public final float x, y, z;
        public int red, green, blue, alpha, blockLight, skyLight;
        public float normalX, normalY, normalZ;

        private Vertex(float x, float y, float z) { this.x = x; this.y = y; this.z = z; }
    }

    private final List<Vertex> vertices = new ArrayList<>();

    public List<Vertex> vertices() { return List.copyOf(vertices); }
    private Vertex current() { return vertices.getLast(); }

    @Override public VertexConsumer addVertex(float x, float y, float z) { vertices.add(new Vertex(x, y, z)); return this; }
    @Override public VertexConsumer setColor(int red, int green, int blue, int alpha) {
        var vertex = current();
        vertex.red = red; vertex.green = green; vertex.blue = blue; vertex.alpha = alpha;
        return this;
    }
    @Override public VertexConsumer setUv(float u, float v) { return this; }
    @Override public VertexConsumer setUv1(int u, int v) { return this; }
    @Override public VertexConsumer setUv2(int u, int v) { current().blockLight = u; current().skyLight = v; return this; }
    @Override public VertexConsumer setNormal(float x, float y, float z) {
        var vertex = current();
        vertex.normalX = x; vertex.normalY = y; vertex.normalZ = z;
        return this;
    }
}
