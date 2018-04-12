package in.arya.audit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * Created by dev on 07/04/18.
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private long id;
    private String firstName;
    private String lastName;

    public String getFullName() {
        String firstName = this.firstName;
        return firstName += this.lastName != null ? " " + this.lastName : "";

    }
}
