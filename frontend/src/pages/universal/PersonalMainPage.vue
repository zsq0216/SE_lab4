<template>
    <q-layout view="hHh lpR fFf">

        <q-header elevated class="text-grey-8" height-hint="64">
            <q-toolbar class="GNL__toolbar">
                <q-btn flat dense round @click="toggleLeftDrawer" aria-label="Menu" icon="menu" class="q-mr-sm" />

                <q-toolbar-title v-if="$q.screen.gt.xs" shrink class="row items-center no-wrap">
                    <!-- <img src="https://cdn.quasar.dev/img/layout-gallery/logo-google.svg"> -->
                    <span class="q-ml-sm">{{ headerLabel }}</span>
                </q-toolbar-title>

                <q-item clickable target="_blank" rel="noopener" @click="toggle">
                    <q-item-section>Dark Mode</q-item-section>
                </q-item>

                <q-space />

                <!-- quasar布局模板提供的顶部搜索栏，留待后用 -->
                <!-- <q-input class="GNL__toolbar-input" outlined dense v-model="search" color="bg-grey-7 shadow-1"
                    placeholder="Search for topics, locations & sources">
                    <template v-slot:prepend>
                        <q-icon v-if="search === ''" name="search" />
                        <q-icon v-else name="clear" class="cursor-pointer" @click="search = ''" />
                    </template>
                    <template v-slot:append>
                        <q-btn flat dense round aria-label="Menu" icon="arrow_drop_down">
                            <q-menu anchor="bottom end" self="top end">
                                <div class="q-pa-md" style="width: 400px">
                                    <div class="text-body2 text-grey q-mb-md">
                                        Narrow your search results
                                    </div>

                                    <div class="row items-center">
                                        <div class="col-3 text-subtitle2 text-grey">
                                            Exact phrase
                                        </div>
                                        <div class="col-9 q-pl-md">
                                            <q-input dense v-model="exactPhrase" />
                                        </div>

                                        <div class="col-3 text-subtitle2 text-grey">
                                            Has words
                                        </div>
                                        <div class="col-9 q-pl-md">
                                            <q-input dense v-model="hasWords" />
                                        </div>

                                        <div class="col-3 text-subtitle2 text-grey">
                                            Exclude words
                                        </div>
                                        <div class="col-9 q-pl-md">
                                            <q-input dense v-model="excludeWords" />
                                        </div>

                                        <div class="col-3 text-subtitle2 text-grey">
                                            Website
                                        </div>
                                        <div class="col-9 q-pl-md">
                                            <q-input dense v-model="byWebsite" />
                                        </div>

                                        <div class="col-12 q-pt-lg row justify-end">
                                            <q-btn flat dense no-caps color="grey-7" size="md" style="min-width: 68px;"
                                                label="Search" v-close-popup />
                                            <q-btn flat dense no-caps color="grey-7" size="md" style="min-width: 68px;"
                                                @click="onClear" label="Clear" v-close-popup />
                                        </div>
                                    </div>
                                </div>
                            </q-menu>
                        </q-btn>
                    </template>
                </q-input> -->

                <div class="q-gutter-sm row items-center no-wrap">
                    <!-- <q-btn flat round dense icon="update" class="q-mr-xs" @click="updata"></q-btn> -->
                    <!-- 消息提示，留作后用：商户接收用户信息&管理员流程批准信息&用户接收商户信息 -->
                    <!-- <q-btn round dense flat color="grey-8" icon="notifications">
                        <q-badge color="red" text-color="white" floating>
                            2
                        </q-badge>
                        <q-tooltip>Notifications</q-tooltip>
                    </q-btn> -->
                    <q-btn round flat>
                        <q-avatar size="26px">
                            <!-- 待修改：用户头像（和商品图片同样流程从数据库里抽取） -->
                            <img :src=Imgurl>
                        </q-avatar>
                        <q-tooltip style="position: center;">
                            {{ username }}
                        </q-tooltip>
                        <q-space />
                    </q-btn>
                </div>

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

                    <q-item class="GNL__drawer-item" :to="isMerchant ? '/merchantpage' : '/user'" exact
                        style="margin-top: 3%;">
                        <q-item-section avatar>
                            <q-icon name="font_download" />
                        </q-item-section>

                        <q-item-section>
                            返回主页
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

        <q-page-container style="display: flex; margin-left: 2%;">
            <!-- TODO：表单默认值 请求数据库内原有值 -->
            <!-- TODO：debug规则判断（setup语法糖问题） -->
            <!-- TODO：链接后端：判断xx是否存在，对应错误码 -->
            <div v-if="showAccountInfo" style="width: 100%;">
                <h5
                    style="margin-left: 1%; line-height: 5px; font-weight: 500; font-size: 30px; font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;">
                    Public profile</h5>

                <q-separator inset class="q-my-sm" style="width: 98%; margin-left: 1%;" />

                <div class="profile-display-box justify-between">

                    <div class="personalInfo-display-change" style="max-width: 100%; width: 70%;">
                        <div class="textInfo" style="max-width: 100%; width: 100%;">
                            <q-form ref="myForm" action="http://120.46.154.28:9999/user" method="post"
                                @submit="postProfileUpdate" @reset="onReset" class="q-gutter-md">
                                <dl>
                                    <dt>
                                        <label class="label" for="userName">Name</label>
                                    </dt>
                                    <dd>
                                        <q-input filled name="username" v-model="username" hint="您在本网站显示的昵称" :rules="[
                                            (val) => (val && val.length > 0) || '请输入您的昵称',
                                            (val) => code != 20004 || '该昵称已存在',
                                        ]" @click="code = 0" :disable="viewOnly" />
                                    </dd>
                                </dl>

                                <dl>
                                    <dt>
                                        <label class="label" for="userPhone">Phone No.</label>
                                    </dt>
                                    <dd>
                                        <q-input filled name="phone" v-model="phone" v-model:model-value="phone"
                                            hint="您绑定的手机号" lazy-rules :rules="[
                                                (val) => (val && val.length > 0) || '请输入您的手机号',
                                                (val) => val.length === 11 || '请输入正确的手机号',
                                                (val) => code != 20005 || '手机已被注册过',
                                            ]" @click="code = 0" :disable="viewOnly" />
                                    </dd>
                                </dl>

                                <dl>
                                    <dt>ID Card No.</dt>
                                    <dd>
                                        <q-input filled name="idNumber" v-model="idNumber" hint="您绑定的身份证号（不可修改）" disable />
                                    </dd>
                                </dl>

                                <dl>
                                    <dt>
                                        <label class="label" for="userEmail">Public email</label>
                                    </dt>
                                    <dd>
                                        <q-input filled name="email" v-model="email" hint="您绑定的电子邮箱" lazy-rules :rules="[
                                            (val) => (val && val.length > 0) || '请输入您的邮箱地址',
                                            (val) => validateEmailDomain(val) || 'Invalid email domain',
                                            (val) => /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(val) || 'Invalid email',
                                            code != 20007 || '邮箱已被注册过',
                                        ]" @click="code = 0" :disable="viewOnly" />
                                    </dd>
                                </dl>

                                <!-- <div class="merchantShow" v-if="isMerchant">
                                    <dl>
                                        <dt>
                                            <label class="label" for="shop">Shop</label>
                                        </dt>
                                        <dd>
                                            <q-input class="username-inputbox" filled v-model="shop" label="Filled"
                                                name="shop" hint="您的商铺名称" disable />
                                        </dd>
                                    </dl>

                                    <dl>
                                        <dt>
                                            <label class="label" for="userAddress">Your address</label>
                                        </dt>
                                        <dd>
                                            <q-input filled name="email" v-model="address" hint="您的备案地址" disable />
                                        </dd>
                                    </dl>

                                    <dl>
                                        <dt>
                                            <label class="label" for="shop-intro">Shop Introduction</label>
                                        </dt>
                                        <dd>
                                            <q-input class="username-inputbox" filled v-model="introduce" label="Filled"
                                                name="shopIntro" hint="您的商铺简介" disable />
                                        </dd>
                                    </dl>
                                </div> -->
                                <div class="button-box">
                                    <div v-if="viewOnly" class="edit-button">
                                        <q-btn color="secondary" label="修改信息" @click="viewEditModeChange" />
                                    </div>

                                    <div v-if="!viewOnly" class="submit-button">
                                        <q-btn color="secondary" label="提交修改" @click="postProfileUpdate" />
                                    </div>
                                    <q-tooltip v-if="editNotification" anchor="bottom middle" self="top middle"
                                        :offset="[10, 10]">
                                        <strong>{{ editMsg }}</strong>
                                        (<q-icon name="keyboard_arrow_down" />)
                                    </q-tooltip>
                                </div>

                            </q-form>

                        </div>
                    </div>

                    <div class="profilePicture">
                        <dl>
                            <dt>
                                <label class="label" for="userName">Profile picture</label>
                            </dt>
                            <dl>
                                <div class="profile-img">
                                    <q-img :src="Imgurl" style="border-radius: 50%;" />
                                </div>
                                <div class="profile-edit">
                                    <q-btn text-color="#000" icon="edit" label="Edit" border-radius="6px"
                                        style="background: #fffbf0; color: black" />
                                </div>
                            </dl>
                        </dl>
                    </div>
                </div>
            </div>

            <div v-if="showAccountAmount" style="width: 100%">
                <div class="q-pa-md row items-start q-gutter-md"
                    style="max-width: 100%; width: 70%; margin-left: 5%; margin-top: 5%;">
                    <q-card class="my-card" flat bordered>

                        <q-card-section>
                            <div class="text-overline text-orange-9">Wallet</div>
                            <div class="text-h5 q-mt-sm q-mb-xs">余额：¥{{ balance }}</div>
                        </q-card-section>

                        <q-card-actions>
                            <!-- <q-btn flat color="dark" label="充值" @click="topUpAccount" /> -->
                            <div class="cursor-pointer" style="width: 100px; margin-left: 10%;">
                                充值
                                <q-popup-edit v-model="label" auto-save>
                                    <q-input v-model="money" hint="请输入充值金额" dense autofocus counter
                                        @keyup.enter="topUpAccount" />
                                </q-popup-edit>
                            </div>

                            <q-space />

                            <q-btn color="grey" round flat dense
                                :icon="expanded ? 'keyboard_arrow_up' : 'keyboard_arrow_down'"
                                @click="expanded = !expanded" />
                        </q-card-actions>

                        <q-slide-transition>
                            <div v-show="expanded">
                                <q-separator />
                                <q-table :rows="transitionRows" :columns="transitionCols" row-key="transferName"
                                    style="margin-top: 2%; margin-left: 1%;" />
                            </div>
                        </q-slide-transition>
                    </q-card>

                    <q-dialog v-model="layout" width>
                        <q-layout view="Lhh lpR fff" container class="bg-white">
                            <q-header class="bg-primary">
                                <q-toolbar>
                                    <q-btn flat @click="drawer = !drawer" round dense icon="menu" />
                                    <q-toolbar-title>User Sign in</q-toolbar-title>
                                    <q-btn flat @click="drawerR = !drawerR" round dense icon="menu" />
                                    <q-btn flat v-close-popup round dense icon="close" />
                                </q-toolbar>
                            </q-header>

                            <q-drawer bordered v-model="drawer" :width="200" :breakpoint="600" class="bg-grey-3 q-pa-sm">
                                <div v-for="n in 50" :key="n">Drawer {{ n }} / 50</div>
                            </q-drawer>

                            <q-drawer side="right" bordered v-model="drawerR" :width="200" :breakpoint="300"
                                class="bg-grey-3 q-pa-sm">
                                <div v-for="n in 50" :key="n">Drawer {{ n }} / 50</div>
                            </q-drawer>

                            <q-page-container>
                                <q-page padding>
                                    <q-form ref="myForm" action="http://120.46.154.28:9999/user" method="post"
                                        @submit="onSubmit" @reset="onReset" class="q-gutter-md">
                                        <br />
                                        <q-input></q-input>

                                        <div class="submit">
                                            <q-btn label="Submit" type="submit" color="primary" />
                                            <q-btn label="Reset" type="reset" color="primary" flat class="q-ml-sm" />
                                        </div>
                                    </q-form>
                                </q-page>
                            </q-page-container>
                        </q-layout>
                    </q-dialog>
                </div>

                <!-- TODO -->
            </div>
            <div v-if="showSettings">
                <h1>设置内容</h1>
                <!-- TODO -->
                <!-- 目前设想：字号大小、颜色主题（深色模式可以放到这里） -->
            </div>

            <q-page-scroller position="bottom-right" :scroll-offset="150" :offset="[18, 18]">
                <q-btn fab icon="keyboard_arrow_up" color="accent" />
            </q-page-scroller>
        </q-page-container>
    </q-layout>
