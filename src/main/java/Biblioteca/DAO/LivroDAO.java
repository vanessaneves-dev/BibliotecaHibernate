package Biblioteca.DAO;

import Biblioteca.Model.Livro;

public class LivroDAO extends GenericDAO<Livro> {
    public LivroDAO() {
        super(Livro.class);
    }
}
