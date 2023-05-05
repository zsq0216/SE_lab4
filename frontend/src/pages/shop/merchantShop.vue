<template>
  <q-layout view="hHh lpR fFf">

    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />

        <q-toolbar-title>

          <q-avatar>
            <img src="https://cdn.quasar.dev/logo-v2/svg/logo-mono-white.svg">
          </q-avatar>
          {{ shopName }}
        </q-toolbar-title>
        <q-btn flat round dense icon="update" class="q-mr-xs" @click="update"></q-btn>

      </q-toolbar>
    </q-header>

    <q-drawer v-model="leftDrawerOpen" :width="280">
      <q-scroll-area class="fit">
        <q-list padding class="text-grey-8" style="margin-top: 3%;">
          <q-item class="GNL__drawer-item" v-ripple v-for="link in links1" :key="link.text" clickable
            @click="handleClick(link)" style="margin-top: 3%;">
            <q-item-section avatar>
              <q-icon :name="link.icon" />
            </q-item-section>
            <q-item-section>
              <q-item-label>{{ link.text }}</q-item-label>
            </q-item-section>
          </q-item>

          <q-separator inset class="q-my-sm" style="margin-top: 4%;" />

          <q-item class="GNL__drawer-item" v-ripple v-for="link in links2" :key="link.text" clickable
            @click="handleClick(link)" style="margin-top: 3%;">
            <q-item-section avatar>
              <q-icon :name="link.icon" />
            </q-item-section>
            <q-item-section>
              <q-item-label>{{ link.text }}</q-item-label>
            </q-item-section>
          </q-item>

          <q-item class="GNL__drawer-item" :to="isMerchant ? '/merchantpage' : '/user'" exact style="margin-top: 3%;">
            <q-item-section avatar>
              <q-icon name="font_download" />
            </q-item-section>

            <q-item-section>
              个人主页
            </q-item-section>
          </q-item>

          <!-- 待写：帮助页面 -->
          <q-item class="GNL__drawer-item" to="/help" exact style="margin-top: 3%;">
            <q-item-section avatar>
              <q-icon name="open_in_new" />
            </q-item-section>

            <!-- TODO: 完善帮助页面 -->
            <q-item-section>
              帮助
            </q-item-section>
          </q-item>

          <q-item class="GNL__drawer-item" to="/contact" exact style="margin-top: 3%;">
            <q-item-section avatar>
              <q-icon name="send" />
            </q-item-section>

            <!-- TODO: 完善联系页面 -->
            <q-item-section>
              联系我们
            </q-item-section>
          </q-item>

          <q-separator inset class="q-my-sm" style="margin-top: 3%;" />

          <div class="q-mt-md" style="margin-top: 6%;">
            <!-- TODO: 完善说明页面，并添加链接 -->
            <div class="flex flex-center q-gutter-xs">
              <a class="GNL__drawer-footer-link" href="javascript:void(0)" aria-label="Privacy">Privacy</a>
              <span> · </span>
              <a class="GNL__drawer-footer-link" href="javascript:void(0)" aria-label="Terms">Terms</a>
            </div>
            <div class="logoutLink flex flex-center q-gutter-xs" style="margin-top: 3%;">
              <a class="GNL__drawer-footer-link" href='/#/login' style="font-weight: bold;">Logout</a>

            </div>
          </div>
        </q-list>
      </q-scroll-area>
    </q-drawer>

    <q-page-container>
      <router-view />
      <div class="q-pa-md row items-start q-gutter-md" style="width: 100%;">

        <div class="shopInfoDisplay" style="width: 30%; margin-top: 3%;">
          <q-card class="my-card" flat bordered>
            <q-card-section>
              <div class="text-overline text-orange-9">注册时间：{{ shopRegistrationTime }}</div>
              <div class="text-h5 q-mt-sm q-mb-xs">店铺名称：{{ shopName }}</div>
              <div class="text-overline text-grey "><q-icon name="place" />
                {{ shopAddress }}</div>
              <div class="text-caption text-grey">简介：{{ shopIntroduce }}</div>
            </q-card-section>

            <q-card-actions>
              <!-- <div class="cursor-pointer" style="width: 100px; margin-left: 10%;">
              充值
              <q-popup-edit v-model="label" auto-save>
                <q-input v-model="money" hint="请输入充值金额" dense autofocus counter @keyup.enter="topUpAccount" />
              </q-popup-edit>
            </div> -->
              <q-btn flat color="secondary" label="下拉查看流水" style="margin-left: 0.5%;" />

              <q-space />

              <q-btn color="grey" round flat dense :icon="transferExpanded ? 'keyboard_arrow_up' : 'keyboard_arrow_down'"
                @click="transferExpanded = !transferExpanded" />
            </q-card-actions>

            <q-slide-transition>
              <div v-show="transferExpanded">
                <q-table :rows="transitionRows" :columns="transitionColumn" row-key="transferName"
                  style="margin-top: 2%; margin-left: 1%;" />
              </div>
            </q-slide-transition>
          </q-card>
        </div>

        <!--商店商品展示-->
        <div class="q-pa-ma" style="width: 65%;">
          <div>
            <q-tabs v-model="tab" dense class="text-grey" active-color="primary" indicator-color="primary" align="justify"
              narrow-indicator>
              <q-tab name="onShelve" label="在售商品" />
              <q-tab name="applications" label="待审批&失败的申请记录" />
              <q-tab name="removals" label="已下架商品" />

            </q-tabs>

            <q-separator />

            <q-tab-panels v-model="tab" animated>
              <q-tab-panel name="onShelve">
                <div class="flex justify-center">
                  <q-scroll-area :visible="visible" style="height: 620px; width: 100%;" class="flex justify-center">
                    <div class="flex q-justify-around" style="width: 900px;">
                      <div v-for="commodity in onShelveCmdt" :key="commodity" class="flex q-py-xs justify-around"
                        style="width: 450px;">
                        <q-card class="my-card">
                          <div class="q-pa-md">
                            <q-carousel class="commodity_img" swipeable animated v-model="commodity.slide" height="200px"
                              thumbnails infinite>
                              <q-carousel-slide v-for="(image, index) in commodity.image" :key="index" :name="index + 1"
                                :img-src="image" />
                            </q-carousel>
                          </div>

                          <q-card-section>
                            <q-btn fab color="primary" icon="delete" class="absolute"
                              style="top: 0; right: 12px; transform: translateY(-50%);"
                              @click="deleteCommodity(commodity)" />

                            <div class="row no-wrap items-center">
                              <div class="col text-h6 ellipsis">
                                <q-btn flat class="text-h6" @click="toEditCommodity(commodity)">
                                  {{ commodity.goodsName }}
                                </q-btn>
                              </div>
                              <!-- <div class="col-auto text-grey text-caption q-pt-md row no-wrap items-center">
                                下架商品
                              </div> -->
                            </div>

                            <q-separator />
                            <!-- <q-rating v-model="stars" :max="5" size="32px" /> -->
                            <div class="text-subtitle3" style="margin-top: 3%;">
                              状态: {{ commodity.status }}
                            </div>
                            <div class="text-subtitle1">
                              ¥ price: {{ commodity.price }}
                            </div>
                            <div class="text-caption text-grey">
                              {{ commodity.description }}
                            </div>
                          </q-card-section>
                        </q-card>
                        <q-separator />

                      </div>
                    </div>
                  </q-scroll-area>
                </div>
              </q-tab-panel>

              <q-tab-panel name="applications">
                <div class="flex justify-center">
                  <q-scroll-area :visible="visible" style="height: 620px; width: 100%;" class="flex justify-center">
                    <div class="flex q-justify-around" style="width: 900px;">
                      <div v-for="commodity in applyingCmdt" :key="commodity" class="flex q-py-xs justify-around"
                        style="width: 450px;">
                        <q-card class="my-card">
                          <div class="q-pa-md">
                            <q-carousel class="commodity_img" swipeable animated v-model="commodity.slide" height="200px"
                              thumbnails infinite>
                              <q-carousel-slide v-for="(image, index) in commodity.image" :key="index" :name="index + 1"
                                :img-src="image" />
                            </q-carousel>
                          </div>

                          <q-card-section>
                            <!-- <q-btn fab color="primary" icon="delete" class="absolute"
                              style="top: 0; right: 12px; transform: translateY(-50%);"
                              @click="deleteCommodity(commodity)" /> -->

                            <div class="row no-wrap items-center">
                              <div class="col text-h6 ellipsis">
                                <q-btn flat class="text-h6">
                                  {{ commodity.goodsName }}
                                </q-btn>
                              </div>
                              <!-- <div class="col-auto text-grey text-caption q-pt-md row no-wrap items-center">
                                下架商品
                              </div> -->
                            </div>

                            <q-separator />
                            <!-- <q-rating v-model="stars" :max="5" size="32px" /> -->
                            <div class="text-subtitle3" style="margin-top: 3%;">
                              状态: {{ commodity.status }}
                            </div>
                            <div class="text-subtitle1">
                              ¥ price: {{ commodity.price }}
                            </div>
                            <div class="text-caption text-grey">
                              {{ commodity.description }}
                            </div>
                          </q-card-section>
                        </q-card>
                        <q-separator />

                      </div>
                    </div>
                  </q-scroll-area>
                </div>
              </q-tab-panel>

              <q-tab-panel name="removals">
                <div class="flex justify-center">
                  <q-scroll-area :visible="visible" style="height: 620px; width: 100%;" class="flex justify-center">
                    <div class="flex q-justify-around" style="width: 900px;">
                      <div v-for="commodity in removedCmdt" :key="commodity" class="flex q-py-xs justify-around"
                        style="width: 450px;">
                        <q-card class="my-card">
                          <div class="q-pa-md">
                            <q-carousel class="commodity_img" swipeable animated v-model="commodity.slide" height="200px"
                              thumbnails infinite>
                              <q-carousel-slide v-for="(image, index) in commodity.image" :key="index" :name="index + 1"
                                :img-src="image" />
                            </q-carousel>
                          </div>

                          <q-card-section>
                            <!-- <q-btn fab color="primary" icon="delete" class="absolute"
                              style="top: 0; right: 12px; transform: translateY(-50%);"
                              @click="deleteCommodity(commodity)" /> -->

                            <div class="row no-wrap items-center">
                              <div class="col text-h6 ellipsis">
                                <q-btn flat class="text-h6">
                                  {{ commodity.goodsName }}
                                </q-btn>
                              </div>
                              <!-- <div class="col-auto text-grey text-caption q-pt-md row no-wrap items-center">
                                下架商品
                              </div> -->
                            </div>

                            <q-separator />
                            <!-- <q-rating v-model="stars" :max="5" size="32px" /> -->
                            <div class="text-subtitle3" style="margin-top: 3%;">
                              状态: {{ commodity.status }}
                            </div>
                            <div class="text-subtitle1">
                              ¥ price: {{ commodity.price }}
                            </div>
                            <div class="text-caption text-grey">
                              {{ commodity.description }}
                            </div>
                          </q-card-section>
                        </q-card>
                        <q-separator />

                      </div>
                    </div>
                  </q-scroll-area>
                </div>
              </q-tab-panel>

              <!--新增tab在这里写-->
            </q-tab-panels>
          </div>

        </div>

        <div>
          <q-dialog v-model="layout_delete" no-click-outside-close>
            <q-layout view="Lhh lpR fff" container class="bg-white">
              <q-header class="bg-primary">
                <q-toolbar>
                  <q-btn flat @click="drawer = !drawer" round dense icon="menu" />
                  <q-toolbar-title>下架商品</q-toolbar-title>
                  <q-btn flat @click="drawerR = !drawerR" round dense icon="menu" />
                  <q-btn flat v-close-popup round dense icon="close" @click="layout_delete = false" />
                </q-toolbar>
              </q-header>

              <q-footer class="bg-black text-white">
                <q-toolbar inset>
                  <q-toolbar-title>Apply</q-toolbar-title>
                </q-toolbar>
              </q-footer>

              <q-page-container>
                <q-page padding style="height:100px">
                  <div class="text-h5 q-mt-sm q-mb-xs" style="height:100px"> <q-chip size="lg" icon="bookmark">
                      商品名称
                    </q-chip> {{ delete_goodsName }}</div>
                  <div class="text-h5 q-mt-sm q-mb-xs" style="height:100px"> <q-chip size="lg" icon="bookmark">
                      商品简介
                    </q-chip> {{ delete_description }}</div>
                  <div class="text-h5 q-mt-sm q-mb-xs" style="height:120px"> <q-chip size="lg" icon="bookmark">
                      商品价格
                    </q-chip> ¥ {{ delete_price }}</div>
                  <q-btn color="primary" glossy label="Delete" @click="deleteOneCommodity" />
                </q-page>
              </q-page-container>
            </q-layout>
          </q-dialog>
        </div>

        <!--上架商品-->
        <div>
          <q-btn round color="purple" icon="add" class="absolute" @click="applyAddCommodity()"
            style="top: 600px; right: 50px; transform: translateY(-50%);" size="30px">
          </q-btn>
          <q-dialog v-model="layout" no-click-outside-close>
            <q-layout view="Lhh lpR fff" container class="bg-white">
              <q-header class="bg-primary">
                <q-toolbar>
                  <q-btn flat @click="drawer = !drawer" round dense icon="menu" />
                  <q-toolbar-title>申请上架商品</q-toolbar-title>
                  <q-btn flat @click="drawerR = !drawerR" round dense icon="menu" />
                  <q-btn flat v-close-popup round dense icon="close" @click="layout = false" />
                </q-toolbar>
              </q-header>

              <q-footer class="bg-black text-white">
                <q-toolbar inset>
                  <q-toolbar-title>Apply</q-toolbar-title>
                </q-toolbar>
              </q-footer>

              <q-page-container>
                <q-page padding>
                  <q-form ref="myForm" @submit="onSubmit" @reset="onReset" class="q-gutter-md">
                    <br>
                    <q-input filled v-model="goodsName" label="商品名称 *" hint="Name and surname" lazy-rules
                      :rules="[val => val && val.length > 0 && val.length < 13 || '请输入您的商品名称']" />

                    <q-input filled v-model="description" label="商品简介 *" hint="Introduce your store within 128 characters"
                      lazy-rules :rules="[val => val && val.length > 0 || '请输入您的简介',
                        val => val.length < 129 || '超过字数限制']">
                    </q-input>

                    <q-input filled v-model="price" label="商品价格 *" hint="registered funds" lazy-rules :rules="[val => val && val.length > 0 || '请输入商品价格',
                      checkprice || '价格需为大与0的浮点数']"></q-input>

                    <q-uploader field-name="files" style="width: 530px" url="http://120.46.154.28:9999/Goods/uploadImg"
                      label="Restricted to images" multiple @uploading="onFileUploading" accept="image/*"
                      Content-Type='multipart/form-data' :auto-upload="false" @uploaded="handleUpload($event)"
                      @rejected="onRejected" />

                    <div class="submit">
                      <q-btn label="Submit" type="submit" color="red-4" />
                      <q-btn label="Reset" type="reset" color="red-4" flat class="q-ml-sm" />
                    </div>

                  </q-form>
                </q-page>
              </q-page-container>
            </q-layout>
          </q-dialog>
        </div>

      </div>
    </q-page-container>

    <!-- 修改商品信息 -->
    <div class="editCommodity" v-if="editCommodity">
      <q-dialog v-model="editCommodity" width>
        <q-layout view="Lhh lpR fff" container class="bg-white">

          <q-header class="bg-primary">
            <q-toolbar>
              <q-btn flat @click="drawer = !drawer" round dense icon="menu" />
              <q-toolbar-title>修改商品信息</q-toolbar-title>
              <q-btn flat @click="drawerR = !drawerR" round dense icon="menu" />
              <q-btn flat v-close-popup round dense icon="close" />
            </q-toolbar>
          </q-header>

          <q-drawer bordered v-model="drawer" :width="200" :breakpoint="600" class="bg-grey-3 q-pa-sm">
            <div v-for="n in 50" :key="n">Drawer {{ n }} / 50</div>
          </q-drawer>

          <q-drawer side="right" bordered v-model="drawerR" :width="200" :breakpoint="300" class="bg-grey-3 q-pa-sm">
            <div v-for="n in 50" :key="n">Drawer {{ n }} / 50</div>
          </q-drawer>

          <q-page-container>
            <q-page padding>
              <q-form ref="myForm" action="http://120.46.154.28:9999/user" method="post" @submit="postProfileUpdate"
                @reset="onReset" class="q-gutter-md">
                <dl>
                  <dt>
                    <label class="label" for="userName">商品名称</label>
                  </dt>
                  <dd>
                    <q-input filled name="goodsName" v-model="editComName" @click="code = 0" :disable="viewOnly" />
                  </dd>
                </dl>

                <dl>
                  <dt>
                    <label class="label" for="userPhone">价格</label>
                  </dt>
                  <dd>
                    <q-input filled name="price" v-model="editComPrice" @click="code = 0" :disable="viewOnly" />
                  </dd>
                </dl>

                <dl>
                  <dt>商品描述</dt>
                  <dd>
                    <q-input filled name="description" v-model="editComDescription" :disable="viewOnly" />
                  </dd>
                </dl>

                <div class="button-box">
                  <div v-if="viewOnly" class="edit-button">
                    <q-btn color="secondary" label="修改信息" @click="viewEditModeChange" />
                  </div>

                  <div v-if="!viewOnly" class="submit-button">
                    <q-btn color="secondary" label="提交修改" @click="postCommodityEdit" />
                  </div>
                  <q-tooltip v-if="editNotification" anchor="bottom middle" self="top middle" :offset="[10, 10]">
                    <strong>{{ editMsg }}</strong>
                    (<q-icon name="keyboard_arrow_down" />)
                  </q-tooltip>
                </div>
              </q-form>
            </q-page>
          </q-page-container>
        </q-layout>
      </q-dialog>
    </div>

  </q-layout>
