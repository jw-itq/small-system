<template>
  <div>
    <div class="nav-body pr">
      <!-- 侧边导航 -->
      <router-link to="">首页分类</router-link>
      <div class="nav-list-main pa">
          <div class="nav-side">
            <ul>
              <li @mouseenter="showDetail(1)" @mouseleave="hideDetail(1)">
                <span class="nav-side-item">家用电器</span>
              </li>
              <li @mouseenter="showDetail(2)" @mouseleave="hideDetail(2)">
                <span class="nav-side-item">手机</span> /
                <span class="nav-side-item">运营商</span> /
                <span class="nav-side-item">数码</span>
              </li>
              <li @mouseenter="showDetail(1)" @mouseleave="hideDetail(1)">
                <span class="nav-side-item">电脑</span> /
                <span class="nav-side-item">办公</span>
              </li>
              <li @mouseenter="showDetail(2)" @mouseleave="hideDetail(2)">
                <span class="nav-side-item">家居</span> /
                <span class="nav-side-item">家具</span> /
                <span class="nav-side-item">家装</span> /
                <span class="nav-side-item">厨具</span>
              </li>
              <li @mouseenter="showDetail(1)" @mouseleave="hideDetail(1)">
                <span class="nav-side-item">男装</span> /
                <span class="nav-side-item">女装</span> /
                <span class="nav-side-item">童装</span> /
                <span class="nav-side-item">内衣</span>
              </li>
              <li @mouseenter="showDetail(2)" @mouseleave="hideDetail(2)">
                <span class="nav-side-item">美妆个护</span> /
                <span class="nav-side-item">宠物</span>
              </li>
              <li @mouseenter="showDetail(1)" @mouseleave="hideDetail(1)">
                <span class="nav-side-item">女鞋</span> /
                <span class="nav-side-item">箱包</span> /
                <span class="nav-side-item">钟表</span> /
                <span class="nav-side-item">珠宝</span>
              </li>
              <li @mouseenter="showDetail(2)" @mouseleave="hideDetail(2)">
                <span class="nav-side-item">男鞋</span> /
                <span class="nav-side-item">运动</span> /
                <span class="nav-side-item">户外</span>
              </li>
              <li @mouseenter="showDetail(1)" @mouseleave="hideDetail(1)">
                <span class="nav-side-item">汽车</span> /
                <span class="nav-side-item">汽车用品</span>
              </li>
              <li @mouseenter="showDetail(2)" @mouseleave="hideDetail(2)">
                <span class="nav-side-item">母婴</span> /
                <span class="nav-side-item">玩具乐器</span>
              </li>
              <li @mouseenter="showDetail(1)" @mouseleave="hideDetail(1)">
                <span class="nav-side-item">食品</span> /
                <span class="nav-side-item">酒类</span> /
                <span class="nav-side-item">生鲜</span> /
                <span class="nav-side-item">特产</span>
              </li>
              <li @mouseenter="showDetail(2)" @mouseleave="hideDetail(2)">
                <span class="nav-side-item">礼品鲜花</span> /
                <span class="nav-side-item">农资绿植</span>
              </li>
              <li @mouseenter="showDetail(1)" @mouseleave="hideDetail(1)">
                <span class="nav-side-item">医药保健</span> /
                <span class="nav-side-item">计生情趣</span>
              </li>
              <li @mouseenter="showDetail(2)" @mouseleave="hideDetail(2)">
                <span class="nav-side-item">图书</span> /
                <span class="nav-side-item">音像</span> /
                <span class="nav-side-item">电子书</span>
              </li>
            </ul>

          </div>


      </div>
      <!----->

    </div>
    <transition name="fade">
      <div class="detail-item-panel panel-1" :duration="{ enter: 100, leave: 100 }" v-show="panel1" @mouseenter="showDetail(1)" ref="itemPanel1" @mouseleave="hideDetail(1)">
        <div class="nav-detail-item">
          <span v-for="(item, index) in panelData1.navTags" :key="index">{{item}} > </span>
        </div>
        <ul>
          <li v-for="(items, index) in panelData1.classNav" :key="index" class="detail-item-row">
            <span class="detail-item-title">{{items.title}}
              <span class="glyphicon glyphicon-menu-right"></span>
            </span>
            <router-link to="/goodsList" v-for="(item, subIndex) in items.tags" :key="subIndex">
              <span class="detail-item">{{item}}</span>
            </router-link>
          </li>
        </ul>
      </div>
    </transition>
    <transition name="fade">
      <div class="detail-item-panel panel-2" :duration="{ enter: 100, leave: 100 }" v-show="panel2" @mouseenter="showDetail(2)" ref="itemPanel2" @mouseleave="hideDetail(2)">
        <div class="nav-detail-item">
          <span v-for="(item, index) in panelData2.navTags" :key="index">{{item}} > </span>
        </div>
        <ul>
          <li v-for="(items, index) in panelData2.classNav" :key="index" class="detail-item-row">
            <span class="detail-item-title">{{items.title}}
              <span class="glyphicon glyphicon-menu-right"></span>
            </span>
            <router-link to="/goodsList" v-for="(item, subIndex) in items.tags" :key="subIndex">
              <span class="detail-item">{{item}}</span>
            </router-link>
          </li>
        </ul>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'HomeNav',
  data () {
    return {
      panel1: false,
      panel2: false,
      nav: [
        '秒杀',
        '优惠券',
        '闪购',
        '拍卖',
        '服装城',
        '超市',
        '生鲜',
        '全球购',
        '金融'
      ],
      panelData1: {
        navTags: [ '清洁用品', '美妆商城', '美妆馆', '妆比社', '全球购美妆', '宠物馆' ],
        classNav: [
          {
            title: '面部护肤',
            tags: [ '补水保湿', '卸妆', '洁面', '爽肤水', '乳液面霜', '精华', '眼霜', '防晒', '面膜', '剃须', '套装' ]
          },
          {
            title: '洗发护发',
            tags: [ '洗发', '护发', '染发', '造型', '假发', '美发工具', '套装' ]
          },
          {
            title: '身体护理',
            tags: [ '补水保湿', '沐浴', '润肤', '精油', '颈部', '手足', '纤体塑形', '美胸', '套装' ]
          },
          {
            title: '口腔护理',
            tags: [ '牙膏/牙粉', '牙刷/牙线', '漱口水', '套装' ]
          },
          {
            title: '女性护理',
            tags: [ '卫生巾', '卫生护垫', '私密护理', '脱毛膏' ]
          },
          {
            title: '香水彩妆',
            tags: [ 'BB霜', '化妆棉', '女士香水', '男士香水', '底妆', '眉笔', '睫毛膏', '眼线', '眼影', '唇膏/彩' ]
          },
          {
            title: '清洁用品',
            tags: [ '纸品湿巾', '衣物清洁', '清洁工具', '家庭清洁', '一次性用品', '驱虫用品', '皮具护理' ]
          },
          {
            title: '宠物生活',
            tags: [ '水族世界', '狗粮', '猫粮', '猫狗罐头', '狗零食', '猫零食', '医疗保健', '宠物玩具', '宠物服饰' ]
          },
          {
            title: '香水彩妆',
            tags: [ 'BB霜', '化妆棉', '女士香水', '男士香水', '底妆', '眉笔', '睫毛膏', '眼线', '眼影', '唇膏/彩' ]
          },
          {
            title: '清洁用品',
            tags: [ '纸品湿巾', '衣物清洁', '清洁工具', '家庭清洁', '一次性用品', '驱虫用品', '皮具护理' ]
          },
          {
            title: '宠物生活',
            tags: [ '水族世界', '狗粮', '猫粮', '猫狗罐头', '狗零食', '猫零食', '医疗保健', '宠物玩具', '宠物服饰' ]
          }
        ]
      },
      panelData2: {
        navTags: [ '赛事', '运动城', '户外馆', '健身房', '骑行馆', '钟表城' ],
        classNav: [
          {
            title: '2017新品',
            tags: [ '休闲鞋', '商务休闲鞋', '正装鞋', '帆布鞋', '凉鞋', '拖鞋', '功能鞋', '增高鞋', '工装鞋', '雨鞋' ]
          },
          {
            title: '运动鞋包',
            tags: [ '跑步鞋', '休闲鞋', '篮球鞋', '帆布鞋', '板鞋', '拖鞋', '运动包' ]
          },
          {
            title: '健身训练',
            tags: [ '跑步机', '健身车/动感单车', '椭圆机', '综合训练器', '划船机', '甩脂机', '倒立机', '武术搏击' ]
          },
          {
            title: '骑行运动',
            tags: [ '山地车', '公路车', '折叠车', '骑行服', '电动车', '电动滑板车', '城市自行车', '平衡车' ]
          },
          {
            title: '体育用品',
            tags: [ '乒乓球', '羽毛球', '篮球', '足球', '轮滑滑板', '网球', '高尔夫', '台球', '排球' ]
          },
          {
            title: '户外鞋服',
            tags: [ '户外风衣', '徒步鞋', 'T恤', '冲锋衣裤', '速干衣裤', '越野跑鞋', '滑雪服', '羽绒服/棉服', '休闲衣裤' ]
          },
          {
            title: '户外装备',
            tags: [ '背包', '帐篷/垫子', '望远镜', '烧烤用具', '便携桌椅床', '户外配饰', '军迷用品', '野餐用品' ]
          },
          {
            title: '垂钓用品',
            tags: [ '钓竿', '鱼线', '浮漂', '鱼饵', '钓鱼配件', '渔具包', '钓箱钓椅', '鱼线轮', '钓鱼灯' ]
          },
          {
            title: '游泳用品',
            tags: [ '女士泳衣', '比基尼', '男士泳衣', '泳镜', '游泳圈', '游泳包防水包', '泳帽', '游泳配件' ]
          },
          {
            title: '垂钓用品',
            tags: [ '钓竿', '鱼线', '浮漂', '鱼饵', '钓鱼配件', '渔具包', '钓箱钓椅', '鱼线轮', '钓鱼灯' ]
          },
          {
            title: '游泳用品',
            tags: [ '女士泳衣', '比基尼', '男士泳衣', '泳镜', '游泳圈', '游泳包防水包', '泳帽', '游泳配件' ]
          }
        ]
      }
    }
  },
  methods: {
    showDetail (index) {
      index === 1 ? (this.panel1 = true) : (this.panel2 = true)
    },
    hideDetail (index) {
      index === 1 ? (this.panel1 = false) : (this.panel2 = false)
    }
  },
  mounted () {
    this.$refs.itemPanel1.style.left =
      this.$refs.navSide.offsetLeft + this.$refs.navSide.offsetWidth + 'px'
    this.$refs.itemPanel2.style.left =
      this.$refs.navSide.offsetLeft + this.$refs.navSide.offsetWidth + 'px'
    this.$refs.itemPanel1.style.top = this.$refs.navSide.offsetTop + 'px'
    this.$refs.itemPanel2.style.top = this.$refs.navSide.offsetTop + 'px'
  },
  updated () {
    this.$refs.itemPanel1.style.left =
      this.$refs.navSide.offsetLeft + this.$refs.navSide.offsetWidth + 'px'
    this.$refs.itemPanel2.style.left =
      this.$refs.navSide.offsetLeft + this.$refs.navSide.offsetWidth + 'px'
    this.$refs.itemPanel1.style.top = this.$refs.navSide.offsetTop + 'px'
    this.$refs.itemPanel2.style.top = this.$refs.navSide.offsetTop + 'px'
  }
  /* computed: {
    ...mapState(['marketing'])
  },

  //store */

}


