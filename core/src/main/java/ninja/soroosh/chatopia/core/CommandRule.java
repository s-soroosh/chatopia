package ninja.soroosh.chatopia.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

@Getter
@AllArgsConstructor
public class CommandRule {
    private String commandName;
    private String help;
    private Object object;
    private Method method;
}