</template>

<script setup>
import { ref } from 'vue'
import { onMounted } from 'vue'
import { useQuasar } from 'quasar'
import { useStore } from 'src/store'
import PSL from 'psl';
import axios from 'axios'

const axiosInstance = axios.create({
    baseURL: 'http://120.46.154.28:9999',
});
let response_register = ref(null)
let code = ref(null)

const leftDrawerOpen = ref(true)
const q = useQuasar()
const store = useStore()

// 功能变量
// url: swy GitHub的头像，以下网页链接是GitHub“储存”我的头像的链接
let Imgurl = ref('https://avatars.githubusercontent.com/u/105032850?s=400&u=285d7d130058e413bb8797cb52bc10f75c343076&v=4')

// 对应渲染
let headerLabel = ref(null)
let showAccountInfo = ref(true)
let showAccountAmount = ref(false)
let showSettings = ref(false)
let isMerchant = ref(false)
let viewOnly = ref(true)        // 禁用/开放个人信息修改
let expanded = ref(false)   // 左侧栏开闭变量
let editNotification = ref(false)
let editMsg = ref(null)

let myForm = ref(null)

let userId = localStorage.getItem('userId')
let username = ref(null)
let pre_username = ref(null)
let phone = ref(null)
let pre_phone = ref(null)
let email = ref(null)
let pre_email = ref(null)
let password = ref(null)
let idNumber = ref(null)
let roleId = ref(null)
let money = ref(null)
let balance = ref(null)

