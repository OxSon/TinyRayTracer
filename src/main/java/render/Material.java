package render;

/**
 * Author: Alec Mills
 */

public enum Material {
    Ivory(new VectorF(3,    0.4F, 0.4F, 0.3F)), RedRubber(new VectorF(3, 0.3F
            , 0.1F, 0.1F));

    public final VectorF diffuseColor;

    Material(VectorF diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

}
