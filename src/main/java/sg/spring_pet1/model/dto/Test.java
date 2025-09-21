package sg.spring_pet1.model.dto;

import lombok.EqualsAndHashCode;

import java.io.Serializable;
@EqualsAndHashCode
public class Test implements Serializable {
    public Test(String s) {
        this.message = s;
    }
    public Test() {
    }

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
