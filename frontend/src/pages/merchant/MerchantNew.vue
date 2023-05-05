<template>
  <q-layout view="hHh lpR fFf">

    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />

        <q-toolbar-title>
          <q-avatar>
            <img src="https://cdn.quasar.dev/logo-v2/svg/logo-mono-white.svg">
          </q-avatar>
          商户首页
        </q-toolbar-title>
        <q-btn flat round dense icon="update" class="q-mr-xs" @click="update"></q-btn>

      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view />
      <div class="q-pa-md row items-start q-gutter-md">
        <!--商户信息展示-->
        <q-card class="my-card" flat bordered style="width: 20%; margin-top: 3%;">
          <q-card-section horizontal>
            <q-card-section class="q-pt-xs">
              <div class="text-overline">Overline</div>
              <div class="text-h5 q-mt-sm q-mb-xs">{{ username }}</div>
              <div class="text-caption text-grey">

              </div>
            </q-card-section>

            <q-card-section class="col-5 flex flex-center">
              <q-img :src="url" style="border-radius: 50%;" />
            </q-card-section>
          </q-card-section>

          <q-separator />

          <q-card-actions>
            <q-btn flat icon="logout" color="accent" to="/login">
              Logout
            </q-btn>
            <q-btn flat icon="settings" color="accent" to="/accountInfo">
              Settings
            </q-btn>
          </q-card-actions>
        </q-card>

        <!--商户商品展示-->
        <div class="q-pa-md" style="max-width: 75%; ">
          <q-card class="my-card">
            <q-tab-panels v-model="tab" animated>
              <q-tab-panel name="mails" class="q-pa-none">

                <q-splitter v-model="splitterModel" style="height: 630px">

                  <template v-slot:before>
                    <q-tabs v-model="innerTab" vertical active-color="accent" class="text-grey">
                      <q-tab name="allShops" icon="toll" label="All" />
                      <q-tab name="openingShops" icon="alarm" label="Opening" />
                      <q-tab name="applyingShops" icon="open_in_new" label="Applying" />
                      <q-tab name="closedShops" icon="movie" label="Closed" />
                    </q-tabs>
                  </template>

                  <template v-slot:after>
                    <q-tab-panels v-model="innerTab" animated transition-prev="slide-down" transition-next="slide-up">

                      <q-tab-panel name="allShops">
                        <div class="q-py-xs justify-around" style="height: 1000px; width: 950px;">
                          <div v-for="shop in allShops" :key="shop">

                            <div class="q-pa-md row items-start q-gutter-md" style="width: 80%;">
                              <q-card class=" my-card" flat bordered style="width: 80%;">
                                <!-- <q-img src="https://cdn.quasar.dev/img/parallax2.jpg" /> -->
                                <q-card-section>
                                  <div class="text-overline text-orange-9">Overline</div>
                                  <q-btn flat class="text-h6" @click="toShop(shop)">
                                    {{ shop.shopName }}
                                    <q-tooltip v-model="shop.showing"> {{ shop.status }}</q-tooltip>
                                  </q-btn>
                                  <div class="row no-wrap items-center">
                                    <q-rating size="18px" v-model="stars" :max="5" color="primary" />
                                  </div>
                                  <q-separator />
                                  <br>
                                  <div class="text-caption text-grey">
                                    商品类别:
                                    <span flat v-for="c in shop.category" :key="c">
                                      {{ c }} &nbsp;
                                    </span>
                                    <br>
                                    商店简介: {{ shop.introduce }}
                                  </div>

                                  <div class="text-caption text-grey">
                                    商品类别:
                                    <span flat v-for="c in shop.category" :key="c">
                                      {{ c }} &nbsp;
                                    </span>
                                    <br>
                                    商店状态: {{ shop.status }}
                                  </div>
                                </q-card-section>

                              </q-card>
                            </div>
                          </div>
                        </div>
                      </q-tab-panel>

                      <q-tab-panel name="openingShops">
                        <div class="q-py-xs justify-around" style="height: 1000px; width: 950px;">
                          <div v-for="shop in openingShops" :key="shop">

                            <div class="q-pa-md row items-start q-gutter-md" style="width: 80%;">
                              <q-card class=" my-card" flat bordered style="width: 80%;">
                                <!-- <q-img src="https://cdn.quasar.dev/img/parallax2.jpg" /> -->
                                <q-card-section>
                                  <div class="text-overline text-orange-9">Overline</div>
                                  <q-btn flat class="text-h6" @click="toShop(shop)" :label="shop.shopName">

                                    <q-tooltip v-model="shop.showing"> {{ shop.status }}</q-tooltip>
                                  </q-btn>

                                  <div class="row no-wrap items-center">
                                    <q-rating size="18px" v-model="stars" :max="5" color="primary" />
                                  </div>
                                  <q-separator />
                                  <br>
                                  <div class="text-caption text-grey">
                                    商品类别:
                                    <span flat v-for="c in shop.category" :key="c">
                                      {{ c }} &nbsp;
                                    </span>
                                    <br>
                                    商店简介: {{ shop.introduce }}
                                  </div>

                                  <div class="text-caption text-grey">
                                    商品类别:
                                    <span flat v-for="c in shop.category" :key="c">
                                      {{ c }} &nbsp;
                                    </span>
                                    <br>
                                    商店状态: {{ shop.status }}
                                  </div>
                                </q-card-section>

                                <q-card-actions>
                                  <!-- <q-btn icon="delete" @click="deleteShop(shop)" /> -->
                                  <q-btn push icon="delete" @click="deleteShop(shop)">
                                    <q-popup-proxy>
                                      <q-banner>
                                        <template v-slot:avatar>
                                          <q-icon name="signal_wifi_off" color="primary" />
                                        </template>
                                        您申请关闭店铺{{ shop.shopName }}
                                      </q-banner>
                                    </q-popup-proxy>
                                  </q-btn>
                                </q-card-actions>

                              </q-card>
                            </div>
                          </div>
                        </div>
                      </q-tab-panel>

                      <q-tab-panel name="applyingShops">
                        <div class="q-py-xs justify-around" style="height: 1000px; width: 950px;">
                          <div v-for="shop in applyingShops" :key="shop">

                            <div class="q-pa-md row items-start q-gutter-md" style="width: 80%;">
                              <q-card class=" my-card" flat bordered style="width: 80%;">
                                <!-- <q-img src="https://cdn.quasar.dev/img/parallax2.jpg" /> -->
                                <q-card-section>
                                  <div class="text-overline text-orange-9">Overline</div>
                                  <q-btn flat class="text-h6" @click="toShop(shop)">
                                    {{ shop.shopName }}
                                    <q-tooltip v-model="shop.showing"> {{ shop.status }}</q-tooltip>
                                  </q-btn>
                                  <div class="row no-wrap items-center">
                                    <q-rating size="18px" v-model="stars" :max="5" color="primary" />
                                  </div>
                                  <q-separator />
                                  <br>
                                  <div class="text-caption text-grey">
                                    商品类别:
                                    <span flat v-for="c in shop.category" :key="c">
                                      {{ c }} &nbsp;
                                    </span>
                                    <br>
                                    商店简介: {{ shop.introduce }}
                                  </div>

                                  <div class="text-caption text-grey">
                                    商品类别:
                                    <span flat v-for="c in shop.category" :key="c">
                                      {{ c }} &nbsp;
                                    </span>
                                    <br>
                                    商店状态: {{ shop.status }}
                                  </div>
                                </q-card-section>

                              </q-card>
                            </div>
                          </div>
                        </div>
                      </q-tab-panel>

                      <q-tab-panel name="closedShops">
                        <div class="q-py-xs justify-around" style="height: 1000px; width: 950px;">
                          <div v-for="shop in closedShops" :key="shop">

                            <div class="q-pa-md row items-start q-gutter-md" style="width: 80%;">
                              <q-card class=" my-card" flat bordered style="width: 80%;">
                                <!-- <q-img src="https://cdn.quasar.dev/img/parallax2.jpg" /> -->
                                <q-card-section>
                                  <div class="text-overline text-orange-9">Overline</div>
                                  <q-btn flat class="text-h6" @click="toShop(shop)">
                                    {{ shop.shopName }}
                                    <q-tooltip v-model="shop.showing"> {{ shop.status }}</q-tooltip>
                                  </q-btn>
                                  <div class="row no-wrap items-center">
                                    <q-rating size="18px" v-model="stars" :max="5" color="primary" />
                                  </div>
                                  <q-separator />
                                  <br>
                                  <div class="text-caption text-grey">
                                    商品类别:
                                    <span flat v-for="c in shop.category" :key="c">
                                      {{ c }} &nbsp;
                                    </span>
                                    <br>
                                    商店简介: {{ shop.introduce }}
                                  </div>

                                  <div class="text-caption text-grey">
                                    商品类别:
                                    <span flat v-for="c in shop.category" :key="c">
                                      {{ c }} &nbsp;
                                    </span>
                                    <br>
                                    商店状态: {{ shop.status }}
                                  </div>
                                </q-card-section>

                              </q-card>
                            </div>
                          </div>
                        </div>
                      </q-tab-panel>
                    </q-tab-panels>
                  </template>

                </q-splitter>
              </q-tab-panel>

            </q-tab-panels>
          </q-card>
        </div>

        <!--申请开店-->
        <div>
          <q-btn round color="primary" icon="add" class="absolute" @click="applyAddShop()"
            style="top: 600px; right: 50px; transform: translateY(-50%);" size="30px">
          </q-btn>
          <q-dialog v-model="layout" no-click-outside-close>
            <q-layout view="Lhh lpR fff" container class="bg-white">
              <q-header class="bg-primary">
                <q-toolbar>
                  <q-btn flat @click="drawer = !drawer" round dense icon="menu" />
                  <q-toolbar-title>申请开店</q-toolbar-title>
                  <q-btn flat @click="drawerR = !drawerR" round dense icon="menu" />
                  <q-btn flat v-close-popup round dense icon="close" @click="layout = false" />
                </q-toolbar>
              </q-header>

              <q-footer class="bg-black text-white">
                <q-toolbar inset>
                  <q-toolbar-title>Apply</q-toolbar-title>
                </q-toolbar>
              </q-footer>

              <q-drawer bordered v-model="drawer" :width="200" :breakpoint="600" class="bg-grey-3 q-pa-sm">
                <q-list>
                  <q-item class="text-weight-bloder" v-for="category in categories" :key="category.id" v-ripple>
                    <q-item-section>
                      <div class="text-weight-bloder">
                        {{ category.text }}
                      </div>
                    </q-item-section>
                  </q-item>
                </q-list>
              </q-drawer>

              <q-page-container>
                <q-page padding>
                  <q-form ref="myForm" @submit="onSubmit" @reset="onReset" class="q-gutter-md">
                    <br>
                    <q-input filled v-model="shopName" label="商店名称 *" hint="Name and surname" lazy-rules :rules="[val => val && val.length > 0 && val.length < 13 || '请输入您的商店名称',
                    val => code != 20009 || '店铺名已被注册过']" />

                    <q-input filled v-model="new_category" label="所售卖的商品类别 *" hint="commodity category"
                      :rules="[checkCategory || '请输入商品类别']">
                      <q-btn flat round dense icon="add_task" class="q-mr-xs" @click="add_category" /></q-input>
                    <div class="q-ma-md">
                      <q-scroll-area style="height: 40px; width: 515px;" class="bg-purple-1">
                        <q-list>
                          <q-item class="text-weight-bloder" v-for="category in categories" :key="category.id" v-ripple>
                            <q-item-section>
                              <div class="text-weight-bloder">
                                {{ category.text }}
                                <q-btn flat round dense icon="remove_done" class="q-mr-xs"
                                  @click="remove_category(category)" />
                              </div>
                            </q-item-section>
                          </q-item>
                        </q-list>
                      </q-scroll-area>
                    </div>
                    <q-input filled v-model="idNumber" label="身份证号 *" hint="Your ID card number" lazy-rules :rules="[
                      val => val && val.length > 0 || '请输入您的身份证号',
                      val => val.length === 18 && checkIdNumber(idNumber) || '请输入正确的身份证号',
                      val => code != 20006 || '该身份证已经被申请']" @click="code = 0">{{ code }}</q-input>

                    <q-input filled v-model="introduce" label="商店简介 *" hint="Introduce your store within 128 characters"
                      lazy-rules :rules="[val => val && val.length > 0 || '请输入您的简介',
                      val => val.length < 129 || '超过字数限制']">
                    </q-input>

                    <q-input filled v-model="address" label="备案地址 *" hint="your record address" lazy-rules :rules="[val => val && val.length > 0 || '请输入您的备案地址',
                    val => val.length < 33 || '超过字数限制']"></q-input>

                    <q-input filled v-model="fund" label="注册资⾦ *" hint="registered funds" lazy-rules :rules="[val => val && val.length > 0 || '请输入您的备案地址',
                    checkFunds || '资金不足1000', val => code != 20010 || '账户资金不足']"></q-input>

                    <q-input filled v-model="registrationTime" :rules="[]">
                      <template v-slot:append>
                        <q-icon name="event" class="cursor-pointer">
                          <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                            <q-date v-model="registrationTime" mask="YYYY-MM-DD">
                              <div class="row items-center justify-end">
                                <q-btn v-close-popup label="Close" color="primary" flat />
                              </div>
                            </q-date>
                          </q-popup-proxy>
                        </q-icon>
                      </template>
                    </q-input>

                    <q-toggle v-model="accept" label="我同意隐私条款" />

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

  </q-layout>
