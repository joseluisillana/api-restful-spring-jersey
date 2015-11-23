package com.bbva.operationalreportingapi.rest.beans.hierarchy;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class that contains Task data from the hierarchy of the process.
 * <p>
 * It can represents data in the formats JSON/XML
 * </p>
 * 
 * @author BBVA-ReportingOperacional
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "task", "duration" })
@Document(collection = "TASKS")
public class Task implements Serializable {

  private static final long serialVersionUID = 4077625198521325571L;

  /** The description of the task. **/
  @Indexed
  @XmlElement(name = "task")
  private String task;

  @XmlElement(name = "duration")
  private Integer duration;

  /**
   * Constructor for {@link Task}.
   * 
   * 
   * @param taskName
   *          the name parameter.
   */
  public Task(String taskName) {

    this.task = taskName;

  }

  /**
   * Default constructor for {@link Task}.
   */
  public Task() {

  }

  /**
   * GETTER for the name.
   * 
   * @return String
   */
  public String getTask() {
    return task;
  }

  /**
   * SETTER for the name.
   * 
   * @param taskName
   *          the name parameter.
   */
  public void setTask(String taskName) {
    this.task = taskName;
  }

  /**
   * GETTER for the duration.
   */
  public Integer getDuration() {
    return duration;
  }

  /**
   * SETTER for the duration.
   * 
   * @param duration
   *          the duration parameter.
   */
  public void setDuration(Integer duration) {
    this.duration = duration;
  }
}
