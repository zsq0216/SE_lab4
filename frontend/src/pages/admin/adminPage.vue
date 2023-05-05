<template>
  <q-layout view="hHh lpR fFf">

    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />

        <q-toolbar-title class="text-italic">
          <q-avatar>
            <img src="https://cdn.quasar.dev/logo-v2/svg/logo-mono-white.svg">
          </q-avatar>
          首页
        </q-toolbar-title>
        <q-btn flat round dense icon="update" class="q-mr-xs" @click="updata"></q-btn>

        <q-btn-dropdown class="glossy" color="dark" label="账户设置">
          <div class="row no-wrap q-pa-md">
            <div class="column items-center">
              <q-avatar size="72px">
                <img src="https://cdn.quasar.dev/img/boy-avatar.png">
              </q-avatar>

              <div class="text-subtitle1 q-mt-md q-mb-xs">{{ username }}</div>

              <q-btn to="/login" color="primary" label="Logout" push size="sm" v-close-popup />
            </div>
          </div>
        </q-btn-dropdown>
      </q-toolbar>
    </q-header>

    <q-drawer v-model="leftDrawerOpen" show-if-above bordered>
      <q-list>
        <q-item-label header>Essential Links</q-item-label>
        <q-item clickable target="_blank" rel="noopener" @click="toggle">
          <q-item-section avatar>
            <q-icon name="face" />
          </q-item-section>
          <q-item-section> Dark theme </q-item-section>
        </q-item>
        <router-link to="/admin" class="router-link-class">
          <q-item clickable target="_blank" rel="noopener" @click="showAdminMainPage">
            <q-item-section avatar>
              <q-icon name="perm_identity" />
            </q-item-section>
            <q-item-section> Admin </q-item-section>
          </q-item>
        </router-link>

        <q-item clickable target="_blank" rel="noopener" @click="showTransitionMode">
          <q-item-section avatar>
            <q-icon name="open_in_new" />
          </q-item-section>
          <q-item-section> 中间账户 </q-item-section>
        </q-item>

        <q-item clickable target="_blank" rel="noopener" @click="showProfitMode">
          <q-item-section avatar>
            <q-icon name="open_in_new" />
          </q-item-section>
          <q-item-section> 利润账户 </q-item-section>
        </q-item>

        <q-item>
          <q-item-section>
            <img src="../../assets/log.png" alt="" />
          </q-item-section>
        </q-item>
      </q-list>
    </q-drawer>

    <q-page-container>
      <div class="q-pa-md" v-if="showAllApplication">

        <div class="applicationTypeSelect" style="width: 100%">
          <q-btn flat rounded color="secondary" label="待查看" @click="selectRemainChecked"
            style="margin-left: 20%; font-size: 105%; font-weight: normal" />
          <q-btn flat rounded color="secondary" label="已同意" @click="selectApproved"
            style="margin-left: 20%;font-size: 105%; font-weight: normal;" />
          <q-btn flat rounded color="secondary" label="已拒绝" @click="selectRejected"
            style="margin-left: 20%; font-size: 105%; font-weight: normal" />
        </div>

        <q-separator style="width: 100%; margin-top: 1%;" />

        <p v-if="!showRejectedApps && !showApprovedApps && !showRemainCheckedApps"
          style="font-size: large; margin-top: 10%; margin-left: 40%;">
          请选择您想要查看的申请类型
        </p>

        <div class="remainCheckedApps" v-if="showRemainCheckedApps">
          <p style="font-size: large; margin-top: 2%; margin-left: 2%;">待批准的店铺申请</p>
          <q-table :rows="rcShopAppRows" :columns="rcShopAppColumns" row-key="id" :pagination="true"
            style="margin-top: 2%; margin-left: 1%;">
            <template v-slot:body-cell-action="{ row }">
              <q-btn color="positive" class="q-mx-auto" icon="check" @click="approveApplication(row)" />
              <q-btn color="negative" class="q-mx-auto" icon="clear" @click="disapproveApplication(row)" />
            </template>
          </q-table>

          <q-separator style="width: 100%; margin-top: 3%;" />

          <p style="font-size: large; margin-top: 2%; margin-left: 2%;">待批准的商品申请</p>
          <div class="flex q-justify-around" style="width:800px">
            <!-- 待审批的上架申请 -->

            <div v-for="commodity in rcCommodityUpApp" :key="commodity" class="flex q-py-xs" style="width: 400px;">
              <q-card class="edit-commodity-card" style="width: 350px;">
                <div class="q-pa-md">
                  <q-carousel class="commodity_img" swipeable animated v-model="commodity.slide" height="200px" thumbnails
                    infinite>
                    <q-carousel-slide v-for="(image, index) in commodity.image" :key="index" :name="index + 1"
                      :img-src="image" />
                  </q-carousel>
                </div>


                <q-card-section>
                  <div class="row no-wrap items-center">
                    <div class="col text-h6 ellipsis">
                      {{ commodity.goodsName }}
                    </div>
                  </div>
                  <div class="text-subtitle1">
                    ¥ price: {{ commodity.price }}
                  </div>
                  <div class="text-caption text-grey">
                    {{ commodity.description }}
                  </div>
                </q-card-section>

                <q-card-actions>
                  <q-btn color="positive" class="q-mx-auto" icon="check" @click="approveCommodityUpdate(commodity)" />
                  <q-btn color="negative" class="q-mx-auto" icon="clear" @click="disapproveCommodityUpdate(commodity)" />
                </q-card-actions>
              </q-card>
              <q-separator />
            </div>

            <!-- 待审批的修改信息申请 -->
            <div v-for="commodity in rcCommodityEditApp" :key="commodity" class="flex q-py-xs justify-around"
              style="width: 400px">
              <q-card class="edit-commodity-card" style="width: 350px;">
                <div class="q-pa-md">
                  <q-carousel class="commodity_img" swipeable animated v-model="commodity.slide" height="200px" thumbnails
                    infinite>
                    <q-carousel-slide v-for="(image, index) in commodity.image" :key="index" :name="index + 1"
                      :img-src="image" />
                  </q-carousel>
                </div>

                <q-card-section>
                  <div class="row no-wrap items-center">
                    <div class="col text-h6 ellipsis">
                      {{ commodity.goodsName }}
                    </div>
                  </div>
                  <div class="text-subtitle1">
                    ¥ price: {{ commodity.price }}
                  </div>
                  <div class="text-caption text-grey">
                    {{ commodity.description }}
                  </div>
                </q-card-section>

                <q-card-actions>
                  <q-btn color="positive" class="q-mx-auto" icon="check" @click="approveCommodityEdit(commodity)" />
                  <q-btn color="negative" class="q-mx-auto" icon="clear" @click="disapproveCommodityEdit(commodity)" />
                </q-card-actions>
              </q-card>
            </div>
          </div>

        </div>

        <div class="approvedApps" v-if="showApprovedApps">
          <p style="font-size: large; margin-top: 2%; margin-left: 2%;">已批准的店铺申请</p>
          <q-table :rows="aShopAppRows" :columns="aShopAppColumns" row-key="shopName" :pagination="true"
            style="margin-top: 2%; margin-left: 1%;" />
          <q-separator style="width: 100%; margin-top: 3%;" />

          <p style="font-size: large; margin-top: 2%; margin-left: 2%;">已同意的商品申请</p>
          <div class="flex q-justify-around" style="width:800px">
            <!-- 已通过的上架申请 -->

            <div v-for="commodity in aCommodityUpApp" :key="commodity" class="flex q-py-xs justify-around"
              style="width: 400px;">
              <q-card class="edit-commodity-card" style="width: 350px;">
                <div class="q-pa-md">
                  <q-carousel class="commodity_img" swipeable animated v-model="commodity.slide" height="200px" thumbnails
                    infinite>
                    <q-carousel-slide v-for="(image, index) in commodity.image" :key="index" :name="index + 1"
                      :img-src="image" />
                  </q-carousel>
                </div>

                <q-card-section>
                  <div class="row no-wrap items-center">
                    <div class="col text-h6 ellipsis">
                      {{ commodity.goodsName }}
                    </div>
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

            <!-- 已通过的修改信息申请 -->
            <div v-for="commodity in aCommodityEditApp" :key="commodity" class="flex q-py-xs justify-around"
              style="width: 400px">
              <q-card class="edit-commodity-card" style="width: 350%;">
                <div class="q-pa-md">
                  <q-carousel class="commodity_img" swipeable animated v-model="commodity.slide" height="200px" thumbnails
                    infinite>
                    <q-carousel-slide v-for="(image, index) in commodity.image" :key="index" :name="index + 1"
                      :img-src="image" />
                  </q-carousel>
                </div>

                <q-card-section>
                  <div class="row no-wrap items-center">
                    <div class="col text-h6 ellipsis">
                      {{ commodity.goodsName }}
                    </div>
                  </div>
                  <div class="text-subtitle1">
                    ¥ price: {{ commodity.price }}
                  </div>
                  <div class="text-caption text-grey">
                    {{ commodity.description }}
                  </div>
                </q-card-section>

              </q-card>
            </div>
          </div>

        </div>

        <div class="rejectedApps" v-if="showRejectedApps">
          <p style="font-size: large; margin-top: 2%; margin-left: 2%;">已拒绝的店铺申请</p>
          <q-table v-if="showRejectedApps" :rows="rShopAppRows" :columns="rShopAppColumns" row-key="name"
            style="margin-top: 2%; margin-left: 1%;" />

          <q-separator style="width: 100%; margin-top: 3%;" />

          <p style="font-size: large; margin-top: 2%; margin-left: 2%;">已拒绝的商品申请</p>
          <div class="flex q-justify-around" style="width:800px">
            <!-- 已拒绝的上架申请 -->
            <div v-for="commodity in rCommodityUpApp" :key="commodity" class="flex q-py-xs justify-around"
              style="width: 400px">
              <q-card class="edit-commodity-card" style="width: 350px;">
                <div class="q-pa-md">
                  <q-carousel class="commodity_img" swipeable animated v-model="commodity.slide" height="200px" thumbnails
                    infinite>
                    <q-carousel-slide v-for="(image, index) in commodity.image" :key="index" :name="index + 1"
                      :img-src="image" />
                  </q-carousel>
                </div>

                <q-card-section>
                  <div class="row no-wrap items-center">
                    <div class="col text-h6 ellipsis">
                      {{ commodity.goodsName }}
                    </div>
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
            <!-- 已拒绝的修改信息申请 -->
            <div v-for="commodity in rCommodityEditApp" :key="commodity" class="flex q-py-xs justify-around"
              style="width: 400px">
              <q-card class="edit-commodity-card" style="width: 350px">
                <div class="q-pa-md">
                  <q-carousel class="commodity_img" swipeable animated v-model="commodity.slide" height="200px" thumbnails
                    infinite>
                    <q-carousel-slide v-for="(image, index) in commodity.image" :key="index" :name="index + 1"
                      :img-src="image" />
                  </q-carousel>
                </div>

                <q-card-section>
                  <div class="row no-wrap items-center">
                    <div class="col text-h6 ellipsis">
                      {{ commodity.goodsName }}
                    </div>
                  </div>
                  <div class="text-subtitle1">
                    ¥ price: {{ commodity.price }}
                  </div>
                  <div class="text-caption text-grey">
                    {{ commodity.description }}
                  </div>
                </q-card-section>

              </q-card>
            </div>
          </div>
        </div>
      </div>

      <!-- 商城中间账户展示 -->
      <div class="transitionAccount" v-if="showTransition && !showAllApplication && !showProfit">
        <q-card class="my-card" flat bordered style="max-width: 100%; width: 70%; margin-left: 5%; margin-top: 3%;">

          <q-card-section>
            <div class="text-overline text-orange-9">商城中间账户</div>
            <div class="text-h5 q-mt-sm q-mb-xs">余额：¥{{ intermediateAccount }}</div>
          </q-card-section>

          <q-card-actions>
            <!-- <div class="cursor-pointer" style="width: 100px; margin-left: 10%;">
              充值
              <q-popup-edit v-model="label" auto-save>
                <q-input v-model="money" hint="请输入充值金额" dense autofocus counter @keyup.enter="topUpAccount" />
              </q-popup-edit>
            </div> -->
            <q-btn flat color="secondary" label="点击右侧下拉查看转账记录" style="margin-left: 0.5%;" />

            <q-space />

            <q-btn color="grey" round flat dense
              :icon="intermediateExpanded ? 'keyboard_arrow_up' : 'keyboard_arrow_down'"
              @click="intermediateExpanded = !intermediateExpanded" />
          </q-card-actions>

          <!-- 商城中间账户的流水 -->
          <q-slide-transition>
            <div v-show="intermediateExpanded">
              <q-separator />
              <q-table :rows="intermediateAccountTrans" :columns="TransitionColumn" row-key="transferName"
                style="margin-top: 2%; margin-left: 1%;" />
            </div>
          </q-slide-transition>
        </q-card>
      </div>

      <!-- 商城利润账户展示 -->
      <div class="profitAccount" v-if="showProfit && !showTransitio && !showAllApplication">
        <q-card class="my-card" flat bordered style="max-width: 100%; width: 70%; margin-left: 5%; margin-top: 3%;">

          <q-card-section>
            <div class="text-overline text-orange-9">商城利润账户</div>
            <div class="text-h5 q-mt-sm q-mb-xs">目前利润：¥{{ profitAccount }}</div>
          </q-card-section>

          <q-card-actions>
            <!-- <div class="cursor-pointer" style="width: 100px; margin-left: 10%;">
              充值
              <q-popup-edit v-model="label" auto-save>
                <q-input v-model="money" hint="请输入充值金额" dense autofocus counter @keyup.enter="topUpAccount" />
              </q-popup-edit>
            </div> -->
            <q-btn flat color="secondary" label="点击右侧下拉查看流水" style="margin-left: 0.5%;" />

            <q-space />

            <q-btn color="grey" round flat dense :icon="profitExpanded ? 'keyboard_arrow_up' : 'keyboard_arrow_down'"
              @click="profitExpanded = !profitExpanded" />
          </q-card-actions>

          <q-slide-transition>
            <div v-show="profitExpanded">
              <q-separator />
              <q-table :rows="profitAccountTrans" :columns="TransitionColumn" row-key="transferName"
                style="margin-top: 2%; margin-left: 1%;" />
            </div>
          </q-slide-transition>
        </q-card>
      </div>
    </q-page-container>
  </q-layout>
