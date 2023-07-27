package com.project.labapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDateConverter());
    }

    private static class StringToDateConverter implements org.springframework.core.convert.converter.Converter<String, Date> {
        @Override
        public Date convert(String source) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
                return dateFormat.parse(source);
            } catch (ParseException e) {
                // Tarih dönüşümünde hata oluşursa null dönebilir ya da hata yönetimi yapılabilir.
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:C:/Users/ASUS/Desktop/labapp/labapp/src/main/resources/static/images");
    }
}