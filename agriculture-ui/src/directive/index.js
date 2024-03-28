import hasRole from './permission/hasRole'
import hasPermi from './permission/hasPermi'
import copyText from './common/copyText'
import dialogDrag from './dialog/drag'

export default function directive(app){
  app.directive('hasRole', hasRole)
  app.directive('hasPermi', hasPermi)
  app.directive('dialogDrag', dialogDrag)
  app.directive('copyText', copyText)
}