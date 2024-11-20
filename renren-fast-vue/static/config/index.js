/**
 * 开发环境
 */
;(function () {
  window.SITE_CONFIG = {}

  // api接口请求地址
  // window.SITE_CONFIG['baseUrl'] = 'http://localhost:8080/renren-fast'
  // 将顺平家居生活-后台管理系统的请求基准 url，
  // 改动到网关, 这里带上/api 可以在网关进行断言
  // 说明是 renren-fast-vue 项目发送的请求
  window.SITE_CONFIG['baseUrl'] = 'http://localhost:5050/api'

  // cdn地址 = 域名 + 版本号
  window.SITE_CONFIG['domain'] = './' // 域名
  window.SITE_CONFIG['version'] = ''   // 版本号(年月日时分)
  window.SITE_CONFIG['cdnUrl'] = window.SITE_CONFIG.domain + window.SITE_CONFIG.version
})()