</template>

<script setup>
import { ref, getCurrentInstance } from 'vue'
import { useStore } from 'src/store'
import axios from 'axios'
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'

let id = 0
const url = ref('https://avatars.githubusercontent.com/u/105032850?s=400&u=285d7d130058e413bb8797cb52bc10f75c343076&v=4')
const store = useStore()
const username = localStorage.getItem('username');
const userId = localStorage.getItem('userId');
const tab = ref('mails')
const innerTab = ref('allShops')
const splitterModel = ref(20)
const router = useRouter()
const layout = ref(false)
const shopName = ref(null);
const new_category = ref('');
const categories = ref([]);
const idNumber = ref(null);
const introduce = ref('');
const address = ref('');
const fund = ref(null);
const registrationTime = ref(null);
const code = ref(null);
const accept = ref(false)
const instance = getCurrentInstance()
const stars = ref(4)
const showing = ref(false)

const axiosInstance = axios.create({
  baseURL: 'http://120.46.154.28:9999',
});
let allShops = ref([])
let openingShops = ref([])
let applyingShops = ref([])
let closedShops = ref([])

// 申请开店表单初始化
function applyAddShop() {
  layout.value = true
}

//跳转到单个店铺
function toShop(shop) {
  store.commit('setShopId', shop.id);
  console.log("shopId")
  console.log(store.state.shopId);

  localStorage.setItem('shopId', shop.id);

  if(shop.status == "闭店未审核" || shop.status == "开店已通过")
  {
    router.push('/merchantshop');
  }
}

