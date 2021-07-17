package com.poet.elasticsearch.helper.core;

import com.poet.elasticsearch.helper.beans.annotation.EsField;
import com.poet.elasticsearch.helper.beans.annotation.EsIndex;
import com.poet.elasticsearch.helper.beans.enums.Meta;
import com.poet.elasticsearch.helper.beans.exception.EsHelperConfigException;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper
 * date:    2021/5/13
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class IndexMapperTrans {

    /**
     *  translate target Index-mapper-class to initialize Index mapper-json
     * @param target
     * @return
     */
    public String handle(Class<?> target, boolean excFormat) {
        EsIndex indexDes = target.getAnnotation(EsIndex.class);
        if (indexDes == null) throw new EsHelperConfigException("undefine @EsIndex ann here,please have a check");
        int shards = indexDes.shards();
        int replicas = indexDes.replicas();

        Field[] fieldArr = target.getDeclaredFields();




        return null;
    }

    @EsIndex(name = "demo", shards = 5, replicas = 1)
    static class Demo {

        @EsField(name = "name", type = Meta.KEYWORD)
        private String name;

        @EsField(type = Meta.INTEGER)
        private int age;


    }









    protected static class IndexDefinition {

        private Settings settings;

        private Mappings mappings;

        public IndexDefinition() {
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

        public IndexDefinition genDefinition() {
            IndexDefinition definition = new IndexDefinition();
            Settings settings = new Settings();
            Mappings mappings = new Mappings();
            definition.setSettings(settings);
            definition.setMappings(mappings);
            return definition;
        }

        public IndexDefinition genDefinition(int shards, int replicas) {
            IndexDefinition definition = new IndexDefinition();
            Settings settings = new Settings();
            settings.setNumber_of_shards(shards);
            settings.setNumber_of_replicas(replicas);
            definition.setSettings(settings);
            return definition;
        }

        public IndexDefinition setSetting(int shards, int replicas) {
            Settings settings = this.getSettings();
            settings.setNumber_of_shards(shards);
            settings.setNumber_of_replicas(replicas);
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
