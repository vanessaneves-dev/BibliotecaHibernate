package Biblioteca;

import Biblioteca.DAO.*;
import jakarta.persistence.*;
import Biblioteca.Model.*;
import Biblioteca.Util.HibernateUtil;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();


        LivroDAO livroDAO = new LivroDAO();
        AutorDAO autorDAO = new AutorDAO();
        EditoraDAO editoraDAO = new EditoraDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        LivroAutorDAO livroAutorDAO = new LivroAutorDAO();

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Gerenciar Livros");
            System.out.println("2. Gerenciar Autores");
            System.out.println("3. Gerenciar Editoras");
            System.out.println("4. Gerenciar Categorias");
            System.out.println("5. Gerenciar Livros-Autores");
            System.out.println("6. Sair");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    gerenciarLivros(scanner, em, livroDAO, autorDAO, editoraDAO, categoriaDAO);
                    break;

                case 2:
                    gerenciarAutores(scanner, em, autorDAO);
                    break;

                case 3:
                    gerenciarEditoras(scanner, em, editoraDAO);
                    break;

                case 4:
                    gerenciarCategorias(scanner, em, categoriaDAO);
                    break;

                case 5:
                    gerenciarLivrosAutores(scanner, em, livroAutorDAO, livroDAO, autorDAO);
                    break;

                case 6:
                    em.close();
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void gerenciarLivros(Scanner scanner, EntityManager em, LivroDAO livroDAO, AutorDAO autorDAO, EditoraDAO editoraDAO, CategoriaDAO categoriaDAO) {
        while (true) {
            System.out.println("Escolha uma opção para Livros:");
            System.out.println("1. Adicionar Livro");
            System.out.println("2. Atualizar Livro");
            System.out.println("3. Remover Livro");
            System.out.println("4. Listar Livros");
            System.out.println("5. Voltar ao menu principal");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Adicionar Livro
                    System.out.println("Digite o ISBN:");
                    Long isbn = scanner.nextLong();
                    scanner.nextLine();  // Consumir nova linha

                    System.out.println("Digite o título:");
                    String titulo = scanner.nextLine();

                    System.out.println("Digite o ano de publicação:");
                    int ano = scanner.nextInt();

                    System.out.println("Digite o ID da editora:");
                    String editoraId = scanner.next();
                    Editora editora = editoraDAO.findById(editoraId, em);

                    System.out.println("Digite o ID da categoria:");
                    int categoriaId = scanner.nextInt();
                    Categoria categoria = categoriaDAO.findById(categoriaId, em);

                    Livro novoLivro = new Livro();
                    novoLivro.setIsbn(isbn);
                    novoLivro.setTitulo(titulo);
                    novoLivro.setAnoPublicacao(ano);
                    novoLivro.setEditora(editora);
                    novoLivro.setCategoria(categoria);

                    livroDAO.save(novoLivro, em);
                    System.out.println("Livro adicionado com sucesso!");
                    break;

                case 2:
                    // Atualizar Livro
                    System.out.println("Digite o ISBN do livro que deseja atualizar:");
                    isbn = scanner.nextLong();
                    scanner.nextLine();  // Consumir nova linha

                    Livro livroExistente = livroDAO.findById(isbn, em);
                    if (livroExistente == null) {
                        System.out.println("Livro não encontrado!");
                        break;
                    }

                    System.out.println("Digite o novo título:");
                    titulo = scanner.nextLine();

                    System.out.println("Digite o novo ano de publicação:");
                    ano = scanner.nextInt();

                    System.out.println("Digite o novo ID da editora:");
                    editoraId = scanner.next();
                    editora = editoraDAO.findById(editoraId, em);

                    System.out.println("Digite o novo ID da categoria:");
                    categoriaId = scanner.nextInt();
                    categoria = categoriaDAO.findById(categoriaId, em);

                    livroExistente.setTitulo(titulo);
                    livroExistente.setAnoPublicacao(ano);
                    livroExistente.setEditora(editora);
                    livroExistente.setCategoria(categoria);

                    livroDAO.update(livroExistente, em);
                    System.out.println("Livro atualizado com sucesso!");
                    break;

                case 3:
                    // Remover Livro
                    System.out.println("Digite o ISBN do livro que deseja remover:");
                    isbn = scanner.nextLong();

                    livroExistente = livroDAO.findById(isbn, em);
                    if (livroExistente == null) {
                        System.out.println("Livro não encontrado!");
                        break;
                    }

                    livroDAO.delete(livroExistente, em);
                    System.out.println("Livro removido com sucesso!");
                    break;

                case 4:
                    // Listar Livros
                    System.out.println("Lista de livros:");
                    for (Livro livro : livroDAO.findAll(em)) {
                        System.out.println(livro.getIsbn() + " - " + livro.getTitulo() + " (" + livro.getAnoPublicacao() + ")");
                    }
                    break;

                case 5:
                    // Voltar ao menu principal
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void gerenciarAutores(Scanner scanner, EntityManager em, AutorDAO autorDAO) {
        while (true) {
            System.out.println("Escolha uma opção para Autores:");
            System.out.println("1. Adicionar Autor");
            System.out.println("2. Atualizar Autor");
            System.out.println("3. Remover Autor");
            System.out.println("4. Listar Autores");
            System.out.println("5. Voltar ao menu principal");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Adicionar Autor
                    System.out.println("Digite o nome:");
                    scanner.nextLine();  // Consumir nova linha
                    String nome = scanner.nextLine();

                    System.out.println("Digite a nacionalidade:");
                    String nacionalidade = scanner.nextLine();

                    Autor novoAutor = new Autor();
                    novoAutor.setNome(nome);
                    novoAutor.setNacionalidade(nacionalidade);

                    autorDAO.save(novoAutor, em);
                    System.out.println("Autor adicionado com sucesso!");
                    break;

                case 2:
                    // Atualizar Autor
                    System.out.println("Digite o ID do autor que deseja atualizar:");
                    int autorId = scanner.nextInt();
                    scanner.nextLine();  // Consumir nova linha

                    Autor autorExistente = autorDAO.findById(autorId, em);
                    if (autorExistente == null) {
                        System.out.println("Autor não encontrado!");
                        break;
                    }

                    System.out.println("Digite o novo nome:");
                    nome = scanner.nextLine();

                    System.out.println("Digite a nova nacionalidade:");
                    nacionalidade = scanner.nextLine();

                    autorExistente.setNome(nome);
                    autorExistente.setNacionalidade(nacionalidade);

                    autorDAO.update(autorExistente, em);
                    System.out.println("Autor atualizado com sucesso!");
                    break;

                case 3:
                    // Remover Autor
                    System.out.println("Digite o ID do autor que deseja remover:");
                    autorId = scanner.nextInt();

                    autorExistente = autorDAO.findById(autorId, em);
                    if (autorExistente == null) {
                        System.out.println("Autor não encontrado!");
                        break;
                    }

                    autorDAO.delete(autorExistente, em);
                    System.out.println("Autor removido com sucesso!");
                    break;

                case 4:
                    // Listar Autores
                    System.out.println("Lista de autores:");
                    for (Autor autor : autorDAO.findAll(em)) {
                        System.out.println(autor.getId() + " - " + autor.getNome() + " (" + autor.getNacionalidade() + ")");
                    }
                    break;

                case 5:
                    // Voltar ao menu principal
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void gerenciarEditoras(Scanner scanner, EntityManager em, EditoraDAO editoraDAO) {
        while (true) {
            System.out.println("Escolha uma opção para Editoras:");
            System.out.println("1. Adicionar Editora");
            System.out.println("2. Atualizar Editora");
            System.out.println("3. Remover Editora");
            System.out.println("4. Listar Editoras");
            System.out.println("5. Voltar ao menu principal");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Adicionar Editora
                    System.out.println("Digite o ID:");
                    scanner.nextLine();  // Consumir nova linha
                    String id = scanner.nextLine();

                    System.out.println("Digite o nome:");
                    String nome = scanner.nextLine();

                    Editora novaEditora = new Editora();
                    novaEditora.setId(id);
                    novaEditora.setNome(nome);

                    editoraDAO.save(novaEditora, em);
                    System.out.println("Editora adicionada com sucesso!");
                    break;

                case 2:
                    // Atualizar Editora
                    System.out.println("Digite o ID da editora que deseja atualizar:");
                    id = scanner.nextLine();

                    Editora editoraExistente = editoraDAO.findById(id, em);
                    if (editoraExistente == null) {
                        System.out.println("Editora não encontrada!");
                        break;
                    }

                    System.out.println("Digite o novo nome:");
                    nome = scanner.nextLine();

                    editoraExistente.setNome(nome);

                    editoraDAO.update(editoraExistente, em);
                    System.out.println("Editora atualizada com sucesso!");
                    break;

                case 3:
                    // Remover Editora
                    System.out.println("Digite o ID da editora que deseja remover:");
                    id = scanner.nextLine();

                    editoraExistente = editoraDAO.findById(id, em);
                    if (editoraExistente == null) {
                        System.out.println("Editora não encontrada!");
                        break;
                    }

                    editoraDAO.delete(editoraExistente, em);
                    System.out.println("Editora removida com sucesso!");
                    break;

                case 4:
                    // Listar Editoras
                    System.out.println("Lista de editoras:");
                    for (Editora editora : editoraDAO.findAll(em)) {
                        System.out.println(editora.getId() + " - " + editora.getNome());
                    }
                    break;

                case 5:
                    // Voltar ao menu principal
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void gerenciarCategorias(Scanner scanner, EntityManager em, CategoriaDAO categoriaDAO) {
        while (true) {
            System.out.println("Escolha uma opção para Categorias:");
            System.out.println("1. Adicionar Categoria");
            System.out.println("2. Atualizar Categoria");
            System.out.println("3. Remover Categoria");
            System.out.println("4. Listar Categorias");
            System.out.println("5. Voltar ao menu principal");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Adicionar Categoria
                    System.out.println("Digite o tipo de categoria:");
                    scanner.nextLine();  // Consumir nova linha
                    String tipoCategoria = scanner.nextLine();

                    Categoria novaCategoria = new Categoria();
                    novaCategoria.setTipoCategoria(tipoCategoria);

                    categoriaDAO.save(novaCategoria, em);
                    System.out.println("Categoria adicionada com sucesso!");
                    break;

                case 2:
                    // Atualizar Categoria
                    System.out.println("Digite o ID da categoria que deseja atualizar:");
                    int categoriaId = scanner.nextInt();
                    scanner.nextLine();  // Consumir nova linha

                    Categoria categoriaExistente = categoriaDAO.findById(categoriaId, em);
                    if (categoriaExistente == null) {
                        System.out.println("Categoria não encontrada!");
                        break;
                    }

                    System.out.println("Digite o novo tipo de categoria:");
                    tipoCategoria = scanner.nextLine();

                    categoriaExistente.setTipoCategoria(tipoCategoria);

                    categoriaDAO.update(categoriaExistente, em);
                    System.out.println("Categoria atualizada com sucesso!");
                    break;

                case 3:
                    // Remover Categoria
                    System.out.println("Digite o ID da categoria que deseja remover:");
                    categoriaId = scanner.nextInt();

                    categoriaExistente = categoriaDAO.findById(categoriaId, em);
                    if (categoriaExistente == null) {
                        System.out.println("Categoria não encontrada!");
                        break;
                    }

                    categoriaDAO.delete(categoriaExistente, em);
                    System.out.println("Categoria removida com sucesso!");
                    break;

                case 4:
                    // Listar Categorias
                    System.out.println("Lista de categorias:");
                    for (Categoria categoria : categoriaDAO.findAll(em)) {
                        System.out.println(categoria.getId() + " - " + categoria.getTipoCategoria());
                    }
                    break;

                case 5:
                    // Voltar ao menu principal
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void gerenciarLivrosAutores(Scanner scanner, EntityManager em, LivroAutorDAO livroAutorDAO, LivroDAO livroDAO, AutorDAO autorDAO) {
        while (true) {
            System.out.println("Escolha uma opção para Livros-Autores:");
            System.out.println("1. Adicionar Associação Livro-Autor");
            System.out.println("2. Remover Associação Livro-Autor");
            System.out.println("3. Listar Associações Livro-Autor");
            System.out.println("4. Voltar ao menu principal");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Adicionar Associação Livro-Autor
                    System.out.println("Digite o ISBN do livro:");
                    long isbn = scanner.nextLong();

                    Livro livro = livroDAO.findById(isbn, em);
                    if (livro == null) {
                        System.out.println("Livro não encontrado!");
                        break;
                    }

                    System.out.println("Digite o ID do autor:");
                    int autorId = scanner.nextInt();

                    Autor autor = autorDAO.findById(autorId, em);
                    if (autor == null) {
                        System.out.println("Autor não encontrado!");
                        break;
                    }

                    LivroAutor novaAssociacao = new LivroAutor();
                    novaAssociacao.setLivro(livro);
                    novaAssociacao.setAutor(autor);

                    livroAutorDAO.save(novaAssociacao, em);
                    System.out.println("Associação Livro-Autor adicionada com sucesso!");
                    break;

                case 2:
                    // Remover Associação Livro-Autor
                    System.out.println("Digite o ID da associação que deseja remover:");
                    int associacaoId = scanner.nextInt();

                    LivroAutor associacaoExistente = livroAutorDAO.findById(associacaoId, em);
                    if (associacaoExistente == null) {
                        System.out.println("Associação não encontrada!");
                        break;
                    }

                    livroAutorDAO.delete(associacaoExistente, em);
                    System.out.println("Associação removida com sucesso!");
                    break;

                case 3:
                    // Listar Associações Livro-Autor
                    System.out.println("Lista de associações Livro-Autor:");
                    for (LivroAutor associacao : livroAutorDAO.findAll(em)) {
                        System.out.println("ID: " + associacao.getId() + " - Livro: " + associacao.getLivro().getTitulo() + " - Autor: " + associacao.getAutor().getNome());
                    }
                    break;

                case 4:
                    // Voltar ao menu principal
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}