</script>

<style lang="scss" rel="stylesheet/scss" scoped>
  @import "../assets/style/mixin";

  /*显示商品详细信息*/
  .detail-item-panel {
    width: 815px;
    height: 485px;
    display: block;
    background-color: #e5e6f5;
    position: absolute;
    border-radius: 4px;
    top: 37px;
    left: 185px;
    z-index: 999;
  }
  .nav-detail-item {
    margin-left: 26px;
    margin-top: 15px;
    margin-bottom: 15px;
    cursor: pointer;
    color: #eee;
  }
  .nav-detail-item span {
    padding: 6px;
    padding-left: 12px;
    margin-left: 15px;
    font-size: 12px;
    background-color: #6e6568;
  }
  .nav-detail-item span:hover {
    margin-left: 15px;
    background-color: #f44336;
  }
  .detail-item-panel ul {
    list-style: none;
  }
  .detail-item-panel li {
    line-height: 38px;
    margin-left: 40px;
  }
  .detail-item-title {
    padding-right: 6px;
    font-weight: bold;
    font-size: 12px;
    cursor: pointer;
    color: #555555;
  }
  .detail-item-title:hover {
    color: #d9534f;
  }
  .detail-item-row a {
    color: #555555;
  }
  .detail-item{
    font-size: 14px;
    padding-left: 12px;
    padding-right: 8px;
    cursor: pointer;
    border-left: 1px solid #ccc;
  }
  .detail-item:hover {
    color: #d9534f;
  }
