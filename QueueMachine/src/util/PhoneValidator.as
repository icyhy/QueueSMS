package util
{
	import mx.validators.ValidationResult;
	import mx.validators.Validator;
	
	/**
	 *手机号验证 
	 * @author 马天翼
	 * 
	 */
	public class PhoneValidator extends Validator
	{
		protected static const cm:RegExp = /(^134[0-8]\d{7}$)|(^13[5-9]\d{8}$)|(^15[01789]\d{8}$)|(^188\d{8}$)|(^1349\d{7}$)/;
		protected static const cu:RegExp = /(^1349\d{7}$)|(^13[12]\d{8}$)|(^156\d{8}$)/;
		protected static const ct:RegExp = /(^1[35]3\d{8}$)|(^18[79]\d{8}$)/;
		private var _invalidFormat:String = "手机号格式错误";
		
		public function PhoneValidator()
		{
			super();
		}
		
		public function set invalidFormat(value:String):void{
			_invalidFormat = value;
		}
		
		public function get invalidFormat():String{
			return _invalidFormat;
		}
		
		/**
		 * 查看手机号是否是移动,联通,电信的 
		 * @param value
		 * @return 
		 * 
		 */
		private static function matchPhone(value:String):Boolean{
			if(cm.test(value))
				return true;
			if(cu.test(value))
				return true;
			if(ct.test(value))
				return true;
			return false;
		}
		
		/**
		 * 验证cn的手机号
		 * @param validator
		 * @param value
		 * @param baseField
		 * @return 
		 */
		public static function validatePhoneNumberCN(validator:PhoneValidator,
													 value:Object,
													 baseField:String):Array{
			var results:Array = [];
			if(!matchPhone(value.toString())){
				results.push(new ValidationResult(true,baseField,'手机号格式错误',validator.invalidFormat));
			}
			return results;
		}
		
		/**
		 * 覆盖Validator的验证方法
		 * @param value
		 * @return 
		 */
		override protected function doValidation(value:Object):Array{
			var results:Array = super.doValidation(value);
			var val:String = value ? String(value) : "";
			if (results.length > 0 || ((val.length == 0) && !required))
				return results;
			else
				return validatePhoneNumberCN(this, value, null);
		}
	}
}