</template>

<script setup>
import { ref, computed, getCurrentInstance } from 'vue'
import axios from 'axios'
import { onMounted } from 'vue'

const axiosInstance = axios.create({
  baseURL: 'http://120.46.154.28:9999',
});
let leftDrawerOpen = ref(false)

// 左侧边栏内项目icon-text列举
const links1 = [
  { icon: 'person', text: '个人账户' },
  { icon: 'star_border', text: '资金信息' }
]

const tab = ref('onShelve')
const layout = ref(false)
const layout_delete = ref(false)

let editCommodity = ref(false)
let viewOnly = ref(true)

const instance = getCurrentInstance()

//上架商品
const goodsName = ref(null)
const description = ref(null)
const price = ref(null)
const image = ref([])

//下架商品信息
const delete_goodsName = ref(null)
const delete_description = ref(null)
const delete_price = ref(null)

//修改申请记录
let editComId = ref(null)
let editComName = ref(null)
let editComDescription = ref(null)
let editComPrice = ref(null)
let editComShopid = ref(null)
let editComImage = ref(null)
let editComStatus = ref(null)
const shops = ref([])
const code = ref(null)
let transferExpanded = ref(false)
const shopId = localStorage.getItem('shopId')

// 商品展示（分类数组）
let onShelveCmdt = ref([])
let removedCmdt = ref([])
let applyingCmdt = ref([])