</template>


<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { onMounted } from 'vue'
import { useQuasar } from 'quasar'

const q = useQuasar();
const axiosInstance = axios.create({
  baseURL: 'http://120.46.154.28:9999',
});

let leftDrawerOpen = ref(false)
let showTransition = ref(false)
let showAllApplication = ref(true)
let showProfit = ref(false)
let showApprovedApps = ref(false)
let showRejectedApps = ref(false)
let showRemainCheckedApps = ref(false)
const username = ref('admin')
let selected = ref([])
let errorMsg = ref(null)
let response_register = ref(null)
let intermediateAccount = ref(0)
let profitAccount = ref(0)
let shopName = ref(null)

// 已同意的商店申请
let aShopAppColumns = [
  {
    name: 'applicationType',
    required: true,
    label: '申请类型',
    align: 'left',
    field: row => row.status,
    format: val => `${val}`,
    sortable: false
  },
  {
    name: 'shopName',
    required: true,
    label: '店铺名称',
    align: 'left',
    field: row => row.shopName,
    format: val => `${val}`,
    sortable: false
  },
  {
    name: 'category',
    required: true,
    label: '商品类别',
    align: 'left',
    field: row => row.category,
    format: val => {
      if (Array.isArray(val)) {
        return val.join(', ')
      } else if (typeof val === 'object' && val !== null && val.id && val.text) {
        return `${val.id} - ${val.text}`
      } else {
        return ''
      }
    },
    sortable: true
  },
  { name: 'idNumber', label: '申请人身份证号', field: 'idNumber' },
  { name: 'introduce', label: '商店简介', field: 'introduce' },
  { name: 'address', label: '备案地址', field: 'address' },
  { name: 'fund', label: '注册资金', field: 'fund', sortable: true, sort: (a, b) => parseFloat(a, 10) - parseFloat(b, 10) },
  { name: 'registrationTime', label: '申请时间 YYYY-MM-DD', field: 'registrationTime', sortable: false },
]
let aShopAppRows = ref([])

