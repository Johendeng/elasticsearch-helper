## ElasticSearch-Helper 
    --- ElasticSearch HighLevelRestClient 封装框架 

`使用示例：https://gitee.com/JohenTeng/elasticsearch-helper-sample.git`

### 背景：
```
  我在进行基于ElasticSearch作为系统一个数据源开发功能时，遇到了一个问题，产品设计了一个丰富的查询过滤模组，
我们需要判断用户是否输入了查询参数，并针对大量的过滤条件开发代码，于是系统代码中出现了大量的类型如下的代码：
```

    BoolQueryBulder bool = QueryBuilders.bool();
    if(StringUtils.isNotBlank(param1)) {
        QueryBuilder field1Query = QueryBuilders.term("field1", param1);
        bool.must(field1Query);
    }
    if(StringUtils.isNotBlank(param2)) {
        QueryBuilder field2Query = QueryBuilders.term("field2", param2);
        bool.must(field2Query);
    }
    if(StringUtils.isNotBlank(param2)) {
        QueryBuilder field2Query = QueryBuilders.term("field2", param2);
        bool.must(field2Query);
    } ... 此处省略 n 行代码 ...

```
这样的代码可维护性极差，并且经过多方的查找也并没有发现满足我心中希望的es框架；因为es的查询语句是使用
json来描述查询行为，这与JavaBean莫名的契合；因此我希望能通过@Annotation + JavaBean 的方式来对es的查询
行为进行描述，于是开发了该框架，希望能通过该框架，减少 60% 的es相关代码，并且让数据源相关的代码与业务代码结偶，
增加系统的可维护性
```
### 结构：
![Image text](https://gitee.com/JohenTeng/elasticsearch-helper/raw/develop/.doc/image/es-helper.png)
```
EsQueryEngine: 接受查询对象，生成es查询对象，该对象需要使用 @EsQueryIndex 描述对象，
               使用 org/pippi/elasticsearch/helper/core/beans/annotation/query/module 下的注解描述属性：
               @Match,@MultiMatch,@Term ... ... 等
RequestHook: 扩展接口，实现Request描述的扩展
ResponseHook: 扩展接口，自定义解读 es 的 SearchResponse

```

### 使用方法：
```
主方法中：
@EnableEsHelper // 启用EsHelper框架 
@SpringBootApplication(scanBasePackages = "com.sun")
public class SampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}

```
```
在 spring-boot配置中定义：

.properties 配置：
## 配置日志是否打印 elasticsearch的查询语句
es.helper.queryLogOut.enable = true 
## 配置自定义处理类所在位置：
es.helper.ext.handle-packages = *** 

.yml 配置：
es:
  helper:
    queryLogOut:
      enable: true
    ext:
      handle-packages: com.***.***.handles1,com.***.***.handles2 
```
```
查询对象：

@EsQueryIndex(index = "test", model = QueryModel.BOOL)
// 是否启用高亮
@HighLight(fields = {"title", "describe"}) 
public class DemoSearchParam {
    // 范围查询
    @Range(value = @Base, tag = Range.LE_GE)
    private RangeParam intensity;
    
    @MultiMatch(value = @Base,fields = {"title", "describe"})
    private String title;
}
返回对象：
// 默认的查询结果处理结果类需要 继承 BaseResp.BaseHit
public class Content extends BaseResp.BaseHit {

    private int intensity;

    private String title;
}

被代理接口：
@EsHelperProxy
public interface TestQueryService {
    BaseResp<Content> queryRecordByIntensity(ContentSearchParam param)
}

调用：
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleApplication.class)
public class TestQueryBeanServiceTest {

    @Resource
    private TestQueryService testQueryService;

    @Test
    public void testQueryService(){
        ContentSearchParam param = new ContentSearchParam();
        RangeParam rangeParam = new RangeParam();
        rangeParam.setLeft(12);
        rangeParam.setRight(15);
        param.setIntensity(rangeParam);
        param.setTitle("测试测试");
        BaseResp<Content> baseResp = testQueryService.queryRecordByIntensity(param);
        System.out.println(SerializerUtils.parseObjToJson(baseResp));
    }

}

```
##### 扩展类，配置类，方法说明：
```
=> 包：org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend下定义的类：
MoreLikeThisParam：@MoreLikeThis修饰的类属性需要定义为该类型;
PageParam：@PageAndOrder修饰的类属性需要定义为该类型;
RangeParam：@Range修饰的类属性需要定义为该类型;

=> 关于钩子方法：
包：org.pippi.elasticsearch.helper.core.hook
作用：我们无法定义所有的查询场景，因此需要使用钩子方法来自定义查询类以及结果处理方法
接口：
@FunctionalInterface RequestHook#handleRequest
@FunctionalInterface ResponseHook#handleResponse

我们可以通过让查询对象 继承 HookQuery抽象类,实现其中的set方法,如：

public class DemoParam extends HookQuery {
    
    @Match(value = @Base)
    private String Field;
    
    @Override
    public void setRequestHook() {
        super.requestHook = (h, p) -> h;
    }
    @Override
    public void setResponseHook() {
        super.responseHook = resp -> null;
    }
}

或者 在代码中定义公共的钩子方法，如：
public interface MySearchHooks extends UserHooks {
    RequestHook reqHook1 = (h, p) -> h;
    ResponseHook respHook1 = resp -> null;
}
使用：
@EsHelperProxy
public interface TestQueryService {
    // 查询在处理了 查询类中的注解定义之后会执行 RequestHook 钩子
    // 得到Es返回原始对象后 会使用 结果处理钩子，返回钩子定义的对象
    @UseRequestHook("reqHook1")
    @UseResponseHook("respHook1")
    BaseResp<Content> queryRecordByIntensity(ContentSearchParam param);
}

=> 关于高亮：
高亮在同一个系统中定义应该是类似的；
我们可以在配置Bean中调用全局高亮配置类，定义不同的高亮配置

class AutoConfiguration {
    
    void config {
    // 定义
        GlobalEsQueryConfig.configHighLight(DEFAULT_KEY ,() -> SearchSourceBuilder.highlight());
        GlobalEsQueryConfig.configHighLight("html" ,() ->
                SearchSourceBuilder.highlight().fragmentSize(10).numOfFragments(5)
        );
    }
}

可以使用 @HighLight(fields = {"title", "describe"}, highLightKey = "html")
中的 highLightKey 来定义使用的HightLight配置

```

### 依赖
```


```
