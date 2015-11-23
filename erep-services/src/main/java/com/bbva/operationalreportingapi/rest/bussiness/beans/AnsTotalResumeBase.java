package com.bbva.operationalreportingapi.rest.bussiness.beans;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Class that resume the informations about totals of ANS compliance information
 * for a date (as usual the last day).
 * 
 * <p>
 * It can represents data in the formats JSON/XML
 * </p>
 * 
 * @author BBVA-ReportingOperacional
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "nameEntity", "totalFinalized", "totalNewOrders",
    "totalOk", "totalKo", "totalWarning", "date" })
public class AnsTotalResumeBase implements Serializable {

  /**
   * The serial Id.
   */
  private static final long serialVersionUID = 8212913277697125799L;

  /** The name of the entity. */
  @XmlElement(name = "nameEntity")
  private String nameEntity;

  /** The total number of finalized processes in time. */
  @XmlElement(name = "totalOk")
  private int totalOk;

  /** The total number of finalized processes with overtime. */
  @XmlElement(name = "totalKo")
  private int totalKo;

  /**
   * The total number of finalized processes with a ten over hundred in the
   * duration of task.
   */
  @XmlElement(name = "totalWarning")
  private int totalWarning;

  /** The total number of new processes. */
  @XmlElement(name = "totalNewOrders")
  private int totalNewOrders;

  /** The total number of finalized processes. */
  @XmlElement(name = "totalFinalized")
  private int totalFinalized;

  /** The date to resume. */
  @XmlElement(name = "date")
  private Date date;

  /**
   * Default constructor for {@link AnsTotalResumeBase}.
   * 
   * @param nameEntity
   *          The name of the entity (Hierarchy element).
   * @param totalOk
   *          The total number of finalized processes in time.
   * @param totalKo
   *          The total number of finalized processes with overtime.
   * @param totalWarning
   *          The total number of finalized processes with a ten over hundred in
   *          the duration of task.
   * @param totalNewOrders
   *          The total number of new processes.
   * @param totalFinalized
   *          The total number of finalized processes.
   * @param date
   *          The total number of new processes.
   */
  public AnsTotalResumeBase(String nameEntity, int totalOk, int totalKo,
      int totalWarning, int totalNewOrders, int totalFinalized, Date date) {
    super();
    this.nameEntity = nameEntity;
    this.totalOk = totalOk;
    this.totalKo = totalKo;
    this.totalWarning = totalWarning;
    this.totalNewOrders = totalNewOrders;
    this.totalFinalized = totalFinalized;
    this.date = date;
  }

  /**
   * Default constructor for {@link AnsTotalResumeBase}.
   */
  public AnsTotalResumeBase() {

  }

  /**
   * Getter for The total number of finalized processes in time.
   * 
   * @return The total number of finalized processes in time.
   */
  public int getTotalOk() {
    return totalOk;
  }

  /**
   * Setter for The total number of finalized processes in time.
   * 
   * @param totalOk
   *          The total number of finalized processes in time.
   */
  public void setTotalOk(int totalOk) {
    this.totalOk = totalOk;
  }

  /**
   * Getter for the total number of finalized processes with overtime.
   * 
   * @return The total number of finalized processes with overtime.
   */
  public int getTotalKo() {
    return totalKo;
  }

  /**
   * Setter for the total number of finalized processes with overtime.
   * 
   * @param totalKo
   *          The total number of finalized processes with overtime.
   */
  public void setTotalKo(int totalKo) {
    this.totalKo = totalKo;
  }

  /**
   * Getter for the total number of finalized processes with a ten over hundred
   * in the duration of task.
   * 
   * @return The total number of finalized processes with a ten over hundred in
   *         the duration of task.
   */
  public int getTotalWarning() {
    return totalWarning;
  }

  /**
   * Setter for the total number of finalized processes with a ten over hundred
   * in the duration of task.
   * 
   * @param totalWarning
   *          The total number of finalized processes with a ten over hundred in
   *          the duration of task.
   */
  public void setTotalWarning(int totalWarning) {
    this.totalWarning = totalWarning;
  }

  /**
   * Getter for the total number of new processes.
   * 
   * @return the total number of new processes.
   */
  public int getTotalNewOrders() {
    return totalNewOrders;
  }

  /**
   * Setter for the total number of new processes.
   * 
   * @param totalNewOrders
   *          the total number of new processes.
   */
  public void setTotalNewOrders(int totalNewOrders) {
    this.totalNewOrders = totalNewOrders;
  }

  /**
   * Getter for the total number of finalized processes.
   * 
   * @return the total number of finalized processes.
   */
  public int getTotalFinalized() {
    return totalFinalized;
  }

  /**
   * Setter for the total number of finalized processes.
   * 
   * @param totalFinalized
   *          the total number of finalized processes.
   */
  public void setTotalFinalized(int totalFinalized) {
    this.totalFinalized = totalFinalized;
  }

  /**
   * Getter for the date to resume.
   * 
   * @return the date to resume.
   */
  public Date getDate() {
    return date;
  }

  /**
   * Setter for the date to resume.
   * 
   * @param date
   *          the date to resume.
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * GETTER for the name of the entity (Hierarchy element).
   * 
   * @return the name of the entity (Hierarchy element).
   */
  public String getNameEntity() {
    return nameEntity;
  }

  /**
   * SETTER for the name of the entity (Hierarchy element).
   * 
   * @param nameEntity
   *          the name of the entity (Hierarchy element).
   */
  public void setNameEntity(String nameEntity) {
    this.nameEntity = nameEntity;
  }
}
