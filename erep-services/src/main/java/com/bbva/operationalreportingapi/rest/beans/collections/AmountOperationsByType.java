package com.bbva.operationalreportingapi.rest.beans.collections;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class that contains sub-family data from the hierarchy of the process.
 * <p>
 * It can represents data in the formats JSON/XML
 * </p>
 * 
 * @author BBVA-ReportingOperacional
 **/
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "operationtype", "amount" })
@Document(collection = "TOTAL")
public class AmountOperationsByType implements Serializable {

  private static final long serialVersionUID = 1278756019817345035L;

  /** The id of the operation type. **/
  @XmlElement(name = "operationtype")
  private String operationtype;

  /** The total amount. **/
  @XmlElement(name = "amount")
  private Integer amount;

  /**
   * Default constructor with parameters.
   * 
   * @param operationtype
   *          the operation type param.
   * @param amount
   *          the amount param.
   */
  public AmountOperationsByType(String operationtype, Integer amount) {
    this.operationtype = operationtype;
    this.amount = amount;
  }

  /**
   * Default constructor.
   */
  public AmountOperationsByType() {
  }

  /**
   * Getter for Operation Type.
   * 
   * @return String
   */
  public String getOperationtype() {
    return operationtype;
  }

  /**
   * Setter for Operation type.
   * 
   * @param operationtype
   *          the operation type param.
   */
  public void setOperationtype(String operationtype) {
    this.operationtype = operationtype;
  }

  /**
   * Getter for Amount.
   * 
   * @return Integer
   */
  public Integer getAmount() {
    return amount;
  }

  /**
   * Setter for Amount.
   * 
   * @param amount
   *          the amount param.
   */
  public void setAmount(Integer amount) {
    this.amount = amount;
  }
}
