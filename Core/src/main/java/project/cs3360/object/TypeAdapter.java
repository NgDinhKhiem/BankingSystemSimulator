package project.cs3360.object;

public interface TypeAdapter<T> {
    T convert(String value);
}
