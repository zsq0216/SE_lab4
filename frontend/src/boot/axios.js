// src/boot/axios.js

import axios from 'axios'

export default ({ app, router, store, ssrContext }) => {
  const axiosInstance = axios.create({
    baseURL: 'http://localhost:9999'
  })

  // 添加请求拦截器
  axiosInstance.interceptors.request.use(
    (config) => {
      // 在这里添加您的请求拦截器代码，例如添加请求头
      return config
    },
    (error) => {
      return Promise.reject(error)
    }
  )

  // 添加响应拦截器
  axiosInstance.interceptors.response.use(
    (response) => {
      // 在这里添加您的响应拦截器代码，例如处理响应数据
      return response
    },
    (error) => {
      return Promise.reject(error)
    }
  )

  // 将 axiosInstance 添加到 Vue 实例的全局属性中
  app.config.globalProperties.$axios = axiosInstance
}
