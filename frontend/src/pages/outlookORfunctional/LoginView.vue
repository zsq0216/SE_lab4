<template>
  <div class="bg">
    <div class="box">
      <!-- 滑块版面，位于页面最上层 -->
      <div class="pre-box" ref="preBox">
        <h2>WELCOME</h2>
        <p>Website title</p>
        <div class="img-box">
          <img :src="flag == true ? imgList[0] : imgList[1]" alt="" />
        </div>
      </div>

      <!-- 商户登录版面（左侧） -->
      <div class="merchant-form">
        <div class="title-box">
          <h2><br />商户登录</h2>
        </div>

        <div class="input-box">
          <q-form class="q-gutter-md" @submit="register_merchant">
            <!-- <input name="username" v-model="name_r" type="text" placeholder="用户名" /> -->
            <q-input ref="name_merchant_r" filled name="username" v-model="name_r" label="姓名 *" hint="username" lazy-rules
              :rules="[
                  (val) => (val && val.length > 0) || '请输入您的用户名',
                  (val) => code_r != 20002 || '用户名或密码错误',
                  (val) => code_r != 20008 || '用户类错误',
                ]" @click="code_r = 0"></q-input>
            <!-- <br><br> -->
            <q-input ref="nameField" name="password" v-model="password_r" filled :type="isPwd ? 'password' : 'text'"
              label="密码 *" hint="Your password *" lazy-rules :rules="[
                  (val) => (val && val.length > 0) || '请输入您的密码',
                  (val) => code_r != 20002 || '用户名或密码错误',
                  (val) => code_r != 20008 || '用户类错误',
                ]" @click="code_r = 0">
              <template v-slot:append>
                <q-icon :name="isPwd ? 'visibility_off' : 'visibility'" class="cursor-pointer" @click="isPwd = !isPwd" />
              </template>
            </q-input>
            <div class="submit-user-login" style="display: flex">
              <q-btn @click="register_merchant" type="submit" label="登录" color="primary" />
              <p @click="mySwitch">我是用户，去登陆</p>
              <!-- <q-btn label="Reset" type="reset" color="primary" flat class="q-ml-sm" /> -->
            </div>
          </q-form>
        </div>

        <div class="btn-box">
          <br /><br />
          <!-- <button @click="register_consumer" class="login-btn">登陆</button> -->
          <!-- <p @click="mySwitch">我是商户，去登陆</p> -->
        </div>

        <div class="register">
          <div class="btn-box">
            <button class="register-btn" @click="layout = true">注册</button>
          </div>
          <br />
          <div class="admin">
            <router-link to="/adminLogin" style="
                        text-decoration: none;
                        display: flex;
                        font-size: 15px;
                        margin-top: 4px;
                      ">管理员入口</router-link>
          </div>
        </div>
      </div>

      <!-- 用户登录版面（右侧） -->
      <div class="user-form">
        <div class="title-box">
          <h2><br />用户登录</h2>
        </div>

        <div class="input-box">
          <q-form ref="consumer_r" class="q-gutter-md" @submit="register_consumer">
            <!-- <input name="username" v-model="name_r" type="text" placeholder="用户名" /> -->
            <q-input filled name="username" v-model.trim="name_r" label="姓名 *" hint="username" lazy-rules :rules="[
                (val) => (val && val.length > 0) || '请输入您的用户名',
                (val) => code_r != 20002 || '用户名或密码错误',
                (val) => code_r != 20008 || '用户类错误',
              ]" @click="code_r = 0"></q-input>
            <!-- <br><br> -->
            <q-input name="password" v-model="password_r" filled :type="isPwd ? 'password' : 'text'" label="密码 *"
              hint="Your password *" lazy-rules :rules="[
                  (val) => (val && val.length > 0) || '请输入您的密码',
                  (val) => code_r != 20002 || '用户名或密码错误',
                  (val) => code_r != 20008 || '用户类错误',
                ]" @click="code_r = 0">
              <template v-slot:append>
                <q-icon :name="isPwd ? 'visibility_off' : 'visibility'" class="cursor-pointer" @click="isPwd = !isPwd" />
              </template>
            </q-input>
            <div class="submit-user-login" style="display: flex">
              <q-btn @click="register_consumer" type="submit" label="登录" color="primary" />
              <p @click="mySwitch">我是商户，去登陆</p>
              <!-- <q-btn label="Reset" type="reset" color="primary" flat class="q-ml-sm" /> -->
            </div>
          </q-form>
        </div>

        <div class="btn-box">
          <br /><br />
          <!-- <button @click="register_consumer" class="login-btn">登陆</button> -->
          <!-- <p @click="mySwitch">我是商户，去登陆</p> -->
        </div>

        <div class="register">
          <div class="btn-box">
            <button @click="layout = true" class="register-btn">注册</button>
          </div>
          <div class="admin">
            <router-link to="/adminLogin" style="
                        text-decoration: none;
                        display: flex;
                        font-size: 15px;
                        margin-top: 4px;
                      ">管理员入口</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 注册按钮的对话框，点击弹出 -->
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

      <q-drawer side="right" bordered v-model="drawerR" :width="200" :breakpoint="300" class="bg-grey-3 q-pa-sm">
        <div v-for="n in 50" :key="n">Drawer {{ n }} / 50</div>
      </q-drawer>

      <q-page-container>
        <q-page padding>
          <q-form ref="myForm" action="http://120.46.154.28:9999/user" method="post" @submit="onSubmit" @reset="onReset"
            class="q-gutter-md">
            <br />
            <q-input filled name="username" v-model.trim="username" label="姓名 *" hint="userId" :rules="[
                (val) => (val && val.length > 0) || '请输入您的姓名',
                (val) => code != 20004 || '该昵称已存在',
              ]" @click="code = 0"></q-input>

            <q-input filled name="phone" v-model="phone" label="手机号码 *" hint="Your phone number" lazy-rules :rules="[
                (val) => (val && val.length > 0) || '请输入您的手机号',
                (val) => val.length === 11 || '请输入正确的手机号',
                (val) => code != 20005 || '手机已被注册过',
              ]" @click="code = 0" />
            <!-- 手机号的合法性，验证了位数: 11位 -->

            <q-input filled name="idNumber" v-model="idNumber" label="身份证号 *" hint="Your ID card number" lazy-rules
              :rules="[
                  (val) => (val && val.length > 0) || '请输入您的身份证号',
                  (val) =>
                    (val.length === 18 && checkIdNumber(idNumber)) ||
                    '请输入正确的身份证号',
                  (val) => code != 20006 || '身份证号已被注册过',
                ]" @click="code = 0" />
            <!-- 身份证号的合法性，验证了位数: 18位 -->

            <q-input filled name="email" v-model="email" label="邮箱 *" hint="Your e-mail address" lazy-rules :rules="[
                (val) => (val && val.length > 0) || '请输入您的邮箱地址',
                (val) => validateEmailDomain(val) || 'Invalid email domain',
                (val) =>
                  /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(
                    val
                  ) || 'Invalid email',
                (val) => code != 20007 || '邮箱已被注册过',
              ]" @click="code = 0" />
            <!-- 邮箱域名不合法: 显示 Invalid email domain -->
            <!-- 邮箱格式不合法（不是邮箱）: 显示 Invalid email -->
            <q-input name="password" v-model="password" filled :type="isPwd ? 'password' : 'text'" label="密码 *"
              hint="Your password *" lazy-rules :rules="[
                  (val) => (val && val.length > 5) || '请输入密码',
                  (val) =>
                    (val && val.length < 33) || '密码长度请设置在6-32位之间',
                  (val) =>
                    validatePassword(val) ||
                    '密码中应该至少包含数字、字母和特殊字符（-或_）中的至少两种',
                ]">
              <template v-slot:append>
                <q-icon :name="isPwd ? 'visibility_off' : 'visibility'" class="cursor-pointer" @click="isPwd = !isPwd" />
              </template>
            </q-input>
            <!-- 输入密码 要求长度6-32位之间，至少含有字母、数字、特殊字符（_ -）中的两种，并且使用password形式表示（展示为···） -->

            <q-input name="confirmPassword" v-model="confirmPassword" filled :type="isConPwd ? 'password' : 'text'"
              label="确认密码 *" hint="Confirm your password *" lazy-rules
              :rules="[(val) => passwordCheck(val) || '两次密码输入不一致']">
              <template v-slot:append>
                <q-icon :name="isConPwd ? 'visibility_off' : 'visibility'" class="cursor-pointer"
                  @click="isConPwd = !isConPwd" />
              </template>
            </q-input>
            <!-- 验证两次输入的密码是否一致 -->

            <q-select name="roleId" filled v-model="model" :options="options" label="请选择账户类型：商户/用户" />

            <q-toggle v-model="accept" label="我同意隐私条款" />

            <div class="submit">
              <q-btn label="Submit" type="submit" color="primary" />
              <q-btn label="Reset" type="reset" color="primary" flat class="q-ml-sm" />
            </div>
          </q-form>
        </q-page>
      </q-page-container>
    </q-layout>
  </q-dialog>
