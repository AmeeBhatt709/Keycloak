package com.example.Keyclock.DTO;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDTO {

    private String id;
    private String userName ;
    private String email;

}
