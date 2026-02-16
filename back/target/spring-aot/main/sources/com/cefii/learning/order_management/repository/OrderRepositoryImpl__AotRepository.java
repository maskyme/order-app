package com.cefii.learning.order_management.repository;

import com.cefii.learning.order_management.model.Order;
import com.cefii.learning.order_management.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.Long;
import java.lang.String;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link OrderRepository}.
 */
@Generated
public class OrderRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public OrderRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link OrderRepository#findByClient(com.cefii.learning.order_management.model.User)}.
   */
  public List<Order> findByClient(User client) {
    String queryString = "SELECT o FROM Order o WHERE o.client = :client";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("client", client);

    return (List<Order>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link OrderRepository#findByClient_Id(java.lang.Long)}.
   */
  public List<Order> findByClient_Id(Long clientId) {
    String queryString = "SELECT o FROM Order o WHERE o.client.id = :clientId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("clientId", clientId);

    return (List<Order>) query.getResultList();
  }
}
