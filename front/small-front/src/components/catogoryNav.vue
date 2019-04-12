<template>
    <div class="header_nav_subnav1">
      <!-- daohang end -->
      <div id="Z_TypeList" class="Z_TypeList">

        <h1 class="title"><a href=""></a></h1>
        <div class="Z_MenuList">
          <ul class="Z_MenuList_ul">
            <li class="list-item alt" v-for="(itm, index) in panelData2" :key="index">
              <!--<router-link to="/goods"><a @click="changePage()">全部商品</a></router-link>-->
              <a class="nav-side-item" @click="linkToSearch(item)" v-for="(item, index) in itm" :key="index">{{ item +" /"}}</a>
            </li>
          </ul>
        </div>

        <div class="Z_SubList">

          <div class="subView" v-for="(itm, index) in panelData1" :key="index">
            <!--<div class="nav-detail-item">
              <span v-for="(item, index) in itm.navTags" :key="index">{{item}} > </span>
            </div>-->
            <ul>
              <li v-for="(items, index) in itm.classNav" :key="index" class="detail-item-row">
                  <span class="detail-item-title" @click="linkToSearch(item)">{{items.title}}
                    <!--<span class="glyphicon glyphicon-menu-right"></span>-->
                  </span>

                <span v-for="(item, subIndex) in items.tags" :key="subIndex">
                  <span class="detail-item" @click="linkToSearch(item)">{{item}}</span>
                </span>
              </li>
            </ul>
          </div>



      </div>
    </div>

    </div>
</template>

<script>
  // 变美项目下拉
  import { catList, catMainList } from '/api/index'
  export default {
    name: 'catogoryNav',
    data () {
      return {
        panelData1: [],
        panelData2: []
      }
    },
    methods: {
      _getCatList () {
        catList().then(res => {
          this.panelData1 = res.result
        })
      },
      _getCatMainList () {
        catMainList().then(res => {
          this.panelData2 = res.result
        })
      },

      /*
      * this.$router.push({
            path: '/refreshsearch',
            query: {
              key: this.input
            }
          })
      * */
      linkToSearch (item) {
        this.$router.push({
          path: '/search',
          query: {
            key: item.toString()
          }
        })
      }
    },
    mounted () {
      this._getCatList()
      this._getCatMainList()
    }

  }

  $(function () {
    var bBtn = false
    var bW = false
    var navW = 0
    $('#Z_TypeList').hover(function () {
        $('.Z_MenuList').queue(function (next) {
          $(this).css({
            'display': 'block',
            'overflow': 'hidden'
          })
          next()
        }).animate({
            'height': '+=435px'
          },
          400,
          function () {
            $('ul.Z_MenuList_ul>li').each(function () {

              $(this).hover(function () {

                  $(this).queue(function (next) {

                    var ins = $(this).index()
                    $(this).addClass('menuItemColor')

                    $('.subView').css({
                      'width': 0,
                      'display': 'none'
                    })

                    function toNavW () {
                      if (!bBtn) {
                        if (parseInt($('header').width()) >= 1190) {
                          bW = true
                        } else {
                          bW = false
                        }

                        navW = bW ? 775 : 565

                        $('.Z_SubList').addClass('box-shadow')
                        $('.Z_SubList').stop().css({
                          'display': 'block'
                        }).animate({
                          'width': navW
                        })
                        $('.subView').eq(ins).stop().css({
                          'display': 'block'

                        }).animate({
                            'width': navW
                          },
                          function () {
                            bBtn = true
                          })
                      } else {
                        $('.subView').eq(ins).stop().css({
                          'display': 'block'
                        }).animate({
                            'width': navW
                          },
                          0)
                      }
                    }
                    toNavW()
                    $(document).bind('ready', toNavW)
                    $(window).bind('resize', function () {
                      $(document).unbind('ready', toNavW)
                      $(document).bind('ready', toNavW)
                    })
                    next()
                  })

                  // $(this).find('h3,p a').css('color', '#fff');
                },
                function () {
                  $(this).removeClass('menuItemColor')
                })

            });

          });

      },
      function () {
        $(this).queue(function (next) {
          bBtn = false
          $(this).find('.Z_MenuList').stop().css({
            'height': 0,
            'display': 'none'
          })
          $('.subView').css({
            'width': 0,
            'display': 'none'
          })
          $('.Z_SubList').removeClass('box-shadow')
          $('.Z_SubList').css({
            'width': 0,
            'display': 'none'
          })
          $('ul.Z_MenuList_ul>li').each(function () {
            $(this).removeClass('menuItemColor');
            // $(this).find('h3').css('color', '#000');
            // $(this).find('p a').css('color', '#888');
          })

          next()

        });

      });

  });

</script>

