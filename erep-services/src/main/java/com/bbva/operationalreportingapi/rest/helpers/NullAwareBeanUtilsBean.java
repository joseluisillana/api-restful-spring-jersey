package com.bbva.operationalreportingapi.rest.helpers;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;

/**
 * Helper tool to be able to copy null values between objects.
 * <p>
 * More information in the link.
 * </p>
 * 
 * @author BBVA-ReportingOperacional
 * 
 */
public class NullAwareBeanUtilsBean extends BeanUtilsBean {

  // http://stackoverflow.com/questions/1301697/helper-in-order-to-copy-non-null-properties-from-object-to-another-java">Helper
  // in order to copy non null properties from object to another ? (Java)

  @Override
  public void copyProperty(Object dest, String name, Object value)
      throws IllegalAccessException, InvocationTargetException {
    if (value == null) {
      return;
    }
    super.copyProperty(dest, name, value);
  }
}
