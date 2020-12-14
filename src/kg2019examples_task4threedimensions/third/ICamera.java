package kg2019examples_task4threedimensions.third;

import kg2019examples_task4threedimensions.math.Vector3;

/**
 * Описывает основную функциональность камеры - превращение координат 
 * из мировой системы координат в систему координат камеры.
 * @author Alexey
 */
public interface ICamera {
    /**
     * Преобразует точку из мировой системы координат в систему координат камеры
     * @param v преобразуемая точка
     * @return новая точка
     */
    Vector3 w2s(Vector3 v);
}
