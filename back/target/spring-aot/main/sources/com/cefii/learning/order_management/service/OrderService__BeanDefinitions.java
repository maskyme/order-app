package com.cefii.learning.order_management.service;

import com.cefii.learning.order_management.repository.OrderRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OrderService}.
 */
@Generated
public class OrderService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'orderService'.
   */
  private static BeanInstanceSupplier<OrderService> getOrderServiceInstanceSupplier() {
    return BeanInstanceSupplier.<OrderService>forConstructor(OrderRepository.class)
            .withGenerator((registeredBean, args) -> new OrderService(args.get(0)));
  }

  /**
   * Get the bean definition for 'orderService'.
   */
  public static BeanDefinition getOrderServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OrderService.class);
    beanDefinition.setInstanceSupplier(getOrderServiceInstanceSupplier());
    return beanDefinition;
  }
}