// 已同意的商品申请
// TODO: 修改商品申请的信息
let aCommodityUpApp = ref([])
let aCommodityEditApp = ref([])

// 已拒绝的商店申请
let rShopAppColumns = [
  {
    name: 'applicationType',
    required: true,
    label: '申请类型',
    align: 'left',
    field: row => row.status,
    format: val => `${val}`,
    sortable: false
  },
  {
    name: 'shopName',
    required: true,
    label: '店铺名称',
    align: 'left',
    field: row => row.shopName,
    format: val => `${val}`,
    sortable: false
  },
  {
    name: 'category',
    required: true,
    label: '商品类别',
    align: 'left',
    field: row => row.category,
    format: val => {
      if (Array.isArray(val)) {
        return val.join(', ')
      } else if (typeof val === 'object' && val !== null && val.id && val.text) {
        return `${val.id} - ${val.text}`
      } else {
        return ''
      }
    },
    sortable: true
  },
  { name: 'idNumber', label: '申请人身份证号', field: 'idNumber' },
  { name: 'introduce', label: '商店简介', field: 'introduce' },
  { name: 'address', label: '备案地址', field: 'address' },
  { name: 'fund', label: '注册资金', field: 'fund', sortable: true, sort: (a, b) => parseFloat(a, 10) - parseFloat(b, 10) },
  { name: 'registrationTime', label: '申请时间 YYYY-MM-DD', field: 'registrationTime', sortable: false },
]
let rShopAppRows = ref([])

