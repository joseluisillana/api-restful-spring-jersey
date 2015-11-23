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
 * Class that contains subfamily data the hierarchy of the process.
 * <p>
 * It can represents data in the formats JSON/XML
 * </p>
 * 
 * @author BBVA-ReportingOperacional
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "subfamily", "processes" })
public class Subfamily implements Serializable {

  private static final long serialVersionUID = 838990383771425011L;

  /** The description of the sub-family. */
  @Indexed
  @XmlElement(name = "subfamily")
  private String subfamily;

  /** List of process in a sub-family. **/
  @XmlElement(name = "processes")
  private Set<Process> processes;

  /**
   * Constructor for {@link Subfamily}.
   * 
   * @param subfamily
   *          the name parameter.
   */
  public Subfamily(String subfamily) {

    this.subfamily = subfamily;
    this.processes = new HashSet<Process>();
  }

  /**
   * Default constructor for {@link Subfamily}.
   */
  public Subfamily() {
    this.processes = new HashSet<Process>();
  }

  /**
   * GETTER for the list of processes.
   * 
   * @return {@link Process} (List)
   */
  public Set<Process> getProcesses() {
    return processes;
  }

  /**
   * SETTER for the list of processes.
   * 
   * @param {@link Process} (List)
   */
  public void setProcesses(Set<Process> processes) {
    this.processes = processes;
  }

  /**
   * GETTER for the subfamily.
   * 
   * @return String
   */
  public String getSubfamily() {
    return subfamily;
  }

  /**
   * SETTER for the name of subfamily.
   * 
   * @param subfamilyName
   *          the name parameter.
   */
  public void setSubfamily(String subfamilyName) {
    this.subfamily = subfamilyName;
  }
}
