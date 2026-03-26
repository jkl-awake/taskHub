package com.taskhub.auto.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Path;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CodeGenerator {

    public static void main(String[] args) {
        String url = readConfig("generator.db.url", "GENERATOR_DB_URL", "jdbc:mysql://124.223.0.244:3190/app_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false");
        String username = readConfig("generator.db.username", "GENERATOR_DB_USERNAME", "app_user");
        String password = readConfig("generator.db.password", "GENERATOR_DB_PASSWORD", "App@123456");
        String tables = readConfig("generator.tables", "GENERATOR_TABLES", "sys_user,sys_role,sys_user_role,project");
        String tablePrefixes = readConfig("generator.table-prefixes", "GENERATOR_TABLE_PREFIXES", "t_,tb_");

        Path moduleRoot = Path.of(System.getProperty("user.dir")).resolve("auto-entity");
        List<String> tableList = splitCsv(tables);
        List<String> prefixList = splitCsv(tablePrefixes);

        FastAutoGenerator.create(url, username, password)
                .dataSourceConfig(builder -> builder
                        .typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            // 自定义类型转换：tinyint -> int (基本类型)
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.TINYINT) {
                                return DbColumnType.BASE_INT;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .globalConfig(builder -> builder
                        .author("Codex")
                        .disableOpenDir()
                        .outputDir(moduleRoot.resolve("src/main/java").toString()))
                .packageConfig(builder -> builder
                        .parent("com.taskhub.auto")
                        .entity("entity")
                        .mapper("mapper")
                        .pathInfo(Map.of(
                                OutputFile.entity, moduleRoot.resolve("src/main/java/com/taskhub/auto/entity").toString(),
                                OutputFile.mapper, moduleRoot.resolve("src/main/java/com/taskhub/auto/mapper").toString(),
                                OutputFile.xml, moduleRoot.resolve("src/main/resources/mapper").toString()
                        )))
                .strategyConfig(builder -> {
                    builder.addInclude(tableList.toArray(String[]::new));
                    if (!prefixList.isEmpty()) {
                        builder.addTablePrefix(prefixList.toArray(String[]::new));
                    }
                    builder.entityBuilder()
                            .enableFileOverride()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .formatFileName("%sDo");
                    builder.mapperBuilder()
                            .enableFileOverride()
                            .enableMapperAnnotation()
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sMapper");

                    builder.controllerBuilder().disable();
                    builder.serviceBuilder().disable();
                })
                .templateConfig(builder -> builder
                        .entity("/templates/entity.java")
                        .disable(TemplateType.SERVICE)
                        .disable(TemplateType.SERVICE_IMPL)
                        .disable(TemplateType.CONTROLLER))
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    private static String readConfig(String propertyKey, String envKey, String defaultValue) {
        String propertyValue = System.getProperty(propertyKey);
        if (propertyValue != null && !propertyValue.isBlank()) {
            return propertyValue;
        }
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }
        return defaultValue;
    }

    private static List<String> splitCsv(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(item -> !item.isEmpty())
                .collect(Collectors.toList());
    }
}