// 已拒绝的商品申请
let rCommodityUpApp = ref([])
let rCommodityEditApp = ref([])

// 待查看的店铺申请
let rcShopAppColumns = [
  {
    name: 'applicationType',
    required: true,
    label: '申请类型',
    align: 'left',
    field: row => row.status,
    format: val => `${val}`,
    sortable: false
  },
  {
    name: 'shopName',
    required: true,
    label: '店铺名称',
    align: 'left',
    field: row => row.shopName,
    format: val => `${val}`,
    sortable: false
  },
  {
    name: 'category',
    required: true,
    label: '商品类别',
    align: 'left',
    field: row => row.category,
    format: val => {
      if (Array.isArray(val)) {
        return val.join(', ')
      } else if (typeof val === 'object' && val !== null && val.id && val.text) {
        return `${val.id} - ${val.text}`
      } else {
        return ''
      }
    },
    sortable: true
  },
  { name: 'idNumber', label: '申请人身份证号', field: 'idNumber' },
  { name: 'introduce', label: '商店简介', field: 'introduce' },
  { name: 'address', label: '备案地址', field: 'address' },
  { name: 'fund', label: '注册资金', field: 'fund', sortable: true, sort: (a, b) => parseFloat(a, 10) - parseFloat(b, 10) },
  { name: 'registrationTime', label: '申请时间 YYYY-MM-DD', field: 'registrationTime', sortable: false },
  {
    name: 'action',
    label: '同意申请',
    field: 'action',
    align: 'center',
    sortable: false
  },
]
let rcShopAppRows = ref([])

