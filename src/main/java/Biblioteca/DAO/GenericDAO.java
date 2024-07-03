package Biblioteca.DAO;


import jakarta.persistence.*;
import java.util.List;

public class GenericDAO<T> {
    private Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void save(T entity, EntityManager em) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    public void update(T entity, EntityManager em) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    public void delete(T entity, EntityManager em) {
        em.getTransaction().begin();
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        em.getTransaction().commit();
    }

    public T findById(Object id, EntityManager em) {
        return em.find(entityClass, id);
    }

    public List<T> findAll(EntityManager em) {
        return em.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
    }
}
