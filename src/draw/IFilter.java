package draw;

public interface IFilter<T> {
    boolean permit(T value);
}
