package ninja.soroosh.chatopia.core.connectors.telegram;

import lombok.Data;

@Data
class Chat {
    private long id;
    private String firstName;
    private String username;
    private String type;
}
