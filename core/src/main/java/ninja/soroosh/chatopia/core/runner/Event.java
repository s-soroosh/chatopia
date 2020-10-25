package ninja.soroosh.chatopia.core.runner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Event<T> {
    private String eventName;
    private T payload;
}
