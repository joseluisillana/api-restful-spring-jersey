package com.bbva.operationalreportingapi.rest.beans.hierarchy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Class that contains Operation Type data from the hierarchy of the process.
 * <p>
 * It can represents data in the formats JSON/XML
 * </p>
 * 
 * @author BBVA-ReportingOperacional
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "operationtype", "phases" })
public class OperationType implements Serializable {

  private static final long serialVersionUID = 4077625198521325571L;

  /** The description of the process. **/
  @Indexed
  @XmlElement(name = "operationtype")
  private String operationtype;

  /**
   * List of operations type in a sub-family.
   * **/
  @XmlElement(name = "phases")
  private Set<Phase> phases;

  /**
   * Constructor for {@link OperationType}.
   * 
   * 
   * @param operationtypeName
   *          the name parameter.
   */
  public OperationType(String operationtypeName) {

    this.operationtype = operationtypeName;
    this.phases = new HashSet<Phase>();
  }

  /**
   * Default constructor for {@link OperationType}.
   */
  public OperationType() {
    this.phases = new HashSet<Phase>();
  }

  /**
   * GETTER for the name.
   * 
   * @return String
   */
  public String getOperationtype() {
    return operationtype;
  }

  /**
   * SETTER for the name
   * 
   * @param operationtypeName
   *          the name parameter.
   */
  public void setOperationtype(String operationtypeName) {
    this.operationtype = operationtypeName;
  }

  /**
   * GETTER for the list of phases.
   */
  public Set<Phase> getPhases() {
    return phases;
  }

  /**
   * SETTER for the list of operations types.
   * 
   * @param operationtypes
   *          the operation types parameter.
   */
  public void setPhases(Set<Phase> phases) {
    this.phases = phases;
  }
}
