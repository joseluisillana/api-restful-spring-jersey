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
@XmlType(propOrder = { "id", "name", "date", "ans", "processData", "tasksData",
    "phasesData" })
@Document(collection = "C_EREP_DAILY_CLAIMS_ANS_COMPLIANCE")
public class ClaimsAnsCompliance implements Serializable {

  /**
   * Serial UID.
   */
  private static final long serialVersionUID = 2983762619706838565L;

  /** The document id. **/
  @Id
  @XmlElement(name = "id")
  private String id;

  /** The name of the process. **/
  @XmlElement(name = "name")
  private String name;

  /** The date. **/
  @XmlElement(name = "date")
  private Long date;

  /** The ANS time. **/
  @XmlElement(name = "ans")
  private int ans;

  /** The process acums and daily counters data **/
  @XmlElement(name = "processData")
  private Map<String, Object> processData;

  /** The tasks acums and daily counters data **/
  @XmlElement(name = "tasksData")
  private Map<String, Object> tasksData;

  /** The phases acums and daily counters data **/
  @XmlElement(name = "phasesData")
  private Map<String, Object> phasesData;

  /**
   * Default constructor with parameters.
   * 
   * @param id
   * @param name
   * @param date
   * @param ans
   * @param ansAvgTime
   * @param acumNewInstances
   * @param acumOpenInstances
   * @param acumClosedInstances
   * @param dailyAnsOK
   * @param dailyAnsKO
   */
  public ClaimsAnsCompliance(String id, String name, Long date, int ans,
      HashMap<String, Object> processData, HashMap<String, Object> tasksData,
      HashMap<String, Object> phasesData) {
    super();
    this.id = id;
    this.name = name;
    this.date = date;
    this.ans = ans;
    this.processData = processData;
    this.tasksData = tasksData;
    this.phasesData = phasesData;
  }

  /**
   * Default constructor.
   */
  public ClaimsAnsCompliance() {
    this.processData = new HashMap<String, Object>();
    this.tasksData = new HashMap<String, Object>();
    this.phasesData = new HashMap<String, Object>();
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

  /**
   * @return the date
   */
  public Long getDate() {
    return date;
  }

  /**
   * @param date
   *          the date to set
   */
  public void setDate(Long date) {
    this.date = date;
  }

  /**
   * @return the ans
   */
  public int getAns() {
    return ans;
  }

  /**
   * @param ans
   *          the ans to set
   */
  public void setAns(int ans) {
    this.ans = ans;
  }

  /**
   * @return the processData
   */
  public Map<String, Object> getProcessData() {
    return processData;
  }

  /**
   * @param processData
   *          the processData to set
   */
  public void setProcessData(HashMap<String, Object> processData) {
    this.processData = processData;
  }

  /**
   * @return the tasksData
   */
  public Map<String, Object> getTasksData() {
    return tasksData;
  }

  /**
   * @param tasksData
   *          the tasksData to set
   */
  public void setTasksData(HashMap<String, Object> tasksData) {
    this.tasksData = tasksData;
  }

  /**
   * @return the phasesData
   */
  public Map<String, Object> getPhasesData() {
    return phasesData;
  }

  /**
   * @param phasesData
   *          the phasesData to set
   */
  public void setPhasesData(HashMap<String, Object> phasesData) {
    this.phasesData = phasesData;
  }
}
