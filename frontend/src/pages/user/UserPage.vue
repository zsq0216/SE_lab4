<template>
  <q-layout view="hHh lpR fFf">

    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />

        <q-toolbar-title>
          <q-avatar>
            <img src="https://cdn.quasar.dev/logo-v2/svg/logo-mono-white.svg">
          </q-avatar>
          用户首页
        </q-toolbar-title>

      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view />
      <div class="q-pa-md row items-start q-gutter-md">
        <!--用户信息展示-->
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

        <!--商品以及店铺信息展示-->
        <div class="q-pa-ma" style="max-width: 75%; ">
          <div>
            <q-tabs v-model="tab" dense class="text-grey" active-color="primary" indicator-color="primary" align="justify"
              narrow-indicator>
              <q-tab name="Commodity" label="Commodity" />
              <q-tab name="Shops" label="Shops" />
            </q-tabs>

            <q-separator />

            <q-tab-panels v-model="tab" animated>
              <q-tab-panel name="Commodity">
                <!--商品信息展示---->
                <div class="text-h6">Commodity</div>
                <div class="flex justify-center">
                  <q-scroll-area :visible="visible" style="height: 620px; width: 800px;" class="flex justify-center">
                    <div class="flex q-justify-around" style="width: 800px;">
                      <div v-for="commodity in commodities" :key="commodity" class="flex q-py-xs justify-around"
                        style="width: 400px;">
                        <q-card class="my-card">
                          <div class="q-pa-md">
                            <q-carousel class="commodity_img" swipeable animated v-model="commodity.slide" height="200px"
                              thumbnails infinite>
                              <q-carousel-slide v-for="(image, index) in commodity.image" :key="index" :name="index + 1"
                                :img-src="image" />
                            </q-carousel>
                          </div>

                          <q-card-section>
                            <q-btn fab color="primary" icon="add_shopping_cart" class="absolute"
                              style="top: 0; right: 12px; transform: translateY(-50%);"
                              @click="addShoppingCart(commodity.id)" />

                            <div class="row no-wrap items-center">
                              <div class="col text-h6 ellipsis">
                                {{ commodity.goodsName }}
                              </div>
                              <div class="col-auto text-grey text-caption q-pt-md row no-wrap items-center">
                                add shopping cart
                              </div>
                            </div>

                            <q-rating v-model="commodity.stars" :max="5" size="32px" />
                          </q-card-section>

                          <q-card-section class="q-pt-none">
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

              <!--店铺信息展示-->
              <q-tab-panel name="Shops">
                <div class="text-h6">Shops</div>
                <div class="flex justify-center">
                  <q-scroll-area :visible="visible" style="height: 620px; width: 800px;" class="justify-center">
                    <div class="q-py-xs justify-around" style="height: 1000px; width: 800px;">
                      <div v-for="shop in shops" :key="shop">
                        <div style="height:250px">
                          <q-card class="shop-card" flat bordered>
                            <q-card-section>
                              <div class="text-overline text-orange-9">Overline</div>
                              <!-- <q-btn class="text-h5 q-mt-sm q-mb-xs" to="/singleShop">{{ shop.shopName }}</q-btn> -->
                              <q-btn flat class="text-h6" @click="toShop(shop.id)">
                                {{ shop.shopName }}
                              </q-btn>
                              <div class="row no-wrap items-center">
                                <q-rating size="18px" v-model="shop.stars" :max="5" color="primary" />
                                <!-- <span class="text-caption text-grey q-ml-sm">4.2 (551)</span> -->
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
                            </q-card-section>

                            <q-card-actions>
                              <q-space />

                              <!-- <q-btn color="grey" round flat dense
                              :icon="expanded ? 'keyboard_arrow_up' : 'keyboard_arrow_down'"
                              @click="expanded = !expanded" /> -->
                            </q-card-actions>

                            <q-slide-transition>
                              <div v-show="expanded">
                                <q-separator />
                                <q-card-section class="text-subitle2">
                                  {{ shop.introduce }}
                                </q-card-section>
                              </div>
                            </q-slide-transition>
                          </q-card>
                        </div>
                      </div>
                    </div>
                  </q-scroll-area>
                </div>
              </q-tab-panel>
            </q-tab-panels>
          </div>

        </div>

        <!--购物车-->
        <div>
          <q-btn round color="purple" icon="shopping_cart" class="absolute" to="/userShoppingCart"
            style="top: 600px; right: 50px; transform: translateY(-50%);" size="30px">
          </q-btn>
        </div>
      </div>


    </q-page-container>

  </q-layout>
</template>

<script setup>
import { ref, getCurrentInstance } from 'vue'
import axios from 'axios'
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'

const url = ref('https://avatars.githubusercontent.com/u/105032850?s=400&u=285d7d130058e413bb8797cb52bc10f75c343076&v=4')
const username = localStorage.getItem('username');
const userId = localStorage.getItem('userId');
const tab = ref("Commodity")
const expanded = ref(false)
const router = useRouter()

const axiosInstance = axios.create({
  baseURL: 'http://120.46.154.28:9999',
});
const shops = ref([])
const instance = getCurrentInstance()

const commodities = ref([])

function addShoppingCart(id) {
  console.log(id)
  console.log('add shopping cart')
  //记得改成全局变量
  axiosInstance.post(`/cart/add2cart?goodsId=${id}&userId=${userId}`).then((res) => {
    console.log("cart")
    console.log(res.data.data)
  });
}

//跳转到单个店铺
function toShop(id) {
  console.log(id)

  localStorage.setItem('shopId', id);

  router.push('/shop');
}

onMounted(() => {
  console.log("localStorage")
  console.log(localStorage.getItem('userId'))

  //请求所有已批准开店的店铺status=1
  axiosInstance.get('/shop/all').then((res) => {
    console.log('店铺')
    console.log(res.data.data)
    const r = res.data.data
    r.forEach(function (item) {
      if (item.status === 1 || item.status === 5 || item.status === 3) {
        shops.value.push(item)
      }
    });
  });
  console.log('获取所有开店的店铺：', shops.value)

  //请求所有商品信息
  axiosInstance.get('/Goods/showAllApprovedGoods').then((res) => {
    console.log('res')
    console.log(res.data.data)
    commodities.value = res.data.data
    console.log(commodities.value)
  });
  instance.proxy.$forceUpdate();

});
</script>

<style lang="sass" scoped>
.my-card
  width: 100%
  max-width: 350px
  height: 100%
  max-height: 430px

.shop-card
  width: 100%
  width: 700px
  height: 100%
  max-height: 210px
</style>