// 待查看的商品申请
let rcCommodityUpApp = ref([])
let rcCommodityEditApp = ref([])

// 管理员可查看的商城中间账户和利润账户
let profitExpanded = ref(false)
let intermediateExpanded = ref(false)
let TransitionColumn = [
  { name: 'transferName', label: '转账人', field: 'transferName' },
  { name: 'receiveName', label: '收款人', field: 'receiveName' },
  { name: 'amount', label: '金额（¥）', field: 'amount' },
]
let intermediateAccountTrans = ref([])
let profitAccountTrans = ref([])

onMounted(() => {
  // 获取所有待审批的商店信息：开店/闭店
  getRcShop()

  // 获取所有通过审批的商店信息：开店/闭店
  getAShop()

  // 获取所有已拒绝审批的商店信息：开店/闭店
  getRShop()

  // 请求所有待审批上架的商品信息
  getComAddApply()

  // 请求所有待审批修改信息的商品信息
  getComEditApply()

  // 请求所有已通过上架的商品信息
  getComAddApproved()

  // 请求所有已通过修改信息的商品信息
  getComEditApproved()

  // 请求所有已驳回上架的商品信息
  getComUpRejected()

  // 请求所有已驳回修改信息的商品信息
  getComEditRejected()

  // 获取商城中间账号信息
  axiosInstance.post('/admin/getIntermediateAccount').then((response) => {
    const r = response.data['data']
    console.log('get intermediate account msg: ', r);
    intermediateAccount.value = r
  });

  // 获取商城利润账户信息
  axiosInstance.post('/admin/getProfitAccount').then((response) => {
    const r = response.data['data']
    console.log('get profile account msg: ', r);
    profitAccount.value = r
  });
  getTransfer()
})

function showAdminMainPage() {
  showTransition.value = false
  showAllApplication.value = true
  showProfit.value = false
}

function showTransitionMode() {
  showTransition.value = true
  showAllApplication.value = false
  showProfit.value = false
}

function showProfitMode() {
  showProfit.value = true
  showAllApplication.value = false
  showAllApplication.value = false
}

function selectRemainChecked() {
  showApprovedApps.value = false
  showRejectedApps.value = false
  showRemainCheckedApps.value = true
}

