# 招聘数据分析平台

基于 Spring Boot + Vue 3 的招聘数据分析可视化平台，提供IT行业招聘数据的多维度分析和可视化展示。

## 技术栈

### 后端
| 技术 | 版本 | 描述 |
| --- | --- | --- |
| Spring Boot | 3.2.0 | 后端框架 |
| Java | 21 | 编程语言 |
| MyBatis-Plus | 3.5.6 | ORM框架 |
| MySQL | 8.0+ | 数据库 |
| SpringDoc OpenAPI | 2.3.0 | API文档 |

### 前端
| 技术 | 版本 | 描述 |
| --- | --- | --- |
| Vue | 3.5.13 | 前端框架 |
| Vite | 6.3.5 | 构建工具 |
| Element Plus | 2.14.3 | UI组件库 |
| ECharts | 6.1.0 | 数据可视化 |
| Axios | 1.18.1 | HTTP客户端 |
| Vue Router | 4.6.4 | 路由管理 |

## 项目结构

```
job-analysis/
├── backend/                          # 后端代码
│   ├── src/main/java/com/example/jobanalysis/
│   │   ├── controller/              # REST API控制层
│   │   ├── service/                 # 业务逻辑层
│   │   ├── mapper/                  # 数据访问层
│   │   ├── entity/                  # 数据库实体
│   │   ├── dto/                     # 数据传输对象
│   │   ├── vo/                      # 视图对象
│   │   ├── config/                  # 配置类
│   │   └── exception/               # 全局异常处理
│   ├── src/main/resources/
│   │   ├── application.yml          # 应用配置
│   │   └── mapper/                  # MyBatis XML映射
│   └── pom.xml                      # Maven依赖
├── frontend/                        # 前端代码
│   ├── src/
│   │   ├── views/                   # 页面视图
│   │   ├── components/              # 组件
│   │   ├── api/                     # API接口调用
│   │   ├── router/                  # 路由配置
│   │   ├── utils/                   # 工具函数
│   │   ├── assets/                  # 静态资源
│   │   ├── App.vue                  # 根组件
│   │   └── main.js                  # 入口文件
│   ├── index.html
│   ├── package.json
│   └── vite.config.js               # Vite配置
└── README.md
```

## 功能特性

### 数据可视化Dashboard

| 图表名称 | 描述 | 图表类型 |
| --- | --- | --- |
| 📊 岗位类型分布 | 按产业类型分组统计岗位数量 | 环形图 |
| 🔑 各关键词与薪资关系 | 展示各关键词的最低和最高薪资 | 柱状图 |
| 📍 各地区岗位数统计 | 按行政区分组统计岗位数量 | 柱状图 |
| ⏳ 工作经验要求分布 | 按工作经验年限分组统计 | 饼图 |
| 🔥 热门关键词TOP10 | 展示出现频率最高的关键词 | 条形图 |
| 🏢 企业规模与薪资关系 | 展示不同规模企业的薪资水平 | 柱状图 |
| 💻 IT行业薪资变化趋势 | 按日期统计平均薪资变化 | 折线图 |

### 数据筛选页面

支持按岗位名称、薪资范围、学历要求、工作经验等条件进行数据筛选。

### 岗位推荐页面

基于用户偏好的智能岗位推荐功能。

## 快速开始

### 环境要求

- JDK 21+
- Node.js 18+
- MySQL 8.0+

### 数据库配置

1. 创建数据库：
```sql
CREATE DATABASE job_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 创建数据表：
```sql
CREATE TABLE job_etl (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    job_name     TEXT COMMENT '岗位名称',
    city         TEXT COMMENT '城市',
    district     TEXT COMMENT '行政区',
    salary_min   INT COMMENT '最低薪资(元)',
    salary_max   INT COMMENT '最高薪资(元)',
    work_year    TEXT COMMENT '工作经验要求',
    degree       TEXT COMMENT '学历要求',
    company_size TEXT COMMENT '企业规模',
    company_type TEXT COMMENT '企业类型',
    industry     TEXT COMMENT '产业类型',
    publish_date DATE COMMENT '发布日期',
    keyword      TEXT COMMENT '关键词',
    company_name TEXT COMMENT '公司名称',
    INDEX idx_city (city(50)),
    INDEX idx_industry (industry(50)),
    INDEX idx_publish_date (publish_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招聘数据ETL表';
```

3. 导入数据（如有数据文件）

### 后端启动

```bash
# 进入后端目录
cd job-analysis

# 编译运行
mvn spring-boot:run
```

后端服务启动后访问：
- API文档：http://localhost:8080/swagger-ui.html
- 健康检查：http://localhost:8080/api-docs

### 前端启动

```bash
# 进入前端目录
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端服务启动后访问：http://localhost:5173

### 构建部署

```bash
# 后端打包
mvn clean package
java -jar target/job-analysis-1.0.0.jar

# 前端构建
cd frontend
npm run build
```

## API接口

### 数据分析接口

| 接口路径 | 方法 | 描述 |
| --- | --- | --- |
| `/api/overview` | GET | 获取首页概览数据 |
| `/api/job/distribution` | GET | 岗位类型分布 |
| `/api/district/distribution` | GET | 各地区岗位数统计 |
| `/api/keyword/salary-trend` | GET | 关键词与薪资关系 |
| `/api/work-year/distribution` | GET | 工作经验要求分布 |
| `/api/keyword/top` | GET | 热门关键词TOP10 |
| `/api/company-size/salary` | GET | 企业规模与薪资关系 |
| `/api/it-sector/salary-trend` | GET | IT行业薪资变化趋势 |

### 岗位查询接口

| 接口路径 | 方法 | 描述 |
| --- | --- | --- |
| `/api/job/list` | GET | 岗位列表查询（分页） |
| `/api/job/recommend` | GET | 岗位推荐 |

## 配置说明

### 后端配置 (`application.yml`)

```yaml
server:
  port: 8080  # 服务端口（可选）

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/job_db  # 数据库连接URL（最后的斜杠后改为你存储job_etl数据库名称）
    username: root  # 这里改为你的数据库用户名
    password: 1234  # 这里改为你的数据库密码

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true  # 驼峰映射
```

### 前端配置

前端API请求基础路径配置在 `src/utils/request.js` 中，默认指向 `http://localhost:8080/api`。

## 数据模型

### 核心表：job_etl

| 字段名 | 类型 | 描述 |
| --- | --- | --- |
| id | BIGINT | 主键ID（自增） |
| job_name | TEXT | 岗位名称 |
| city | TEXT | 城市 |
| district | TEXT | 行政区 |
| salary_min | INT | 最低薪资（元） |
| salary_max | INT | 最高薪资（元） |
| work_year | TEXT | 工作经验要求 |
| degree | TEXT | 学历要求 |
| company_size | TEXT | 企业规模 |
| company_type | TEXT | 企业类型 |
| industry | TEXT | 产业类型 |
| publish_date | DATE | 发布日期 |
| keyword | TEXT | 关键词 |
| company_name | TEXT | 公司名称 |

## 开发规范

### 后端

- 采用标准三层架构：Controller → Service → Mapper
- 使用DTO进行数据传输，VO进行响应封装
- 统一异常处理，返回标准化Result对象
- SQL语句使用MyBatis-Plus注解或XML方式

### 前端

- 组件化开发，每个图表独立为一个组件
- API调用统一封装在 `src/api/` 目录
- 使用Element Plus栅格系统进行布局
- ECharts图表配置独立管理

## 许可证

MIT License

## 贡献

欢迎提交Issue和Pull Request。