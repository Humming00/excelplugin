package com.humming.springmvc.plugin.simpleexcel.excelexport;

import com.alibaba.excel.EasyExcel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.humming.springmvc.plugin.simpleexcel.excelexport.ExcelHandlerAdapter.ExcelHandlerMethodReqAttributeKey;

public class ExcelExportReturnHandler implements HandlerMethodReturnValueHandler {
    public static final String ContentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String ContentDisposition = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return true;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        // returnValue 是controller 返回的数据
        // 这里可以处理Result这种加一层的包装类型

        // 做excel导出
        ExcelExportInfo excelExportInfo = getExcelExportInfo(webRequest);
        mavContainer.setRequestHandled(true);
        HttpServletResponse response = Objects.requireNonNull(webRequest.getNativeResponse(HttpServletResponse.class));
        writeToResponse(returnValue,excelExportInfo, response);
    }

    private void writeToResponse(Object returnValue,ExcelExportInfo excelExportInfo,HttpServletResponse response) throws IOException, HttpMessageNotWritableException {
        response.setContentType(ContentType);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" +
                URLEncoder.encode(excelExportInfo.getFileName(), StandardCharsets.UTF_8));

        // 3. 写入Excel并输出到响应流
        EasyExcel.write(response.getOutputStream(), excelExportInfo.getHeadClass())
                .sheet(excelExportInfo.getSheetName())
                .doWrite(getRowList(returnValue));
    }

    private List<?>getRowList(Object returnValue){
        if (returnValue == null){
            return Collections.emptyList();
        }
        if (returnValue instanceof List){
            return  (List<?>) returnValue;
        }
        return Collections.singletonList(returnValue);
    }
    private ExcelExportInfo getExcelExportInfo(NativeWebRequest webRequest){
        Object attribute = webRequest.getAttribute(ExcelHandlerMethodReqAttributeKey, RequestAttributes.SCOPE_REQUEST);
        return  (ExcelExportInfo)attribute;
    }
}

