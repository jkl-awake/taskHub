package com.taskhub.api.common.utils;

import com.taskhub.api.common.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class PageHeaderUtils {

    public static final String HEADER_PAGE_NUM = "pageNum";
    public static final String HEADER_PAGE_SIZE = "pageSize";
    public static final int DEFAULT_PAGE_NUM = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    private PageHeaderUtils() {
    }

    public static int getPageNum() {
        return getPageNum(getCurrentRequest());
    }

    public static int getPageSize() {
        return getPageSize(getCurrentRequest());
    }

    public static int getPageNum(HttpServletRequest request) {
        return parseHeaderAsPositiveInt(request, HEADER_PAGE_NUM, DEFAULT_PAGE_NUM);
    }

    public static int getPageSize(HttpServletRequest request) {
        return parseHeaderAsPositiveInt(request, HEADER_PAGE_SIZE, DEFAULT_PAGE_SIZE);
    }

    private static int parseHeaderAsPositiveInt(HttpServletRequest request, String headerName, int defaultValue) {
        if (request == null) {
            return defaultValue;
        }

        String rawValue = request.getHeader(headerName);
        if (rawValue == null || rawValue.isBlank()) {
            return defaultValue;
        }

        try {
            int value = Integer.parseInt(rawValue.trim());
            if (value <= 0) {
                throw new BusinessException(400, headerName + " must be greater than 0");
            }
            return value;
        } catch (NumberFormatException exception) {
            throw new BusinessException(400, headerName + " must be a number");
        }
    }

    private static HttpServletRequest getCurrentRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (!(attributes instanceof ServletRequestAttributes servletRequestAttributes)) {
            return null;
        }
        return servletRequestAttributes.getRequest();
    }
}

