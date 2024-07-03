package Biblioteca.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro {
    @Id
    private long isbn;
    private String titulo;
    private int anoPublicacao;

    @ManyToOne
    @JoinColumn(name = "fk_editora")
    private Editora editora;

    @ManyToOne
    @JoinColumn(name = "fk_categoria")
    private Categoria categoria;
}
