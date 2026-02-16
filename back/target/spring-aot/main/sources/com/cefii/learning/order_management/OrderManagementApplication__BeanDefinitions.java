package com.cefii.learning.order_management;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OrderManagementApplication}.
 */
@Generated
public class OrderManagementApplication__BeanDefinitions {
  /**
   * Get the bean definition for 'orderManagementApplication'.
   */
  public static BeanDefinition getOrderManagementApplicationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OrderManagementApplication.class);
    beanDefinition.setInstanceSupplier(OrderManagementApplication::new);
    return beanDefinition;
  }
}