function selectApproved() {
  showApprovedApps.value = true
  showRejectedApps.value = false
  showRemainCheckedApps.value = false
}

function selectRejected() {
  showApprovedApps.value = false
  showRejectedApps.value = true
  showRemainCheckedApps.value = false
}

// 获取所有待审批的商店信息：开店/闭店
function getRcShop() {
  axiosInstance.post('/shop/showByStatus0_3').then((response) => {
    const r = response.data['data']
    console.log('get rc shop msg: ', r);
    r.forEach(function (item) {
      // console.log(item.id)
      // item.id is shopId!
      item.shopId = item.id;
      delete item.id
    });
    r.map(obj => {
      const [year, month, day] = obj.registrationTime;
      obj.registrationTime = `${year}-${month}-${day}`;
      switch (obj.status) {
        case 0:
          obj.status = '申请开店';
          break;
        case 3:
          obj.status = '申请闭店';
          break;
        default:
          break;
      }
      return obj;
    });
    rcShopAppRows.value.splice(0, rcShopAppRows.value.length, ...r);
    console.log('rc shop value: ', rcShopAppRows.value)
  });
}

// 获取所有通过审批的商店信息：开店/闭店
function getAShop() {
  axiosInstance.post('/shop/showByStatus1_4').then((response) => {
    const r = response.data['data']
    console.log('get approved shop msg: ', r);
    r.forEach(function (item) {
      // console.log(item.id)
      // item.id is shopId!
      item.shopId = item.id;
      delete item.id
    });
    r.map(obj => {
      const [year, month, day] = obj.registrationTime;
      obj.registrationTime = `${year}-${month}-${day}`;
      switch (obj.status) {
        case 1:
          obj.status = '申请开店';
          break;
        case 4:
          obj.status = '申请闭店';
          break;
        default:
          break;
      }
      return obj;
    });
    aShopAppRows.value.splice(0, aShopAppRows.value.length, ...r);
    console.log(aShopAppRows.value)
  });
}

// 获取所有已拒绝审批的商店信息：开店/闭店
function getRShop() {
  axiosInstance.post('/shop/showByStatus2_5').then((response) => {
    const r = response.data['data']
    console.log('get delete shop msg: ', r);
    r.forEach(function (item) {
      // console.log(item.id)
      // item.id is shopId!
      item.shopId = item.id;
      delete item.id
    });
    r.map(obj => {
      const [year, month, day] = obj.registrationTime;
      obj.registrationTime = `${year}-${month}-${day}`;
      switch (obj.status) {
        case 2:
          obj.status = '申请开店';
          break;
        case 5:
          obj.status = '申请闭店';
          break;
        default:
          break;
      }
      return obj;
    });
    rShopAppRows.value.splice(0, rShopAppRows.value.length, ...r);
    console.log(rShopAppRows.value)
  });
}

// 请求所有待审批上架的商品信息
function getComAddApply() {
  axiosInstance.get('/Goods/showAddApply').then((response) => {
    const r = response.data['data']
    console.log('get 待审批上架商品 msg: ', r);
    r.forEach(function (item) {
      if (item !== null) {
        rcCommodityUpApp.value.push(item)
      }
    });
    console.log("申请待审批上架商品后：", rcCommodityUpApp.value)
  });
}

// 请求所有待审批修改信息的商品信息
function getComEditApply() {
  axiosInstance.get('/Goods/showUpdateApply').then((response) => {
    const r = response.data['data']
    console.log('get 待审批修改商品 msg: ', r);
    r.forEach(function (item) {
      if (item !== null) {
        rcCommodityEditApp.value.push(item)
      }
    });
    // rcCommodityEditApp.value.push(0, rcCommodityEditApp.value.length, ...r);
    console.log("申请待审批修改商品后：", rcCommodityEditApp.value)
  });
}

// 请求所有已通过上架的商品信息
function getComAddApproved() {
  axiosInstance.get('/Goods/showAddApproved').then((response) => {
    const r = response.data['data']
    console.log('get 已通过上架商品 msg: ', r);
    r.forEach(function (item) {
      if (item !== null) {
        aCommodityUpApp.value.push(item)
      }
    });
    // aCommodityUpApp.value.splice(0, aCommodityUpApp.value.length, ...r);
    console.log("已通过上架商品后：", aCommodityUpApp.value)
  });
}

