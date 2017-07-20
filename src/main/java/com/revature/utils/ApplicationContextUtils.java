package com.revature.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {

  private static ApplicationContext context;



  private static ApplicationContext getContext() {
    return context;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    context = applicationContext;
  }

  @SuppressWarnings("unchecked")
  public static <T> T getBean(String beanName) {
    T bean = null;
    try {
      bean = (T) getContext().getBean(beanName);
      return bean;
    } catch (Exception e) {
      return bean;
    }
  }

}
