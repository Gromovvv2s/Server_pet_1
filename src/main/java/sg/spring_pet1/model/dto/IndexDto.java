package sg.spring_pet1.model.dto;

import java.io.Serializable;
import java.util.Objects;

public class IndexDto implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IndexDto() {
    }

    public IndexDto(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexDto indexDto = (IndexDto) o;
        return Objects.equals(message, indexDto.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
}
