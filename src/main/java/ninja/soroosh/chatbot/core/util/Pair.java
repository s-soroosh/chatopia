package ninja.soroosh.chatbot.core.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair<T, K> {
    private T item1;
    private K item2;

    public static <T, K> Pair<T, K> of(T item1, K item2) {
        return new Pair<>(item1, item2);
    }
}
