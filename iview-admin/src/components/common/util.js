export const showTitle = (item, vm) => {
  console.log('showTitle item:',item)
  return vm.$config.useI18n ? vm.$t(item.routerName) : ((item.menuName) || item.routerName)
}
