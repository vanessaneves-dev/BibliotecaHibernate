package Biblioteca.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Editora {
    @Id
    private String id;
    private String nome;
}
