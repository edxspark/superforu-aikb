<template>
  <div class="">
    <HomeHeader></HomeHeader>
    <div class="homePageContent">
      <div id="homePage1" class="homePageBanner">
        <div class="homePageBannerLeft">
          <div>
            <div class="linkAny"
              >链接知识，<br />
              AI赋能提高生产力！</div
            >
            <div class="committedTo">
              致力于个人｜团队｜企业，进行知识创造、
              <br />
              知识管理、知识运用、知识分享、知识搜索！
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
            </div>
          </div>
          <div class="ellaLiu"
            ><img :src="ellaLiu" class="ellaLiuImg" alt=""
          /></div>
        </div>
        <div class="homePageBannerRight">
          <img :src="bannerRightImg" class="bannerRightImg" alt="" />
          <img :src="bannerRightBg" class="bannerRightBg" alt="" />
        </div>
      </div>
      <div class="websiteIntroduction">
        <div
          v-for="(item, index) in homePage.introduction"
          :key="index"
          class="introductionContent"
          id="homePage2"
        >
          <div class="introductionContentBg">
            <img
              :src="item.introductionContentImg"
              class="introductionContentImg"
              alt=""
            />
          </div>
          <p class="title">{{ item.title }}</p>
          <p class="text">{{ item.text }}</p>
        </div>
      </div>
      <a-divider class="half-divider" />
      <div>
        <img class="yellowLine" :src="yellowLine" alt="" />
      </div>

      <div class="knowledge" id="homePage3">
        <div class="knowledgeContent">
          <div class="knowledgeWarehouse">知识库</div>
          <div class="knowledgeCreation">知识创造</div>
          <img
            :src="homePage.knowledgeCreationImg"
            class="knowledgeCreationImg"
            alt=""
          />
          <div class="intelligentAssistance"
            >提供全球最优秀的组件和管理模式，支持 AI 智能辅助！</div
          >
          <a-button
            type="primary"
            shape="round"
            size="large"
            status="success"
            @click="loginBtn"
            >{{$t('menu.homePage.go.now')}}&nbsp; <icon-arrow-right
          /></a-button>
        </div>
        <div class="knowledgeRight">
          <img :src="homePage.knowledgeRightImg" alt="" />
          <div class="onlineDocment"
            >在线文档所见即所得，支持markdown、数据库、todo-list、画板、AI智能辅助等</div
          >
          <div class="knowledgeImgText">
            <div
              v-for="(item, index) in homePage.knowledgeImgTextContent"
              :key="index"
              class="knowledgeImgTextContent"
            >
              <img :src="item.img" alt="" />
              <div class="knowledgeImgTextItem">{{ item.text }}</div>
            </div>
          </div>
        </div>
      </div>
      <a-divider />
      <div  class="multiDatabaseManagement">
        <div class="multiDatabaseManagementCont">
          <div class="multiDatabaseManagementLeft">
            <img
              :src="homePage.multiDatabaseManagementLeftTop"
              class="multiDatabaseManagementLeftTop"
              alt=""
            />
            <div class="multiDatabaseManagementLeftText"
              >知识库多库分类管理</div
            >
            <div class="multiDatabaseManagementLeftBottom">
              <div
                v-for="(
                  item, index
                ) in homePage.multiDatabaseManagementLeftBottom"
                :key="index"
                class="multiDatabaseManagementLeftItem"
              >
                <img
                  :src="item.img"
                  class="multiDatabaseManagementLeftBottomImg"
                  alt=""
                />
                <div class="multiDatabaseManagementLeftBottomText">{{
                  item.text
                }}</div>
              </div>
            </div>
          </div>
          <div class="multiDatabaseManagementRight">
            <div class="multiDatabaseManagementRightTitle">知识库</div>
            <div class="multiDatabaseManagementRightManag">知识管理</div>
            <img :src="homePage.multiDatabaseManagementRight" alt="" />
            <div class="multiDatabaseManagementRightText"
              >提供全球最优秀的组件和管理模式，增加 AI 辅助！</div
            >
            <a-button
              type="primary"
              shape="round"
              size="large"
              status="success"
              @click="loginBtn"
              >{{$t('menu.homePage.go.now')}}&nbsp; <icon-arrow-right
            /></a-button>
          </div>
        </div>
      </div>
      <a-divider />
      <div class="multiDatabaseManagement paddingTop0">
        <div class="multiDatabaseManagementCont paddingTop10">
          <div class="multiDatabaseManagementRight">
            <div class="multiDatabaseManagementRightTitle">知识AI</div>
            <div class="multiDatabaseManagementRightManag">知识运用</div>
            <img :src="homePage.knowledgeApplicationLeft" alt="" />
            <div class="multiDatabaseManagementRightText"
              >集成多个本地大模型，关联在线笔记训练自己的知识AI！</div
            >
            <a-button
              type="primary"
              shape="round"
              size="large"
              status="success"
              @click="loginBtn"
              >{{$t('menu.homePage.go.now')}}&nbsp; <icon-arrow-right
            /></a-button>
          </div>
          <div class="multiDatabaseManagementLeft">
            <img
              :src="homePage.knowledgeApplicationRightTop"
              class="multiDatabaseManagementLeftTop"
              alt=""
            />
            <div class="multiDatabaseManagementLeftText"
              >支持AI临时文档、永久AI知识库构建</div
            >
            <div
              class="multiDatabaseManagementLeftBottom paddingTop10 paddingBottom10"
            >
              <div
                v-for="(
                  item, index
                ) in homePage.knowledgeApplicationRightBottom"
                :key="index"
                class="multiDatabaseManagementLeftItem"
              >
                <img
                  :src="item.img"
                  class="multiDatabaseManagementLeftBottomImg"
                  alt=""
                />
                <div
                  class="multiDatabaseManagementLeftBottomText paddingTop10"
                  >{{ item.text }}</div
                >
              </div>
            </div>
          </div>
        </div>
      </div>
      <a-divider />
      <div class="multiDatabaseManagement">
        <div class="multiDatabaseManagementCont paddingTop0">
          <div class="multiDatabaseManagementLeft">
            <img
              :src="homePage.knowledgeShreLeft"
              class="multiDatabaseManagementLeftTop"
              alt=""
            />
            <div class="multiDatabaseManagementLeftText"
              >创造自己的知识AI，进行知识分享！</div
            >
            <div class="multiDatabaseManagementLeftBottom">
              <div
                v-for="(item, index) in homePage.knowledgeShreRightBottom"
                :key="index"
                class="multiDatabaseManagementLeftItem paddingBottom20"
              >
                <img
                  :src="item.img"
                  class="multiDatabaseManagementLeftBottomImg"
                  alt=""
                />
                <div class="multiDatabaseManagementLeftBottomText">{{
                  item.text
                }}</div>
              </div>
            </div>
          </div>
          <div class="multiDatabaseManagementRight">
            <div class="multiDatabaseManagementRightTitle">知识AI</div>
            <div class="multiDatabaseManagementRightManag">知识分享</div>
            <img :src="homePage.knowledgeShreRight" alt="" />
            <div class="multiDatabaseManagementRightText"
              >有效地将丰富的知识资源总结、沉淀,并传承！</div
            >
            <a-button
              type="primary"
              shape="round"
              size="large"
              status="success"
              @click="loginBtn"
              >{{$t('menu.homePage.go.now')}}&nbsp; <icon-arrow-right
            /></a-button>
          </div>
        </div>
      </div>
      <a-divider />
      <div class="multiDatabaseManagement paddingTop0">
        <div class="multiDatabaseManagementCont1 multiDatabaseManagementCont">
          <div class="multiDatabaseManagementRight">
            <div class="multiDatabaseManagementRightTitle marginTop20"
              >知识AI</div
            >
            <div class="multiDatabaseManagementRightManag marginTop20"
              >知识搜索</div
            >
            <img
              :src="homePage.knowledgeSearchLeft"
              class="paddingTop20"
              alt=""
            />
            <div class="multiDatabaseManagementRightText paddingTop20"
              >AI引路，智慧导航，精准搜索你所需！</div
            >
            <a-button
              type="primary"
              shape="round"
              size="large"
              status="success"
              @click="loginBtn"
              >{{$t('menu.homePage.go.now')}}&nbsp; <icon-arrow-right
            /></a-button>
          </div>
          <div class="multiDatabaseManagementLeft websiteIntroduction">
            <div
              v-for="(item, index) in homePage.knowledgeSearchRight"
              :key="index"
              class="introductionContent"
            >
              <div class="introductionContentBg">
                <img :src="item.img" class="introductionContentImg" alt="" />
              </div>
              <p class="text">{{ item.text }}</p>
            </div>
          </div>
        </div>
      </div>
      <a-divider />
      <div  class="missionManage">
        <div class="mission" >使命</div>
        <div class="missionValue">
          <div class="improvingKnowledge">重新激活沉睡的知识，提高知识生产力！</div>
          <div class="missionListContent" >
            <div
              v-for="(item, index) in homePage.missionList"
              :key="index"
              class="missionList"
              >{{ item }}</div>
          </div>
        </div>
      </div>
      <div id="homePage4"></div>
      <div class="KnowledgeBgContent" >
        <img :src="homePage.KnowledgeBgContent" class="KnowledgeBgImg" alt="" />
        <img
          :src="homePage.KnowledgeBgContentImg"
          class="KnowledgeBgContentImg"
          alt=""
        />
        <div class="KnowledgeBgContentBottom"></div>
      </div>
      <div class="custom">
        <div class="customer">客户群体</div>
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
  import experienceNow from '@/assets/homePage/experienceNow.png';
  import ellaLiu from '@/assets/homePage/ellaLiu.png';
  import yellowLine from '@/assets/homePage/yellowLine.png';
  import bannerRightBg from '@/assets/homePage/bannerRightBg.png';
  import bannerRightImg from '@/assets/homePage/bannerRightImg.png';
  import introductionContent1 from '@/assets/homePage/introductionContent1.png';
  import introductionContent2 from '@/assets/homePage/introductionContentImg2.png';
  import introductionContent3 from '@/assets/homePage/introductionContentImg3.png';
  import introductionContent4 from '@/assets/homePage/introductionContentImg4.png';
  import introductionContent5 from '@/assets/homePage/introductionContentImg5.png';
  import introductionContent6 from '@/assets/homePage/introductionContentImg6.png';
  import { useRouter } from 'vue-router';
  // import { useI18n } from 'vue-i18n';
  // import useLoading from '@/hooks/loading';
  // import { useUserStore } from '@/store';

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
        introduction: [
          {
            introductionContentImg: introductionContent1,
            title: '个人知识管理场景',
            text: '构建你的个人笔记和知识管理体系，绘制思维导图、在线文档写作、画流程图、在线office等',
          },
          {
            introductionContentImg: introductionContent2,
            title: '团队企业知识管理场景',
            text: '团队知识库建设、项目文档，开发手册、技术手册、接口文档、在线写作等。',
          },
          {
            introductionContentImg: introductionContent3,
            title: '学校图书馆知识管理场景',
            text: '海量电子书进行AI训练，提供知识分析、知识搜索、知识分享等。 ',
          },
          {
            introductionContentImg: introductionContent4,
            title: '物资知识管理场景',
            text: '采购物资法律法规、招投标合同规范分析、合同识别等，实现资源最优配置与协同发展。',
          },
          {
            introductionContentImg: introductionContent5,
            title: '客服中心/FAQ/在线问答',
            text: '客服问答、FAQ、产品手册、帮助说明、在线问答、技术支持、客户手册、24小时客服API接入。',
          },
          {
            introductionContentImg: introductionContent6,
            title: '营销市场知识管理场景',
            text: '高质量博文、图片、PPT等素材生成，提高生产效率与质量，智能驱动引领营销创新。',
          },
        ],
        knowledgeCreationImg: '/assets/homePage/knowledgeCreationImg.svg',
        knowledgeRightImg: '/assets/homePage/knowledgeRightImg.svg',
        knowledgeImgTextContent: [
          {
            img: '/assets/homePage/knowledgeImgText1.svg',
            text: '流程图全面梳理业务流程',
          },
          {
            img: '/assets/homePage/knowledgeImgText3.svg',
            text: '思维导图改善人类的发散思维',
          },
          {
            img: '/assets/homePage/knowledgeImgText2.svg',
            text: '在线office多人在线协同',
          },
        ],
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
        knowledgeSearchRight: [
          {
            img: '/assets/homePage/knowledgeSearchRight1.svg',
            text: 'AI知识库知识沉淀',
          },
          {
            img: '/assets/homePage/knowledgeSearchRight2.svg',
            text: 'AI知识库知识分类',
          },
          {
            img: '/assets/homePage/knowledgeSearchRight3.svg',
            text: 'AI知识库知识识别',
          },
          {
            img: '/assets/homePage/knowledgeSearchRight4.svg',
            text: 'AI知识库知识精准检索输出',
          },
        ],
        missionList: [
          '连接知识，AI赋能开启知识的潜能，将知识转化为生产力，创造价值',
        ],
        KnowledgeBgContent: '/assets/homePage/KnowledgeBgContent.svg',
        KnowledgeBgContentImg: '/assets/homePage/KnowledgeBgContentImg.svg',
        customList: [
          '/assets/homePage/customList1.svg',
          '/assets/homePage/customList2.svg',
          '/assets/homePage/customList3.svg',
          '/assets/homePage/customList4.svg',
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
        registerBtn,
        experienceNow,
        ellaLiu,
        bannerRightBg,
        bannerRightImg,
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
    background-color: var(--color-bg-1);
    background-image: url('/assets/homePage/section-bg1.svg');
    background-repeat: no-repeat;
  }
  .homePageBanner {
    padding-left: 100px;
    display: flex;
    justify-content: space-between;
  }
  .linkAny {
    color: rgb(var(--primary-6));
    font-weight: 500;
    font-size: 48px;
    line-height: 56px;
  }
  .homePageBannerLeft {
    flex: 1;
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
  .homePageBannerRight {
    position: relative;
    width: 480px;
    .bannerRightImg {
      width: 480px;
      height: 330px;
    }
    .bannerRightBg {
      position: absolute;
      right: 0px;
      width: 498px;
      top: 350px;
      z-index: 1;
    }
  }
  .websiteIntroduction {
    margin-top: 100px;
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    padding: 0 20px;
    position: relative;
    max-width: 1205px;
    min-width: 1000px;
    z-index: 11;
    margin: 100px auto 0px;
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
    margin-top: 100px;
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
    margin-top: 40px;
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