// 添加商品种类
function add_category() {
  if (new_category.value != '') {
    categories.value.push({ id: id++, text: new_category.value });
    new_category.value = '';
    console.log(categories.value);
  }
}

function update() {
  allShops = ref([])
  openingShops = ref([])
  applyingShops = ref([])
  closedShops = ref([])
  console.log("update")
  axiosInstance.post('/shop/showUser', { id: userId })
    .then((response) => {
      const r = response.data['data'];
      console.log('Show shop message: ', r);
      r.map((obj) => {
        const [year, month, day] = obj.registrationTime;
        obj.registrationTime = `${year}-${month}-${day}`;
        return obj;
      });
      r.map((obj) => {
        switch (obj.status) {
          case 0:
            obj.status = '开店未审核';
            applyingShops.value.push(obj)
            break;
          case 1:
            obj.status = '开店已通过';
            openingShops.value.push(obj)
            break;
          case 2:
            obj.status = '开店未通过';
            applyingShops.value.push(obj)
            break;
          case 3:
            obj.status = '闭店未审核';
            applyingShops.value.push(obj)
            break;
          case 4:
            obj.status = '闭店已通过';
            closedShops.value.push(obj)
            break;
          case 5:
            obj.status = '闭店未通过';
            openingShops.value.push(obj)
            break;
          default:
            break;
        }
        return obj;
      });
      allShops.value.splice(0, allShops.value.length, ...r);
      console.log(allShops.value);
    });
}

