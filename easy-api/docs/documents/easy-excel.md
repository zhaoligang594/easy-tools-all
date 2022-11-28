##  一、简单使用EXCEL操作组件

### 1.1 背景

>本组件是一个专门处理EXCEL的组件。支持下载EXCEL以及生成EXCEL文件。

### 1.2 核心功能

* java对象转成EXCEL文档或者直接下载EXCEL文档。
* 解析EXCEL文件或者解析上传的EXCEL文件到java对象，方便与用户的使用。

## 二、如何使用

### 2.1 引入组件坐标到pom.xml

```xml
    <dependency>
        <groupId>vip.breakpoint</groupId>
        <artifactId>easy-excel</artifactId>
        <version>XXXXX</version>
    </dependency>
```

!> 建议使用最新的版本！！

### 2.2 定义操作的java对象

?> 下面的代码定义了一个简单的java的POJO对象，在属性上使用了注解`@ExcelField`，表明这个字段需要解析成EXCEL的字段。`@ExcelField`注解中`name`属性表明导出字段的名字。

```java
/**
 * @author : breakpoint
 * create on 2022/08/27
 * 欢迎关注公众号 《代码废柴》
 */
// 注意访问的权限  public 可以访问其他的情况有可能 获取不到数据
public class Data {

    @ExcelField(name = "名字")
    private String name;
    @ExcelField(name = "年龄")
    private Integer age;

    @ExcelField(name = "日期")
    private Date date;
    @ExcelField(name = "其他信息", selectValues = {"AA", "BB", "CC"})
    private String message;

    public Data() {
    }

    public Data(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", date=" + date +
                '}';
    }
}
```

!> 在 `@ExcelField` 注解中，还有很多的配置，可以定义日期的格式，下拉选框的值以及排序等等。具体的定义如下：

```java
/**
 * Excel 的工具注解
 *
 * @author breakpoint/赵先生
 * 2020/10/28
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

    // 和 属性 的 汉语意思
    String name();

    // 日期的类型
    String datePattern() default "yyyy-MM-dd HH:mm:ss";

    // 下拉列表的操作里面的 可以填写的 值
    String[] selectValues() default {};

    //  order
    int order() default 0;

    // 是否导出该列
    boolean isExplored() default true;

    // 模版是否包含这一列
    boolean isMouldColumn() default true;
}
```

### 2.3 生成EXCEL文件以及解析EXCEL文件

* 生成EXCEL文件

```java
    private final String dir = "C:\\work\\idea_work\\";

    @Test
    public void test01() throws Exception {
        List<Data> list = new ArrayList<>();
        list.add(new Data("lisi", 30));
        list.add(new Data("wangwu", 26));
        HSSFWorkbook hssfWorkbook = HSSFWorkbookUtils.getHSSFWorkbook("sheet1", list, false);
        FileOutputStream out = new FileOutputStream(dir + "2022.08.27" + ".xls");
        hssfWorkbook.write(out);
        out.close();
        System.out.println("==okk==");
    }
```

* 解析EXCEL文件

```java
    private final String dir = "C:\\work\\idea_work\\";
    @Test
    public void test02() throws Exception {
        FileInputStream inputStream = new FileInputStream(dir + "2022.08.27" + ".xls");
        List<Data> dataList = ParseExcelInputStreamUtils.getDataList(inputStream, Data.class);
        System.out.println(dataList);
        System.out.println("==okk==");
    }
```

### 2.4 下载EXCEL文件

```java
    @GetMapping("testDownload")
    public void downExcel(HttpServletResponse response) throws Exception {
        List<Data> list = new ArrayList<>();
        list.add(new Data("lisi", 30));
        list.add(new Data("wangwu", 26));
        ExcelDownloadUtils.downLoadExcelByCurrentData(response, list, "测试", "sheet1");
        System.out.println("==okk==");
    }
```

## 三、联系方式

🐘

<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="pic/image-20220516083922821.png" width="100px;" alt="thanhtoan1196"/>
      </a>
      <br />
      <span>微信</span>
    </td>
    <td align="center">
      <a href="#">
        <img src="pic/README/image-20221124084524936.png" width="100px;" alt="memset0"/>
      </a>
      <br />
      <span>微信公众号</span>
    </td>
  </tr>
</table>

!> 以上就是 `easy-excel` 组件的全部功能，由于作者水平有限，肯定会存在需要歧义的地方，如果你有任何的疑问，都可以联系本作者。同时也欢迎关注《代码废柴》公众号。

**{docsify-updated}** 

