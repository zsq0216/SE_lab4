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
          <q-item clickable target="_blank" rel="noopener">
            <q-item-section avatar>
              <q-icon name="perm_identity" />
            </q-item-section>
            <q-item-section> Admin </q-item-section>
          </q-item>
        </router-link>
        <q-item clickable target="_blank" rel="noopener">
          <q-item-section avatar>
            <q-icon name="verified_user" to="/admin/pass" />
          </q-item-section>
          <q-item-section> Pass </q-item-section>
        </q-item>
        <router-link to="/admin/reject" class="router-link-class">
          <q-item clickable target="_blank" rel="noopener">
            <q-item-section avatar>
              <q-icon name="edit_off" />
            </q-item-section>
            <q-item-section> Reject </q-item-section>
          </q-item>
        </router-link>
        <q-item>
          <q-item-section>
            <img src="../../assets/log.png" alt="" />
          </q-item-section>
        </q-item>
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>

    <div class="q-pa-md">
      <q-table title="PASS 已通过申请" :rows="rows" :columns="columns" row-key="name" />
    </div>
  </q-layout>
</template>


<script>
import { ref } from 'vue'
import axios from 'axios'
import { onMounted } from 'vue'
import { useQuasar } from 'quasar'

const columns = [
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
  { name: 'registrationTime', label: '申请时间 YYYY-MM-DD', field: 'registrationTime', sortable: false }
]

const rows = ref([])

export default {
  setup() {
    const q = useQuasar();
    const leftDrawerOpen = ref(false)
    const rightDrawerOpen = ref(false)
    const selected = ref([])
    const axiosInstance = axios.create({
      baseURL: 'http://120.46.154.28:9999',
    });
    const username = ref('admin')
    onMounted(() => {
      axiosInstance.post('/shop/showByStatus1').then((response) => {
        const r = response.data['data']
        console.log(r);
        r.forEach(function (item) {
          delete item.id;
        });
        r.map(obj => {
          const [year, month, day] = obj.registrationTime;
          obj.registrationTime = `${year}-${month}-${day}`;
          return obj;
        });
        rows.value.splice(0, rows.value.length, ...r);
        console.log(rows.value)

      });
    });

    return {
      updata() {
        axiosInstance.post('/shop/showByStatus1').then((response) => {
          const r = response.data['data']
          console.log(r);
          r.forEach(function (item) {
            delete item.id;
          });
          r.map(obj => {
            const [year, month, day] = obj.registrationTime;
            obj.registrationTime = `${year}-${month}-${day}`;
            return obj;
          });
          rows.value.splice(0, rows.value.length, ...r);
          console.log(rows.value)
        });
        instance.proxy.$forceUpdate();
      },
      selected,
      columns,
      rows,
      leftDrawerOpen,
      q,
      toggleLeftDrawer() {
        leftDrawerOpen.value = !leftDrawerOpen.value
      },

      rightDrawerOpen,
      toggleRightDrawer() {
        rightDrawerOpen.value = !rightDrawerOpen.value;
      },
      toggle() {
        q.dark.toggle();
      },

      drawer: ref(false),
      drawerR: ref(false),
      layout: ref(false),
      username,

      getSelectedString() {
        return selected.value.length === 0 ? '' : `${selected.value.length} record${selected.value.length > 1 ? 's' : ''} selected of ${rows.value.length}`
      }

    }
  }
}
</script>
<style>
.router-link-class {
  color: inherit;
  text-decoration: none;
}
</style>
