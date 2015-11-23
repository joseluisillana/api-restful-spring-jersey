package com.bbva.operationalreportingapi.rest.beans.collections;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class that contains the process in order, aggregated by weeks.
 * <p>
 * It can represents data in the formats JSON/XML
 * </p>
 * 
 * @author BBVA-ReportingOperacional
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "process", "family", "subfamily", "date", "total" })
@Document(collection = "C_EREP_WEEKLY_REGISTRATION")
public class WeeklyRegistration extends DailyRegistration implements
    Serializable {

  private static final long serialVersionUID = -6537714844356426159L;

}
