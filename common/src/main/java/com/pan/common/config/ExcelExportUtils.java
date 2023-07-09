package com.pan.common.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ExcelExportUtils {

    /**
     * 导出excel
     * @param response
     * @param workbook
     * @param fileName
     */
    public static void exportExcel(HttpServletResponse response, Workbook workbook,String fileName) {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.builder("attachment")
                        .filename(fileName+".xlsx").build().toString());
        try {
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            log.error("Error while writing to output stream", e);
        }
    }
}
