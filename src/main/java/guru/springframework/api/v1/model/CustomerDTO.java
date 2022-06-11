package guru.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerDTO {
    @ApiModelProperty(value = "This is first name", required = true)
    private String firstname;
    private String lastname;
    @JsonProperty("customer_url")
    private String customerUrl;
}