// 流水相关变量
let transitionCols = [
    { name: 'transferName', label: '转账人', field: 'transferName' },
    { name: 'receiveName', label: '收款人', field: 'receiveName' },
    { name: 'amount', label: '金额（¥）', field: 'amount' },
]
let transitionRows = ref([])

// 左侧边栏内项目icon-text列举
const links1 = [
    { icon: 'person', text: '个人账户' },
    { icon: 'star_border', text: '资金信息' }
]

onMounted(() => {
    console.log('userid: ' + userId)
    axiosInstance.post('/user/getUserInfo', {
        id: userId,
    }).then(response => {
        const r = response.data['data']
        console.log(r)
        username.value = r.username
        pre_username.value = r.username

        phone.value = r.phone
        pre_phone.value = r.phone

        email.value = r.email
        pre_email.value = r.email

        roleId.value = r.roleId
        password.value = r.password
        idNumber.value = r.idNumber

        if (roleId.value === 3) {
            isMerchant.value = true;
        } else {
            isMerchant.value = false;
        }
        console.log('Get user info\nusername: ' + username.value +
            '\nphone: ' + phone.value +
            '\nid number: ' + idNumber.value +
            '\nEmail: ' + email.value +
            '\nrole: ' + roleId.value +
            '\nisMerchant:' + isMerchant.value)
    });
    getAccount();
    console.log('balance: ', balance.value)
    getTransition()
});

