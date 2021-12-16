package org.pippi.elasticsearch.helper.core.utils;

import com.google.common.collect.Sets;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.pippi.elasticsearch.helper.core.beans.enums.EsMeta;

import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


/**
 * EsHelperRelBeansGenerator
 *
 * @author JohenTeng
 * @date 2021/12/10
 */
public class EsHelperRelBeansGenerator {


    private static String fileHeader = "package xxx\n\n";

    private static String PROP = "properties";

    private static String SP = "_";

    private String exportPath = System.getProperty("user.dir") + "/es-helper-gen/";

    private String index ;

    private RestHighLevelClient client;

    private String docProp = PROP;

    public EsHelperRelBeansGenerator(String index, RestHighLevelClient client) {
        this.index = index;
        this.client = client;
    }

    private StringBuilder imp = new StringBuilder(fileHeader + "\n"
            + "\n"
            + "import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;\n");

    private String clzHeader = "public class %sEntity extends BaseResp.BaseHit { \n";

    private StringBuilder clz = new StringBuilder();

    private StringBuilder gs = new StringBuilder("\n\t// getter and setter \n");


    /**
     * import ****
     *
     * fields ****
     *
     * getter,setter ****
     */
    public void run() throws IOException {
        GetMappingsRequest mappingReq = new GetMappingsRequest();
        mappingReq.indices(this.index);
        GetMappingsResponse mappingResp = client.indices().getMapping(mappingReq, RequestOptions.DEFAULT);
        Map<String, Object> propMap = (Map<String, Object>)mappingResp.mappings().get(this.index)
                .getSourceAsMap().get(docProp);

        String indexName = this.index;
        FieldMap fieldMap = checkHumpField(this.index);
        if (!Objects.isNull(fieldMap)) {
            indexName = fieldMap.getField();
        }
        String clazzHeader = String.format(clzHeader, this.upCaseField(indexName));
        clz.append(clazzHeader);

        Set<Class> typeSet = Sets.newHashSet();
        propMap.entrySet().stream().forEach(field -> {
            String fieldName = field.getKey();
            Map<String, Object> typeMap = (Map<String, Object>) field.getValue();
            String type = (String)typeMap.get("type");
            Class typeClazz = EsMeta.getEsMetaJavaClazz(type);
            if (!typeSet.contains(typeClazz)) {
                this.addImp(typeClazz);
                typeSet.add(typeClazz);
            }
            String filedNameEd = this.addClz(fieldName, typeClazz);
            this.addGs(filedNameEd, typeClazz);
        });
        this.clz.append(this.gs.toString());
        this.clz.append("}");
        this.imp.append(this.clz.toString());
        String clazzFile = this.imp.toString();
        File file = new File(exportPath + "/" + this.upCaseField(indexName) + "Entity.java");
        if (!file.exists()) {
            file.getParentFile().mkdir();
            file.createNewFile();
        }
        FileWriter fileOutput = new FileWriter(file);
        fileOutput.write(clazzFile);
        fileOutput.flush();
        fileOutput.close();
    }

    public void setExportPath(String exportPath) {
        this.exportPath = exportPath;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setClient(RestHighLevelClient client) {
        this.client = client;
    }

    public void setDocProp(String docProp) {
        this.docProp = docProp;
    }

    private FieldMap checkHumpField(String field) {
        String orgField = field;
        if (field.contains(SP)) {
            while (true) {
                int i = -1;
                if ((i = field.indexOf(SP)) < 0) {
                    break;
                }
                if (i >= (field.length() - 1)) {
                    field = field.replace(SP, "");
                    break;
                } else {
                    char c = field.charAt(i + 1);
                    char cl = (char) (c <= 'z' && c >= 'a' ? (c - 32) : c);
                    field = field.replace(SP + c, String.valueOf(cl));
                }
            }
        } else {
            return null;
        }
        String jsonMap = "\t@JsonProperty(value = \"%s\")";
        FieldMap res = new FieldMap(field, String.format(jsonMap, orgField));
        return res;
    }

    private void addImp (Class clazz) {
        imp.append("import " + clazz.getTypeName() + "\n");
    }

    private String addClz (String name, Class type) {
        FieldMap fieldMap = checkHumpField(name);
        String field = name;
        if (Objects.nonNull(fieldMap)) {
            clz.append(fieldMap.getJsonProp() + "\n");
            field = fieldMap.getField();
        }
        String fieldProp = "\tprivate " + type.getSimpleName() + " " + field + "\n";
        clz.append(fieldProp);
        return field;
    }

    private void addGs (String field, Class type) {

        String gm = "\tpublic %s get%s () {\n" +
                    "\t\treturn this.%s" +
                    "\t}\n";

        String sm = "\tpublic void set%s(%s %s) {\n" +
                    "\t\tthis.%s = %s\n" +
                    "\t}\n";

        gm = String.format(gm, type.getSimpleName(), this.upCaseField(field), field);
        sm = String.format(sm, this.upCaseField(field), type.getSimpleName(), field, field, field);
        gs.append(gm).append(sm);
    }

    private String upCaseField(String field) {
        char c = field.charAt(0);
        String h = (char)(c <= 'z' && c >= 'a' ? (c - 32) : c) + "";
        return field.replaceFirst(c + "", h);
    }


    private class FieldMap {

        private String field;

        private String jsonProp;

        public FieldMap() {
        }

        public FieldMap(String field, String jsonProp) {
            this.field = field;
            this.jsonProp = jsonProp;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getJsonProp() {
            return jsonProp;
        }

        public void setJsonProp(String jsonProp) {
            this.jsonProp = jsonProp;
        }
    }

}
