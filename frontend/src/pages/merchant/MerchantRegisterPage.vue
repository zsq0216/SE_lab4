<template>
    <div class="bg">
        <div class="header">
            <q-layout view="hHh lpR fFf">
                <q-header elevated class="bg-deep-orange-2 text-white">
                    <q-toolbar>
                        <q-toolbar-title>
                            <q-avatar>
                                <img src="https://cdn.quasar.dev/logo-v2/svg/logo-mono-white.svg">
                            </q-avatar>
                            merchant register
                        </q-toolbar-title>
                    </q-toolbar>
                </q-header>
            </q-layout>
        </div>

        <div class="title">
            <h2>商户注册</h2>
        </div>

        <div class="q-pa-md" style="max-width: 600px">
            <br>
            <br>
            <br>
            <q-form @submit="onSubmit" @reset="onReset" class="q-gutter-md">
                <br>
                <q-input filled v-model="name" label="姓名 *" hint="Name and surname" lazy-rules
                    :rules="[val => val && val.length > 0 || '请输入您的姓名']" />

                <q-input filled v-model="phone_number" label="手机号码 *" hint="Your phone number" lazy-rules :rules="[
                    val => val && val.length > 0 || '请输入您的手机号',
                    val => val.length === 11 || '请输入正确的手机号'
                ]" />

                <q-input filled v-model="id_number" label="身份证号 *" hint="Your ID card number" lazy-rules :rules="[
                    val => val && val.length > 0 || '请输入您的身份证号',
                    val => val.length === 18 || '请输入正确的身份证号']" />


                <q-input filled v-model="address" label="联系地址 *" hint="Your address " lazy-rules
                    :rules="[val => val && val.length > 0 || '请输入您的收/发货地址']" />

                <q-input filled v-model="shop" label="店铺名称 *" hint="Your shop " lazy-rules
                    :rules="[val => val && val.length > 0 || '请输入您的店铺名称']" />

                <q-toggle v-model="accept" label="我同意隐私条款" />

                <div>
                    <q-btn label="Submit" type="submit" color="red-4" />
                    <q-btn label="Reset" type="reset" color="red-4" flat class="q-ml-sm" />
                </div>
            </q-form>
            <div class="switch">
                <router-link to="/userRegister" :style="{ textDecoration: 'none' }">
                    <p>我是用户</p>
                </router-link>
                <router-link to="/" :style="{ textDecoration: 'none' }">
                    <p>已有账户？去登录</p>
                </router-link>
            </div>

        </div>


        <q-page-container>
            <router-view />
        </q-page-container>

    </div>
</template>

<script>
import { useQuasar } from 'quasar'
import { ref } from 'vue'

export default {
    setup() {
        const $q = useQuasar()

        const name = ref(null)
        const id_number = ref(null)
        const phone_number = ref(null)
        const address = ref(null)
        const shop = ref(null)
        const accept = ref(false)

        return {
            name,
            phone_number,
            id_number,
            address,
            shop,
            accept,

            onSubmit() {
                if (accept.value !== true) {
                    $q.notify({
                        color: 'red-5',
                        textColor: 'white',
                        icon: 'warning',
                        message: 'You need to accept the license and terms first'
                    })
                }
                else {
                    $q.notify({
                        color: 'green-4',
                        textColor: 'white',
                        icon: 'cloud_done',
                        message: 'Submitted'
                    })
                }
            },

            onReset() {
                name.value = null
                accept.value = false
                phone_number.value = null
                id_number.value = null
                shop.value = null
                address.value = null
            }
        }
    }
}
</script>


<style scoped>
.bg {
    background: linear-gradient(to right, rgb(239, 223, 205), rgb(254, 195, 189));
    position: fixed;
    height: 100%;
    width: 100%;
    top: 0;
    left: 0;
    display: flex;
    justify-content: center;
    align-items: center;
}

.title {
    display: flex;
    width: 340px;
}

.title h2 {
    margin-top: 50px;
    text-align: center;
    letter-spacing: 5px;
    text-shadow: 4px 4px 3px rgba(0, 0, 0, .1);
    font-weight: normal;
}

.switch {
    justify-content: center;
    height: 120px;
    display: flex;
    flex-direction: column;
}

.switch p {
    font-size: 16px;
}

.switch p:hover {
    cursor: pointer;
}
</style>