// 请求所有已通过修改信息的商品信息
function getComEditApproved() {
  axiosInstance.get('/Goods/showUpdateApproved').then((response) => {
    const r = response.data['data']
    console.log('get 已通过修改商品 msg: ', r);
    r.forEach(function (item) {
      if (item !== null) {
        aCommodityUpApp.value.push(item)
      }
    });
    // aCommodityUpApp.value.splice(0, aCommodityUpApp.value.length, ...r);
    console.log("申请已通过修改商品后：", aCommodityEditApp.value)
  });
}

// 请求所有已驳回上架的商品信息
function getComUpRejected() {
  axiosInstance.get('/Goods/showAddRejected').then((response) => {
    const r = response.data['data']
    console.log('get 已驳回上架商品 msg: ', r);
    r.forEach(function (item) {
      if (item !== null) {
        rCommodityUpApp.value.push(item)
      }
    });
    // rCommodityUpApp.value.splice(0, aCommodityUpApp.value.length, ...r);
    console.log("已驳回上架商品后：", rCommodityUpApp.value)
  });
}

// 请求所有已驳回修改信息的商品信息
function getComEditRejected() {
  axiosInstance.get('/Goods/showUpdateRejected').then((response) => {
    const r = response.data['data']
    console.log('get 已驳回修改商品 msg: ', r);
    r.forEach(function (item) {
      if (item !== null) {
        rCommodityUpApp.value.push(item)
      }
    });
    // rCommodityUpApp.value.splice(0, aCommodityUpApp.value.length, ...r);
    console.log("申请已驳回修改商品后：", rCommodityEditApp.value)
  });
}

function getTransfer() {
  profitAccountTrans.value = []
  intermediateAccountTrans.value = []
  axiosInstance.post('/transferRecords/adminIntermediate', {
    // shopId: shopId,
  }).then((response) => {
    const r = response.data['data']
    console.log('get admin intermediate transfer records: ', r);
    r.forEach(function (item) {
      // console.log('get receive name: ', item['receiveName'])
      intermediateAccountTrans.value.push(item)
    });
  });
  axiosInstance.post('/transferRecords/adminProfit', {
    // shopId: shopId,
  }).then((response) => {
    const r = response.data['data']
    console.log('get admin profit transfer records: ', r);
    r.forEach(function (item) {
      // console.log('get receive name: ', item['receiveName'])
      profitAccountTrans.value.push(item)
    });
  });
}

// 同意申请
function approveApplication(rcShopAppRow) {
  // 根据行的id执行同意申请操作
  console.log('同意申请传参: shop id: ', rcShopAppRow.shopId, ' user id: ', rcShopAppRow.userId, '  fund: ', rcShopAppRow.fund)
  if (rcShopAppRow.status === '申请开店') {
    axiosInstance.put('/shop/approve', {
      id: rcShopAppRow.shopId,
      userId: rcShopAppRow.userId,
      fund: rcShopAppRow.fund,
    }).then((response) => {
      const r = response.data['data']
      console.log('开店申请response data: ', r);
    }).catch((error) => {
      console.error('Error:', error);
      // 处理错误
      errorMsg.value = response_register.value['message']
    });
  }
  else if (rcShopAppRow.status === '申请闭店') {
    axiosInstance.post('/shop/approveDelete', {
      id: rcShopAppRow.shopId,
      account: rcShopAppRow.account,
      userId: rcShopAppRow.userId,
    }).then((response) => {
      const r = response.data['data']
      console.log(r);
    }).catch((error) => {
      console.error('Error:', error);
      // 处理错误
    });
  }
  axiosInstance.post('/shop/showByStatus1_4').then((response) => {
    const r = response.data['data']
    console.log('get approved shop msg: ', r);
    r.forEach(function (item) {
      // console.log(item.id)
      // item.id is shopId!
      item.shopId = item.id;
      delete item.id
    });
    r.map(obj => {
      const [year, month, day] = obj.registrationTime;
      obj.registrationTime = `${year}-${month}-${day}`;
      switch (obj.status) {
        case 1:
          obj.status = '申请开店';
          break;
        case 4:
          obj.status = '申请闭店';
          break;
        default:
          break;
      }
      return obj;
    });
    aShopAppRows.value.splice(0, aShopAppRows.value.length, ...r);
    console.log(aShopAppRows.value)
    updata()
  });
}

