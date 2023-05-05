<template>
  <q-layout view="hHh lpR fFf">

    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />

        <q-toolbar-title>
          <q-avatar>
            <img src="https://cdn.quasar.dev/logo-v2/svg/logo-mono-white.svg">
          </q-avatar>
          Welcome to {{ shopName }}
        </q-toolbar-title>

      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view />
      <div class="q-pa-md row items-start q-gutter-md">
        <!--商店基本信息-->
        <div class="q-pa-ma" style="width: 30%">
          <div style="width:300px;">
            <q-img :src="url" style="border: 50%; left:50px" />
            <div class="text-h5 q-mt-sm q-mb-xs" style="margin-left:100px;height:50px"> {{ shopName }}</div>
            <div style="height:50px">
              <q-btn v-bind:label="buttonLabel" style="color: goldenrod; left:70px; width: 250px"
                v-on:click="toggleLabel" />
            </div>
            <div>
              <q-separator style="margin-left:80px" />
            </div>
            <div class="text-caption text-grey" style="margin-left:70px; max-height:50px">
              {{ shopIntroduce }}
            </div>
            <div class="col-auto text-grey text-h6 q-pt-md row no-wrap items-center" style="margin-left:70px">
              <q-icon name="place" />
              {{ shopAddress }}
            </div>
            <div class="text-grey text-h6" style="margin-left:80px; max-height:50px">
              注册时间 {{ shopRegistrationTime }}
            </div>
          </div>
        </div>

        <!--商店商品展示-->
        <div class="q-pa-ma" style="width: 65%;">
          <div>
            <q-tabs v-model="tab" dense class="text-grey" active-color="primary" indicator-color="primary" align="justify"
              narrow-indicator>
              <q-tab name="Commodity" label="Commodity" />
            </q-tabs>

            <q-separator />

            <q-tab-panels v-model="tab" animated>
              <q-tab-panel name="Commodity">
                <div class="flex justify-center">
                  <q-scroll-area :visible="visible" style="height: 620px; width: 900px;" class="flex justify-center">
                    <div class="flex q-justify-around" style="width: 100%;">
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
import { ref, computed,  getCurrentInstance } from 'vue'
import { useStore } from 'src/store'
import axios from 'axios'
import { onMounted } from 'vue'

const store = useStore()
const shopId = localStorage.getItem('shopId')
const axiosInstance = axios.create({
  baseURL: 'http://120.46.154.28:9999',
});
const userId = localStorage.getItem('userId')
const tab = ref("Commodity")
const url = ref('https://avatars.githubusercontent.com/u/105032850?s=400&u=285d7d130058e413bb8797cb52bc10f75c343076&v=4')
const isFollowed = ref(false)
const buttonLabel = computed(() => {
  if (isFollowed.value) {
    return 'Unfollow'
  } else {
    return 'Follow'
  }
})

const commodities = ref([])
const instance = getCurrentInstance()
const shops = ref([])

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

function toggleLabel() {
  isFollowed.value = !isFollowed.value
}

function addShoppingCart(id) {
  console.log(id)
  console.log("add shopping cart")

  //记得改成全局变量
  axiosInstance.post(`/cart/add2cart?goodsId=${id}&userId=${userId}`).then((res) => {
      console.log("cart")
      console.log(res.data)
    });
}

onMounted(() => {
  //显示该店铺的信息
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

  //显示该店铺的所有上架成功的商品
  axiosInstance.get('/Goods/showAddRecord_1', {
    params: {
      shopId: shopId
    }
  }).then((response) => {
    const r = response.data['data']
    console.log('showAddRecord_1')
    commodities.value = r
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
</style>
