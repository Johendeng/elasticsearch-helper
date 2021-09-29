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
这样的代码可维护性极差，并且经过多方的查找也并没有发现满足我心中希望的es-template框架；因为es的查询语句是使用
json来描述查询行为，这与JavaBean莫名的契合；因此我希望能通过@Annotation + JavaBean 的方式来对es的查询
行为进行描述，于是开发了该框架，希望能通过该框架，减少es开发的代码量，增加代码的可维护性。
```
### 结构：
```


```
### 使用方法：
```


```
### 依赖
```


```
