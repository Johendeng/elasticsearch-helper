## ElasticSearch-Helper 
##### -- ElasticSearch HighLevelRestClient 封装框架

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


```
### 依赖
```


```
