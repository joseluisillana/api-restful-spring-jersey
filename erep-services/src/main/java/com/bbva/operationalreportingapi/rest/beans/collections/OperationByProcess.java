package com.bbva.operationalreportingapi.rest.beans.collections;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
@XmlType(propOrder = { "id", "operationtype", "instancecount", "ansok",
    "ansko", "answarning", "ansavgtime", "ans" })
@Document(collection = "C_EREP_OPERATIONBYPROCESS_DAILY_PROCESS_ANS_COMPLIANCE")
public class OperationByProcess implements Serializable {

  /**
   * Serial UID.
   */
  private static final long serialVersionUID = -782412258248811174L;

  /** The operation type. **/
  @XmlElement(name = "operationtype")
  private String operationtype;

  /** The instance count. **/
  @XmlElement(name = "instancecount")
  private Integer instancecount;

  /** The ans OK count. **/
  @XmlElement(name = "ansok")
  private Integer ansok;

  /** The ans KO count. **/
  @XmlElement(name = "ansko")
  private Integer ansko;

  /** The ans warning count. **/
  @XmlElement(name = "answarning")
  private Integer answarning;

  /** The ans average time. **/
  @XmlElement(name = "ansavgtime")
  private Long ansavgtime;

  /** The ans time. **/
  @XmlElement(name = "ans")
  private Long ans;

  /**
   * Default Constructor.
   */
  public OperationByProcess() {
    super();
  }

  /**
   * GETTER for the operation type.
   * 
   * @return the operation type.
   */
  public String getOperationtype() {
    return operationtype;
  }

  /**
   * SETTER for the operation type.
   * 
   * @param operationtype
   *          the operation type param.
   */
  public void setOperationtype(String operationtype) {
    this.operationtype = operationtype;
  }

  /**
   * GETTER for the instance count.
   * 
   * @return the instance count.
   */
  public Integer getInstancecount() {
    return instancecount;
  }

  /**
   * SETTER for the instance count.
   * 
   * @param instancecount
   *          the instance count param.
   */
  public void setInstancecount(Integer instancecount) {
    this.instancecount = instancecount;
  }

  /**
   * GETTER for the amount of operations with Ok in ANS time.
   * 
   * @return the amount of operations with Ok in ANS time.
   */
  public Integer getAnsok() {
    return ansok;
  }

  /**
   * SETTER for the amount of operations with Ok in ANS time.
   * 
   * @param ansOkCount
   *          the amount of operations with Ok in ANS time.
   */
  public void setAnsok(Integer ansOkCount) {
    this.ansok = ansOkCount;
  }

  /**
   * GETTER for the amount of operations with KO in ANS time.
   * 
   * @return the amount of operations with KO in ANS time.
   */
  public Integer getAnsko() {
    return ansko;
  }

  /**
   * SETTER for the amount of operations with KO in ANS time.
   * 
   * @param ansKoCount
   *          the amount of operations with KO in ANS time.
   */
  public void setAnsko(Integer ansKoCount) {
    this.ansko = ansKoCount;
  }

  /**
   * GETTER for the amount of operations with Warnings in ANS time.
   * 
   * @return the amount of operations with warnings in ANS time.
   */
  public Integer getAnswarning() {
    return answarning;
  }

  /**
   * SETTER for the amount of operations with Warnings in ANS time.
   * 
   * @param ansWarningCount
   *          the amount of operations with Warnings in ANS time.
   */
  public void setAnswarning(Integer ansWarningCount) {
    this.answarning = ansWarningCount;
  }

  /**
   * GETTER for the AVG of the ANS time expended for this operation.
   * 
   * @return the AVG of the ANS time expended for this operation.
   */
  public Long getAnsavgtime() {
    return ansavgtime;
  }

  /**
   * SETTER for the AVG of the ANS time expended for this operation.
   * 
   * @param ansAverageTime
   *          the AVG of the ANS time expended for this operation.
   */
  public void setAnsavgtime(Long ansAverageTime) {
    this.ansavgtime = ansAverageTime;
  }

  /**
   * GETTER for the ANS time expended for this operation.
   * 
   * @return the ANS time expended for this operation.
   */
  public Long getAns() {
    return ans;
  }

  /**
   * SETTER for the ANS time expended for this operation.
   * 
   * @param ans
   *          the ANS time expended for this operation.
   */
  public void setAns(Long ans) {
    this.ans = ans;
  }
}