<style lang="scss" rel="stylesheet/scss" scoped>
  @import "../assets/style/mixin";
  ul {
    list-style: none
  }

  .gl {
    float: left
  }

  .gr {
    float: right
  }

  .gclear {
    clear: both;
    font-size: 0;
    overflow: hidden;
    height: 0;
  }

  article, aside, details, figcaption, figure, footer, header, hgroup, nav, section {
    display: block;
  }

  audio, canvas, video {
    display: inline-block;
    *display: inline;
    *zoom: 1;
  }

  audio:not([controls]) {
    display: none;
  }
  [hidden] {
    display: none;
  }

  body {
    color: black;
    font: 12px/1.5 "微软雅黑", arial, \5b8b\4f53
  }

  a {
    text-decoration: none;
    color: black;
  }

  a:visited {
    /*color:black*/
  }

  a:hover,a:active {
    color: #e4007f;
    text-decoration: none;
    outline: 0;
  }

  .relative {
    position: relative
  }

  .header_nav {
    height: 0px;
    margin: 110px auto 0;
    width: 1000px;
  }

  .header_nav_subnav1 {
    /*width: 197px;
    position: absolute;
    height: 45px;*/
  }

  .Z_TypeList {
    position: absolute;
    left: 100px;
    top: -10px;
    z-index: 99;
    height: 45px;

  }

  .Z_TypeList .title a {
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

  .Z_TypeList .title a:hover {
    /*background: url(../img/i_05.png) right center #2a0015 no-repeat*/
    //background: url(/static/images/account-icon@2x.32d87deb02b3d1c3cc5bcff0c26314ac.png) 0px 153px;
  }

  .Z_TypeList .Z_MenuList {
    width: 200px;
    line-height: 26px;
    position: absolute;
    left: -60px;
    top: 34px;
    height: 0;
    display: none;
    z-index: 999;
    background-color: #e5e6f5;
    border-radius: 8px;
  }

  .Z_TypeList .Z_MenuList ul li {
    display: block;
    position: relative;
    padding-left: 10px;
    background: #ffffff;
    border-bottom: 1px solid #ffffff;
    padding-top: 7px
  }

  .Z_TypeList .Z_MenuList ul li.alt {
    background: #f0f0f0;
  }

  .Z_TypeList .Z_MenuList ul li p {
    padding: 0 5px 2px 0px;
    display: block;
    line-height: 2;
  }

  .Z_TypeList .Z_MenuList ul li p a {
    color: #414141;
    padding: 0px 4px 0 0;
    white-space: nowrap;
  }

  .Z_TypeList .Z_MenuList h3 {
    padding-left: 22px;
    font-size: 14px;
    font-weight: 100;
    display: block
  }

  /* Z_SubList*/
  .Z_SubList {
    position: absolute;
    height: 435px;
    z-index: 9;
    align-items: center;
    left: 140px;
    top: 34px;
    line-height: 31px;
    overflow: hidden;
    width: 0;
    background-color: #e5e6f5;
    display: none;
    border-radius: 14px;
  }

  .Z_SubList .subView {
    position: absolute;
    top: 0;
    left: 0;
    overflow: hidden;
    display: none;
    float: left;
    background-color: #e5e6f5;
    width: 600px;
    border-radius: 14px;
  }

  /*------------------*/

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
  /*---------------------*/


  .Z_SubList_ul {
    width: 530px;
    margin: 0 0 15px 10px
  }

  .Z_SubList .subItem {
    width: 540px;
    height: 420px;
    position: relative
  }

  .nav50 {
    float: left;
    width: 50%
  }

  .subItemimg1,.subItemimg2,.subItemimg3,.subItemimg4 {
    position: absolute;
    bottom: 0;
    font-size: 0px;
    line-height: normal;
    font-size: 0;
  }

  .subItemimg1 {
    right: 0
  }

  .subItemimg2 {
    left: -10px
  }

  .subItemimg3 {
    left: 2%
  }

  .subItemimg4 {
    left: 1%
  }

  #subItemh4 h4 {
    display: inline-block;
    margin: 10px 15px 0 22px;
    white-space: nowrap;
    width: 60px;
  }

  #subItemh4 h4 a {
    font-size: 12px;
    color: #414141;
    padding: 0 2px
  }

  #subItemh4 h4 a:hover {
    background: #DFDFDF;
  }

  .Z_SubList .subItem-hd {
    height: 34px;
    line-height: 34px;
    border-bottom: 2px solid #a6937c;
    font-size: 15px;
    font-family: "寰蒋闆呴粦";
    margin-bottom: 6px;
    display: none;
  }

  .Z_SubList .subItem-hd a {
    color: #000;
  }

  .Z_SubList .subItem-title {
    font-weight: normal;
    font-size: 14px;
    padding-left: 5px;
    display: block;
    margin-top: 8px;
  }

  .Z_SubList .subItem-title a {
    color: #d9004b;
  }

  .Z_SubList .subItem-cat {
    margin-left: 10px;
  }

  .Z_SubList .subItem-cat a {
    padding: 2px;
    color: #414141;
    margin: 0 2px;
    font-family: \5b8b\4f53;
    white-space: nowrap;
    overflow: hidden
  }

  .Z_SubList .subItem-cat .navgd {
    border-radius: 3px;
    border: 1px solid #CCC;
    padding: 2px 5px;
  }

  .Z_SubList .subItem-cat a:hover {
    background: #dfdfdf;
    text-decoration: none;
  }

  .Z_TypeList .Z_MenuList ul li.menuItemColor {
    background-color: #ccc;
  }

  .Z_TypeList .Z_MenuList ul li a:hover {
    color: #e4007f
  }

  .Z_TypeList dl {
    width: 212px;
    float: right;
    position: relative;
    top: -430px;
  }

  .Z_TypeList dl dt {
    font-size: 14px;
    color: #414141;
  }

  .Z_TypeList dl dd {
    margin-top: 5px;
  }

  .Z_TypeList dl.dlbanner_zdy {
    top: -438px
  }

</style>
