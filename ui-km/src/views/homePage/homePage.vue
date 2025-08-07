<template>
  <div>
    <HomeHeader></HomeHeader>
    <div class="homePageContent">
      <div id="homePage1" class="homePageBanner">
        <div class="homePageBannerLeft">
          <div>
            <div class="title">Superforu AIKB<br />
             </div>
            <div class="sub-title">{{$t('menu.homePage.sub.title')}}&nbsp;</div>
            <br />
            <div class="committedTo">
              {{$t('menu.homePage.sub.title1')}}
              <br />
              {{$t('menu.homePage.sub.title2')}}
              <br />
            </div>
            <div class="experienceNow">
              <a-button
                type="primary"
                class="goWorkspace"
                size="large"
                @click="loginBtn"
              >
                {{$t('menu.homePage.go.now')}}&nbsp;
                <icon-arrow-right />
              </a-button>

              <a-dropdown-button class="tempEditor">
                {{$t('menu.homePage.temp.edit')}}
                <template #icon >
                  <icon-down />
                </template>
                <template #content>
                  <a-doption @click="goTempEditor('markdown')"><icon-file/> {{$t('menu.homePage.function.markdown')}} </a-doption>
                  <a-doption @click="goTempEditor('flowchat')"><icon-share-alt />{{$t('menu.homePage.function.flowchat')}}</a-doption>
                  <a-doption @click="goTempEditor('minder')"><icon-branch/> {{$t('menu.homePage.function.minder')}} </a-doption>
                  <a-doption @click="goTempEditor('whiteboard')"><icon-computer/> {{$t('menu.homePage.function.whiteboard')}} </a-doption>
                </template>
              </a-dropdown-button>

            </div>
          </div>

        </div>
        <div class="homePageBannerRight">
          <img :src="`${sourceHost}/superforu-main-ui/homePage/banners.gif`" class="bannerRightImg" alt="" />
          <img :src="bannerRightBg" class="bannerRightBg" alt="" />
        </div>
      </div>

      <div class="websiteIntroduction" id="websiteIntroduction">
          <a-tabs :size="'large'">
            <a-tab-pane key="1">
              <template #title>
                <div class="presentTab">
                  <div><icon-calendar/> {{$t('menu.homePage.function.ai')}}</div>
                </div>
              </template>
              <a-row>
                <a-col :span="8">
                  <a-typography :style="{ marginTop: '-30px',marginRight:'30px'}">
                    <a-typography-title :heading="3">
                       您的智能伙伴
                    </a-typography-title>
                    <a-typography-paragraph>
                      连接知识文档，如电子书、博文、手册、论文、邮件等，助手将调用AI大模型算法和海量数据来提供准确、及时的答案和解决方案。
                      <a-typography-paragraph :style="{marginTop:'10px'}">
                        <ol>
                          <li>智能问答: 链接在线离线知识文档，精准问答；</li>
                          <li>智能创作: 继写、改写都能胜任，增强你的创作能力；</li>
                          <li>智能作图: 轻松创作极致出图，快速生成高质量图；</li>
                          <li>智能视频: 轻松创作极致短视频，作为也如此简单；</li>
                        </ol>
                      </a-typography-paragraph>
                    </a-typography-paragraph>
                  </a-typography>
                </a-col>
                <a-col :span="16">
                  <div class="presentTabItem">
                      <img :src="`${sourceHost}/superforu-main-ui/homePage/AIGPT.gif`" class="imgItem" alt="" />
                  </div>
                </a-col>
              </a-row>
            </a-tab-pane>
            <a-tab-pane key="2">
              <template #title>
                <div class="presentTab"><icon-clock-circle/> {{$t('menu.homePage.function.roadmap')}}</div>
              </template>
              <a-row>
                <a-col :span="8">
                  <a-typography :style="{ marginTop: '-30px',marginRight:'30px'}">
                    <a-typography-title :heading="3">
                      系统化进行知识管理
                    </a-typography-title>
                    <a-typography-paragraph>
                      知识地图是一种信息可视化工具，帮助用户将知识结构化展示，形成清晰的信息关系和逻辑框架。用户可以通过知识地图快速查找信息、理清思路、进行知识整理和分享，提高学习和工作效率。
                      <a-typography-paragraph :style="{marginTop:'10px'}">
                        <ol>
                          <li>知识梳理: 系统化整理知识点，制定学习工作计划；</li>
                          <li>项目规划: 整体统筹整个项目，高效进行团队协作；</li>
                          <li>知识管理: 轻松系统工程化重构现有知识体系；</li>
                        </ol>
                      </a-typography-paragraph>
                    </a-typography-paragraph>
                  </a-typography>
                </a-col>
                <a-col :span="16">
                  <div class="presentTabItem">
                    <img :src="`${sourceHost}/superforu-main-ui/homePage/km-roadmap.gif`" class="imgItem" alt="" />
                  </div>
                </a-col>
              </a-row>
            </a-tab-pane>
            <a-tab-pane key="3">
              <template #title>
                <div class="presentTab"><icon-user/> {{$t('menu.homePage.function.markdown')}}</div>
              </template>
              <a-row>
                <a-col :span="8">
                  <a-typography :style="{ marginTop: '-30px',marginRight:'30px'}">
                    <a-typography-title :heading="3">
                      您的智能写作伙伴
                    </a-typography-title>
                    <a-typography-paragraph>
                      Markdown是一种轻量级标记语言，用于快速编写格式化文档和网页内容。用户可以使用简单的标记语法，快速生成美观的文档格式，支持文字排版、图片插入、链接等功能，方便用户进行文档编辑和发布。
                      <a-typography-paragraph :style="{marginTop:'10px'}">
                        <ol>
                          <li>文档编写: 轻松编写博客、笔记、公众号、文档等；</li>
                          <li>智能辅助: AI 续写、改写，让写作更专业更轻松；</li>
                          <li>协同分享: 多成员协同编辑，轻松分享到互联网；</li>
                        </ol>
                      </a-typography-paragraph>
                    </a-typography-paragraph>
                  </a-typography>
                </a-col>
                <a-col :span="16">
                  <div class="presentTabItem">
                    <img :src="`${sourceHost}/superforu-main-ui/homePage/markdown.gif`" class="imgItem" alt="" />
                  </div>
                </a-col>
              </a-row>
            </a-tab-pane>
            <a-tab-pane key="4">
              <template #title>
                <div class="presentTab"><icon-user/> {{$t('menu.homePage.function.flowchat')}}</div>
              </template>
              <a-row>
                <a-col :span="8">
                  <a-typography :style="{ marginTop: '-30px',marginRight:'30px'}">
                    <a-typography-title :heading="3">
                      流程可视化工具
                    </a-typography-title>
                    <a-typography-paragraph>
                      流程图是一种图形化表示流程和步骤的工具，帮助用户展示工作流程、业务流程、决策流程等信息。用户可以通过流程图清晰地展示流程步骤、关系和逻辑，帮助团队理解和执行流程。
                      <a-typography-paragraph :style="{marginTop:'10px'}">
                        <ol>
                          <li>工作流程和任务分配: 可以使用流程图制定工作流程、任务分配，提高项目管理效率；</li>
                          <li>流程分析和优化设计: 用户可以利用流程图分析流程瓶颈、优化流程设计，提升流程效率；</li>
                        </ol>
                      </a-typography-paragraph>
                    </a-typography-paragraph>
                  </a-typography>
                </a-col>
                <a-col :span="16">
                  <div class="presentTabItem">
                    <img :src="`${sourceHost}/superforu-main-ui/homePage/flowchat.gif`" class="imgItem" alt="" />
                  </div>
                </a-col>
              </a-row>
            </a-tab-pane>
            <a-tab-pane key="5">
              <template #title>
                <div class="presentTab"><icon-user/> {{$t('menu.homePage.function.minder')}}</div>
              </template>
              <a-row>
                <a-col :span="8">
                  <a-typography :style="{ marginTop: '-30px',marginRight:'30px'}">
                    <a-typography-title :heading="3">
                      提升您的思维
                    </a-typography-title>
                    <a-typography-paragraph>
                      思维导图是一种图形化展示思维结构和关系的工具，帮助用户整理思绪、梳理思路、构建思维框架。用户可以通过思维导图进行头脑风暴、知识整理、项目规划等操作，提高思维清晰度和创造力。
                      <a-typography-paragraph :style="{marginTop:'10px'}">
                        <ol>
                          <li>内容整理和计划制定: 可以在会议中使用思维导图整理会议内容、制定计划，提高会议效率；</li>
                          <li>知识整理和思路梳理: 可以在学习中使用思维导图整理知识点、梳理思路，提高学习效果；</li>
                        </ol>
                      </a-typography-paragraph>
                    </a-typography-paragraph>
                  </a-typography>
                </a-col>
                <a-col :span="16">
                  <div class="presentTabItem">
                    <img :src="`${sourceHost}/superforu-main-ui/homePage/minder.gif`" class="imgItem" alt="" />
                  </div>
                </a-col>
              </a-row>
            </a-tab-pane>
            <a-tab-pane key="6">
              <template #title>
                <div class="presentTab"><icon-user/> {{$t('menu.homePage.function.whiteboard')}}</div>
              </template>
              <a-row>
                <a-col :span="8">
                  <a-typography :style="{ marginTop: '-30px',marginRight:'30px'}">
                    <a-typography-title :heading="3">
                      实时研讨协作工具
                    </a-typography-title>
                    <a-typography-paragraph>
                      在线白板是一种实时协作工具，用户可以在白板上进行文字、图形、标注等操作，支持多人同时协作编辑。用户可以通过在线白板进行会议记录、团队合作、教学演示等操作，实现远程协作和信息共享。
                      <a-typography-paragraph :style="{marginTop:'10px'}">
                        <ol>
                          <li>头脑风暴和项目规划: 可以在团队协作中使用在线白板进行头脑风暴、项目规划，促进团队合作；</li>
                          <li>教学演示和学生互动: 可以在教学中使用在线白板进行教学演示、学生互动，提高教学效果。；</li>
                        </ol>
                      </a-typography-paragraph>
                    </a-typography-paragraph>
                  </a-typography>
                </a-col>
                <a-col :span="16">
                  <div class="presentTabItem">
                    <img :src="`${sourceHost}/superforu-main-ui/homePage/whiteboard.gif`" class="imgItem" alt="" />
                  </div>
                </a-col>
              </a-row>
            </a-tab-pane>
            <a-tab-pane key="7">
              <template #title>
                <icon-user/> {{$t('menu.homePage.function.office')}}
              </template>
              <a-row>
                <a-col :span="8">
                  <a-typography :style="{ marginTop: '-30px',marginRight:'30px'}">
                    <a-typography-title :heading="3">
                      在线办公套件
                    </a-typography-title>
                    <a-typography-paragraph>
                      在线Office产品是一款集成了文档处理、表格编辑、演示制作等功能的在线办公套件，用户可以通过浏览器随时随地访问并编辑各种办公文档。
                      <a-typography-paragraph :style="{marginTop:'10px'}">
                        <ol>
                          <li>文档处理: 可以使用在线Office产品编辑和格式化各类文档，包括报告、信函、简历等，实现文档的快速创建和编辑；</li>
                          <li>表格编辑: 可以利用在线Office产品创建和编辑各种表格，包括数据整理、统计分析、项目计划等，提高数据处理效率；</li>
                          <li>演示制作: 用户可以通过在线Office产品制作演示文稿，包括幻灯片设计、图表插入、动画效果等，实现专业的演示效果；</li>
                        </ol>
                      </a-typography-paragraph>
                    </a-typography-paragraph>
                  </a-typography>
                </a-col>
                <a-col :span="16">
                  <div class="presentTabItem">
                    <img :src="`${sourceHost}/superforu-main-ui/homePage/office.png`" class="imgItem" alt="" />
                  </div>
                </a-col>
              </a-row>
            </a-tab-pane>
          </a-tabs>
      </div>
      <a-divider class="half-divider" />
      <div>
        <img class="yellowLine" :src="yellowLine" alt="" />
      </div>
      <div class="custom" id="custom">

        <div class="customer">
          <a-typography :style="{ marginTop: '-40px' }">
            <a-typography-title :heading="2">
              {{$t('menu.homePage.custom')}}
            </a-typography-title>
            <a-typography-paragraph>
              <div :style="{color:'var(--color-text-2)',fontSize:'18px'}">{{$t('menu.homePage.custom.extend')}}</div>
            </a-typography-paragraph>
          </a-typography>

        </div>
        <div class="customContent">
          <img
            v-for="(item, index) in homePage.customList"
            :key="index"
            :src="item"
            class="customListImg"
            alt=""
          />
        </div>
      </div>

      <div class="goWorkspaceBottom">
        <a-button
          type="primary"
          class="goWorkspace"
          size="large"
          @click="loginBtn"
        >
          {{$t('menu.homePage.go.now')}}&nbsp;
          <icon-arrow-right />
        </a-button>
      </div>
      <!--      <div class="possibleIssues">-->
      <!--        <div class="possibleIssuesTitle">您可能有很多问题问我们...</div>-->
      <!--        <div class="soOurKnow">因此，我们列出认为您可能想知道的事情，如果还有更多问题请到官方渠道给我们留言！</div>-->
      <!--        <div class="possibleIssuesContent">-->
      <!--          <p class="possibleIssuesList" v-for="(item,index) in homePage.possibleIssuesList" :key="index">-->
      <!--            <img :src="item.img" alt=""  class="possibleIssuesListImg">-->
      <!--            <span class="possibleIssuesListText">{{ item.text }}</span>-->
      <!--          </p>-->
      <!--        </div>-->
      <!--      </div>-->
    </div>
    <a-divider />
    <HomeFooter></HomeFooter>
  </div>
