package UI
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.managers.PopUpManager;
	
	import spark.components.Button;
	
	/**
	 *业务列表菜单按钮 
	 * @author 马天翼
	 * 
	 */
	public class BusinessButton extends Button
	{
		private var businessName:String;
		
		public function BusinessButton(name:String)
		{
			super();
			this.businessName = name;
			this.label = name;
			this.percentWidth = 60;
			this.percentHeight = 20;
			this.setStyle("fontSize",24);
			this.addEventListener(MouseEvent.CLICK,businessClickHandler);
		}
		
		
		/**
		 * 业务按钮的监听器，弹出手机输入框
		 * @param event
		 * 
		 */
		private function businessClickHandler(event:MouseEvent):void{
			var queueMethod:ChooseQueueMethodMenu = new ChooseQueueMethodMenu();
			PopUpManager.addPopUp(queueMethod,this.parent,true);
			PopUpManager.centerPopUp(queueMethod);
		}
	}
}