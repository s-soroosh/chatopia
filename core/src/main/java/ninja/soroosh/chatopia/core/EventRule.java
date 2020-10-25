package ninja.soroosh.chatopia.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

@Getter
@AllArgsConstructor
public class EventRule {
    private String eventName;
    private Object object;
    private Method method;
}