/*大的导航信息，包含导航，幻灯片等*/
/*.nav-body {
  width: 200px;
  height: 100%;
  padding: 0px;
  color: #fff;
  float: left;
  background-color: #ff1c1c;
  &:hover {
    a:before {
      background-position: -5px 0;
    }
    .nav-side {
      top: 18px;
      visibility: visible;
      opacity: 1;//更改不透明级别，如果是0就是隐藏了
      -webkit-transition: opacity .15s ease-out;
      transition: opacity .15s ease-out;
    }
  }
}*/

  /*----------------------*/
// 用户
.nav-body {
  margin-left: 41px;
  width: 36px;
  &:hover {
    a:before {
      background-position: 0px 153px;
    }

    .nav-side{
      height: 485px;
      width: 168px;
      &:before {
        left: 50%;
      }
    }
    }



  .nav-list-main{
    width: 168px;
    transform: translate(-50%);
    left: 50%;
    top: 18px;
  }

  > a {
    position: relative;
    @include wh(36px, 20px);
    display: block;
    text-indent: -9999px;
    &:before {
      content: " ";
      position: absolute;
      left: 8px;
      top: 0;
      @include wh(20px);
      background: url(/static/images/account-icon@2x.32d87deb02b3d1c3cc5bcff0c26314ac.png) 0px 153px;
      background-size: 240px 107px;
      transition: none;
    }
  }
  li + li {
    text-align: center;
    position: relative;
    border-top: 1px solid #f5f5f5;
    line-height: 40px;
    height: 34px;
    color: #616161;
    font-size: 12px;
    &:hover {
      background: #fafafa;
    }
    a {
      display: block;
      color: #616161;
    }
  }


}

  /*原来的样式*/
