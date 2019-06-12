<template>
  <Button v-if="showValue" :disabled="disabledValue" :type="buttonStyle" :loading="btnLoading">{{ buttonText }}</Button>
</template>

<script>
export default {
  name: 'ButtonCustom',
  components: {

  },
  props: {
    buttonStyle: {
      type: String
    },
    buttonText: {
      type: String
    },
    partType: {
      type: Number
    },
    btnLoading: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      disabledValue: false,
      showValue: true,
      loading: false
    }
  },
  provide () {
    return {

    }
  },
  watch: {
    partType (val) {
      const self = this
      if (val && val === 1) {
        self.disabledValue = true
      }
      if (val && val === 4) {
        self.showValue = true
      }
      if (val && val === 5) {
        self.showValue = false
      }
    },
    btnLoading (val) {
      const self = this
      self.loading = val
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      const self = this
      if (self.partType && self.partType === 1) {
        self.disabledValue = true
      }
      if (self.partType && self.partType === 4) {
        self.showValue = true
      }
      if (self.partType && self.partType === 5) {
        self.showValue = false
      }
    }
  }
}
</script>

<style lang="less">

</style>
