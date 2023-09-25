## ElasticSearch-Helper 
    --- ElasticSearch HighLevelRestClient 封装框架 

### 背景：
```
 为了简化elasticsearch的api，使用对象对查询行为进行描述，简化业务代码。
 有多简单？ 请求参数上面加注解，然后完了·····  
 要多简单就有多简单，并且保留了es各种查询需要调整的参数设置，毕竟es不是关系型数据库，
 不是简单的查出来就完事了，还有文档关联性，评分等等高级的特性，我们最大限度的隐藏了
 es的java-high-level-client查询语法，让你只关注查询条件本身，让业务回归业务，
 其余的交给框架；
 同时我们引入了 类似mybatis-plus的 lambda查询定义，无缝切换，如果你想把es当
 关系型数据库使用，当然也可以；
 来啊··  玩起来
 
```
### 快速开始：  
以 account 索引为例子， 所以结构在 sample 包里面可以看到哟：
查询 字段 city (类型为 keyword) 为 Nogal 的数据

    @EsMapper
    public interface EsAccountMapper extends EsBaseMapper<AccountEntity> {
        
        	List<AccountEntity> queryByParam(SimpleAccountQueryParam param);
    }

方式一： 

    @EsAnnQueryIndex(index = "account", model = QueryModel.BOOL, traceScore = true, minScore = 0.0001f)
    public class SimpleAccountQueryParam {
        
        @Term
        private String city;
    }

然后嘛···  正常调用即可

方式二：
EsAccountMapper新增方法：
    
    @EsAnnQueryIndex(index = "account")
    List<AccountEntity> queryByParam(@Term String city);

然后嘛···  正常调用即可

方式三：

    private EsAccountMapper accountMapper;

    public List<AccountEntity> getAccountByCity(String city) {
        return accountMapper.selectList(Wrappers.lambdaQuery(AccountEntity.class)
            .term(AccountEntity:getCity, city));
    }

更加详细的使用，查看wiki；

有问题欢迎提 issue

```