function disapproveApplication(rcShopAppRow) {
  // 根据行的id执行同意申请操作
  console.log('拒绝申请传参: ', rcShopAppRow)
  if (rcShopAppRow.status === '申请开店') {
    axiosInstance.put('/shop/disapprove', {
      id: rcShopAppRow.shopId,
      userId: rcShopAppRow.userId,
      fund: rcShopAppRow.fund,
    }).then((response) => {
      const r = response.data['data']
      console.log('开店申请response data: ', r);
    }).catch((error) => {
      console.error('Error:', error);
      // 处理错误
      errorMsg.value = response_register.value['message']
    });
  }
  else if (rcShopAppRow.status === '申请闭店') {
    axiosInstance.post('/shop/disapproveDelete', {
      id: rcShopAppRow.shopId,
      account: rcShopAppRow.account,
      userId: rcShopAppRow.userId,
    }).then((response) => {
      const r = response.data['data']
      console.log(r);
      updata()
    }).catch((error) => {
      console.error('Error:', error);
      // 处理错误
    });
  }
}

function approveCommodityUpdate(good) {
  console.log('同意商品上架传参：', good.id)
  axiosInstance.put('/Goods/addGoodsApprove', {
    goodsId: good.id,
  }, {
    params: {
      goodsId: good.id,
    }
  }).then((response) => {
    const r = response.data['data']
    console.log('get 同意商品上架 msg: ', r);
    updata()
  });
}


function disapproveCommodityUpdate(good) {
  console.log('不同意商品上架传参：', good, '\nid: ', good.id)
  axiosInstance.put('/Goods/addGoodsReject', {
    goodsId: good.id,
  }, {
    params: {
      goodsId: good.id,
    }
  }).then((response) => {
    const r = response.data['data']
    console.log('get 不同意商品上架 msg: ', r);
    updata()
  });
}

function approveCommodityEdit(good) {
  console.log('同意商品修改传参：', good.id)
  axiosInstance.put('/Goods/updateGoodsApprove', {
    goodsId: good.id,
  }, {
    params: {
      goodsId: good.id,
    }
  }).then((response) => {
    const r = response.data['data']
    console.log('get 同意商品修改 msg: ', r);
    updata()
  });
}

function disapproveCommodityEdit(good) {
  console.log('不同意商品修改传参：', good.id)
  axiosInstance.put('/Goods/updateGoodsReject', {
    goodsId: good.id,
  }, {
    params: {
      goodsId: good.id,
    }
  }).then((response) => {
    const r = response.data['data']
    console.log('get 不同意商品修改 msg: ', r);
    updata()
  });
}

// 刷新button事件
function updata() {
  aShopAppRows.value = []
  rShopAppRows.value = []
  rcShopAppRows.value = []

  aCommodityUpApp.value = []
  aCommodityEditApp.value = []

  rCommodityUpApp.value = []
  rCommodityEditApp.value = []

  rcCommodityUpApp.value = []
  rcCommodityEditApp.value = []

  // 获取所有待审批的商店信息：开店/闭店
  getRcShop()

  // 获取所有通过审批的商店信息：开店/闭店
  getAShop()

  // 获取所有已拒绝审批的商店信息：开店/闭店
  getRShop()

  // 请求所有待审批上架的商品信息
  getComAddApply()

  // 请求所有待审批修改信息的商品信息
  getComEditApply()

  // 请求所有已通过上架的商品信息
  getComAddApproved()

  // 请求所有已通过修改信息的商品信息
  getComEditApproved()

  // 请求所有已驳回上架的商品信息
  getComUpRejected()

  // 请求所有已驳回修改信息的商品信息
  getComEditRejected()

  // 获取商城中间账号信息
  axiosInstance.post('/admin/getIntermediateAccount').then((response) => {
    const r = response.data['data']
    console.log('get intermediate account msg: ', r);
    intermediateAccount.value = r
  });

  // 获取商城利润账户信息
  axiosInstance.post('/admin/getProfitAccount').then((response) => {
    const r = response.data['data']
    console.log('get profile account msg: ', r);
    profitAccount.value = r
  });

  getTransfer()
}

function toggleLeftDrawer() {
  leftDrawerOpen.value = !leftDrawerOpen.value
}

// 深色模式
function toggle() {
  q.dark.toggle();
}


// ？
function getSelectedString() {
  return selected.value.length === 0 ? '' : `${selected.value.length} record${selected.value.length > 1 ? 's' : ''} selected of ${rows.value.length}`
}
</script>

<style>
.router-link-class {
  color: inherit;
  text-decoration: none;
}
</style>
