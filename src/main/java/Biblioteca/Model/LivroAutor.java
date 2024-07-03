package Biblioteca.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroAutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_livro")
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "fk_autor")
    private Autor autor;
}