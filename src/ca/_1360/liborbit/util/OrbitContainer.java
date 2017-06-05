/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitContainer.java
 * Holds a modifiable object reference
 */

package ca._1360.liborbit.util;

/**
 * @param <T> The object type
 */
public final class OrbitContainer<T> {
    private T value;

    /**
     * @param value The initial value
     */
    public OrbitContainer(T value) {
        this.value = value;
    }

    /**
     * @return The current value
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value The new value
     */
    public void setValue(T value) {
        this.value = value;
    }
}
