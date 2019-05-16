export default {
  props: {
    parentItem: {
      type: Object,
      default: () => {}
    },
    theme: String,
    iconSize: Number
  },
  computed: {
    parentName () {
      return this.parentItem.routerName
    },
    children () {
      return this.parentItem.children
    },
    textColor () {
      return this.theme === 'dark' ? '#fff' : '#495060'
    }
  }
}
