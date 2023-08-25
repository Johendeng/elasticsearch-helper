package org.pippi.elasticsearch.helper.core.utils;

import com.google.common.collect.Maps;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsField;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsIndex;
import org.pippi.elasticsearch.helper.model.enums.EsMeta;
import org.pippi.elasticsearch.helper.model.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.model.exception.EsHelperIOException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author    JohenTeng
 * @date     2021/5/13
 *
 **/
@Deprecated
public class IndexMappingUtils {


    private static final String _TYPE_KEY = "type";
    private static final String _ANALYZER = "analyzer";
    private static final String _FORMAT = "format";
    private static final String _SEARCH_ANALYZER = "search_analyzer";

    private String exportLoc = "out/";
    private static final String _DEFAULT_MAPPING_NAME = "mapping-";

    public void setExportLoc(String exportLoc) {
        this.exportLoc = exportLoc;
    }

    /**
     *  translate target Index-mapper-class to initialize Index mapper-json
     * @param target
     * return
     */
    public String read(Class<?> target, boolean prettyExport) {
        EsIndex indexDes = target.getAnnotation(EsIndex.class);
        if (indexDes == null) throw new EsHelperConfigException("undefine @EsIndex ann here,please have a check");
        int shards = indexDes.shards();
        int replicas = indexDes.replicas();
        IndexDefinition indexDefinition = IndexDefinition.builder().init(shards, replicas);
        Field[] fieldArr = target.getDeclaredFields();
        for (Field field : fieldArr) {
            indexDefinition.appendPropertie(field);
        }
        if (prettyExport) {
            return SerializerUtils.parseObjToJsonPretty(indexDefinition);
        }

        return SerializerUtils.parseObjToJson(indexDefinition);
    }


    public void export(String mappingName, String filePath, Class<?> target) {
        this.export(mappingName, filePath, target, true);
    }

    public void export(String mappingName, String filePath, Class<?> target, boolean prettyExport) {
        String currentMappingName = mappingName;
        if (StringUtils.isBlank(mappingName)) currentMappingName = _DEFAULT_MAPPING_NAME + (new Date()).getTime() + ".json";
        if (StringUtils.isBlank(filePath)) filePath = exportLoc + currentMappingName;
        String mappingJsonStr = this.read(target, prettyExport);

        File fileInstance = new File(filePath);
        if (!fileInstance.getParentFile().exists()) {
            fileInstance.getParentFile().mkdir();
        }
        try (
            FileOutputStream out = new FileOutputStream(fileInstance)
        ){
            out.write(mappingJsonStr.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (Exception e) {
            throw new EsHelperIOException("export ES-MAPPING-JSON-FILE ERROR", e);
        }
    }

    protected static class IndexDefinition {

        private Settings settings;

        private Mappings mappings;

        public IndexDefinition() {
        }

        public static IndexDefinition builder(){
            return new IndexDefinition();
        }

        public IndexDefinition(Settings settings, Mappings mappings) {
            this.settings = settings;
            this.mappings = mappings;
        }

        public Settings getSettings() {
            return settings;
        }

        public void setSettings(Settings settings) {
            this.settings = settings;
        }

        public Mappings getMappings() {
            return mappings;
        }

        public void setMappings(Mappings mappings) {
            this.mappings = mappings;
        }

        public IndexDefinition init() {
            Settings settings = new Settings();
            Mappings mappings = new Mappings();
            this.setSettings(settings);
            this.setMappings(mappings);
            return this;
        }

        public IndexDefinition init(int shards, int replicas) {
            this.init();
            this.getSettings().setNumber_of_shards(shards);
            this.getSettings().setNumber_of_replicas(replicas);
            return this;
        }

        public IndexDefinition setSetting(int shards, int replicas) {
            Settings settings = this.getSettings();
            settings.setNumber_of_shards(shards);
            settings.setNumber_of_replicas(replicas);
            return this;
        }

        public IndexDefinition appendPropertie (Field field) {
            Mappings mappings = this.getMappings();
            EsField esField = field.getAnnotation(EsField.class);
            if (esField != null) {
                String esFieldName = esField.name();
                if (StringUtils.isBlank(esFieldName)) esFieldName = field.getName();
                Map<String, Object> esFieldDes = Maps.newHashMap();
                String typeName ;
                EsMeta type = esField.type();
                if (type == null) typeName = esField.typeStringify();
                else typeName = type.getType();
                if (StringUtils.isBlank(typeName)) throw new EsHelperConfigException("@EsField type is undefine");

                esFieldDes.put(_TYPE_KEY, typeName);

                String analyzer = esField.analyzer();
                if (StringUtils.isNotBlank(analyzer)) {
                    esFieldDes.put(_ANALYZER, analyzer);
                    esFieldDes.put(_SEARCH_ANALYZER, analyzer);
                }
                String format = esField.format();
                if (StringUtils.isNotBlank(format)) {
                    esFieldDes.put(_FORMAT, format);
                }

                mappings.getProperties().put(esFieldName, esFieldDes);
            }
            return this;
        }


    }

    protected static class Settings {
        private int number_of_shards;
        private int number_of_replicas;

        public Settings() {
        }

        public Settings(int number_of_shards, int number_of_replicas) {
            this.number_of_shards = number_of_shards;
            this.number_of_replicas = number_of_replicas;
        }

        public int getNumber_of_shards() {
            return number_of_shards;
        }

        public void setNumber_of_shards(int number_of_shards) {
            this.number_of_shards = number_of_shards;
        }

        public int getNumber_of_replicas() {
            return number_of_replicas;
        }

        public void setNumber_of_replicas(int number_of_replicas) {
            this.number_of_replicas = number_of_replicas;
        }
    }

    protected static class Mappings {

        private Map<String, Object> properties;

        public Mappings() {
            properties = new HashMap<>();
        }

        public Mappings(Map<String, Object> properties) {
            this.properties = properties;
        }

        public Map<String, Object> getProperties() {
            return properties;
        }

        public void setProperties(Map<String, Object> properties) {
            this.properties = properties;
        }
    }

}
