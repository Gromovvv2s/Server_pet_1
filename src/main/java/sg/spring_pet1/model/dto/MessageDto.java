package sg.spring_pet1.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MessageDto implements Serializable {
    private String message;
    private String nameTo;
    private String nameFrom;
    private Date date;
}
