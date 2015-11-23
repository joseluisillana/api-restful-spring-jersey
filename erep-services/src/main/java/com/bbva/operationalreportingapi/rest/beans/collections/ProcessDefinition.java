package com.bbva.operationalreportingapi.rest.beans.collections;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class that contains data for ans compliance.
 * <p>
 * It can represents data in the formats JSON/XML
 * </p>
 * 
 * @author BBVA-ReportingOperacional
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "timeIntervalLevels", "processGraph",
    "phases", "tasks" })
@Document(collection = "C_EREP_PROCESS_DEFINITION")
public class ProcessDefinition implements Serializable {

  /**
   * Serial UID
   */
  private static final long serialVersionUID = 4989518536779832584L;

  /** The document id. **/
  @Id
  @XmlElement(name = "id")
  private String id;

  /** The Name of the process. **/
  @XmlElement(name = "name")
  private String name;

  /** The time interval level time. **/
  @XmlElement(name = "timeIntervalLevels")
  private Map<String, Object> timeIntervalLevels;

  /** The list of process graph layout **/
  @XmlElement(name = "processGraph")
  private Map<String, Object> processGraph;

  /** The list phases **/
  @XmlElement(name = "phases")
  private Map<String, Object> phases;

  /** The list tasks **/
  @XmlElement(name = "tasks")
  private Map<String, Object> tasks;

  /**
   * Constructor with fields
   * 
   * @param id
   * @param ans
   * @param processGraph
   * @param phases
   * @param tasks
   */
  public ProcessDefinition(String id, String name,
      Map<String, Object> timeIntervalLevels, int humanAnsTimeUnit,
      Map<String, Object> processGraph, Map<String, Object> phases,
      Map<String, Object> tasks) {
    super();
    this.id = id;
    this.name = name;
    this.timeIntervalLevels = timeIntervalLevels;
    this.processGraph = processGraph;
    this.phases = phases;
    this.tasks = tasks;

  }

  /**
   * Default Constructor
   * 
   */
  public ProcessDefinition() {
    this.timeIntervalLevels = new HashMap<String, Object>();
    this.processGraph = new HashMap<String, Object>();
    this.phases = new HashMap<String, Object>();
    this.tasks = new HashMap<String, Object>();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the timeIntervalLevels
   */
  public Map<String, Object> getTimeIntervalLevels() {
    return timeIntervalLevels;
  }

  /**
   * @param timeIntervalLevels
   *          the timeIntervalLevels to set
   */
  public void setTimeIntervalLevels(Map<String, Object> timeIntervalLevels) {
    this.timeIntervalLevels = timeIntervalLevels;
  }

  /**
   * @return the processGraph
   */
  public Map<String, Object> getProcessGraph() {
    return processGraph;
  }

  /**
   * @param processGraph
   *          the processGraph to set
   */
  public void setProcessGraph(Map<String, Object> processGraph) {
    this.processGraph = processGraph;
  }

  /**
   * @return the phases
   */
  public Map<String, Object> getPhases() {
    return phases;
  }

  /**
   * @param phases
   *          the phases to set
   */
  public void setPhases(Map<String, Object> phases) {
    this.phases = phases;
  }

  /**
   * @return the tasks
   */
  public Map<String, Object> getTasks() {
    return tasks;
  }

  /**
   * @param tasks
   *          the tasks to set
   */
  public void setTasks(Map<String, Object> tasks) {
    this.tasks = tasks;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

}