.nav-item {
  width: 680px;
  height: 36px;
  margin: 0px auto;
}
.nav-item ul {
  list-style: none;
  margin-left: 15px;
}
.nav-item li {
  float: left;
  font-size: 16px;
  font-weight: bold;

  margin-left: 30px;
}
.nav-item a {
  text-decoration: none;
  color: #555555;
}
.nav-item a:hover {
  color: #d9534f;
}
/*大的导航信息，包含导航，幻灯片等*/
// 用户信息弹出
.nav-list-main {
  position: absolute;
  z-index: 30;
  //padding-top: 16px;
 /* opacity: 1;
  visibility: visible;*/
  //top: -3000px;
  .nav-side {
    position: relative;
    height: 0px;
    overflow: hidden;
   // padding-top: 12px;
    background: #e5e6f5;
    /*border: 1px solid #d6d6d6;
    border-radius: 4px;*/
    z-index: 10;
    transition: all .7s;
   /* &:before {
      position: absolute;
      content: " ";
      background: url(/static/images/account-icon@2x.32d87deb02b3d1c3cc5bcff0c26314ac.png) no-repeat 0px 153px;
      background-size: 260px 107px;
      top: -8px;
      margin-left: -10px;
    }*/
  }
}

/*导航图片*/
.nav-show-img {
  margin-top: 10px;
  float: left;
}
.nav-show-img:nth-child(2) {
  margin-left: 12px;
}
/*显示商品*/
.content {
  width: 100%;
}

</style>
