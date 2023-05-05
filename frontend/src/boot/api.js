import axios from 'axios';
import store from '../store';

const api = axios.create({
  baseURL: 'http://localhost:9999',
});

api.interceptors.request.use(
  config => {
    const token = store.getters['auth/token'];
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => Promise.reject(error)
);

api.interceptors.response.use(
  response => response,
  error => {
    if (error.response.status === 401) {
      // token验证失败，跳转到登录页面
      router.push('/login');
    }
    return Promise.reject(error);
  }
);

export default api;
