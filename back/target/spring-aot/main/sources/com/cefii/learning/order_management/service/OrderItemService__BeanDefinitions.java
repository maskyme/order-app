package com.cefii.learning.order_management.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OrderItemService}.
 */
@Generated
public class OrderItemService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'orderItemService'.
   */
  private static BeanInstanceSupplier<OrderItemService> getOrderItemServiceInstanceSupplier() {
    return BeanInstanceSupplier.<OrderItemService>forConstructor(OrderItemService.class)
            .withGenerator((registeredBean, args) -> new OrderItemService(args.get(0)));
  }

  /**
   * Get the bean definition for 'orderItemService'.
   */
  public static BeanDefinition getOrderItemServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OrderItemService.class);
    beanDefinition.setInstanceSupplier(getOrderItemServiceInstanceSupplier());
    return beanDefinition;
  }
}
