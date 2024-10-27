package ma.enset.tp4.commonapi.dtos;

import lombok.Data;

@Data
public class DebitAccountRequestDTO {
    private String id;
    private double amount;
    private String currency;
}