// 流水
let transitionColumn = [
  { name: 'transferName', label: '转账人', field: 'transferName' },
  { name: 'receiveName', label: '收款人', field: 'receiveName' },
  { name: 'amount', label: '金额（¥）', field: 'amount' },
]
let transitionRows = ref([])


const shopName = computed(() => {
  return shops.value ? shops.value.shopName : '';
});

const shopIntroduce = computed(() => {
  return shops.value ? shops.value.introduce : '';
});

const shopAddress = computed(() => {
  return shops.value ? shops.value.address : '';
});

const shopRegistrationTime = computed(() => {
  return shops.value ? shops.value.registrationTime : '';
});


const myForm = ref(null);

// 暂禁用
// 左侧边栏开闭
// function toggleLeftDrawer() {
// leftDrawerOpen.value = !leftDrawerOpen.value
// }

function update() {
  getShelvedCommodities()
  getApplication()
  getRemovedCommodities()
  getTransition()
  instance.proxy.$forceUpdate();
}

function checkprice(price) {
  if (isNaN(price)) {
    return false;
  }
  if (price < 0) {
    return false;
  }
  return true; // 输入值合法
}

//下架商品
function deleteCommodity(commodity) {
  console.log('delete commodity parameter: ', commodity.id)
  axiosInstance.put('/Goods/remove',
    {
      id: commodity.id,
    },
    {
      params: {
        goodsId: commodity.id,
      }
    }).then((response) => {
      console.log('下架商品：', response)
      update()
    });
}


