## ElasticSearch-Helper 
    --- ElasticSearch HighLevelRestClient 封装框架 

`使用示例：https://gitee.com/JohenTeng/elasticsearch-helper-sample.git`

### 背景：
```
 为了简化elasticsearch的api，使用对象对查询行为进行描述，简化业务代码。
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

=》@EsCondition 条件筛选注解
当属性不被 @EsCondition注解 修饰时，默认当 属性值不为空时 就会在Es查询语句中构建该属性的查询条件，

可以使用 @EsCondition 注解来自定义筛选条件；
你需要 实现 EsConditionHandler 接口中的boolean test(R val) 方法来判断值是否被采纳；

    public class UserAgeConditionHandler implements EsConditionHandler<Integer> {
    
        @Overider
        public boolean test(Integer age) {
            ... 你的条件判断逻辑 ...
        }
    }

同时使用@EsCondition注解修饰属性，例如 @EsCondition(UserAgeConditionHandler.class)

```

### 依赖
```
v1.0.0-bate.1

spring-boot-starter:

    <dependency>
        <groupId>io.github.johendeng</groupId>
        <artifactId>elasticsearch-helper-boot-starter</artifactId>
        <version>1.0.0-RELEASE</version>
    </dependency>

core:
    <dependency>
        <groupId>io.github.johendeng</groupId>
        <artifactId>elasticsearch-helper-core</artifactId>
        <version>1.0.0-RELEASE</version>
    </dependency>
```