</template>

<script>
import { ref } from 'vue';
import icecream from '../../assets/imgs/ice-cream.png';
import swinging from '../../assets/imgs/swinging.png';
import { useQuasar } from 'quasar';
import PSL from 'psl';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useStore } from 'src/store';

export default {
  setup() {
    const code_r = ref(null);
    const preRef = ref('');
    const imgList = ref([icecream, swinging]);
    const flag = ref(true);
    const q = useQuasar();
    const router = useRouter();

    const submitting = ref(false);
    const axiosInstance = axios.create({
      baseURL: 'http://120.46.154.28:9999',
    });

    let username = ref(null);
    let shop = ref(null);
    let idNumber = ref(null);
    let phone = ref(null);
    let address = ref(null);
    let email = ref(null);
    let accept = ref(false);
    let password = ref(null);
    let confirmPassword = ref(null);
    const response_register = ref(null);
    const code = ref(null);

    const myForm = ref(null);
    const nameField = ref(null);
    const name_merchant_r = ref(null);
    const consumer_r = ref(null)

    const name_r = ref(null);
    const password_r = ref(null);
    const model = ref(null);
    const roleId = ref(null);
    const store = useStore();

    function simulateSubmit() {
      submitting.value = true;

      // Simulating a delay here.
      // When we are done, we reset "submitting"
      // Boolean to false to restore the
      // initial state.
      setTimeout(() => {
        // delay simulated, we are done,
        // now restoring submit to its initial state
        submitting.value = false;
      }, 3000);
    }

    const mySwitch = () => {
      if (flag.value) {
        preRef.value.style.background = 'rgb(231, 202, 244)';
        preRef.value.style.transform = 'translateX(100%)';
      } else {
        preRef.value.style.background = 'rgb(240, 233, 249)';
        preRef.value.style.transform = 'translateX(0%)';
      }
      flag.value = !flag.value;
    };

    const validateEmailDomain = (val) => {
      // 使用PSL方法检查邮箱域名是否合法，需要安装PSL包
      const domain = val.split('@')[1];
      const parsed = PSL.parse(domain);
      return !!parsed && !!parsed.domain;
    };

    const passwordCheck = (val) => {
      // 检查密码和确认密码中两次输入是否相同
      return val === password.value;
    };

    const validatePassword = (val) => {
      let count = 0;
      count += /[a-zA-Z]/.test(val) ? 1 : 0; // 包含字母
      count += /\d/.test(val) ? 1 : 0; // 包含数字
      count += /[-_]/.test(val) ? 1 : 0; // 包含特殊字符
      return count > 1;
    };

    function onReset() {
      username.value = null;
      accept.value = false;
      phone.value = null;
      idNumber.value = null;
      address.value = null;
      email.value = null;
      shop.value = null;
      password.value = null;
      confirmPassword.value = null;
    };

    return {
      code_r,
      model,
      options: ['merchant', 'consumer'],
      preRef,
      imgList,
      flag,
      mySwitch,
      username,
      phone,
      idNumber,
      q,
      password,
      confirmPassword,
      code,
      consumer_r,

      myForm,
      name_merchant_r,
      nameField,

      name_r,
      password_r,
      roleId,
      isPwd: ref(true),
      isConPwd: ref(true),

      // 假设修改昵称的方法名为 changeNickname
      async changeNickname() {
        // 修改昵称的代码

        // 手动将 code 置为 0，清除报错信息
        this.code = 0;
      },

      checkIdNumber(idNumber) {
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

        return checkCode === idNumber.charAt(17);
      },
      address,
      shop,
      email,

      submitting,
      simulateSubmit,

      layout: ref(false),

      drawer: ref(false),
      drawerR: ref(false),

      onSubmit() {
        if (model.value == 'merchant') roleId.value = 3;
        else roleId.value = 2;
        store.commit('setUsername', username.value);

        localStorage.setItem('username', username.value);
        var myVar = localStorage.getItem('username');
        console.log("localStorage.getItem('myVar')")
        console.log(myVar)

        console.log('注册：username');
        console.log(store.state.username);
        axiosInstance
          .post('/user', {
            username: username.value,
            password: password.value,
            idNumber: idNumber.value,
            email: email.value,
            phone: phone.value,
            roleId: roleId.value,
          })
          .then((response) => {
            console.log('Response data:', response.data);
            //处理返回数据
            //导航到另一个页面
            response_register.value = response.data;
            console.log(response_register);

            code.value = response_register.value['code'];

            if (code.value == 20000 && roleId.value == 3) {
              store.commit('setUserId', response_register.value['data']);
              localStorage.setItem('userId', response_register.value['data']);
              var myVar = localStorage.getItem('userId');
              console.log("localStorage.getItem('myVar')")
              console.log(myVar)

              router.push('/merchantpage');
              onReset();
            } else if (code.value == 20000 && roleId.value == 2) {
              store.commit('setUserId', response_register.value['data']);

              localStorage.setItem('userId', response_register.value['data']);
              var myVar = localStorage.getItem('userId');
              console.log("localStorage.getItem('myVar')")
              console.log(myVar)
              onReset();

              router.push('/user');
            }
            console.log(store.state.userId);

            myForm.value.resetValidation();
          })
          .catch((error) => {
            console.error('Error:', error);
            // 处理错误
            myForm.value.resetValidation();
          });
      },

      onReset() {
        username.value = null;
        accept.value = false;
        phone.value = null;
        idNumber.value = null;
        address.value = null;
        email.value = null;
        shop.value = null;
        password.value = null;
        confirmPassword.value = null;
      },

      validateEmailDomain,

      accept,
      passwordCheck,
      validatePassword,

      register_merchant() {
        console.log('register_merchant: name_r password_r username');
        console.log(name_r.value);
        console.log(password_r.value);
        store.commit('setUsername', name_r.value);
        console.log(store.state.username);
        localStorage.setItem('username', name_r.value);
        var myVar = localStorage.getItem('username');
        console.log("localStorage.getItem('myVar')")
        console.log(myVar)

        axiosInstance
          .post('/user/login', {
            username: name_r.value,
            password: password_r.value,
            roleId: 3,
          })
          .then((response) => {
            // 处理返回数据
            // 导航到另一个页面
            response_register.value = response.data;
            console.log('r_m_response: response_register');
            console.log(response_register);
            code_r.value = response_register.value['code'];

            nameField.value.validate();
            name_merchant_r.value.validate();

            if (code_r.value == 20000) {
              store.commit('setUserId', response_register.value['data']);
              localStorage.setItem('userId', response_register.value['data']);
              var myVar = localStorage.getItem('userId');
              console.log("localStorage.getItem('myVar')")
              console.log(myVar)
              console.log('userId');
              console.log(store.state.userId);
              router.push('/merchantpage');
            }
          })
          .catch(() => {
            console.log('error');
            // 处理错误
          });
      },
      register_consumer() {
        console.log('register_consumer: name_r password_r username');
        console.log(name_r.value);
        console.log(password_r.value);
        store.commit('setUsername', name_r.value);
        console.log(store.state.username);

        localStorage.setItem('username', name_r.value);
        var myVar = localStorage.getItem('username');
        console.log("localStorage.getItem('myVar')")
        console.log(myVar)

        axiosInstance
          .post('/user/login', {
            username: name_r.value,
            password: password_r.value,
            roleId: 2,
          })
          .then((response) => {
            //console.log('Response data:', response.data)
            // 处理返回数据
            // 导航到另一个页面
            response_register.value = response.data;
            console.log('response_register');
            console.log(response_register);
            code_r.value = response_register.value['code'];
            console.log(code_r);
            consumer_r.value.validate();
            nameField.value.validate();
            if (code_r.value == 20000) {
              store.commit('setUserId', response_register.value['data']);
              console.log(store.state.userId);

              localStorage.setItem('userId', response_register.value['data']);
              var myVar = localStorage.getItem('userId');
              console.log("localStorage.getItem('myVar')")
              console.log(myVar)

              router.push('/user');
            }
          })
          .catch((error) => {
            console.error('Error:', error);
            // 处理错误
          });
      },
    };
  },
  mounted() {
    this.q = useQuasar();
    this.preRef = this.$refs.preBox;
    const store = useStore()
    store.commit('setUserId', 0);

    localStorage.setItem('userId', 0);
    console.log("localStorage")
    console.log(localStorage.getItem('userId'))
  },
};
</script>

