<template>
  <q-layout view="hHh lpR fFf">

    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />
        <q-toolbar-title>
          <q-avatar>
            <img src="https://cdn.quasar.dev/logo-v2/svg/logo-mono-white.svg">
          </q-avatar>
          购物车
        </q-toolbar-title>
      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view />
      <div class="q-pa-md row items-start q-gutter-md" style="width:100%">
        <!--用户信息展示-->
        <!-- <q-card class="my-card" flat bordered style="width: 280px">
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
        </q-card> -->

        <div class="q-pa-ma" style="width:30%;">
          <div style="width:300px;">
            <q-img :src="url" style="border-radius: 50%; left:50px" />
            <div class="text-h5 q-mt-sm q-mb-xs" style="margin-left:70px"> {{ username }}</div>

            <q-separator style="margin-left:50px" />
            <div class="col-auto text-grey text q-pt-md row no-wrap items-center" style="margin-left:70px">
              <q-icon name="place" />
              Shanghai, China
            </div>
          </div>
        </div>

        <!--购物车商品展示-->
        <div class="q-pa-ma" style="width:65%">
          <div>
            <q-tabs v-model="tab" dense class="text-grey" active-color="primary" indicator-color="primary" align="justify"
              narrow-indicator>
              <q-tab name="Commodity" label="Commodity" />
            </q-tabs>

            <q-separator />

            <q-tab-panels v-model="tab" animated>
              <q-tab-panel name="Commodity">
                <div class="flex justify-center">
                  <q-scroll-area :visible="visible" style="height: 650px; width: 900px;" class="flex justify-center">
                    <div class="flex q-justify-around" style="width: 900px;">
                      <div v-for="commodity in commodities" :key="commodity" class="flex q-py-xs justify-around"
                        style="width: 900px;">
                        <q-card class="shop-card" flat bordered>
                          <q-card-section horizontal>
                            <q-card-section class="q-pt-xs" style="width:60%; height:100%">
                              <div class="text-overline">Overline</div>
                              <div class="text-h5 q-mt-sm q-mb-xs">{{ commodity.goods.goodsName }}</div>
                              <div class="text-caption text-grey" style="height:20px">
                                {{ commodity.goods.description }}
                              </div>
                            </q-card-section>
                            <q-card-section>

                            </q-card-section>

                            <q-card-section class="col-5 flex justify-end">
                              <q-img class="rounded-borders" :src="commodity.goods.image[0]"
                                style="width:200px; height:150px"></q-img>
                            </q-card-section>
                          </q-card-section>

                          <q-card-section>
                            <template v-if="!commodity.cartGoods.isRemoved">
                              <div class="flex">
                                <q-btn class="decrement" icon="remove" size="xs" @click="decrement(commodity)" />
                                <div class="justify-center" style="width:30px; display:flex; align-items:center">{{
                                  commodity.cartGoods.goodsQuantity }}</div>
                                <q-btn class="increment" icon="add" size="xs" @click="increment(commodity)" />
                              </div>
                            </template>
                            <template v-else>
                              <div class="text-red">已下架</div>
                            </template>
                          </q-card-section>

                          <q-separator />


                          <q-card-actions>
                            <q-card-section horizontal>
                              <q-checkbox v-model="selectedCommodities[commodity.goods.id]" />
                            </q-card-section>
                            <q-btn flat color="primary" @click="singel_delete(commodity.goods.id)">
                              Delete
                            </q-btn>
                          </q-card-actions>
                        </q-card>
                      </div>
                    </div>
                  </q-scroll-area>
                </div>
              </q-tab-panel>
            </q-tab-panels>
          </div>
        </div>

        <div>
          <q-btn round color="purple" icon="delete" class="absolute" to="/userShoppingCart" @click="batch_delete"
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

const url = ref('https://avatars.githubusercontent.com/u/105032850?s=400&u=285d7d130058e413bb8797cb52bc10f75c343076&v=4')
const username = localStorage.getItem('username')
const tab = ref("Commodity")
const instance = getCurrentInstance()
const userId = localStorage.getItem('userId')


const axiosInstance = axios.create({
  baseURL: 'http://120.46.154.28:9999',
});

const commodities = ref([])

const selectedCommodities = ref([]) // 存储选中的商品

function batch_delete() {
  console.log(selectedCommodities.value)
  const selectedCommodityIds = Object.keys(selectedCommodities.value).map(Number);
  axiosInstance.delete(`/cart/deleteMulti?goodsIdList=${selectedCommodityIds}&userId=${userId}`).then((res) => {
    console.log("batch")
    console.log(res.data)
    update()
  });
}

function decrement(commodity) {
  console.log("decrement")
  console.log(commodity)
  axiosInstance.put(`/cart/deleteSingle?goodsId=${commodity.goods.id}&userId=${userId}`).then((res) => {
    console.log("cart")
    console.log(res.data)
    update()
  });
}

function increment(commodity) {
  console.log("increment")
  console.log(commodity)
  axiosInstance.post(`/cart/add2cart?goodsId=${commodity.goods.id}&userId=${userId}`).then((res) => {
    console.log("cart")
    console.log(res.data)
    update()
  });

}

function update() {
  axiosInstance.get('/cart/showList',
    {
      params: {
        userId: userId
      }
    }).then((res) => {
      console.log("update")
      console.log(res.data.data)
      commodities.value = res.data.data
    });

  instance.proxy.$forceUpdate();
}

onMounted(() => {
  //userId全局变量
  axiosInstance.get('/cart/showList',
    {
      params: {
        userId: userId
      }
    }).then((res) => {
      console.log("res")
      console.log(res.data.data)
      commodities.value = res.data.data
    });
});
</script>

<style lang="sass" scoped>
.shop-card
  width: 100%
  width: 700px
  height: 100%
  max-height: 310px

</style>
