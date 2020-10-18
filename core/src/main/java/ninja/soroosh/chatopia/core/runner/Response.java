package ninja.soroosh.chatopia.core.runner;

import java.util.Collections;
import java.util.List;

public interface Response {
    String message();

    default List<Option> options() {
        return Collections.emptyList();
    }
}
