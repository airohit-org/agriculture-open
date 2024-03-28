<script setup>
import svgIcon from '@/components/svg-icon/index.vue'
  const props=defineProps({
    icon: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    }
  })

  const render=(h, context)=> {
    const { icon, title } = context.props
    const vnodes = []
    if (icon) {
      vnodes.push(<svg-icon icon-class={icon}/>)
    }

    if (title) {
      if (title.length > 5) {
        vnodes.push(<span slot='title' title={(title)}>{(title)}</span>)
      } else {
        vnodes.push(<span slot='title'>{(title)}</span>)
      }
    }
    return vnodes
  }

</script>