// 深色模式
function toggle() {
    q.dark.toggle();
}

// 暂禁用
// 左侧边栏开闭
function toggleLeftDrawer() {
    leftDrawerOpen.value = !leftDrawerOpen.value
}

// 侧边栏对应渲染：分别设置布尔值 分情况讨论
function handleClick(link) {
    if (link.icon === 'person') {
        this.showDefault = false
        this.showAccountInfo = true
        this.showAccountAmount = false
        this.showSettings = false
        this.headerLabel = '个人信息'
    } else if (link.icon === 'star_border') {
        this.showDefault = false
        this.showAccountInfo = false
        this.showAccountAmount = true
        this.showSettings = false
        this.headerLabel = '您的账户'
    }
    else if (link.icon === 'settings') {
        this.showDefault = false
        this.showAccountInfo = false
        this.showAccountAmount = false
        this.showSettings = true
        this.headerLabel = '设置'
    }
    else {
        this.showDefault = true
        this.showAccountInfo = false
        this.showAccountAmount = false
        this.showSettings = false
    }
}

function viewEditModeChange() {
    viewOnly.value = false
    console.log(viewOnly)
    editNotification.value = false
}

// 检查充值金额填写
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

function validateEmailDomain(val) {
    // 使用PSL方法检查邮箱域名是否合法，需要安装PSL包
    const domain = val.split('@')[1]
    const parsed = PSL.parse(domain)
    return !!parsed && !!parsed.domain
}

