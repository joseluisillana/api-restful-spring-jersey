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
 * Class that contains process data from the hierarchy of the process.
 * <p>
 * It can represents data in the formats JSON/XML
 * </p>
 * 
 * @author BBVA-ReportingOperacional
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "process", "operationtypes" })
public class Process implements Serializable {

  /**
   * The serial id.
   */
  private static final long serialVersionUID = 4077625198521325571L;

  /** The description of the process. **/
  @Indexed
  @XmlElement(name = "process")
  private String process;

  /** List of process in a sub-family. **/
  @XmlElement(name = "operationtypes")
  private Set<String> operationtypes;

  /**
   * Constructor for {@link Process}.
   * 
   * 
   * @param processName
   *          the name parameter.
   */
  public Process(String processName) {

    this.process = processName;
    this.operationtypes = new HashSet<String>();
  }

  /**
   * Default constructor for {@link Process}.
   */
  public Process() {
    this.operationtypes = new HashSet<String>();
  }

  /**
   * GETTER for the name.
   * 
   * @return String
   */
  public String getProcess() {
    return process;
  }

  /**
   * SETTER for the name
   * 
   * @param processName
   *          the name parameter.
   */
  public void setProcess(String processName) {
    this.process = processName;
  }

  /**
   * GETTER for the list of operations.
   */
  public Set<String> getOperationtypes() {
    return operationtypes;
  }

  /**
   * SETTER for the list of operations.
   * 
   * @param operationtypes
   *          the operation types parameter.
   */
  public void setOperationtypes(Set<String> operationtypes) {
    this.operationtypes = operationtypes;
  }
}
