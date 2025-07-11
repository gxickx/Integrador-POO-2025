package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;

public class Persistencia {
   private final EntityManager em;

   public Persistencia(EntityManagerFactory emf) {
      this.em = emf.createEntityManager();
   }

   public void iniciarTransaccion() {
      this.em.getTransaction().begin();
   }

   public void confirmarTransaccion() {
      this.em.getTransaction().commit();
   }

   public void descartarTransaccion() {
      this.em.getTransaction().rollback();
   }

   public void insertar(Object o) {
      this.em.persist(o);
   }

   public void modificar(Object o) {
      this.em.merge(o);
   }

   public void eliminar(Object o) {
      this.em.remove(o);
   }

   public void refrescar(Object o) {
      this.em.refresh(o);
   }

   public <T> T buscar(Class<T> clase, Object id) {
      return this.em.find(clase, id);
   }

   public <T> List<T> buscarTodos(Class<T> clase) {
      CriteriaBuilder cb = this.em.getCriteriaBuilder();
      CriteriaQuery<T> consulta = cb.createQuery(clase);
      Root<T> origen = consulta.from(clase);
      consulta.select(origen);
      return this.em.createQuery(consulta).getResultList();
   }

   public <T, P> List<T> buscarTodosOrdenadosPor(Class<T> clase, SingularAttribute<T, P> orden) {
      CriteriaBuilder cb = this.em.getCriteriaBuilder();
      CriteriaQuery<T> consulta = cb.createQuery(clase);
      Root<T> origen = consulta.from(clase);
      consulta.select(origen);
      consulta.orderBy(new Order[]{cb.asc(origen.get(orden))});
      return this.em.createQuery(consulta).getResultList();
   }
}