// 提交上架商品的表单
function onSubmit() {
  axiosInstance
    .post('/Goods/addGoodsApply',
      {
        goodsName: goodsName.value,
        description: description.value,
        price: price.value,
        shopId: shopId,
        image: image.value
      },
      {
        params: {
          shopId: shopId
        }
      }
    )
    .then((response) => {
      //console.log('Response data:', response.data)
      // 处理返回数据
      // 导航到另一个页面
      console.log('shangjia')
      console.log('shangjia')
      code.value = response.data['code'];
      if (code.value == 20000) {
        layout.value = false;
        console.log('layout');
        console.log(layout.value);
        update()
        onReset()
      }
      console.log(response.data);
    })
    .catch((error) => {
      console.error('Error:', error);
      // 处理错误
      myForm.value.resetValidation();
    });
}

function onReset() {
  goodsName.value = null
  description.value = null
  price.value = null
  image.value = []
  myForm.value.resetValidation();
}

function handleUpload(event) {
  console.log('handleUpload')
  console.log(event)
  const r = event.xhr.responseText;
  const r_obj = JSON.parse(r)
  console.log(r_obj)
  r_obj.message = 'http://120.46.154.28:9999/img/' + r_obj.message
  image.value.push(r_obj.message)
  console.log(image.value)
}