</template>

<script lang="ts">
  import { defineComponent, getCurrentInstance, reactive } from 'vue';
  import HomeFooter from '@/components/homePage/footer.vue';
  import HomeHeader from '@/components/homePage/header.vue';
  import ellaLiu from '@/assets/homePage/ellaLiu.png';
  import yellowLine from '@/assets/homePage/yellowLine.png';
  import bannerRightBg from '@/assets/homePage/bannerRightBg.png';
  import { useRouter } from 'vue-router';

  export default defineComponent({
    name: '',
    // 注册你的组件
    components: { HomeFooter, HomeHeader },
    props: {},

    // 定义一个组件的 emitted 事件，当在 emits 选项中定义了原生事件 (如 click) 时，将使用组件中的事件替代原生事件侦听器。
    emits: {
      click: (evt: MouseEvent) => evt instanceof MouseEvent,
    },
    setup(props, { attrs, slots, emit, expose }) {
      const { proxy } = getCurrentInstance() as any; // 全局变量代理，用于使用 app.config.globalProperties.xxx 挂载的参数，也可以不用断言用`.?`
      const router = useRouter();

      // 主题（根据当前时间改变主题：黑夜：19:00～6:00，白天：6:00~19:00）
      const now = new Date();
      const hours = now.getHours();
      if(hours>25 && hours<25){
        localStorage.setItem("arco-theme","light");
        localStorage.setItem("spf_theme","light");
      }else{
        localStorage.setItem("arco-theme","dark");
        localStorage.setItem("spf_theme","dark");
      }


      // 临时编辑器
      const goTempEditor = (editor: string) => {
        if(editor === "markdown"){
          window.open("/aieditor/index.html?isTempEdit=true");
        }else if(editor === "flowchat"){
          window.open("/flowchat/index.html?isTempEdit=true");
        }else if(editor === "minder"){
          window.open("/minder/index.html?isTempEdit=true");
        }else if(editor === "whiteboard"){
          window.open("/whiteboard/build/index.html?isTempEdit=true");
        }
      }
      const sourceHost = import.meta.env.VITE_SOURCE_HOST;
      const loginBtn = () => {
        router.push({
          name: 'login',
          query: {},
        });
      };

      const registerBtn = () => {
        router.push({
          name: 'register',
          query: {},
        });
      };

      const homePage = reactive({
        knowledgeCreationImg: '/assets/homePage/knowledgeCreationImg.svg',
        knowledgeRightImg: '/assets/homePage/knowledgeRightImg.svg',
        multiDatabaseManagementLeftTop:
          '/assets/homePage/multiDatabaseManagementLeftTop.svg',
        multiDatabaseManagementRight:
          '/assets/homePage/multiDatabaseManagementRight.svg',
        multiDatabaseManagementLeftBottom: [
          {
            img: '/assets/homePage/multiDatabaseManagementLeftBottom1.svg',
            text: '在线文档编辑管理',
          },
          {
            img: '/assets/homePage/multiDatabaseManagementLeftBottom2.svg',
            text: '本地文档快速上传',
          },
          {
            img: '/assets/homePage/multiDatabaseManagementLeftBottom3.svg',
            text: '知识库协同分享',
          },
        ],
        knowledgeApplicationLeft:
          '/assets/homePage/knowledgeApplicationLeft.svg',
        knowledgeApplicationRightTop:
          '/assets/homePage/knowledgeApplicationRightTop.svg',
        knowledgeApplicationRightBottom: [
          {
            img: '/assets/homePage/knowledgeApplicationRightBottom1.svg',
            text: 'AI 多模型多模态赋能',
          },
          {
            img: '/assets/homePage/knowledgeApplicationRightBottom2.svg',
            text: 'AIGC 内容创造',
          },
          {
            img: '/assets/homePage/knowledgeApplicationRightBottom3.svg',
            text: '自定义创建知识AI',
          },
        ],
        knowledgeShreLeft: '/assets/homePage/knowledgeShreLeftTop.svg',
        knowledgeShreRight: '/assets/homePage/knowledgeShreRight.svg',
        knowledgeShreRightBottom: [
          {
            img: '/assets/homePage/knowledgeShreLeftBottom1.svg',
            text: 'AI知识星球',
          },
          {
            img: '/assets/homePage/knowledgeShreLeftBottom2.svg',
            text: 'AI知识库模型权限控制',
          },
          {
            img: '/assets/homePage/knowledgeShreLeftBottom3.svg',
            text: 'AI知识库模型共建',
          },
        ],
        knowledgeSearchLeft: '/assets/homePage/knowledgeSearchLeft.svg',
        missionList: [
          '连接知识，AI赋能开启知识的潜能，将知识转化为生产力，创造价值',
        ],
        KnowledgeBgContent: '/assets/homePage/KnowledgeBgContent.svg',
        KnowledgeBgContentImg: '/assets/homePage/KnowledgeBgContentImg.svg',
        customList: [
          '/assets/homePage/Customer-SG.jpeg',
          '/assets/homePage/Customer-Skhynix.jpeg',
          '/assets/homePage/Customer-Shnormal.jpeg',
          '/assets/homePage/Customer-Szairport.jpeg',
        ],
        possibleIssuesList: [
          {
            img: '/assets/homePage/caretRight.svg',
            text: '为什么做AI知识管理',
          },
          {
            img: '/assets/homePage/caretRight.svg',
            text: '与其他有什么区别',
          },
          {
            img: '/assets/homePage/caretRight.svg',
            text: '平台有什么价值',
          },
          {
            img: '/assets/homePage/caretRight.svg',
            text: '我有资源是否如何合作',
          },
        ],
      });
      return {
        homePage,
        yellowLine,
        loginBtn,
        sourceHost,
        registerBtn,
        goTempEditor,
        ellaLiu,
        bannerRightBg,
        proxy,
        attrs, // Attribute (非响应式对象，等同于 attrs)，有状态，会随组件本身的更新而更新
        slots,
        emit, // 触发事件 (方法，等同于 emit)
        expose, // 暴露公共 property (函数)
      };
    },

    methods: {},
  });