// 表单提交（总体设置成一个表单，随时修改）
// TODO：与后端链接，提交判断（是否与提交之前相同）
function postProfileUpdate() {
    if (username.value === pre_username.value && phone.value === pre_phone.value && email.value === pre_email.value) {
        editNotification.value = true
        editMsg.value = '您未更新个人信息！'
        console.log('Havent update anything!')
    } else {
        axiosInstance.put('/user/update', {
            id: userId,
            username: username.value,
            password: password.value,
            idNumber: idNumber.value,
            email: email.value,
            phone: phone.value,
            roleId: roleId.value,
        }).then((response) => {
            console.log('Response data:', response.data);
            response_register.value = response.data;
            console.log(response_register);
            code.value = response_register.value['code'];
            editNotification.value = true
            editMsg.value = response_register.value['message']
            if (code.value === 20000) {
                viewOnly.value = 1
            }
            console.log('Push success. ', 'Modify success:', viewOnly.value);
            console.log('Edit profile\nusername:', username.value, '\nphone:', phone.value, '\nemail:', email.value)
            console.log('Edit notification: ', editNotification.value, 'Edit message: ', editMsg.value)
        }).catch((error) => {
            console.error('Error:', error);
            // 处理错误
            editNotification.value = true
            editMsg.value = response_register.value['message']
            console.log('(error) Edit notification: ', editNotification.value, 'Edit message: ', editMsg.value)
        });
    }
}

function getAccount() {
    axiosInstance.post('user/getAccount', {
        id: userId,
    }).then((response) => {
        response_register.value = response['data']
        console.log('get account: ', response_register.value)
        balance.value = response_register.value['data']
        console.log('balance: ', balance.value)
        code.value = response_register.value['code']
    }).catch((error) => {
        console.error('Error: ', error);
    })
}

// 充值操作 触发：Enter
function topUpAccount() {
    console.log('top up money is: ', money.value)
    console.log('userid is: ', userId)
    axiosInstance.put(`/user/topUpAccount?userId=${userId}&money=${money.value}`
    ).then((response) => {
        console.log('Response data:', response.data);
        response_register.value = response.data;
        console.log(response_register);

        code.value = response_register.value['code'];
        balance.value = response_register.value['data'];

        if (code.value == 20000) {
            // showTopUpNotification()
            console.log()
        }
    }).catch((error) => {
        console.error('Error:', error);
        // 处理错误
        // myForm.value.resetValidation();
    });
}

// 获取个人账户的流水
function getTransition() {
    transitionRows.value = []
    console.log('current userid(transition): ', userId)
    axiosInstance.post('/transferRecords/user', {
        id: userId
    }, {
        params: {
            id: userId
        }
    }).then((response) => {
        const r = response.data['data']
        console.log('get user transfer records: ', r);
        r.forEach(function (item) {
            transitionRows.value.push(item);
        });
    });
    console.log('transition: ', transitionRows.value)
}

</script>

<style lang="sass">
.GNL

    &__toolbar
        height: 64px

    &__toolbar-input
        width: 55%

    &__drawer-item
        line-height: 24px
        border-radius: 0 24px 24px 0
        margin-right: 12px

    .q-item__section--avatar
        .q-icon
            color: #5f6368

    .q-item__label
        color: #3c4043
        letter-spacing: .01785714em
        font-size: .875rem
        font-weight: 500
        line-height: 1.25rem

    &__drawer-footer-link
        color: inherit
        text-decoration: none
        font-weight: 500
        font-size: .75rem

    &:hover
        color: #000
</style>

<style scoped>
.personalInfo-display-change {
    display: flex;
    align-items: center;
    margin-left: 3%;
}

.profile-display-box {
    max-width: 100%;
    width: 100%;
    display: flex;
}

dt {
    margin: 0 0 6px;
    max-width: 100%;
    width: 100%;
}

dd {
    margin-left: 0%;
    max-width: 100%;
    width: 100%;
}

.label {
    display: flex;
    align-items: center;
    font-weight: 500;
    font-size: 18px;
}

.username-inputbox,
.phone-inputbox,
.email-inputbox,
.shop-inputbox,
.shopIntro-inputbox {
    max-width: 100%;
    width: 100%;
    height: 75px;
    font-size: 14px;
    font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
}

.profilePicture {
    margin-left: 7%;
    width: 50%;
    max-width: 100%;
    display: flex;
}

.note {
    font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
    font-size: smaller;
    color: #414141;
}

.submit-button button,
.edit-button button {
    fill: azure;
    background-color: rgba(127, 82, 190, 0.837);
    color: azure;
    cursor: pointer;
    border-radius: 6px;
    border: 0;
    display: flex;
    align-items: center;
    height: 32px;
    min-width: max-content;
    padding: 0 12px;
    margin-bottom: 10%;
}

.button-content {
    font-size: 15px;
    font-weight: 8px;
}

.profile-img {
    width: 230px;
    max-width: 100%;
    position: relative;
    z-index: 0;
}

.profile-edit {
    margin-top: -50px;
    position: relative;
    z-index: 1;
}

.my-card {
    width: 100%;
    max-width: 100%;
}
</style>
