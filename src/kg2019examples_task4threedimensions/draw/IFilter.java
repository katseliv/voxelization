package kg2019examples_task4threedimensions.draw;

public interface IFilter<T> {
    boolean permit(T value);
}
