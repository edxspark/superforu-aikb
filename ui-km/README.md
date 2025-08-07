## Superforu 前端主应用

本项目的技术栈为 vue + ES2015 + TypeScript + Arco Design 和 echarts等

## 运行程序

开发环境:

```bash
yarn install
yarn dev
```

切换国内源：  
yarn config set registry https://registry.npmmirror.com  


访问： [http://localhost:5173](http://localhost:5173) 

## 配置文件
```markdown
├── README.md
├── config
│   ├── plugin  # vite插件
│   ├── vite.config.base.ts  # 基础环境配置
│   ├── vite.config.dev.ts   # 开发环境配置
│   ├── vite.config.prod.ts  # 生产环境配置
└── package.json
```

## 开发约定
```javascript

// 共通参数
localStorage.setItem('spf_app_server_host', import.meta.env.VITE_API_BASE_URL);
localStorage.setItem('spf_client_id', import.meta.env.VITE_APP_CLIENT_ID);
localStorage.setItem('spf_language', localStorage.getItem('arco-locale') || 'zh');
localStorage.setItem('spf_file_editors_host', '/editors.html');
localStorage.setItem("spf_office_template_url",import.meta.env.VITE_AI_APP_URL);

const token = `Bearer ${res.data.access_token}`;
localStorage.setItem('spf_token', token);
localStorage.setItem('spf_user_info', JSON.stringify(spfUserInfo));

```

## 目录结构
``` markdown
├── README.md
├── package.json
├── index.html
├── src
│   ├── api  # 请求接口
│   ├── assets  # 静态资源
│          └── style 全局样式
│   ├── components  # 通用业务组件
│   ├── config  # 全局配置(包含echarts主题)
│          └── settings.json  # 配置文件
│   ├── directives # 指令集（如需，可自行补充）
│   ├── filters # 过滤器（如需，可自行补充）
│   ├── hooks # 全局hooks
│   ├── layout  # 布局
│   ├── locale  # 国际化语言包
│   ├── mock  # 模拟数据
│   ├── views  # 页面模板
│   ├── router # 路由配置
│   ├── store  # 状态管理中心
│   ├── types  # Typescript 类型
│   └── utils  # 工具库
│   └── App.vue  # 视图入口
│   └── main.ts  # 入口文件
└── tsconfig.json
```
## 应用部署
第一步：
```
yarn build
```
2. 复制docker文件夹到服务器
3. 复制dist中的全部文件到docker目录下的html目录中
4. 使用docker-compose命令启动nginx
```
docker-compose up -d
```
5. 访问：http://ip


## 开发文档
Arco Design (React)： [https://arco.design/vue/docs/start](https://arco.design/vue/docs/start)  
Arco 主题定制与使用：https://arco.design/docs/designlab/use-theme-package

