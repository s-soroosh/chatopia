package ninja.soroosh.chatopia.core.runner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEventPayload {
    private String id;
    private String firstName;
    private String lastName;
}