// 删除商品种类
function remove_category(category) {
  console.log(category);
  console.log(category.id);
  categories.value = categories.value.filter((c) => c.id !== category.id);
  id--;
  categories.value.forEach((category, index) => {
    category.id = index;
  });
  console.log(categories.value);
}

// 检查身份证号合法性
function checkIdNumber(idNumber) {
  if (idNumber.length !== 18) {
    return false;
  }

  const factors = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
  let sum = 0;

  for (let i = 0; i < 17; i++) {
    const digit = parseInt(idNumber.charAt(i));
    const factor = factors[i];
    sum += digit * factor;
  }
  console.log(sum);

  const checkCodeMap = {
    0: '1',
    1: '0',
    2: 'X',
    3: '9',
    4: '8',
    5: '7',
    6: '6',
    7: '5',
    8: '4',
    9: '3',
    10: '2',
  };

  const remainder = sum % 11;

  const checkCode = checkCodeMap[remainder];
  console.log(typeof idNumber);
  console.log(idNumber.charAt(17));
  console.log(typeof checkCode);
  console.log(checkCode === idNumber.charAt(17));
  return checkCode === idNumber.charAt(17);
}

function checkCategory() {
  if (categories.value.length == 0) {
    return false;
  } else return true;
}

// 检查输入金额
function checkFunds(value) {
  const numValue = Number(value);
  if (isNaN(numValue)) {
    return '请输入数字';
  }
  if (numValue < 1000) {
    return '注册资金必须大于1000';
  }
  return true;
}

