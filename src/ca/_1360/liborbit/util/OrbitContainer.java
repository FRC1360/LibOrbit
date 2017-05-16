package ca._1360.liborbit.util;

public final class OrbitContainer<T> {
    private T value;

    public OrbitContainer(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