</script>

<style lang="less" scoped>
  .homePageContent {
    padding: 40px 0px;
    width: 100%;
    background-repeat: no-repeat;
  }
  .homePageBanner {
    padding-left: 100px;
    display: flex;
    justify-content: space-between;

  }
  .title {
    color: var(--color-text-1);
    font-weight: 500;
    font-size: 48px;
    line-height: 56px;
  }

  .sub-title {
    color: var(--color-text-2);
    font-weight: 400;
    font-size: 32px;
    line-height: 56px;
  }


  .homePageBannerLeft {
    padding-top:50px;
    align-items: center;
    justify-content: flex-start;
    display: flex;
  }
  .committedTo {
    font-size: 16px;
    line-height: 33px;
    color: var(--color-text-2);
    margin: 15px 0px 30px;
  }
  .tempEditor{
      padding-left: 20px;
    vertical-align: bottom;
  }
  .experienceNow {
    margin-top: 50px;
    height: 100px;
    .goWorkspace {
      height: 50px;
      width: 200px;
      font-size: 16px;
      font-weight: 700;
    }
    .register {
      height: 50px;
      width: 100px;
      margin-left: 20px;
      font-weight: 700;
    }

    .download {
       margin-left: 40px;
       vertical-align:bottom;
    }
  }
  .ellaLiu {
    position: relative;
    top: 4px;
    left: -46px;
    .ellaLiuImg {
      width: 156px;
    }
  }


  .presentTabItem{
    position: relative;
    width: 680px;
    top:10px;
    .imgItem{
      border-radius: 20px;
      padding:5px;
      width: 670px;
      height: 380px;
    }
  }

  .homePageBannerRight {
    position: relative;
    top: 40px;
    width: 680px;
    .bannerRightImg {
      background:linear-gradient(115deg,#4fcf70,#fad648,#a767e5,#12bcfe,#44ce7b);
      border-radius: 20px;
      padding:5px;
      width: 670px;
      height: 380px;
    }
    .bannerRightBg {
      position: absolute;
      right: 0px;
      width: 498px;
      top: 400px;
      z-index: 1;
    }
  }
  .websiteIntroduction {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    padding: 0 20px;
    position: relative;
    max-width: 1205px;
    min-width: 1000px;
    z-index: 11;
    margin: 100px auto 0px;
    .presentTab{
      font-size: 16px;
    }
    .introductionContent {
      width: 32%;
      max-width: 453px;
      margin-bottom: 15px;
      .introductionContentBg {
        width: 100%;
        height: 205px;
        background: url('@/assets/homePage/introductionContentBg.png');
        background-size: 100% 100%;
        text-align: center;
        .introductionContentImg {
          height: 205px;
        }
      }
      .title {
        font-size: 18px;
        font-weight: bold;
        color: rgb(var(--primary-6));;
        line-height: 22px;
        margin: 20px 0px;
      }
      .text {
        font-size: 16px;
        height: 100px;
        line-height: 30px;
        color: var(--color-text-2);
      }
    }
  }
  .yellowLine {
    height: 107px;
    margin: 40px 0px;
  }
  .knowledge {
    max-width: 1305px;
    min-width: 1100px;
    z-index: 11;
    margin: 0px auto 30px;
    display: flex;
    justify-content: space-between;
    .knowledgeContent {
      width: 406px;
      .knowledgeWarehouse {
        margin-top: 10px;
        font-size: 16px;
        line-height: 20px;
        color: var(--color-text-2);
      }
      .knowledgeCreation {
        color: var(--color-text-2);
        font-size: 36px;
        height: 93px;
        line-height: 93px;
      }
      .knowledgeCreationImg {
        height: 222.5px;
        // width: 300px;
        margin: 3px 0px 15px;
      }
      .intelligentAssistance {
        font-size: 20px;
        line-height: 32.5px;
        color: var(--color-text-2);
        margin: 10px 0px;
      }
      .atOnce {
        width: 144.53px;
        height: 56px;
        background-color: #248046;
        padding: 16px, 42.53px, 16px, 43px;
        border-radius: 28px;
        color: var(--color-text-2);
        line-height: 56px;
        font-size: 14px;
        text-align: center;
      }
    }
    .knowledgeRight {
      width: 780px;
      .knowledgeRightImg {
        height: 280px;
        padding: 5px;
      }

      .onlineDocment {
        color: var(--color-text-2);
        font-size: 19px;
        line-height: 25px;
        height: 25px;
        margin: 15px 0px 40px;
      }
      .knowledgeImgText {
        display: flex;
        justify-content: space-between;
        align-content: center;
        .knowledgeImgTextContent {
          width: 249px;
          height: 215px;
        }
        .knowledgeImgTextItem {
          font-size: 18px;
          color: var(--color-text-2);
          margin-top: 20px;
          line-height: 23px;
        }
      }
    }
  }
  .multiDatabaseManagement {
    padding: 30px 0px 0px;
    .websiteIntroduction {
      margin: 0px;
      min-width: 779px;
      padding: 0px;
      border-bottom: none;
    }
    .introductionContent {
      width: 370px;
      margin: 0px;
      padding: 0px;
      .introductionContentBg {
        width: 370px;
        height: 150px;
        .introductionContentImg {
          height: 150px;
        }
      }
      .text {
        height: 40px;
      }
    }
  }
  .multiDatabaseManagementCont {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 5px auto;
    max-width: 1250px;
    min-width: 1000px;
    // height: 620px;
    padding: 20px 0px 40px;
    .multiDatabaseManagementLeft {
      width: 779px;
      .multiDatabaseManagementLeftTop {
        width: 778px;
      }
      .multiDatabaseManagementLeftText {
        font-size: 20px;
        margin-top: 15px;
        line-height: 25px;
        height: 25px;
        color: var(--color-text-2);
        margin-bottom: 30px;
      }
      .multiDatabaseManagementLeftBottomImg {
        width: 249px;
        height: 150px;
        margin-bottom: 10px;
      }
      .multiDatabaseManagementLeftBottomText {
        font-size: 18px;
        line-height: 25px;
        height: 25px;
        color: var(--color-text-2);
      }
      .multiDatabaseManagementLeftBottom {
        display: flex;
        justify-content: space-between;
      }
    }
    .multiDatabaseManagementRight {
      width: 406px;
      .multiDatabaseManagementRightTitle {
        font-size: 16px;
        height: 20px;
        line-height: 20px;
        color: var(--color-text-2);
      }
      .multiDatabaseManagementRightManag {
        font-size: 36px;
        font-weight: 800;
        line-height: 58px;
        height: 58px;
        letter-spacing: 0em;
        text-align: left;
        color: var(--color-text-2);
        margin-bottom: 5px;
      }
      .multiDatabaseManagementRightText {
        margin-top: 15px;
        font-size: 18px;
        font-weight: 400;
        line-height: 33px;
        letter-spacing: 0em;
        text-align: left;
        height: 85px;
        color: var(--color-text-2);
      }
      .multiDatabaseManagementRightBtn {
        width: 144.53px;
        height: 56px;
        line-height: 56px;
        text-align: center;
        padding: 16px, 42.53px, 16px, 43px;
        border-radius: 28px;
        color: var(--color-text-2);
        background-color: #248046;
      }
    }
  }
  .multiDatabaseManagementCont1 {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin: 5px auto;
    max-width: 1250px;
    min-width: 1000px;
    padding: 20px 0px 40px;
  }
  .paddingTop0 {
    padding-top: 0px;
  }
  .paddingTop10 {
    padding-top: 10px;
  }
  .paddingTop20 {
    padding-top: 20px;
  }
  .paddingBottom20 {
    padding-bottom: 20px;
  }
  .marginTop20 {
    margin-top: 20px;
  }
  .missionManage {
    margin: 60px auto 0px;
    max-width: 1260px;
    display: flex;
    justify-content: space-between;
    .mission {
      width: 200px;
      height: 58px;
      font-size: 40px;
      font-weight: 700;
      line-height: 58px;
      color: var(--color-text-2);
    }
    .missionValue {
      flex: 1;
      margin-left: 60px;
      .missionListContent {
        width: 500px;
        height: 152px;
        padding-top: 15px;
      }
      .missionList {
        font-size: 16px;
        font-weight: 400;
        line-height: 33px;
        letter-spacing: 0em;
        text-align: left;
        color: var(--color-text-2);
      }
    }
    .improvingKnowledge {
      font-size: 36px;
      font-weight: 800;
      line-height: 42px;
      letter-spacing: 0em;
      text-align: left;
      color: var(--color-text-2);
    }
  }
  .KnowledgeBgContent {
    width: 100%;
    position: relative;
    text-align: center;
  }
  .KnowledgeBgImg {
    position: absolute;
    top: -140px;
    left: 0;
  }
  .KnowledgeBgContentImg {
    width: 1180px;
    height: 716px;
    position: relative;
    top: -160px;
  }
  .KnowledgeBgContentBottom {
    position: relative;
    top: -75px;
  }
  .custom {
    margin-top: 30px;
    padding-bottom: 40px;
  }
  .customer {
    font-size: 40px;
    font-weight: 700;
    line-height: 58px;
    height: 58px;
    letter-spacing: 0em;
    text-align: center;
    color: var(--color-text-2);
  }
  .customContent {
    margin: 0 auto 100px;
    margin-top: 60px;
    width: 1115px;
    display: flex;
    justify-content: space-between;
    .customListImg {
      width: 266px;
      height: 147px;
    }
  }

  .goWorkspaceBottom {
    text-align: center;
    padding-bottom: 50px;
    .goWorkspace {
      height: 50px;
      width: 200px;
    }
  }

  .possibleIssues {
    max-width: 1300px;
    width: 100%;
    margin: 0 auto 60px;
    padding: 0px 30px;
    .possibleIssuesTitle {
      font-size: 36px;
      font-weight: 700;
      line-height: 58px;
      height: 58px;
      letter-spacing: 0em;
      text-align: left;
      padding-left: 20px;
      margin-bottom: 5px;
      color: var(--color-text-2);
    }
    .soOurKnow {
      font-size: 16px;
      font-weight: 400;
      line-height: 30px;
      height: 30px;
      letter-spacing: 0em;
      text-align: left;
      margin-bottom: 15px;
      padding-left: 20px;
      color: var(--color-text-2);
    }
    .possibleIssuesContent {
      border: 1px solid #949ba44d;
      border-radius: 4px;
      .possibleIssuesList {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        height: 66.5px;
        padding-left: 40px;
        color: var(--color-text-2);
        margin: 0;
        .possibleIssuesListText {
          font-size: 18px;
          font-weight: 400;
          letter-spacing: 0em;
          text-align: left;
          margin-left: 10px;
        }
      }
    }
  }
</style>