// 提交申请开店表单
function onSubmit() {
  console.log(categories.value);
  const newTextArr = categories.value.map((item) => item.text);
  console.log(newTextArr);
  console.log('fund: ', fund.value);
  axiosInstance.post('/shop/add', {
    shopName: shopName.value,
    category: newTextArr,
    idNumber: idNumber.value,
    introduce: introduce.value,
    address: address.value,
    fund: fund.value,
    registrationTime: registrationTime.value,
    userId: localStorage.getItem('userId'),
  }).then((response) => {
    //console.log('Response data:', response.data)
    // 处理返回数据
    // 导航到另一个页面
    code.value = response.data['code'];
    console.log('code');
    console.log(code.value);
    instance.proxy.$forceUpdate();
    if (code.value == 20000) {
      layout.value = false;
      console.log('layout');
      console.log(layout.value);
      update()
    }
    console.log(response.data);
    onReset()
    layout.value = false
    //myForm.value.resetValidation();
  }).catch((error) => {
    console.error('Error:', error);
    // 处理错误
    myForm.value.resetValidation();
  });
  layout.value = false
}

// 重置申请开店表单
function onReset() {
  shopName.value = null;
  accept.value = false;
  introduce.value = null;
  categories.value = [];
  idNumber.value = null;
  address.value = null;
  fund.value = null;
  registrationTime.value = null;
}

onMounted(() => {
  //展示所有商店
  console.log('Show shop');
  console.log(store.state.userId);
  axiosInstance.post('/shop/showUser', { id: userId })
    .then((response) => {
      const r = response.data['data'];
      console.log('Show shop message: ', r);
      r.map((obj) => {
        const [year, month, day] = obj.registrationTime;
        obj.registrationTime = `${year}-${month}-${day}`;
        return obj;
      });
      r.map((obj) => {
        switch (obj.status) {
          case 0:
            obj.status = '开店未审核';
            applyingShops.value.push(obj)
            break;
          case 1:
            obj.status = '开店已通过';
            openingShops.value.push(obj)
            break;
          case 2:
            obj.status = '开店未通过';
            closedShops.value.push(obj)
            break;
          case 3:
            obj.status = '闭店未审核';
            applyingShops.value.push(obj)
            break;
          case 4:
            obj.status = '闭店已通过';
            closedShops.value.push(obj)
            break;
          case 5:
            obj.status = '闭店未通过';
            openingShops.value.push(obj)
            break;
          default:
            break;
        }
        return obj;
      });
      allShops.value.splice(0, allShops.value.length, ...r);
      console.log(allShops.value);
    });
  console.log('closed shops: ', closedShops.value)
});

function deleteShop(shop) {
  console.log("delete shop parameters: ", shop)
  axiosInstance.post('/shop/delete', {
    id: shop.id,
    userId: shop.userId,
    fund: shop.fund,
  }).then((response) => {
    code.value = response.data['code'];
    console.log('code');
    console.log(code.value);
    instance.proxy.$forceUpdate();
    // if (code.value == 20000) {
    //   layout.value = false;
    //   console.log('layout');
    //   console.log(layout.value);
    // }
    console.log(response.data);
  }).catch((error) => {
    console.error('Error:', error);
    // 处理错误
  });
  update()
}

</script>

<style lang="sass" scoped>
.shop-card
  width: 100%
  width: 800px
  height: 100%
  max-height: 210px
</style>