<style scoped>
.bg {
  background: linear-gradient(to right, rgb(239, 235, 244), rgb(239, 205, 255));
  position: fixed;
  height: 100%;
  width: 100%;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.box {
  width: 1050px;
  height: 600px;
  position: relative;
  /* background-color: rgb(255, 245, 240); */
  margin: auto;
  border-radius: 8px;
  border: 1px solid rgba(255, 255, 255, 0.6);
  box-shadow: 4px 4px 3px rgba(0, 0, 0, 0.1);
  display: flex;
}

.pre-box {
  width: calc(1050px / 2);
  height: 100%;
  left: 0;
  top: 0;
  position: absolute;
  background-color: rgb(240, 233, 249);
  border-radius: 8px;
  box-shadow: 4px 4px 3px rgba(0, 0, 0, 0.1);
  transition: 0.5s ease-in-out;
  z-index: 1000;
}

.pre-box h2 {
  margin-top: 100px;
  text-align: center;
  letter-spacing: 5px;
  text-shadow: 4px 4px 3px rgba(0, 0, 0, 0.1);
}

.pre-box p {
  height: 30px;
  line-height: 30px;
  text-align: center;
  margin: 20px 0;
  font-weight: bold;
  text-shadow: 4px 4px 3px rgba(0, 0, 0, 0.1);
}

.img-box {
  width: 350px;
  overflow: hidden;
  margin: 20px auto;
}

.img-box img {
  width: 100%;
  transition: 0.5s ease-in-out;
}

.user-form,
.merchant-form {
  flex: 1;
  height: 100%;
}

.title-box {
  height: 180px;
  line-height: 100px;
}

.title-box h2 {
  text-align: center;
  text-shadow: 4px 4px 3px rgba(0, 0, 0, 0.1);
  letter-spacing: 5px;
}

.input-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  /* top: 100px; */
  height: 200px;
  position: relative;
}

