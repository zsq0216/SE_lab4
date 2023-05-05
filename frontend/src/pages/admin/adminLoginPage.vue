<template>
  <div class="bg">
    <div class="q-pa-md" style="max-width: 400px">

      <q-form @submit="onSubmit" @reset="onReset" class="q-gutter-md">
        <q-input filled v-model="username" label="请输入管理员账号 *" hint="Admin account" lazy-rules
          :rules="[val => val && val.length > 0 || 'Please type something']" />

        <q-input v-model="password" label="请输入密码" filled :type="isPwd ? 'password' : 'text'" hint="Password">
          <template v-slot:append>
            <q-icon :name="isPwd ? 'visibility_off' : 'visibility'" class="cursor-pointer" @click="isPwd = !isPwd" />
          </template>
        </q-input>

        <!-- <q-toggle v-model="accept" label="I accept the license and terms" /> -->

        <div>
          <q-btn label="Submit" color="primary" to="/admin" type="submit" />
          <q-btn label="Reset" type="reset" color="primary" flat class="q-ml-sm" />
        </div>
      </q-form>

    </div>
  </div>
</template>


<script>
// import { useQuasar } from 'quasar'
import { ref } from 'vue'

export default {
  setup() {
    // const $q = useQuasar()

    const username = ref(null)
    const password = ref(null)
    const accept = ref(false)

    function linkClick(e, go) {
      e.preventDefault() // we choose when we navigate

      // console.log('triggering navigation in 2s')
      setTimeout(() => {
        // console.log('navigating as promised 2s ago')
        go()
      }, 2000)
    }


    function onSubmit() {
      if (password.value === '123456') {
        // 密码正确，进行跳转
        linkClick();
      } else {
        // 密码不正确，给出错误提示
        $q.notify({
          color: 'red-5',
          textColor: 'white',
          icon: 'warning',
          message: 'Password is incorrect'
        })
      }
    }


    return {
      username,
      password: ref(''),
      isPwd: ref(true),
      accept,

      onSubmit,
      // linkClick,

      onReset() {
        username.value = null
        password.value = null
        accept.value = false
      }
    }
  }
}
</script>


<style scoped>
.bg {
  /* background: linear-gradient(to right, rgb(239, 235, 244), rgb(239, 205, 255)); */
  position: fixed;
  height: 100%;
  width: 100%;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
