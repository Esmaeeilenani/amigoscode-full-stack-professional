package com.amigoscode.customer;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Integer id;


    private String name;

    private String email;

    private Integer age;


    private String gender;

}