onMounted(() => {
  //显示该店铺的信息
  console.log('shopId: ', shopId)

  console.log('current shopID: ', shopId)
  axiosInstance.get('/shop/showShopByShopId', {
    params: {
      shopId: shopId
    }
  }).then((response) => {
    const r = response.data['data']
    const [year, month, day] = r.registrationTime;
    r.registrationTime = `${year}-${month}-${day}`;
    console.log('shop message')
    shops.value = r
    console.log(shops.value)
  });

  getShelvedCommodities()
  getApplication()
  getRemovedCommodities()
  getTransition()
});

function toEditCommodity(commodity) {
  console.log('edit commodity msg: ', commodity);
  editCommodity.value = true
  editComName.value = commodity.goodsName
  editComId.value = commodity.id
  editComDescription.value = commodity.description
  editComPrice.value = commodity.price
  editComShopid.value = commodity.shopId
  editComImage.value = commodity.image
  editComStatus.value = commodity.status
}

function viewEditModeChange() {
  viewOnly.value = !viewOnly.value
  console.log('view mode: ', viewOnly.value)
}

function applyAddCommodity() {
  layout.value = true
  goodsName.value = null
  description.value = null
  price.value = null
  image.value = []
}

function postCommodityEdit() {
  console.log('post edit commodity: \ngoods id: ', editComId.value,
    '\ngoods name: ', editComName.value,
    '\ngoods price: ', editComPrice.value,
    '\ngoods description: ', editComDescription.value,
    '\ngoods shop id: ', editComShopid.value,
    '\ngoods image: ', editComImage.value,)

  axiosInstance
    .post('/Goods/updateGoodsApply',
      {
        goodsName: editComName.value,
        description: editComDescription.value,
        price: editComPrice.value,
        shopId: editComShopid.value,
        image: editComImage.value
      },
      {
        params: {
          goodsId: editComId.value,
        }
      }
    )
    .then((response) => {
      //console.log('Response data:', response.data)
      // 处理返回数据
      // 导航到另一个页面
      console.log('shangjia')
      code.value = response.data['code'];
      if (code.value == 20000) {
        layout.value = false;
        console.log('layout');
        console.log(layout.value);
      }
      console.log(response.data);
      viewOnly.value = true
      editCommodity.value = false
      update()
    })
    .catch((error) => {
      console.error('Error:', error);
      // 处理错误
      // myForm.value.resetValidation();
    });
}

