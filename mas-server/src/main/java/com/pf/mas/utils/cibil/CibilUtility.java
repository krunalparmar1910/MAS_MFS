package com.pf.mas.utils.cibil;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@NoArgsConstructor
public class CibilUtility {

    public static <T> T convertXmlToDto(String xmlData, Class<T> targetType) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            return xmlMapper.readValue(xmlData, targetType);
        } catch (Exception e) {
            log.error("Could not convert XML data to DTO.", e);
        }
        return null;
    }

    public static LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        return LocalDate.parse(date, formatter);
    }

    public static String decodeAndExtractEncodedRawXml(String htmlEncodedXml) {
        String decodedXml = htmlEncodedXml;
        while (decodedXml.contains("&amp;lt;") || decodedXml.contains("&amp;gt;")) {
            decodedXml = decodeHtmlEncodedXml(decodedXml);
        }

        String cleanedXmlResponse = decodedXml.replaceAll("<\\?xml version=\"1.0\"\\?>", "");
        cleanedXmlResponse = cleanedXmlResponse.replaceAll("<\\?xml version=\"1\\.0\" encoding=\"utf-8\" \\?>", "");
        cleanedXmlResponse = cleanedXmlResponse.replaceAll("<\\?xml.*?\\?>", "");

        return cleanedXmlResponse;
    }

    public static String decodeHtmlEncodedXml(String htmlEncodedXml) {
        String decodedXml = htmlEncodedXml;
        decodedXml = decodedXml
                .replaceAll("&amp;", "&")
                .replaceAll("&amp;lt;", "<")
                .replaceAll("&amp;gt;", ">")
                .replaceAll("&quot;", "\"")
                .replaceAll("&apos;", "'")
                .replaceAll("&#xD;", "")
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&nbsp;", " ")
                .replaceAll("&amp;quot;", "\"")
                .replaceAll("&amp;apos;", "'")
                .replaceAll("&amp;nbsp;", " ");


        return decodedXml;
    }



}
