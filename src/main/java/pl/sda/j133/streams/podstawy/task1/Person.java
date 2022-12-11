package pl.sda.j133.streams.podstawy.task1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String firstName;
    private String lastName;
    private Integer age;
    private Boolean male;

    public static void main(String[] args) {
    }

    public boolean isMale() {
        return male;
    }
}