// 获取改店铺的所有已上架商品
function getShelvedCommodities() {
  onShelveCmdt = ref([])
  axiosInstance.get('/Goods/showAddRecord_1', {
    params: {
      shopId: shopId
    }
  }).then((response) => {
    const r = response.data['data']
    r.forEach(function (item) {
      // item.id is shopId!
      item.status = '在售中'
    });
    onShelveCmdt.value = r
    console.log('获取在售商品(record 1)：', onShelveCmdt.value)
  });
  // 修改商品信息成功的案例也算做上架中的商品
  axiosInstance.get('/Goods/showUpdateRecord_8', {
    params: {
      shopId: shopId
    }
  }).then((response) => {
    const r = response.data['data']
    r.forEach(function (item) {
      // console.log(item.id)
      // item.id is shopId!
      if (item !== null) {
        item.status = '在售中（修改信息成功）'
        onShelveCmdt.value.push(item)
      }
    });
  });
  // 修改信息失败也是上架商品
  axiosInstance.get('/Goods/showUpdateRecord_9', {
    params: {
      shopId: shopId
    }
  }).then((response) => {
    const r = response.data['data']
    r.forEach(function (item) {
      // console.log(item.id)
      // item.id is shopId!
      if (item !== null) {
        item.status = '在售中（修改信息失败）'
        onShelveCmdt.value.push(item)
      }
    });
  });
}

