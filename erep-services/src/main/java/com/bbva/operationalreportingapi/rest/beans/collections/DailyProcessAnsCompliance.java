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
 * Class that contains the daily process ans time compliance.
 * <p>
 * It can represents data in the formats JSON/XML
 * </p>
 * 
 * @author BBVA-ReportingOperacional
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "family", "subfamily", "process", "date",
    "operations" })
@Document(collection = "C_EREP_DAILY_PROCESS_ANS_COMPLIANCE")
public class DailyProcessAnsCompliance implements Serializable {

  /**
   * Serial UID.
   */
  private static final long serialVersionUID = 3642249309605157599L;

  /** The document id. **/
  @Id
  @XmlElement(name = "id")
  private String id;

  /** The family. **/
  @XmlElement(name = "family")
  private String family;

  /** The sub-familia. **/
  @XmlElement(name = "subfamily")
  private String subfamily;

  /** The process. **/
  @XmlElement(name = "process")
  private String process;

  /** The list of operations with ans time data. **/
  @XmlElement(name = "operations")
  @XmlList
  private Set<OperationByProcess> operations;

  /** The date. **/
  @XmlElement(name = "date")
  private Long date;

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
  public DailyProcessAnsCompliance(String process, String family,
      String subfamily, Long date) {
    this.process = process;
    this.family = family;
    this.subfamily = subfamily;
    this.date = date;
    this.operations = new HashSet<OperationByProcess>();
  }

  /**
   * Default constructor.
   */
  public DailyProcessAnsCompliance() {
    this.operations = new HashSet<OperationByProcess>();
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
   * GETTER method for the operations list with ans data.
   * 
   * @return AmountOperationsByType (List).
   */
  public Set<OperationByProcess> getOperations() {
    return operations;
  }

  /**
   * SETTER method for the operations list with ans data.
   * 
   * @param operations
   *          parameter.
   */
  public void setOperations(Set<OperationByProcess> operations) {
    this.operations = operations;
  }

  /**
   * GETTER Method form date.
   * 
   * @return String
   */
  public Long getDate() {
    return date;
  }

  /**
   * SETTER method form date.
   * 
   * @param date
   *          parameter.
   */
  public void setDate(Long date) {
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
