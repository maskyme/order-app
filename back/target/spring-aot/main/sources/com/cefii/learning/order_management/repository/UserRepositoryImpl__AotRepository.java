package com.cefii.learning.order_management.repository;

import com.cefii.learning.order_management.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.String;
import java.util.Optional;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link UserRepository}.
 */
@Generated
public class UserRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public UserRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link UserRepository#findByUsername(java.lang.String)}.
   */
  public Optional<User> findByUsername(String username) {
    String queryString = "SELECT u FROM User u WHERE u.username = :username";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("username", username);

    return Optional.ofNullable((User) convertOne(query.getSingleResultOrNull(), false, User.class));
  }
}
