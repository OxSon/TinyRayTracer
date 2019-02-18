package render;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author: Alec Mills
 */

public final class VectorF implements Comparable<VectorF>, Iterable<Float> {
    private float[] components;

    /**
     * an n-dimensional VectorF (i.e components have float values) with default component values of 0F
     * @param dimensions number of dimensions (n)
     */
    public VectorF(int dimensions) {
        components = new float[dimensions];
    }

    /**
     * an n-dimensional VectorF (i.e. components have float values)
     * components are assumed to be passed in an increasing dimension order begging with 1 (i.e. 1,2,3)
     * @param dimensions number of components in vector
     * @param components array of values to be stored in vector
     */
    public VectorF(int dimensions, float... components) {
        this.components = new float[dimensions];

        System.arraycopy(components, 0, this.components, 0, components.length);
    }

    public VectorF add(VectorF arg) {
        if (components.length != arg.components.length)
            throw new IllegalArgumentException("Vectors must have equal dimensions to be added together");

        int n = components.length;
        var result = new VectorF(n);

        for(int i = 0; i < n; i++) {
            result.components[i] = components[i] + arg.components[i];
        }

        return result;
    }

    public VectorF scalarMultiply(float scalar) {
        VectorF result = new VectorF(components.length);

        for(int i = 0; i < components.length; i++) {
            result.components[i] = components[i] * scalar;
        }

        return result;
    }

    public VectorF scalarDivdide(float scalar) {
        scalar = 1 / scalar;
        return scalarMultiply(scalar);
    }

    public VectorF subtract(VectorF arg) {
        return add(arg.scalarMultiply(-1));
    }

    /**
     * returns the <i>dot product</i> of this * arg
     * @param arg argument
     * @return this <i>dotted with</i> arg
     */
    public float dotProduct(VectorF arg) {
        if (components.length != arg.components.length)
            throw new IllegalArgumentException("Vectors must have equal dimensions to be dot-producted");

        float sum = 0F;
        for(int i = 0; i < components.length; i++) {
            sum += (components[i] * arg.components[i]);
        }

        return sum;
    }

    public float magnitude() {
        float radicand = 0F;

        for(var comp : components)
            radicand += comp * comp;

        return (float) Math.sqrt(radicand);
    }

    public VectorF normalize() {
        return scalarDivdide(magnitude());
    }

    public float[] getComponents() {
        return components;
    }

    @Override
    public boolean equals(Object arg) {
        if (!(arg instanceof VectorF))
            return false;
        var vector = (VectorF) arg;

        if (components.length != vector.components.length)
            return false;

        float tolerance = 0.001F;
        for(int i = 0; i < components.length; i++) {
            if (Math.abs(components[i] - vector.components[i]) > tolerance)
                return false;
        }

        return true;
    }

    @Override
    public int compareTo(VectorF vectorF) {
        return Float.compare(magnitude(), vectorF.magnitude());
    }

    @Override
    public Iterator<Float> iterator() {
        return new VectorIterator();
    }

    class VectorIterator implements Iterator<Float> {
        private float current;
        private int index;
        private VectorF vector;

        public VectorIterator() {
            current = components[index];
        }

        @Override
        public boolean hasNext() {
            return index < components.length;
        }

        public Float next() {
            float next = current;
            if (!hasNext())
                throw new NoSuchElementException();

            return components[index++];
        }
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("(");

        for(int i = 0; i < components.length; i++) {
            sb.append(components[i]);
            if (i < components.length - 1)
                sb.append(", ");
        }
        sb.append(")");

        return sb.toString();
    }

    public static void main(String[] args) {
        VectorF vector = new VectorF(3);

        System.out.print("<");
        for(Float el : vector) {
            int i = 0;
            System.out.printf("%.1f; ", el);
        }
        System.out.println(">");

        System.out.printf("%n%s%n", vector);
    }
}