// 获取改店铺的所有申请中 & 申请失败的商品
function getApplication() {
  applyingCmdt = ref([])
  // 上架等待审批
  axiosInstance.get('/Goods/showAddRecord_0', {
    params: {
      shopId: shopId
    }
  }).then((response) => {
    const r = response.data['data']
    r.forEach(function (item) {
      // console.log(item.id)
      // item.id is shopId!
      if (item !== null) {
        item.status = '上架申请审批中'
        applyingCmdt.value.push(item)
      }
    });
    // console.log('获取申请商品(record 0)：', applyingCmdt.value)
  });
  // 上架失败
  axiosInstance.get('/Goods/showAddRecord_2', {
    params: {
      shopId: shopId
    }
  }).then((response) => {
    const r = response.data['data']
    r.forEach(function (item) {
      // console.log(item.id)
      // item.id is shopId!
      if (item !== null) {
        item.status = '上架申请失败'
        applyingCmdt.value.push(item)
      }
    });
    // if (r !== []){
    //     applyingCmdt.value.push(r)
    //   }
    // console.log('获取申请商品(record 2)：', applyingCmdt.value)
  });
  // 修改信息审批中
  axiosInstance.get('/Goods/showUpdateRecord_7', {
    params: {
      shopId: shopId
    }
  }).then((response) => {
    const r = response.data['data']
    r.forEach(function (item) {
      // console.log(item.id)
      // item.id is shopId!
      if (item !== null) {
        item.status = '修改信息申请审批中'
        applyingCmdt.value.push(item)
      }
    });
    // if (r !== []){
    //     applyingCmdt.value.push(r)
    //   }
    // console.log('获取申请商品(record 7)：', applyingCmdt.value)
  });
  // 修改信息失败
  axiosInstance.get('/Goods/showUpdateRecord_9', {
    params: {
      shopId: shopId
    }
  }).then((response) => {
    const r = response.data['data']
    r.forEach(function (item) {
      // console.log(item.id)
      // item.id is shopId!
      if (item !== null) {
        item.status = '修改信息申请失败'
        applyingCmdt.value.push(item)
      }
    });
    // if (r !== []){
    //     applyingCmdt.value.push(r)
    //   }
    // console.log('获取申请商品(record 9)：', applyingCmdt.value)
  });
}

// 获取所有已下架商品
function getRemovedCommodities() {
  removedCmdt = ref([])
  axiosInstance.get('/Goods/showDeleteRecord_5', {
    params: {
      shopId: shopId
    }
  }).then((response) => {
    const r = response.data['data']
    // console.log('showDeleteRecord_5')
    r.forEach(function (item) {
      // console.log(item.id)
      // item.id is shopId!
      if (item !== null) {
        item.status = '已下架'
        removedCmdt.value.push(item)
      }
    });
    console.log('获取下架商品(record 5)：', removedCmdt.value)
  });
}

function getTransition() {
  console.log('current shopid: ', shopId)
  axiosInstance.post('/transferRecords/shop', {
    id: shopId
  }, {
    params: {
      id: shopId
    }
  }).then((response) => {
    const r = response.data['data']
    console.log('get shop transfer records: ', r);
    r.forEach(function (item) {
      transitionRows.value.push(item);
    });
  });
  console.log('transition: ', transitionRows.value)
}

</script>

<style lang="sass" scoped>
.my-card
  width: 100%
  max-width: 350px
  height: 100%
  max-height: 430px
</style>
