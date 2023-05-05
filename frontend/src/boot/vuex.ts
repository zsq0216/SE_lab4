import { boot } from 'quasar/wrappers';
import { InjectionKey } from 'vue';
import {
  createStore,
  Store as VuexStore,
  useStore as vuexUseStore,
} from 'vuex';

// Define your own store structure
interface State {
  username: string;
  userId: number;
  shopId: number;
  token: string | null;
}

// Provide typings for `this.$store`
declare module '@vue/runtime-core' {
  interface ComponentCustomProperties {
    $store: VuexStore<State>;
  }
}

// provide typings for `useStore` helper
export const storeKey: InjectionKey<VuexStore<State>> = Symbol('vuex-key');

const store = createStore<State>({
  state() {
    return {
      username: '',
      userId: 0,
      shopId: 0,
      token: null,
    };
  },
  mutations: {
    setUsername(state, username: string) {
      state.username = username;
    },
    setUserId(state, userId) {
      state.userId = userId;
    },
    setShopId(state, shopId) {
      state.shopId = shopId;
    },
    SET_TOKEN(state, token) {
      state.token = token;
    },
  },
  getters: {
    isAuthenticated: (state) => {
      return !!state.token && !!state.userId;
    },
  },

  });

export default boot(({ app }) => {
  app.use(store, storeKey);
});

export function useStore() {
  return vuexUseStore(storeKey);
}
