package com.bbva.operationalreportingapi.rest.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Tool to format dates in ISO 8601 format.
 * 
 * @author BBVA-ReportingOperacional
 * @extends {@link XmlAdapter}
 */
public class DateFormatterIso8601Adaptor extends XmlAdapter<String, Date> {

  private static final String FORMATO_FECHA_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSZZ";
  private SimpleDateFormat dateFormat;

  /**
   * Default constructor of {@link DateFormatterIso8601Adaptor}.
   */
  public DateFormatterIso8601Adaptor() {
    super();

    dateFormat = new SimpleDateFormat(FORMATO_FECHA_ISO_8601);
  }

  @Override
  public Date unmarshal(String date) throws ParseException {
    return dateFormat.parse(date);
  }

  @Override
  public String marshal(Date date) throws ParseException {
    return dateFormat.format(date);
  }

}