input {
  width: 250px;
  height: 40px;
  margin-bottom: 20px;
  text-indent: 7px;
  border-radius: 4px;
  border: 1px solid rgb(196, 192, 191);
}

input:focus {
  color: rgb(114, 9, 9);
}

input:focus::placeholder {
  opacity: 0;
}

.btn-box {
  display: flex;
  justify-content: center;
  height: 60px;
}

button {
  width: 60px;
  height: 30px;
  margin: 0 7px;
  margin: 0 7px;
  line-height: 30px;
  border: none;
  border-radius: 4px;
}

.login-btn {
  background-color: rgb(246, 157, 179);
  color: white;
}

button:hover {
  cursor: pointer;
  opacity: 0.8;
}

.btn-box p {
  height: 70px;
  line-height: 33px;
  margin-top: 20px;
  font-size: 14px;
}

.submit-user-login p .submit-merchant-login p {
  height: 70px;
  line-height: 40px;
  /* margin-top: 20px; */
  font-size: 14px;
}

.submit-user-login p:hover {
  cursor: pointer;
}

.submit-merchant-login p:hover {
  cursor: pointer;
}

.btn-box router-link {
  height: 120px;
  line-height: 40px;
  font-size: 14px;
}

.btn-box p:hover {
  cursor: pointer;
}

.register,
.admin_entry {
  display: flex;
  justify-content: center;
}

.register p {
  height: 30px;
  line-height: 5px;
  font-size: 14px;
  color: #841c34;
}

.register p:hover,
.admin_entry p:hover {
  cursor: pointer;
  /* border-bottom: 1px solid white; */
}

.register-btn {
  background-color: rgb(140, 0, 195);
  color: white;
}

.admin {
  display: flex;
  justify-content: center;
  height: 70px;
  color: white;
  width: 90px;
}

.admin router-link {
  text-decoration: none;
  text-align: center;
  font-size: 17px;
}
</style>
