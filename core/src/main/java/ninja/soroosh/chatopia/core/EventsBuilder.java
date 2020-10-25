package ninja.soroosh.chatopia.core;

import ninja.soroosh.chatopia.core.annotation.OnEvent;
import ninja.soroosh.chatopia.core.util.Pair;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EventsBuilder {
    public List<EventRule> buildFromOnCommandAnnotations(Collection<Object> candidateObjects) {
        return candidateObjects.stream().
                flatMap(object -> Arrays.stream(object.getClass()
                        .getDeclaredMethods())
                        .map(m -> Pair.of(object, m))
                )
                .filter(objectMethodPair -> objectMethodPair.getItem2().isAnnotationPresent(OnEvent.class))
                .map(objectMethodPair -> toEvent(objectMethodPair.getItem1(), objectMethodPair.getItem2()))
                .collect(Collectors.toList());
    }

    private EventRule toEvent(Object object, Method method) {
        final OnEvent onEventannotation = method.getAnnotation(OnEvent.class);
        return new EventRule(onEventannotation.value(), object, method);
    }
}
