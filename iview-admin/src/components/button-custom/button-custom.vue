<template>
  <Button v-if="showValue" :disabled="disabledValue" :type="buttonStyle">{{ buttonText }}</Button>
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
    }
  },
  data () {
    return {
      disabledValue: false,
      showValue: true
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
