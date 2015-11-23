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
 * Class that contains the family data from the hierarchy of the process.
 * <p>
 * It can represents data in the formats JSON/XML
 * </p>
 * 
 * @author BBVA-ReportingOperacional
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "family", "subfamilies" })
@Document(collection = "C_EREP_OPERATIONS_CONFIGURATIONS")
public class OperationsConfigurations implements Serializable {

  private static final long serialVersionUID = -4370666995863576715L;

  /** The name of the family. */
  @Indexed
  @XmlElement(name = "family")
  private String family;

  /** List of sub-families in family. **/
  @XmlElement(name = "subfamilies")
  private Set<Subfamily> subfamilies;

  /**
   * Constructor of {@link OperationsConfigurations}.
   * 
   * @param family
   *          the name parameter.
   */
  public OperationsConfigurations(String family) {
    this.family = family;
    this.subfamilies = new HashSet<Subfamily>();
  }

  /**
   * Default constructor for {@link OperationsConfigurations}.
   */
  public OperationsConfigurations() {
    this.subfamilies = new HashSet<Subfamily>();
  }

  /**
   * GETTER for the {@link Subfamily} list.
   * 
   * @return {@link Subfamily} List.
   */
  public Set<Subfamily> getSubfamilies() {
    return subfamilies;
  }

  /**
   * SETTER for the {@link Subfamily} list.
   * 
   * @param {@link Subfamily} List.
   */
  public void setSubfamilies(Set<Subfamily> subfamilies) {
    this.subfamilies = subfamilies;
  }

  /**
   * GETTER for name of family.
   * 
   * @return String
   */
  public String getFamily() {
    return family;
  }

  /**
   * SETTER for the name of family.
   * 
   * @param family
   *          the name of family parameter.
   */
  public void setFamily(String family) {
    this.family = family;
  }
}
