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
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class that contains Phase data from the hierarchy of the process.
 * <p>
 * It can represents data in the formats JSON/XML
 * </p>
 * 
 * @author BBVA-ReportingOperacional
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "phase", "tasks" })
@Document(collection = "PHASES")
public class Phase implements Serializable {

  private static final long serialVersionUID = 4077625198521325571L;

  /** The description of the phase. **/
  @Indexed
  @XmlElement(name = "phase")
  private String phase;

  /**
   * List of operations type in a sub-family.
   * **/
  @XmlElement(name = "tasks")
  private Set<Task> tasks;

  /**
   * Constructor for {@link Phase}.
   * 
   * 
   * @param phaseName
   *          the name parameter.
   */
  public Phase(String phaseName) {

    this.phase = phaseName;
    this.tasks = new HashSet<Task>();
  }

  /**
   * Default constructor for {@link Phase}.
   */
  public Phase() {
    this.tasks = new HashSet<Task>();
  }

  /**
   * GETTER for the name.
   * 
   * @return String
   */
  public String getPhase() {
    return phase;
  }

  /**
   * SETTER for the name
   * 
   * @param phaseName
   *          the name parameter.
   */
  public void setPhase(String phaseName) {
    this.phase = phaseName;
  }

  /**
   * GETTER for the list of s.
   */
  public Set<Task> gets() {
    return tasks;
  }

  /**
   * SETTER for the list of tasks.
   * 
   * @param tasks
   *          the tasks parameter.
   */
  public void sets(Set<Task> tasks) {
    this.tasks = tasks;
  }
}
