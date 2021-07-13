package com.poet.elasticsearch.helper.core;

import com.poet.elasticsearch.helper.beans.annotation.EsField;
import com.poet.elasticsearch.helper.beans.annotation.EsIndex;
import com.poet.elasticsearch.helper.beans.enums.Meta;

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
    public String handle(Class<?> target) {







        return null;
    }

    @EsIndex(name = "demo", shards = 5, replicas = 1)
    static class Demo {

        @EsField(name = "name", type = Meta.KEYWORD)
        private String name;

        @EsField(type = Meta.INTEGER)
        private int age;


    }







    class IndexDefinition {

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

    }

    class Settings {
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

    class Mappings {

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
