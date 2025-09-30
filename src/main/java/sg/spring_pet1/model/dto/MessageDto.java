package sg.spring_pet1.model.dto;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class MessageDto implements Serializable {
    private String message;

    public MessageDto(String message) {
        this.message = message;
    }
    public MessageDto() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
