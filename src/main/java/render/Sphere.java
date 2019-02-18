package render;

/**
 * Author: Alec Mills
 */

public class Sphere {
    private final VectorF center;
    private final float radius;
    private final Material mat;

    public Sphere(VectorF center, float radius, Material mat) {
        this.center = center;
        this.radius = radius;
        this.mat = mat;
    }

    public boolean rayIntersect(final VectorF origin, final VectorF dir,
                                /**FIXME what is this used for??*/final float sphereDistance) {

        VectorF originToCenter = center.subtract(origin); //vector V

        //case wherein sphere is behind the ray origin
        if (dir.dotProduct(originToCenter) <= 0)
            return false;

        // vector center->origin projected onto the ray
        VectorF vpc =
                dir.scalarMultiply(dir.dotProduct(originToCenter)
                        / dir.magnitude());

        //closest point on the ray to the center
        VectorF centerPrime = origin.add(vpc);
        float distance = center.subtract(centerPrime).magnitude();

        return (distance < radius);
    }



    public VectorF castRay(final VectorF origin, final VectorF dir) {
        //FIXME what is this used for?
        float sphereDistance = Float.MAX_VALUE;
        if (!rayIntersect(origin, dir, sphereDistance))
            //background color
            return new VectorF(3, 0.2F, 0.7F, 0.8F);

        //sphere color
        return new VectorF(3, 0.4F, 0.4F, 0.3F);
    }
}
