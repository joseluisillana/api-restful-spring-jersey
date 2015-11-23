package com.bbva.operationalreportingapi.rest.beans.collections;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class that contains the process in order, aggregated by day.
 * <p>
 * It can represents data in the formats JSON/XML
 * </p>
 * 
 * @author BBVA-ReportingOperacional
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "process", "family", "subfamily", "date", "total" })
@Document(collection = "C_EREP_DAILY_REGISTRATION")
public class DailyRegistration implements Serializable {

  private static final long serialVersionUID = 9019497708230454300L;

  /** The document id. **/
  @Id
  @XmlElement(name = "id")
  private String id;

  /** The process. **/
  @XmlElement(name = "process")
  private String process;

  /** The family. **/
  @XmlElement(name = "family")
  private String family;

  /** The sub-familia. **/
  @XmlElement(name = "subfamily")
  private String subfamily;

  /** The date of the operation. **/
  @XmlElement(name = "date")
  private String date;

  /** The list of amounts classified by operation type. **/
  @XmlElement(name = "total")
  @XmlList
  private Set<AmountOperationsByType> total;

  /**
   * Default constructor with parameters.
   * 
   * @param process
   *          the process param.
   * @param family
   *          the family param.
   * @param subfamily
   *          the subfamily param.
   * @param date
   *          the date parameter.
   */
  public DailyRegistration(String process, String family, String subfamily,
      String date) {
    this.process = process;
    this.family = family;
    this.subfamily = subfamily;
    this.date = date;
    this.total = new HashSet<AmountOperationsByType>();
  }

  /**
   * Default constructor.
   */
  public DailyRegistration() {
    this.total = new HashSet<AmountOperationsByType>();
  }

  /**
   * GETTER method for process.
   * 
   * @return String
   */
  public String getProcess() {
    return process;
  }

  /**
   * SETTER method for process.
   * 
   * @param process
   *          the process param.
   */
  public void setProcess(String process) {
    this.process = process;
  }

  /**
   * GETTER method for family.
   * 
   * @return String.
   */
  public String getFamily() {
    return family;
  }

  /**
   * SETTET method form family.
   * 
   * @param family
   *          the family parameter.
   */
  public void setFamily(String family) {
    this.family = family;
  }

  /**
   * GETTER method form subfamily.
   * 
   * @return String
   */
  public String getSubfamily() {
    return subfamily;
  }

  /**
   * SETTER method for subfamily.
   * 
   * @param subfamily
   *          the subfamily method.
   */
  public void setSubfamily(String subfamily) {
    this.subfamily = subfamily;
  }

  /**
   * GETTER method for the total list of operations by type.
   * 
   * @return AmountOperationsByType (List).
   */
  public Set<AmountOperationsByType> getTotal() {
    return total;
  }

  /**
   * SETTER method for the total list of operations by type.
   * 
   * @param total
   *          parameter.
   */
  public void setTotal(Set<AmountOperationsByType> total) {
    this.total = total;
  }

  /**
   * GETTER Method form date.
   * 
   * @return String
   */
  public String getDate() {
    return date;
  }

  /**
   * SETTER method form date.
   * 
   * @param date
   *          parameter.
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * GETTER Method for id.
   * 
   * @return String.
   */
  public String getId() {
    return id;
  }

  /**
   * SETTER Method for the id.
   * 
   * @param id
   *          the ID parameter.
   */
  public void setId(String id) {
    this.id = id;
  }

}
