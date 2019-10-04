package ninja.soroosh.chatopia.core;

import ninja.soroosh.chatopia.core.annotation.OnCommand;
import ninja.soroosh.chatopia.core.util.Pair;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RulesBuilder {
    public List<Rule> buildFromOnCommandAnnotations(Collection<Object> candidateObjects) {
        return candidateObjects.stream().
                flatMap(object -> Arrays.stream(object.getClass().getDeclaredMethods()).map(m -> Pair.of(object, m)))
                .filter(objectMethodPair -> objectMethodPair.getItem2().isAnnotationPresent(OnCommand.class))
                .map(objectMethodPair -> toRule(objectMethodPair.getItem1(), objectMethodPair.getItem2()))
                .collect(Collectors.toList());
    }

    private Rule toRule(Object object, Method method) {
        final OnCommand onCommandAnnotation = method.getAnnotation(OnCommand.class);
        return new Rule(onCommandAnnotation.value(), onCommandAnnotation.help(), object, method);
    }
}
