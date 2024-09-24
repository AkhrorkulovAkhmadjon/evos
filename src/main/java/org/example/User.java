package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String tel_number;
    private String userName;
    private String latitude;
    private String longitude;
    private Long id;
    private String status;
    private int userStep;
}
